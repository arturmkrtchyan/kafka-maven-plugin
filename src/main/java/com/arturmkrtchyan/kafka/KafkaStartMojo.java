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

import com.arturmkrtchyan.kafka.util.TarUnpacker;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.arturmkrtchyan.kafka.KafkaFileSystemHelper.*;

/**
 * Goal which starts kafka broker.
 */
@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class KafkaStartMojo extends AbstractKafkaMojo {

    private KafkaDownloader kafkaDownloader = new KafkaDownloader();
    private TarUnpacker tarUnpacker = new TarUnpacker();


    public void execute() throws MojoExecutionException {

        if(isSkip())
        {
            return;
        }

        downloadKafka();
        KafkaInstance instance = createKafkaInstance();

        Conf.fromPath(instance.getConfig())
            .merge("log.dirs", instance.getLogs().toString())
            .merge(getServer());

        Conf.fromPath(instance.getZookeeperConfig())
            .merge("dataDir", instance.getZookeeperData().toString())
            .merge(getZookeeper());

        getKafkaManager().startZookeeper(instance);
        getKafkaManager().startKafka(instance);

        List<String> topics = getTopics();

        if(topics!=null)
        {
            for (String topic : topics)
            {
                getKafkaManager().createTopic(instance, topic);
            }
        }
    }


    protected KafkaInstance createKafkaInstance() {

        final Path artifactPath = artifactPath(getScalaVersion(), getKafkaVersion());
        final Path instanceDir = instanceDir(getBuildDir());

        try {
            debug(String.format("Unpacking kafka from %s into %s", artifactPath, instanceDir));
            tarUnpacker.unpack(artifactPath, instanceDir, true);
        } catch (IOException e) {
            throw new KafkaPluginException(String.format("Unable to unpack kafka from %s into %s", artifactPath, instanceDir), e);
        }
        return KafkaInstance.fromPath(instanceDir.resolve(instanceName(getScalaVersion(), getKafkaVersion())));
    }

    protected void downloadKafka() {
        final String artifactName = artifactName(getScalaVersion(), getKafkaVersion());

        debug(String.format("Checking if %s is already downloaded into %s", artifactName, KAFKA_ARTIFACT_DIR));

        if(!kafkaDownloader.isDownloaded(artifactPath(getScalaVersion(), getKafkaVersion()))) {
            getLog().info(getDottedString());
            getLog().info(String.format("Downloading %s into %s", artifactName, KAFKA_ARTIFACT_DIR));
            getLog().info(getDottedString());

            kafkaDownloader.download(getKafkaLocation(), Paths.get(KAFKA_ARTIFACT_DIR), getScalaVersion(), getKafkaVersion());
        } else {
            debug(String.format("%s is already downloaded into %s", artifactName, KAFKA_ARTIFACT_DIR));
        }
    }
}
