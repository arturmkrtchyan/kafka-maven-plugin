package com.arturmkrtchyan.kafka;

import com.arturmkrtchyan.kafka.util.ApacheMirrorLocator;
import com.arturmkrtchyan.kafka.util.ApacheProjectDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KafkaManager {

    public boolean createKafkaInstance() {
        return false;
    }

    public boolean startKafka() {
        return false;
    }

    public boolean stopKafka() {
        return false;
    }

    public boolean cleanupKafka() {
        return false;
    }

    protected boolean startZookeeper() {
        return false;
    }

    protected boolean stopZookeeper() {
        return false;
    }

}
