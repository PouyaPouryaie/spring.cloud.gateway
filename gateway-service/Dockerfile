FROM azul/zulu-openjdk-alpine:17
VOLUME /tmp
MAINTAINER pouya
EXPOSE 8091
ADD target/gateway-service-*.jar /gateway-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/gateway-service.jar"]