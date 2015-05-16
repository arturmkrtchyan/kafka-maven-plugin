package com.arturmkrtchyan.kafka.util;


import com.github.kevinsawicki.http.HttpRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApacheProjectDownloader {

    public Path download(final String url, final String projectName, final Path directory) throws IOException, RuntimeException {
        final Path projectPath = directory.resolve(projectName);
        download(url, projectPath);
        return projectPath;
    }

    private void download(final String url, final Path projectPath) throws IOException, RuntimeException {
        final HttpRequest request = HttpRequest.get(url);
        if (request.ok() && request.contentLength() > 0) {
            Files.createFile(projectPath);
            request.receive(projectPath.toFile());
        } else {
            throw new RuntimeException(String.format("Unable to download kafka from %s, to %s", url, projectPath.getParent().toString()));
        }
    }
}
