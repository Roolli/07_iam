FROM ubuntu

ADD hero-swarm.jar /opt/wildfly-swarm.jar

RUN apt-get update
RUN apt-get -y install software-properties-common
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections

RUN apt-get -y install oracle-java8-installer

RUN apt-get -qq update

RUN echo "mysql-server mysql-server/root_password password root" | debconf-set-selections
RUN echo "mysql-server mysql-server/root_password_again password root" | debconf-set-selections
RUN apt-get -y install mysql-server nmap
RUN sed -i -e"s/^bind-address\s*=\s*127.0.0.1/bind-address = 0.0.0.0/" /etc/mysql/mysql.conf.d/mysqld.cnf
EXPOSE 3306
EXPOSE 8080
CMD ["mysqld"]

#ENTRYPOINT ["java", "-jar", "/opt/wildfly-swarm.jar", "-Dswarm.ds.name=hoeds", "-Dswarm.ds.username=root", "-Dswarm.ds.password=root", "-Dswarm.ds.connection.url=jdbc:mysql://localhost:3306/mysql?useSSL=false", "-Djava.net.preferIPv4Stack=true", "&"]
