FROM azul/zulu-openjdk-alpine:17
VOLUME /tmp
MAINTAINER pouya
EXPOSE 8091
ADD target/gateway-service-*.jar /gateway-service.jar
ENTRYPOINT ["java","-jar", "/gateway-service.jar"]