if [ "$1" = "--win" ]; then
  cd /cygdrive/f/storage/Development/twitchbot/twitchbot/docs
fi
bundle exec middleman build
rm ../src/main/resources/static/docs/index.html
