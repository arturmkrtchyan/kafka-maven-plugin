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
package com.arturmkrtchyan.kafka.util;


import com.github.kevinsawicki.http.HttpRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ApacheProjectDownloader {

    public Path download(final String url, final String projectName, final Path directory) throws IOException, RuntimeException {
        final Path projectPath = directory.resolve(projectName);
        createDirectory(directory);
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

    private void createDirectory(final Path directory) throws IOException {
        if(!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }
}
