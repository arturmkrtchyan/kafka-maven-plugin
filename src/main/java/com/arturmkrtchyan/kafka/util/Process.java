package com.arturmkrtchyan.kafka.util;

public class Process {

    private int id;
    private String name;
    private String arguments;

    private Process(int id, String name, String arguments) {
        this.id = id;
        this.name = name;
        this.arguments = arguments;
    }

    public static Process fromJpsString(final String jpsOutput) {
        final String[] processParts = jpsOutput.split(" ");
        final int processId = Integer.valueOf(processParts[0]);
        final String processName = processParts[1];
        final String processArgs = jpsOutput.substring((processId + " " + processName).length());
        return new Process(processId, processName, processArgs);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Process process = (Process) o;

        return id == process.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Process{" +
                "  id=" + id +
                ",  name='" + name + '\'' +
                ",  arguments='" + arguments + '\'' +
                "}";
    }
}
