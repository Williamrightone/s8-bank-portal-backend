FROM openjdk:17-alpine
COPY ./target/*.jar bank-portal-backend.jar
RUN sh -c 'touch bank-portal-backend.jar'
EXPOSE 8091
ENTRYPOINT ["java","-jar","bank-portal-backend.jar"]