FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp
COPY target/auth-0.1.0.jar auth.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /auth.jar"]
EXPOSE 7878