package com.arturmkrtchyan.kafka;

public class KafkaFileSystemHelper {

    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String KAFKA_ARTIFACT_DIR = TMP_DIR + FILE_SEPARATOR + ".kafka" + FILE_SEPARATOR;

    public static String artifactName(String scalaVersion, String kafkaVersion) {
        return String.format("kafka_%s-%s.tgz", scalaVersion, kafkaVersion);
    }

    public static String kafkaDir(String buildDir) {
        return String.format("%s/kafka/", buildDir);
    }

}
