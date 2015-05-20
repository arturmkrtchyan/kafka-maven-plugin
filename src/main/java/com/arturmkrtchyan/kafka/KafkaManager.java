package com.arturmkrtchyan.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class KafkaManager {

    Logger logger = LoggerFactory.getLogger(KafkaManager.class);

    public boolean startKafka(final Path instancePath) {
        return false;
    }

    public boolean stopKafka() {
        return false;
    }

    protected boolean startZookeeper(final Path instancePath) {
        try {
            new ProcessExecutor().command(instancePath.toString() + "/bin/zookeeper-server-start.sh",
                    instancePath.toString() + "/config/zookeeper.properties").start();
            TimeUnit.SECONDS.sleep(10);
            String output = new ProcessExecutor().command("jps")
                    .readOutput(true).execute()
                    .outputUTF8();
            logger.debug(output);
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to start zookeeper instance based on %s", instancePath.toString()), e);
        }
        return false;
    }

    protected boolean stopZookeeper(final Path instancePath) {
        try {
            new ProcessExecutor().command(instancePath.toString() + "/bin/zookeeper-server-stop.sh").start();
            TimeUnit.SECONDS.sleep(5);
            String output = new ProcessExecutor().command("jps")
                    .readOutput(true).execute()
                    .outputUTF8();
            logger.debug(output);
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to stop zookeeper instance based on %s", instancePath.toString()), e);
        }
        return false;
    }
}

