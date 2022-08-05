package de.sharpadogge.twitchbot.modules.bot.commands;

import de.sharpadogge.twitchbot.jooq.Tables;
import de.sharpadogge.twitchbot.modules.bot.BotClient;
import de.sharpadogge.twitchbot.modules.command.entity.UserCommand;
import de.sharpadogge.twitchbot.modules.integration.IntegrationService;
import de.sharpadogge.twitchbot.modules.integration.model.Integration;
import de.sharpadogge.twitchbot.modules.music.MusicRepository;
import de.sharpadogge.twitchbot.modules.music.entity.MusicYoutubeSettings;
import de.sharpadogge.twitchbot.modules.spotify.SpotifyAPI;
import de.sharpadogge.twitchbot.modules.spotify.model.Artist;
import de.sharpadogge.twitchbot.modules.spotify.model.ResultCurrentSong;
import de.sharpadogge.twitchbot.modules.spotify.model.ResultSearchTrack;
import de.sharpadogge.twitchbot.modules.youtube.YoutubeDownloader;
import de.sharpadogge.twitchbot.modules.youtube.model.YoutubeVideo;

import org.jooq.DSLContext;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class CommandExecutor {

    private final Logger log = LoggerFactory.getLogger(CommandExecutor.class);

    private final ChannelMessageEvent event;

    private final UserCommand command;

    private final BotClient client;

    private final DSLContext create;

    private final Map<String, String> temp = new HashMap<>();

    public CommandExecutor(ChannelMessageEvent event, UserCommand command, BotClient client) {
        this.event = event;
        this.command = command;
        this.client = client;
        this.create = BotClient.getContext().getBean(DSLContext.class);
    }

    public void executeAction(final String action) {
        log.info("Run action: " + action);
        final String actionName = action.split(" ")[0];
        if (actionName.equalsIgnoreCase("say")) {
            final String[] args = replaceVars(action.split(" ", 2));
            if (args.length == 2) {
                event.sendReply(replaceVars(args[1]));
            }
        }
        if (actionName.equalsIgnoreCase("set")) {
            final String[] args = replaceVars(action.split(" ", 3));
            if (args.length == 3) {
                temp.put(args[1], args[2]);
            }
        }
        if (actionName.equalsIgnoreCase("spotify.request")) {
            final String[] args = replaceVars(action.split(" ", 2));

            IntegrationService integrationService = BotClient.getContext().getBean(IntegrationService.class);
            Integration integration = integrationService.getUserIntegration(client.getUser().getId(), "spotify", true);

            SpotifyAPI api = BotClient.getContext().getBean(SpotifyAPI.class);

            ResultCurrentSong currentSong = api.getCurrentSong(integration.getAuth());
            if (currentSong != null && currentSong.getPlaying()) {
                ResultSearchTrack requestedSong = api.searchTrack(integration.getAuth(), args[1]);
                String songName = requestedSong.getTracks().getItems().get(0).getName();
                temp.put("song.name", songName);

                List<Artist> artists = requestedSong.getTracks().getItems().get(0).getArtists();
                StringBuilder artist = new StringBuilder(artists.get(0).getName());
                temp.put("song.artist", artist.toString());
                if (artists.size() > 1) {
                    int last = artists.size() - 1;
                    for (int i = 1; i < artists.size(); i++) {
                        if (i != last) {
                            artist.append(", ").append(artists.get(i).getName());
                        }
                        else {
                            artist.append(" und ").append(artists.get(i).getName());
                        }
                    }
                }

                Boolean success = api.addTrackToQueue(integration.getAuth(), requestedSong);
                if (success) {
                    temp.put("response", temp.getOrDefault("spotify.success", "/me '${song.name}' von ${song.artist} wurde der Warteschlange hinzugefügt [@${user}]"));
                } else {
                    temp.put("response", temp.getOrDefault("spotify.error", "/me Es sind schlimme Dinge passiert. Dein gewünschter Song konnte nicht hinzugefügt werden [@${user}]"));
                }
            }
            else {
                temp.put("response", temp.getOrDefault("spotify.paused", "/me Da momentan keine Musik abgespielt wird, können keine Songs requested werden [@${user}]"));
            }
        }
        if (actionName.equalsIgnoreCase("spotify.get")) {
            IntegrationService integrationService = BotClient.getContext().getBean(IntegrationService.class);
            Integration integration = integrationService.getUserIntegration(client.getUser().getId(), "spotify", true);

            SpotifyAPI api = BotClient.getContext().getBean(SpotifyAPI.class);

            ResultCurrentSong currentSong = api.getCurrentSong(integration.getAuth());
            if (currentSong != null && currentSong.getPlaying()) {

                final String songName = currentSong.getItem().getName();
                List<Artist> artists = currentSong.getItem().getArtists();
                StringBuilder artist = new StringBuilder(artists.get(0).getName());
                if (artists.size() > 1) {
                    int last = artists.size() - 1;
                    for (int i = 1; i < artists.size(); i++) {
                        if (i != last) {
                            artist.append(", ").append(artists.get(i).getName());
                        }
                        else {
                            artist.append(" und ").append(artists.get(i).getName());
                        }
                    }
                }
                temp.put("song.name", songName);
                temp.put("song.artist", artist.toString());
                temp.put("response", temp.getOrDefault("spotify.success", "/me Aktuell wird '${song.name}' von ${song.artist} gespielt"));
            } else {
                temp.put("response", temp.getOrDefault("spotify.paused", "/me Es wird aktuell kein Song abgespielt"));
            }
        }
        if (actionName.equalsIgnoreCase("spotify.skip")) {
            IntegrationService integrationService = BotClient.getContext().getBean(IntegrationService.class);
            Integration integration = integrationService.getUserIntegration(client.getUser().getId(), "spotify", true);

            SpotifyAPI api = BotClient.getContext().getBean(SpotifyAPI.class);

            boolean canSkip = api.skipSong(integration.getAuth());
            if (canSkip) {
                temp.put("response", "Skipping...");
                return;
            }
            temp.put("response", "Can't skip at the moment");
        }
        if (actionName.equalsIgnoreCase("yt.download")) {
            try {
                final String[] args = replaceVars(action.split(" ", 2));
                final String url = args[1];
                if (Pattern.compile("https?:\\/\\/(?:www\\.)?youtube\\.com\\/watch\\?v=[A-Za-z0-9_-]+$").matcher(url).matches()) {
                    final String videoId = url.replaceAll("^.+v=", "");
                    
                    YoutubeDownloader ytdl = new YoutubeDownloader();

                    YoutubeVideo video = ytdl.getVideo(videoId);
                    Long videoDurationMs = video.getDurationMs();

                    MusicRepository repo = BotClient.getContext().getBean(MusicRepository.class);
                    MusicYoutubeSettings settings = repo.getMusicYoutubeSettingsForUser(client.getUser().getId());
                    
                    boolean validDuration = settings.getMaxVideoLength() <= 0 ? true : videoDurationMs <= settings.getMaxVideoLength() * 1000;
                    if (validDuration) {
                        repo.addSongRequest(client.getUser().getId(), event.getActor().getNick(), video);
                        temp.put("response", "Song hinzugefügt: '" + video.details().title() + "' vom Channel '" + video.details().author() + "'");
                    }
                    else temp.put("response", "Zu langes Video (max. " + settings.getMaxVideoLength() + " Sekunden)");
                }
                else temp.put("response", "Ungültige URL");
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String replaceVars(String string) {
        // "static" variables
        string = string.replace("${user}", event.getActor().getNick());
        string = string.replace("${points}", getActivityPoints().toString());

        // command arguments
        if (command.getArgc() != 0) {
            String[] args = event.getMessage().split(" ", command.getArgc() + 1);
            if (args.length == command.getArgc() + 1) {
                for (int i = 1; i < command.getArgc() + 1; i++) {
                    string = string.replaceAll("\\$\\{" + i + "}", args[i]);
                }
            }
        }

        // dynamic variables
        for (String dynVar : temp.keySet()) {
            for (int i = 0; i < 10; i++) {
                string = string.replace("${" + dynVar + "}", temp.get(dynVar));
            }
        }

        return string;
    }

    public String[] replaceVars(String[] strings) {
        String[] out = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            out[i] = replaceVars(strings[i]);
        }
        return out;
    }

    public Integer getActivityPoints() {
        double points = .0;
        Optional<Double> pointsOpt = create.select(Tables.TWITCH_USER_STATS_OVERALL.ACTIVITY_POINTS)
                .from(Tables.TWITCH_USER_STATS_OVERALL)
                .where(Tables.TWITCH_USER_STATS_OVERALL.CHANNEL.eq(event.getChannel().getName()))
                .and(Tables.TWITCH_USER_STATS_OVERALL.USERNAME.eq(event.getActor().getNick()))
                .fetchOptionalInto(Double.class);

        if (pointsOpt.isPresent()) {
            points = pointsOpt.get();
        }
        return Integer.parseInt(Double.toString(points).replaceAll("\\.\\d+", ""));
    }
}
