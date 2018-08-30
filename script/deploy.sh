ps aux | grep -ie play | awk '{print "kill -9 " $2}' | sh -x
rm -r play-final-assignment-1.0-SNAPSHOT | echo "artifact already deleted"
unzip play-final-assignment-1.0-SNAPSHOT.zip
rm -f play-final-assignment-1.0-SNAPSHOT.zip  
./play-final-assignment-1.0-SNAPSHOT/bin/play-final-assignment &
