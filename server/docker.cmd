@REM docker base
@REM docker build -f Dockerfile.base -t terrancesnyder/jdk-curl-base:21-jammy .
@REM docker push terrancesnyder/jdk-curl-base:21-jammy


mvn clean compile jib:build -DskipTests