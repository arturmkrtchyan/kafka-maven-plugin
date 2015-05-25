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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
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
     * @param instance Kafka instance
     * @throws KafkaPluginException if fails to start an instance
     */
    public void startKafka(final KafkaInstance instance) {
        try {
            executeInBackground(instance.getStartupScript().toString(), instance.getConfig().toString());
            wait(7, TimeUnit.SECONDS);
            logger.debug(execute("jps", "-v"));
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to start kafka instance based on %s",
                    instance.getPath().toString()), e);
        }
    }

    /**
     * Stops a kafka instance.
     *
     * @param instance Kafka instance
     * @throws KafkaPluginException if fails to stop an instance
     */
    public void stopKafka(final KafkaInstance instance) {
        try {
            executeInBackground(instance.getShutdownScript().toString());
            wait(5, TimeUnit.SECONDS);
            logger.debug(execute("jps", "-v"));
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to stop kafka instance based on %s",
                    instance.getPath().toString()), e);
        }
    }

    /**
     * Starts a zookeeper instance.
     *
     * @param instance Kafka instance
     * @throws KafkaPluginException if fails to stop an instance
     */
    protected void startZookeeper(final KafkaInstance instance) {
        try {
            executeInBackground(instance.getZookeeperStartupScript().toString(),
                    instance.getZookeeperConfig().toString());
            wait(7, TimeUnit.SECONDS);
            logger.debug(execute("jps", "-v"));
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to start zookeeper instance based on %s",
                    instance.getPath().toString()), e);
        }
    }

    /**
     * Stops a zookeeper instance.
     *
     * @param instance Kafka instance
     * @throws KafkaPluginException if fails to stop an instance
     */
    protected void stopZookeeper(final KafkaInstance instance) {
        try {
            executeInBackground(instance.getZookeeperShutdownScript().toString());
            wait(5, TimeUnit.SECONDS);
            logger.debug(execute("jps", "-v"));
        } catch (Exception e) {
            throw new KafkaPluginException(String.format("Unable to stop zookeeper instance based on %s",
                    instance.getPath().toString()), e);
        }
    }

    private void wait(final int seconds, TimeUnit unit) {
        try {
            unit.sleep(seconds);
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

