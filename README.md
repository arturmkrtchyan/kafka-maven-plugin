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
            <version>0.0.3-SNAPSHOT</version>
            <configuration>
                <scalaVersion>2.10</scalaVersion>
                <kafkaVersion>0.10.1.0</kafkaVersion>
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

**This plugin has 3 goals:**

- kafka:help
  - Display help information on kafka-maven-plugin.
- kafka:start
  - Goal which starts kafka broker.
- kafka:stop
  - Goal which stops kafka broker.

By default kafka logs and zookeeper data stored under **target/kafka/version**  folder.  Can be overwritten by passing corresponding kafka or zookeeper configuration properties.

**Passing kafka and zookeeper properties**

Optional `zookeeper` and `server` sections under plugin configuration. All provided key value pairs will be passed down to appropriate config files.

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.arturmkrtchyan.kafka</groupId>
            <artifactId>kafka-maven-plugin</artifactId>
            <version>0.0.3-SNAPSHOT</version>
            <configuration>
                <scalaVersion>2.10</scalaVersion>
                <kafkaVersion>0.10.1.0</kafkaVersion>
                    <server>                
                        <log.dirs>${project.build.directory}/kafka-logs</log.dirs>
                        <delete.topic.enable>true</delete.topic.enable>
                        <auto.create.topics.enable>true</auto.create.topics.enable>
                    </server>
                    <zookeeper>
                        <dataDir>${project.build.directory}/zookeeper</dataDir>
                    </zookeeper>                
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

** Pre-create kafka topics during startup **

Optionally configure list of topic names to pre-create via `topics` parameter.

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.arturmkrtchyan.kafka</groupId>
            <artifactId>kafka-maven-plugin</artifactId>
            <version>0.0.3-SNAPSHOT</version>
            <configuration>
                <scalaVersion>2.10</scalaVersion>
                <kafkaVersion>0.10.1.0</kafkaVersion>
				<topics>
					<param>my-test-topic</param>
					<param>my-another-test-topic</param>
				</topics>                
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
