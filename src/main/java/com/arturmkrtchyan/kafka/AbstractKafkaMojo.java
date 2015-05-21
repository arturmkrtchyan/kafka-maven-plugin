package com.arturmkrtchyan.kafka;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractKafkaMojo extends AbstractMojo {

    protected KafkaManager kafkaManager = new KafkaManager();

    @Parameter(required = true, readonly = true, defaultValue = "${project.build.directory}")
    protected String buildDir;

    /**
     * The version of the scala used in kafka build.
     */
    @Parameter(required = true, defaultValue = "2.9.2")
    protected String scalaVersion;

    /**
     * The version of the kafka.
     */
    @Parameter(required = true, defaultValue = "0.8.2.1")
    protected String kafkaVersion;

    protected String getDottedString() {
        return "------------------------------------------------------------------------";
    }

    protected void debug(final String message) {
        if(getLog().isDebugEnabled()) {
            getLog().debug(message);
        }
    }

}
