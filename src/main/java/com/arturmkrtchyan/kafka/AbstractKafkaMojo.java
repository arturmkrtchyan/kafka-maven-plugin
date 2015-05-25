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


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractKafkaMojo extends AbstractMojo {

    private KafkaManager kafkaManager = new KafkaManager();

    /**
     * The Project build directory which is defaulting to ${project.build.directory}
     */
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

    protected String getDottedString() {
        return "------------------------------------------------------------------------";
    }

    protected void debug(final String message) {
        if(getLog().isDebugEnabled()) {
            getLog().debug(message);
        }
    }

    protected KafkaManager getKafkaManager() {
        return kafkaManager;
    }

    protected String getBuildDir() {
        return buildDir;
    }

    protected String getScalaVersion() {
        return scalaVersion;
    }

    protected String getKafkaVersion() {
        return kafkaVersion;
    }
}
