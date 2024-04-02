FROM eclipse-temurin:17
LABEL authors="uvher"
ADD target/ipfs-demon-0.0.1-SNAPSHOT.jar ipfs-demon-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","ipfs-demon-0.0.1-SNAPSHOT.jar"]