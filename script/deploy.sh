ps -ef | grep -i play-final-assignment-1.0-SNAPSHOT | grep -v grep | awk '{print $2}' | xargs kill
rm -r play-final-assignment-1.0-SNAPSHOT | echo "artifact already deleted"
unzip play-final-assignment-1.0-SNAPSHOT.zip
rm -f play-final-assignment-1.0-SNAPSHOT.zip  
./play-final-assignment-1.0-SNAPSHOT/bin/play-final-assignment &

