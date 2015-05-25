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
        getKafkaManager().stopKafka(getRunningKafkaInstance());
        getKafkaManager().stopZookeeper(getRunningKafkaInstance());
    }

    protected KafkaInstance getRunningKafkaInstance() {
        final Path instancePath = instanceDir(getBuildDir()).resolve(instanceName(getScalaVersion(), getKafkaVersion()));
        return KafkaInstance.fromPath(instancePath);
    }

}
