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
