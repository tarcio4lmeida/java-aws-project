FROM openjdk:17
VOLUME /tmp
COPY build/libs/curso_aws_project02-0.0.7.jar /app/curso_aws_project02-0.0.7.jar
#ENTRYPOINT ["java","-cp","app:app/lib/*","br.com.siecola.aws_project02.AwsProject02Application"]
ENTRYPOINT ["java","-jar","/app/curso_aws_project02-0.0.7.jar"]
