#java -cp target/langJunkie-1.0-SNAPSHOT.jar ru.lukas.langjunkie.loader.App $1
#mvn exec:java -pl loader -Dexec.args="$1 $2"
#mvn exec:java -pl api -Dexec.spring.profiles.active=DEV
java -jar web/target/web-1.0.jar
