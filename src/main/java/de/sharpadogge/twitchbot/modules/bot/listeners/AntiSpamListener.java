package de.sharpadogge.twitchbot.modules.bot.listeners;

import de.sharpadogge.twitchbot.modules.bot.BotClient;
import de.sharpadogge.twitchbot.modules.bot.EventListener;
import de.sharpadogge.twitchbot.modules.bot.data.TwitchBotDataHolder;
import de.sharpadogge.twitchbot.modules.spam.SupportedSpamFilter;
import de.sharpadogge.twitchbot.modules.spam.UserSpamFilterService;
import de.sharpadogge.twitchbot.modules.spam.entity.UserSpamFilter;
import de.sharpadogge.twitchbot.permission.PermissionUtils;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.feature.twitch.messagetag.Emotes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

public class AntiSpamListener extends EventListener {

    Map<String, SpamInfo> spamMap = new HashMap<>();

    private final List<String> bannedLinks = Arrays.asList(
            "xvideos.", "pornhub.", "xhamster.", "xnxx.", "youporn.", "hclips.", "porn.", "tnaflix.", "tube8.",
            "spankbang.", "drtuber.", "spankwire.", "keezmovies.", "nuvid.", "ixxx.", "sunporno.", "pornhd.",
            "porn300.", "pornone.", "sexvid.", "thumbzilla.", "zbporn.", "/fuq.", ".fuq.", "xxxbunker.", "3movs.",
            "cumlouder.", "xbabe.", "porndroids.", "alohatube.", "maturetube.", "tubev.", "4tube.", "bestfreetube.",
            "/shameless.", ".shameless.", "megatube.", "porntube.", "porndig.", "pornburst.", "bigporn.", "bobs-tube.",
            "redporn.", "pornrox.", "pornmaki.", "pornid.", "slutload.", "proporn.", "pornhost.", "xxxvideos247.",
            "handjobhub.", "dansmovies.", "porn7.", "forhertube.", "maxiporn.", "pornheed.", "orgasm.", "pornrabbit.",
            "tiava.", "/fux.", ".fux.", "h2porn.", "metaporn.", "pornxio.", "pornerbros.", "youjizz.",
            "iporntv.", "mobilepornmovies.", "watchmygf.", "pornplanner.", "mypornbible.", "landburschen", "gay", "lesbian",
            "sex", "porn", "hentai"
    );

    @Override
    public void onMessage(ChannelMessageEvent event) {
        final long userId = getClient().getUser().getId();
        UserSpamFilterService spamFilterService = BotClient.getContext().getBean(UserSpamFilterService.class);

        final String author = event.getActor().getNick();
        if (!spamMap.containsKey(author)) {
            spamMap.put(author, new SpamInfo());
        }
        spamMap.get(author).tick();

        Map<String, UserSpamFilter> filterSettingsMap = spamFilterService.getFiltersForUser(userId);
        Map<String, List<String>> exemptionMap = spamFilterService.getExemptions(userId);

        if (filterSettingsMap.containsKey(SupportedSpamFilter.BLACKLISTED_WORDS.getIdentifier())) {
            String filter = SupportedSpamFilter.BLACKLISTED_WORDS.getIdentifier();
            UserSpamFilter settings = filterSettingsMap.get(filter);
            if (settings.getActive()) {
                List<String> blacklistedWords = spamFilterService.getBlacklistedWords(userId);
                List<String> exemptions = exemptionMap.containsKey(filter) ? exemptionMap.get(filter) : new ArrayList<>();
                antiBlacklistWords(event, settings, exemptions, blacklistedWords);
            }
        }

        if (filterSettingsMap.containsKey(SupportedSpamFilter.CAPS_LOCK.getIdentifier())) {
            String filter = SupportedSpamFilter.CAPS_LOCK.getIdentifier();
            UserSpamFilter settings = filterSettingsMap.get(filter);
            if (settings.getActive()) {
                List<String> exemptions = exemptionMap.containsKey(filter) ? exemptionMap.get(filter) : new ArrayList<>();
                antiCapsLock(event, settings, exemptions);
            }
        }

        if (filterSettingsMap.containsKey(SupportedSpamFilter.SPECIAL_CHARS.getIdentifier())) {
            String filter = SupportedSpamFilter.SPECIAL_CHARS.getIdentifier();
            UserSpamFilter settings = filterSettingsMap.get(filter);
            if (settings.getActive()) {
                List<String> exemptions = exemptionMap.containsKey(filter) ? exemptionMap.get(filter) : new ArrayList<>();
                antiSpecialCharacters(event, settings, exemptions);
            }
        }

        if (filterSettingsMap.containsKey(SupportedSpamFilter.EMOTE_SPAM.getIdentifier())) {
            String filter = SupportedSpamFilter.EMOTE_SPAM.getIdentifier();
            UserSpamFilter settings = filterSettingsMap.get(filter);
            if (settings.getActive()) {
                List<String> exemptions = exemptionMap.containsKey(filter) ? exemptionMap.get(filter) : new ArrayList<>();
                antiEmote(event, settings, exemptions);
            }
        }

        if (filterSettingsMap.containsKey(SupportedSpamFilter.LINKS.getIdentifier())) {
            String filter = SupportedSpamFilter.LINKS.getIdentifier();
            UserSpamFilter settings = filterSettingsMap.get(filter);
            if (settings.getActive()) {
                List<String> exemptions = exemptionMap.containsKey(filter) ? exemptionMap.get(filter) : new ArrayList<>();
                antiLinks(event, settings, exemptions);
            }
        }
    }

