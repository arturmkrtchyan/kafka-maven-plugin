package com.arturmkrtchyan.kafka;

import java.nio.file.Path;

public class KafkaInstance {

    private final Path path;

    private KafkaInstance(final Path instancePath){
        this.path = instancePath;
    }

    protected static KafkaInstance fromPath(final Path instancePath) {
        return new KafkaInstance(instancePath);
    }

    public Path getPath() {
        return path;
    }

    public Path getStartupScript() {
        return getPath().resolve("bin/kafka-server-start.sh");
    }

    public Path getShutdownScript() {
        return getPath().resolve("bin/kafka-server-stop.sh");
    }

    public Path getConfig() {
        return getPath().resolve("config/server.properties");
    }

    public Path getZookeeperStartupScript() {
        return getPath().resolve("bin/zookeeper-server-start.sh");
    }

    public Path getZookeeperShutdownScript() {
        return getPath().resolve("bin/zookeeper-server-stop.sh");
    }

    public Path getZookeeperConfig() {
        return getPath().resolve("config/zookeeper.properties");
    }
}
