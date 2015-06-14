# kafka-maven-plugin

[![Build Status](https://api.travis-ci.org/repositories/arturmkrtchyan/kafka-maven-plugin.png)](https://travis-ci.org/arturmkrtchyan/kafka-maven-plugin) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.arturmkrtchyan.kafka/kafka-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.arturmkrtchyan.kafka/kafka-maven-plugin)
[![License](http://img.shields.io/:license-Apache 2.0-blue.svg)](https://raw.githubusercontent.com/arturmkrtchyan/kafka-maven-plugin/master/LICENSE.txt)

A Maven plugin to start and stop a Kafka broker during tests.

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.arturmkrtchyan.kafka</groupId>
            <artifactId>kafka-maven-plugin</artifactId>
            <version>0.0.1</version>
            <configuration>
                <scalaVersion>2.9.2</scalaVersion>
                <kafkaVersion>0.8.2.1</kafkaVersion>
            </configuration>
            <executions>
                <execution>
                    <id>pre-integration</id>
                    <phase>pre-integration-test</phase>
                    <goals>
                        <goal>start</goal>
                    </goals>
                </execution>
                <execution>
                    <id>post-integration</id>
                    <phase>post-integration-test</phase>
                    <goals>
                        <goal>stop</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
