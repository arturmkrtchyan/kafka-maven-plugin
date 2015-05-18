package com.arturmkrtchyan.kafka;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.nio.file.Paths;

import static com.arturmkrtchyan.kafka.KafkaFileSystemHelper.*;

/**
 * Goal which starts kafka broker.
 */
@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class KafkaStartMojo extends AbstractMojo {

    KafkaDownloader kafkaDownloader = new KafkaDownloader();
    KafkaManager kafkaManager = new KafkaManager();

    @Parameter(required = true, defaultValue = "${project.build.directory}")
    private String buildDir;

    /**
     * The version of the scala used in kafka build.
     */
    @Parameter(required = true, defaultValue = "2.9.2")
    private String scalaVersion;

    /**
     * The version of the kafka.
     */
    @Parameter(required = true, defaultValue = "0.8.2.1")
    private String kafkaVersion;


    public void execute() throws MojoExecutionException {

        downloadKafka();
        System.out.println(kafkaDir(buildDir));
        //createKafkaInstance();

    }

    protected void downloadKafka() {
        final String artifactName = artifactName(scalaVersion, kafkaVersion);

        if(getLog().isDebugEnabled()) {
            getLog().debug(String.format("Checking if %s is already downloaded into %s", artifactName, KAFKA_ARTIFACT_DIR));
        }

        if(!kafkaDownloader.isDownloaded(Paths.get(KAFKA_ARTIFACT_DIR), scalaVersion, kafkaVersion)) {
            getLog().info(getDottedString());
            getLog().info(String.format("Downloading %s into %s", artifactName, KAFKA_ARTIFACT_DIR));
            getLog().info(getDottedString());

            kafkaDownloader.download(Paths.get(KAFKA_ARTIFACT_DIR), scalaVersion, kafkaVersion);
        }
    }

    private String getDottedString() {
        return "------------------------------------------------------------------------";
    }

}
