package com.arturmkrtchyan.kafka;

import com.arturmkrtchyan.kafka.util.ApacheMirrorLocator;
import com.arturmkrtchyan.kafka.util.ApacheProjectDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KafkaManager {

    public void downloadKafka(Path localBasePath, String scalaVersion, String kafkaVersion) throws KafkaPluginException {
        final ApacheProjectDownloader apacheProjectDownloader = new ApacheProjectDownloader();
        final ApacheMirrorLocator apacheMirrorLocator = new ApacheMirrorLocator();
        final String mirrorUrl = apacheMirrorLocator.locate();

        try {
            apacheProjectDownloader.download(mirrorUrl + "kafka/" + kafkaVersion + "/kafka_" + scalaVersion + "-" + kafkaVersion + ".tgz",
                    "kafka_" + scalaVersion + "-" + kafkaVersion + ".tgz", localBasePath);
        } catch (IOException | RuntimeException e) {
            throw new KafkaPluginException("Unable to download kafka.", e);
        }

    }

    public boolean isKafkaAvailable(Path localBasePath, String scalaVersion, String kafkaVersion) {
        return false;
    }

    public boolean extractKafka(Path localBasePath, String scalaVersion, String kafkaVersion) {
        return false;
    }

    public boolean startKafka() {
        return false;
    }

    public boolean stopKafka() {
        return false;
    }

    protected boolean startZookeeper() {
        return false;
    }

    protected boolean stopZookeeper() {
        return false;
    }


    public static void main(String[] args) {
        KafkaManager  kafkaManager =  new KafkaManager();

        String property = "java.io.tmpdir";

        String tempDir = System.getProperty(property);

        kafkaManager.downloadKafka(Paths.get(tempDir), "2.9.2", "0.8.2.1");
    }

}
