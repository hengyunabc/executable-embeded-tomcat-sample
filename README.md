# executable-embeded-tomcat-sample

## Features

* Support web.xml

spring boot is a great project, but it don't support web.xml. This project support traditional web.xml.

* Support Fat jar

By `spring-boot-maven-plugin`, the project can be packaged into an executable jar.

You can also use `maven-assembly-plugin/jar-with-dependencies`, but `spring-boot-maven-plugin` is a better choice.

* Run in IDE


## Sample

Run main class `io.github.hengyunabc.Main` in your IDE, or

```bash
mvn clean package

java -jar target/executable-embeded-tomcat-sample-0.0.1-SNAPSHOT.jar
```

Open the following link in your browser:

http://localhost:8080/test.jsp

http://localhost:8080/hello

## How to migrate from a webapp project

It is very simple.

1. add embeded tomcat maven dependencies into your pom.xml

1. move `src/main/webapp/WEB-INF` to `src/main/resources/WEB-INF`, move all files under `src/main/webapp` to `src/main/META-INF/resources`.

> according to servlet 3.0, ServletContext can get static content from `META-INF/resource`.

1. add a main class, start your embeded tomcat.

## Others

http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/basic_app_embedded_tomcat/basic_app-tomcat-embedded.html

> A sample from oracle, but it do not support web.xml, and do not support run in IDE.

https://github.com/kui/executable-war-sample

> Another sample, **it has security problems**. people can access your .class file through http!!

## License
Apache License V2
