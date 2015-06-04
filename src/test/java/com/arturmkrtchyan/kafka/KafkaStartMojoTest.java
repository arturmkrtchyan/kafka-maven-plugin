package com.arturmkrtchyan.kafka;

import com.arturmkrtchyan.kafka.util.*;
import com.arturmkrtchyan.kafka.util.Process;
import net.vidageek.mirror.dsl.Mirror;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.arturmkrtchyan.kafka.KafkaFileSystemHelper.*;
import static org.junit.Assert.*;

public class KafkaStartMojoTest {

    private final KafkaStartMojo startMojo = new KafkaStartMojo();
    private final String kafkaInstanceId = UUID.randomUUID().toString();
    private final JavaProcessMonitor processMonitor = new JavaProcessMonitor();

    private List<Process> kafkaProcessesBefore;
    private List<Process> zookeeperProcessesBefore;
    private List<Process> kafkaProcessesAfter;
    private List<Process> zookeeperProcessesAfter;


    @Before
    public void setUp() throws Exception {
        new Mirror().on(startMojo).set().field("scalaVersion").withValue("2.9.2");
        new Mirror().on(startMojo).set().field("kafkaVersion").withValue("0.8.2.1");
        new Mirror().on(startMojo).set().field("buildDir").withValue(
                KAFKA_ARTIFACT_DIR + FILE_SEPARATOR + kafkaInstanceId);

        kafkaProcessesBefore = processMonitor.getProcesses(KafkaManager.KAFKA_PROCESS_NAME);
        zookeeperProcessesBefore = processMonitor.getProcesses(KafkaManager.ZOOKEEPER_PROCESS_NAME);
    }

    @After
    public void tearDown() throws Exception {
        kafkaProcessesAfter = processMonitor.getProcesses(KafkaManager.KAFKA_PROCESS_NAME);
        zookeeperProcessesAfter = processMonitor.getProcesses(KafkaManager.ZOOKEEPER_PROCESS_NAME);

        // kill kafka processes
        kafkaProcessesAfter.removeAll(kafkaProcessesBefore);
        for(final Process process : kafkaProcessesAfter) {
            processMonitor.killProcess(process);
        }

        // kill zookeeper processes
        zookeeperProcessesAfter.removeAll(zookeeperProcessesBefore);
        for(final Process process : zookeeperProcessesAfter) {
            processMonitor.killProcess(process);
        }

        FileUtils.deleteDirectory(Paths.get(KAFKA_ARTIFACT_DIR).toFile());
    }

    @Test
    public void testExecute() throws Exception {

        startMojo.execute();

        kafkaProcessesAfter = processMonitor.getProcesses(KafkaManager.KAFKA_PROCESS_NAME);
        zookeeperProcessesAfter = processMonitor.getProcesses(KafkaManager.ZOOKEEPER_PROCESS_NAME);

        assertTrue("After mojo execution we must have 1 more kafka process running.", kafkaProcessesAfter.size() - kafkaProcessesBefore.size() == 1);
        assertTrue("After mojo execution we must have 1 more zookeeper process running.", zookeeperProcessesAfter.size() - zookeeperProcessesBefore.size() == 1);

    }
}