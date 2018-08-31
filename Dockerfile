FROM java
COPY target/universal/play-final-assignment-1.0-SNAPSHOT.zip / 
RUN unzip play-final-assignment-1.0-SNAPSHOT.zip
ENV DUMMY=knol
CMD ./play-final-assignment-1.0-SNAPSHOT/bin/play-final-assignment
