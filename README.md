# kafka-maven-plugin

[![Build Status](https://api.travis-ci.org/repositories/arturmkrtchyan/kafka-maven-plugin.png)](https://travis-ci.org/arturmkrtchyan/kafka-maven-plugin)

A Maven plugin to start and stop a Kafka broker during tests.

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.arturmkrtchyan</groupId>
            <artifactId>kafka-maven-plugin</artifactId>
            <version>0.0.1</version>
            <configuration></configuration>
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
