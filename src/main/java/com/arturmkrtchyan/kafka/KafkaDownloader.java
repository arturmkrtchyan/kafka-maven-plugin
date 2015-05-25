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
