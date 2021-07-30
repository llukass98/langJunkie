#java -cp target/langJunkie-1.0-SNAPSHOT.jar ru.lukas.langjunkie.loader.App $1
mvn exec:java -pl loader -Dexec.args="$1 $2"
