package com.arturmkrtchyan.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * KafkaManager to start and stop zookeeper and kafka instances.
 */
public class KafkaManager {

    final Logger logger = LoggerFactory.getLogger(KafkaManager.class);

    /**
     * Starts a kafka instance.
     *
     * @param instancePath Kafka instance path
     * @throws KafkaPluginException if fails to start an instance
     */
    public void startKafka(final Path instancePath) {
        try {
            executeInBackground(instancePath.toString() + "/bin/kafka-server-start.sh",
                    instancePath.toString() + "/config/server.properties");
            wait(7);

            logger.debug(execute("jps", "-v"));
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to start kafka instance based on %s", instancePath.toString()), e);
        }
    }

    /**
     * Stops a kafka instance.
     *
     * @param instancePath Kafka instance path
     * @throws KafkaPluginException if fails to stop an instance
     */
    public void stopKafka(final Path instancePath) {
        try {
            executeInBackground(instancePath.toString() + "/bin/kafka-server-stop.sh");
            wait(5);

            logger.debug(execute("jps", "-v"));
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to start kafka instance based on %s", instancePath.toString()), e);
        }
    }

    /**
     * Starts a zookeeper instance.
     *
     * @param instancePath Kafka instance path
     * @throws KafkaPluginException if fails to stop an instance
     */
    protected void startZookeeper(final Path instancePath) {
        try {
            executeInBackground(instancePath.toString() + "/bin/zookeeper-server-start.sh",
                    instancePath.toString() + "/config/zookeeper.properties");
            wait(7);

            logger.debug(execute("jps", "-v"));
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to start zookeeper instance based on %s", instancePath.toString()), e);
        }
    }

    /**
     * Stops a zookeeper instance.
     *
     * @param instancePath Kafka instance path
     * @throws KafkaPluginException if fails to stop an instance
     */
    protected void stopZookeeper(final Path instancePath) {
        try {
            executeInBackground(instancePath.toString() + "/bin/zookeeper-server-stop.sh");
            wait(5);
            logger.debug(execute("jps", "-v"));
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to stop zookeeper instance based on %s", instancePath.toString()), e);
        }
    }

    private void wait(final int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            logger.warn("Thread was interupted.", e);
        }
    }

    private void executeInBackground(final String... commands) throws IOException {
        new ProcessExecutor().command(Arrays.asList(commands)).start();
    }

    private String execute(final String... commands) throws InterruptedException, TimeoutException, IOException {
        return new ProcessExecutor().command(Arrays.asList(commands))
                    .readOutput(true).execute()
                    .outputUTF8();
    }
}