    private void antiBlacklistWords(final ChannelMessageEvent event, final UserSpamFilter settings, final List<String> exemptions, final List<String> words) {
        if (PermissionUtils.checkCustomPermission(event, exemptions)) return;

        boolean found = false;
        for (final String word : words) {
            if (event.getMessage().toLowerCase().contains(word)) {
                found = true;
                break;
            }
        }
        if (found) {
            TwitchBotDataHolder.deleteMessagesFrom(event.getChannel().getName(), event.getActor().getNick());
            event.sendReply("/timeout " + event.getActor().getNick() + " " + settings.getTimeoutDuration() + " Bad words");
            if (!settings.getSilent()) {
                if (settings.getMessage() != null && settings.getMessage().length() != 0) {
                    event.sendReply(settings.getMessage() + " [@" + event.getActor().getNick() + "]");
                }
                else event.sendReply("Sowas sagt man aber nicht. [@" + event.getActor().getNick() + "]");
            }
        }
    }

    private void antiCapsLock(final ChannelMessageEvent event, final UserSpamFilter settings, final List<String> exemptions) {
        if (PermissionUtils.checkCustomPermission(event, exemptions)) return;

        int big = 0;
        int small = 0;
        final String message = event.getMessage();
        final String messageLettersOnly = message.replaceAll("[^A-z]", "");
        for (char c : messageLettersOnly.toCharArray()) {
            if (Character.isUpperCase(c)) big++;
            else small++;
        }
        float capsPercentage = (float) big / (big + small) * 100;
        if (capsPercentage >= 50) {
            spamMap.get(event.getActor().getNick()).capsCounter.add(LocalDateTime.now());
        }

        if (spamMap.get(event.getActor().getNick()).capsCounter.size() >= settings.getAllowedLimit()) {
            TwitchBotDataHolder.deleteMessagesFrom(event.getChannel().getName(), event.getActor().getNick());
            event.sendReply("/timeout " + event.getActor().getNick() + " " + settings.getTimeoutDuration() + " Zu hohe Nutzung von Caps-Lock");
            if (!settings.getSilent()) {
                if (settings.getMessage() != null && settings.getMessage().length() != 0) {
                    event.sendReply(settings.getMessage() + " [@" + event.getActor().getNick() + "]");
                }
                else event.sendReply("Du verwendest zu viel Caps-Lock. [@" + event.getActor().getNick() + "]");
            }
        }
    }

