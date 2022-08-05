del "F:\!Development\twitchbot-re\twitchbot\built\app.jar"
call mvn clean package -DskipTests=true
copy "F:\!Development\twitchbot-re\twitchbot\target\twitchbot-0.0.1-SNAPSHOT.jar" "F:\!Development\twitchbot-re\twitchbot\built\app.jar"
