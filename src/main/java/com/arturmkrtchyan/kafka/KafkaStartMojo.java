package com.arturmkrtchyan.kafka;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * Goal which starts kafka broker.
 */
@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class KafkaStartMojo extends AbstractMojo {

    /**
     * Location of the file.
     */
    @Parameter(required = true, defaultValue = "${project.build.directory}")
    private File outputDirectory;

    /**
     * This will turn on verbose behavior and will print out
     * all information about the artifacts.
     *
     * @parameter expression="${verbose}" default-value="false"
     */
    @Parameter(defaultValue = "false")
    private boolean verbose;



    public void execute() throws MojoExecutionException {
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isVerbose() {
        return verbose;
    }
}
