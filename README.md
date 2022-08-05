# Twitch-Bot

## Dependencies

```
- Apache2
- MySQL Server
- NodeJS (+npm)
- Java 14
- Cygwin (for Windows Development)
    + "make"                4.3-1
    + "ruby-devel"          2.6.4-1
    + "ruby-geminstaller"   3.0.3-1
    + "gcc-core"            10.2.0-1
    + "gcc-g++"             10.2.0-1
    + "zlib-devel"          1.2.11-1
    + "ruby-pkg-config"     1.3.7-1
    + "libiconv-devel"      1.16-2
    + "libxml2-devel"       2.9.10-2
    + "libxslt-devel"       1.1.29-1
- Ruby >=2.3 && <2.7 (for Slate/Docs)  - skip if using cygwin
```

## Installation

```shell script
a2enmod ssl proxy proxy_http headers
# install node_modules for the web application
cd src\main\resources\static
npm install
# slate docs
cd docs
gem install bundler
bundle config build.nokogiri --use-system-libraries
bundle install
bundle exec middleman build
```

## Features

### Current Features

- Spam Protection (Word-Blacklist, Links, Caps Lock, Emotes, Special Characters)
- Music Bot (via Spotify)
- Basic Chat Overlay -> usage: {host}/ext/chat.html?channel={channelName}

### Planned Features

- !game and !title Command
- Custom Commands creatable/editable from frontend
- Queue Info (Need to wait, until Spotify updated their API, because its not possible to view the queue via API)
- Bot will only collect data and work in general while you are live (so he does not need to leave after stream)
- Song Analytics (for fun) - Genres and stuff ..
- User Analytics (Creation date, Follow date, some useful stuff)
- Web-Alert when a user joins the chat with creation date "<=7 days"

#### Apache2-Config

```
<VirtualHost *:443>
        ServerAdmin christian.peters@outlook.de
        ServerName sharpa.de
        ServerAlias www.sharpa.de

        SSLProxyEngine on
        ProxyPreserveHost on
        RequestHeader set X-Forwarded-Proto https
        RequestHeader set X-Forwarded-Port 443
        ProxyPass / https://{serverIP}:8080/
        ProxyPassReverse / https://{serverIP}:8080/
</VirtualHost>

<VirtualHost *:80>
        ServerAdmin christian.peters@outlook.de
        ServerName sharpa.de
        ServerAlias www.sharpa.de

        ProxyPreserveHost on
        RequestHeader set X-Forwarded-Proto https
        RequestHeader set X-Forwarded-Port 443
        ProxyPass / http://{serverIP}:8080/
        ProxyPassReverse / http://{serverIP}:8080/
</VirtualHost>
```

#### Prod-Config

```
server.use-forward-headers=true
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/twitchbot?useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin&useSSL=true&allowPublicKeyRetrieval=true
app.host=https://sharpa.de
app.twitchRedirect=${app.host}/auth/code
app.spotifyRedirect=${app.host}/api/spotify/auth/code
```
