FROM mariadb:bionic

ADD stable-swarm.jar /opt/wildfly-swarm.jar

RUN apt-get update
RUN apt-get -y install software-properties-common
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections

RUN apt-get -y install openjdk-8-jdk 

RUN apt-get -qq update

EXPOSE 3306
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/wildfly-swarm.jar", "-Dswarm.ds.name=hoeds", "-Dswarm.ds.username=root", "-Dswarm.ds.password=root", "-Dswarm.ds.connection.url=jdbc:mysql://localhost:3306/mysql?useSSL=false", "-Djava.net.preferIPv4Stack=true", "&"]
