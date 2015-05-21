package com.arturmkrtchyan.kafka;

import com.arturmkrtchyan.kafka.util.ApacheMirrorLocator;
import com.arturmkrtchyan.kafka.util.ApacheProjectDownloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class KafkaDownloader {

    public void download(final Path localBasePath, final String scalaVersion, final String kafkaVersion) throws KafkaPluginException {
        final ApacheProjectDownloader apacheProjectDownloader = new ApacheProjectDownloader();
        final ApacheMirrorLocator apacheMirrorLocator = new ApacheMirrorLocator();
        final String mirrorUrl = apacheMirrorLocator.locate();
        final String artifactName = KafkaFileSystemHelper.artifactName(scalaVersion, kafkaVersion);
        final String artifactUrl = artifactUrl(mirrorUrl, kafkaVersion, artifactName);

        try {
            apacheProjectDownloader.download(artifactUrl, artifactName, localBasePath);
        } catch (IOException | RuntimeException e) {
            throw new KafkaPluginException(String.format("Unable to download %s from %s", artifactName, artifactUrl), e);
        }
    }

    public boolean isDownloaded(final Path kafkaFile) {
        return Files.exists(kafkaFile);
    }

    protected String artifactUrl(final String mirrorUrl, final String kafkaVersion, final String artifactName) {
        return String.format("%skafka/%s/%s", mirrorUrl, kafkaVersion, artifactName);
    }

}
