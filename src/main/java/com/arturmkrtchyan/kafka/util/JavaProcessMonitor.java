package com.arturmkrtchyan.kafka.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class JavaProcessMonitor {

    final Logger logger = LoggerFactory.getLogger(JavaProcessMonitor.class);

    public List<Process> getProcesses(final String processName) {
        final List<Process> processes = getProcesses();
        final List<Process> filteredProcesses = new LinkedList<>();
        for (Process process : processes) {
            if(process.getName().equalsIgnoreCase(processName)) {
                filteredProcesses.add(process);
            }
        }
        return filteredProcesses;
    }

    public List<Process> getProcesses() {
        final List<Process> processes = new LinkedList<>();
        try {
            final String result = execute("jps", "-v");
            final String[] processesAsStrLines = result.split(System.getProperty("line.separator"));
            for (final String processStr : processesAsStrLines) {
                processes.add(Process.fromJpsString(processStr));
            }
        } catch (InterruptedException | TimeoutException | IOException e) {
            throw new IllegalStateException(e);
        }
        return processes;
    }

    public boolean killProcess(Process process) {
        try {
            execute("kill", "-9", String.valueOf(process.getId()));
            return true;
        } catch (InterruptedException | TimeoutException | IOException e) {
            logger.error(String.format("Failed to kill %s", process.toString()), e);
        }
        return false;
    }

    private String execute(final String... commands) throws InterruptedException, TimeoutException, IOException {
        return new ProcessExecutor().command(Arrays.asList(commands))
                .readOutput(true).execute()
                .outputUTF8();
    }

}
