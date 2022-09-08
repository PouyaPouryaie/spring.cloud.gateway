FROM azul/zulu-openjdk-alpine:17
VOLUME /tmp
MAINTAINER pouya
EXPOSE 8761
ADD target/discovery-service-*.jar /discovery-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker","-jar", "/discovery-service.jar"]

# add jasypt secret-key in jar file:
#ENTRYPOINT ["java", "-jar", "/springboot-real.jar", "--jasypt.encryptor.password=secret-key"]

#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "/springboot-real-1.0.0.jar", "--spring.config.location=file:application.properties"]