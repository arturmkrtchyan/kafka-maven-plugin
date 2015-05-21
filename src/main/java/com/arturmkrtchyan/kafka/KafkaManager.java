package com.arturmkrtchyan.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
            executeInBackground(instancePath.toString() + "/bin/zookeeper-server-start.sh",
                    instancePath.toString() + "/config/zookeeper.properties");
            wait(7);

            logger.debug(execute("jps"));
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to start zookeeper instance based on %s", instancePath.toString()), e);
        }
        return false;
    }

    protected boolean stopZookeeper(final Path instancePath) {
        try {
            executeInBackground(instancePath.toString() + "/bin/zookeeper-server-stop.sh");
            wait(5);
            logger.debug(execute("jps"));
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to stop zookeeper instance based on %s", instancePath.toString()), e);
        }
        return false;
    }

    private void wait(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            logger.warn("Thread was interupted.", e);
        }
    }

    private void executeInBackground(String... commands) throws IOException {
        new ProcessExecutor().command(Arrays.asList(commands)).start();
    }

    private String execute(String... commands) throws InterruptedException, TimeoutException, IOException {
        return new ProcessExecutor().command(Arrays.asList(commands))
                    .readOutput(true).execute()
                    .outputUTF8();
    }
}

