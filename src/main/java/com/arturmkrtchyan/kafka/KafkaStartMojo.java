package com.arturmkrtchyan.kafka;

import com.arturmkrtchyan.kafka.util.TarUnpacker;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.arturmkrtchyan.kafka.KafkaFileSystemHelper.*;

/**
 * Goal which starts kafka broker.
 */
@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class KafkaStartMojo extends AbstractMojo {

    private KafkaDownloader kafkaDownloader = new KafkaDownloader();
    private KafkaManager kafkaManager = new KafkaManager();
    private TarUnpacker tarUnpacker = new TarUnpacker();

    @Parameter(required = true, readonly = true, defaultValue = "${project.build.directory}")
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
        Path instancePath = createKafkaInstance();

        kafkaManager.startZookeeper(instancePath);
        kafkaManager.startKafka(instancePath);
    }


    private Path createKafkaInstance() {

        final Path artifactPath = artifactPath(scalaVersion, kafkaVersion);
        final Path instanceDir = instanceDir(buildDir);

        try {
            debug(String.format("Unpacking kafka from %s into %s", artifactPath, instanceDir));
            tarUnpacker.unpack(artifactPath, instanceDir, true);
        } catch (IOException e) {
            throw new KafkaPluginException(String.format("Unable to unpack kafka from %s into %s", artifactPath, instanceDir), e);
        }
        return instanceDir.resolve(instanceName(scalaVersion, kafkaVersion));
    }

    protected void downloadKafka() {
        final String artifactName = artifactName(scalaVersion, kafkaVersion);

        debug(String.format("Checking if %s is already downloaded into %s", artifactName, KAFKA_ARTIFACT_DIR));

        if(!kafkaDownloader.isDownloaded(artifactPath(scalaVersion, kafkaVersion))) {
            getLog().info(getDottedString());
            getLog().info(String.format("Downloading %s into %s", artifactName, KAFKA_ARTIFACT_DIR));
            getLog().info(getDottedString());

            kafkaDownloader.download(Paths.get(KAFKA_ARTIFACT_DIR), scalaVersion, kafkaVersion);
        } else {
            debug(String.format("%s is already downloaded into %s", artifactName, KAFKA_ARTIFACT_DIR));
        }
    }

    private String getDottedString() {
        return "------------------------------------------------------------------------";
    }

    private void debug(final String message) {
        if(getLog().isDebugEnabled()) {
            getLog().debug(message);
        }
    }

}
