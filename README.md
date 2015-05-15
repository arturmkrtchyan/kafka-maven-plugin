# kafka-maven-plugin

[![Build Status](https://api.travis-ci.org/repositories/arturmkrtchyan/kafka-maven-plugin.png)](https://travis-ci.org/arturmkrtchyan/kafka-maven-plugin)

A Maven plugin to start and stop a Kafka broker during tests.

```xml
<build>
        <plugin>
            <groupId>com.arturmkrtchyan</groupId>
            <artifactId>kafka-maven-plugin</artifactId>
            <version>1.0.0</version>
            <configuration>
            </configuration>
            <executions>
                <execution>
                    <id>preintegration</id>
                    <phase>pre-integration-test</phase>
                    <goals>
                        <goal>start</goal>
                    </goals>
                </execution>
                <execution>
                    <id>postintegration</id>
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
