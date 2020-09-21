FROM openjdk:14.0.2
COPY ./target /usr/src/svr
WORKDIR /usr/src/svr
EXPOSE 8080
EXPOSE 7800
EXPOSE 7801
EXPOSE 7802
EXPOSE 7803
EXPOSE 7804
EXPOSE 7805
EXPOSE 7806
EXPOSE 7808
EXPOSE 7809
ENTRYPOINT ["java", "-jar", "encripted-service-0.0.1-SNAPSHOT.jar"]
#CMD ["-Djgroups.tcpping.initial_hosts=localhost[7800]"]
