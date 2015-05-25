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
import java.nio.file.Paths;

public class KafkaFileSystemHelper {

    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String KAFKA_ARTIFACT_DIR = TMP_DIR + FILE_SEPARATOR + ".kafka" + FILE_SEPARATOR;

    public static String artifactName(final String scalaVersion, final String kafkaVersion) {
        return String.format("kafka_%s-%s.tgz", scalaVersion, kafkaVersion);
    }

    public static Path artifactPath(final String scalaVersion, final String kafkaVersion) {
        return Paths.get(KAFKA_ARTIFACT_DIR + artifactName(scalaVersion, kafkaVersion));
    }

    public static Path instanceDir(final String buildDir) {
        return Paths.get(String.format("%s/kafka/", buildDir));
    }

    public static String instanceName(final String scalaVersion, final String kafkaVersion) {
        return String.format("kafka_%s-%s", scalaVersion, kafkaVersion);
    }

}
