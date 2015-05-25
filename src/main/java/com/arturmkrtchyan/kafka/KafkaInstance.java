/*
 * Copyright 2015 Artur Mkrtchyan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
