FROM openjdk:8-jdk-alpine

WORKDIR /opendoc-manager

COPY ~/.m2/settings.xml /root/.m2/settings.xml

COPY pom.xml /opendoc-manager/

COPY opendoc-rest-controller/pom.xml /opendoc-manager/opendoc-rest-controller/

COPY . /opendoc/

RUN apk add --no-cache maven && mvn clean package -B -f /opendoc-manager/opendoc-rest-controller/pom.xml -DskipTests

RUN mv /opendoc-manager/opendoc-rest-controller/target/*.jar /app/app.jar

RUN apk del --no-cache maven &&

rm -rf /opendoc-manager/*.xml /opendoc-manager/opendoc-rest-controller/opendoc/.mvn /root/.m2

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opendoc-manager/opendoc-rest-controller-1.0.0-SNAPSHOT.jar"]
