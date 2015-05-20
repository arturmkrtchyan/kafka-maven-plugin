package com.arturmkrtchyan.kafka;

import com.arturmkrtchyan.kafka.util.TarUnpacker;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.nio.file.Path;

import static com.arturmkrtchyan.kafka.KafkaFileSystemHelper.instanceDir;
import static com.arturmkrtchyan.kafka.KafkaFileSystemHelper.instanceName;

/**
 * Goal which stops kafka broker.
 */
@Mojo(name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class KafkaStopMojo extends AbstractMojo {

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

        final Path instancePath = instanceDir(buildDir).resolve(instanceName(scalaVersion, kafkaVersion));

        kafkaManager.stopZookeeper(instancePath);
    }

}