    private void antiSpecialCharacters(final ChannelMessageEvent event, final UserSpamFilter settings, final List<String> exemptions) {
        if (PermissionUtils.checkCustomPermission(event, exemptions)) return;

        final String message = event.getMessage();
        final String messageSpecialOnly = message.replaceAll("(?:[A-Za-z0-9 ]|[ÄäÖöÜüẞß]|(@[A-Za-z][A-Za-z0-9]+?))", "");
        int special = messageSpecialOnly.length();
        int nonSpecial = message.length() - special;
        float specialPercentage = (float) special / (nonSpecial + special) * 100;
        if (specialPercentage >= 25) {
            spamMap.get(event.getActor().getNick()).specialCharCounter.add(LocalDateTime.now());
        }
        if (specialPercentage >= 50) {
            spamMap.get(event.getActor().getNick()).specialCharCounter.add(LocalDateTime.now());
        }

        if (spamMap.get(event.getActor().getNick()).specialCharCounter.size() >= settings.getAllowedLimit()) {
            TwitchBotDataHolder.deleteMessagesFrom(event.getChannel().getName(), event.getActor().getNick());
            event.sendReply("/timeout " + event.getActor().getNick() + " " + settings.getTimeoutDuration() + " Zu hohe Nutzung von Sonderzeichen");
            if (!settings.getSilent()) {
                if (settings.getMessage() != null && settings.getMessage().length() != 0) {
                    event.sendReply(settings.getMessage() + " [@" + event.getActor().getNick() + "]");
                }
                else event.sendReply("Du verwendest zu viele Sonderzeichen. [@" + event.getActor().getNick() + "]");
            }
        }
    }

    private void antiEmote(final ChannelMessageEvent event, final UserSpamFilter settings, final List<String> exemptions) {
        if (PermissionUtils.checkCustomPermission(event, exemptions)) return;
        int maxEmotes = 3;

        final Optional<Emotes> emotes = event.getSource().getTag("emotes", Emotes.class);
        if (emotes.isPresent()) {
            if (emotes.get().getEmotes().size() > maxEmotes) {
                TwitchBotDataHolder.deleteMessagesFrom(event.getChannel().getName(), event.getActor().getNick());
                event.sendReply("/timeout " + event.getActor().getNick() + " " + settings.getTimeoutDuration() + " Emote-Spam");
                if (!settings.getSilent()) {
                    if (settings.getMessage() != null && settings.getMessage().length() != 0) {
                        event.sendReply(settings.getMessage() + " [@" + event.getActor().getNick() + "]");
                    }
                    else event.sendReply("Bitte verwende nicht so viele Emotes in einer Nachricht. [@" + event.getActor().getNick() + "]");
                }
            }
        }
    }

    // TODO: whitelist link pattern (for clips etc)
    private void antiLinks(final ChannelMessageEvent event, final UserSpamFilter settings, final List<String> exemptions) {
        if (PermissionUtils.checkCustomPermission(event, exemptions)) return;

        if (Pattern.compile("(http://|https://).+").matcher(event.getMessage()).find()) {
            TwitchBotDataHolder.deleteMessagesFrom(event.getChannel().getName(), event.getActor().getNick());
            boolean ban = false;
            for (String banned : bannedLinks) {
                if (event.getMessage().contains(banned)) {
                    ban = true;
                }
            }
            if (ban) event.sendReply("/ban " + event.getActor().getNick() + " Verbotene Links");
            else event.sendReply("/timeout " + event.getActor().getNick() + " " + settings.getTimeoutDuration() + " Keine Links posten");
            if (!settings.getSilent() && !ban) {
                if (settings.getMessage() != null && settings.getMessage().length() != 0) {
                    event.sendReply(settings.getMessage() + " [@" + event.getActor().getNick() + "]");
                }
                else event.sendReply("Bitte verschicke keine Links. [@" + event.getActor().getNick() + "]");
            }
        }
    }

    private static class SpamInfo {
        int capsExpireSeconds = (60 * 5);
        int specialCharExpireSeconds = (60 * 5);

        List<LocalDateTime> capsCounter = new ArrayList<>();
        List<LocalDateTime> specialCharCounter = new ArrayList<>();

        public SpamInfo() {}
        public void tick() {
            List<LocalDateTime> capsCounterNew = new ArrayList<>();
            for (LocalDateTime time : capsCounter) {
                if (LocalDateTime.now().isBefore(time.plusSeconds(capsExpireSeconds))) {
                    capsCounterNew.add(time);
                }
            }
            capsCounter = capsCounterNew;

            List<LocalDateTime> specialCharCounterNew = new ArrayList<>();
            for (LocalDateTime time : specialCharCounter) {
                if (LocalDateTime.now().isBefore(time.plusSeconds(specialCharExpireSeconds))) {
                    specialCharCounterNew.add(time);
                }
            }
            specialCharCounter = specialCharCounterNew;
        }
    }
}
