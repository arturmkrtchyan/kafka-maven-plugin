package com.arturmkrtchyan.kafka;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.nio.file.Path;

import static com.arturmkrtchyan.kafka.KafkaFileSystemHelper.instanceDir;
import static com.arturmkrtchyan.kafka.KafkaFileSystemHelper.instanceName;

/**
 * Goal which stops kafka broker.
 */
@Mojo(name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class KafkaStopMojo extends AbstractKafkaMojo {


    public void execute() throws MojoExecutionException {

        final Path instancePath = instanceDir(getBuildDir()).resolve(instanceName(getScalaVersion(), getKafkaVersion()));

        getKafkaManager().stopKafka(instancePath);
        getKafkaManager().stopZookeeper(instancePath);
    }

}
