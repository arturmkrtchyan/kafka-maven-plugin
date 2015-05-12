package com.arturmkrtchyan.kafka.util;


import com.github.kevinsawicki.http.HttpRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApacheProjectDownloader {

    public Path download(String url, String projectName, Path directory) throws IOException {
        Path projectPath = directory.resolve(projectName);
        if(!Files.exists(projectPath)) {
            download(url, projectPath);
        }
        return projectPath;
    }

    private void download(String url, Path projectPath) throws IOException {
        HttpRequest request = HttpRequest.get(url);
        if (request.ok() && request.contentLength() > 0) {
            Files.createFile(projectPath);
            request.receive(projectPath.toFile());
        }
    }

    public static void main(String[] args) throws IOException {
        ApacheProjectDownloader downloader = new ApacheProjectDownloader();

        String property = "java.io.tmpdir";

        String tempDir = System.getProperty(property);

        downloader.download("http://mirror.dkd.de/apache/kafka/0.8.2.1/kafka_2.9.2-0.8.2.1.tgz", "kafka_2.9.2-0.8.2.1.tgz", Paths.get(tempDir));
    }
}
