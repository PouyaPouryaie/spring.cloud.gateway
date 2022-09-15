FROM azul/zulu-openjdk-alpine:17
VOLUME /tmp
MAINTAINER pouya
EXPOSE 8090
ADD target/order-service-*.jar /order-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/order-service.jar"]