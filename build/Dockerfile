FROM maven:3.9.0-eclipse-temurin-19 as build

COPY ./ ./

RUN mvn clean install

RUN mvn package

FROM eclipse-temurin:19-jdk-jammy

ARG USERNAME=dftuser
ARG USER_UID=1000
ARG USER_GID=$USER_UID

RUN apt-get update && apt-get install --no-install-recommends \
    && groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME \
    && apt-get update \
    && apt-get install -y sudo \
    && echo $USERNAME ALL=\(root\) NOPASSWD:ALL > /etc/sudoers.d/$USERNAME \
    && chmod 0440 /etc/sudoers.d/$USERNAME \
    && rm -rf /var/lib/apt/lists/*


USER $USERNAME

WORKDIR /home/user/dftuser/dftapp


COPY --from=build demand-capacity-mgmt-backend/target/*.jar ./app.jar


EXPOSE 8080

CMD ["java", "-jar", "./app.jar"]


