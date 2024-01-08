FROM openjdk:17
VOLUME /tmp
ARG DEPENDENCY=target/libs
COPY build/libs/curso_aws_project01-1.3.2.jar /app/curso_aws_project01-1.3.2.jar
#ENTRYPOINT ["java","-cp","app:app/lib/*","br.com.siecola.aws_project01.AwsProject01Application"]
ENTRYPOINT ["java","-jar","/app/curso_aws_project01-1.3.2.jar"]
