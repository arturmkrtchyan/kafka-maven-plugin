package com.arturmkrtchyan.kafka.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import static java.nio.file.attribute.PosixFilePermission.*;

public class TarUnpacker {

    private static final int BUFFER_SIZE = 8 * 1024;

    /**
     * Unpack the archive to specified directory.
     *
     * @param inputPath  tar or tar.gz file Path
     * @param outputPath destination Path
     * @param isGzipped true if the file is gzipped.
     * @return true in case of success, otherwise - false
     */
    public boolean unpack(final Path inputPath, final Path outputPath, final boolean isGzipped) throws IOException {
        boolean result = false;
        try (final InputStream inputStream = Files.newInputStream(inputPath);
             final TarArchiveInputStream tarArchiveInputStream = createTarArchiveInputStream(inputStream, isGzipped)) {
            result = unpack(tarArchiveInputStream, outputPath);
        }
        return result;
    }

    protected TarArchiveInputStream createTarArchiveInputStream(final InputStream inputStream,
                                                                final boolean isGzipped) throws IOException {
        return (isGzipped) ?
                new TarArchiveInputStream(new GZIPInputStream(inputStream), BUFFER_SIZE) :
                new TarArchiveInputStream(new BufferedInputStream(inputStream), BUFFER_SIZE);
    }

    /**
     * Unpack data from the stream to specified directory.
     *
     * @param in        stream with tar data
     * @param outputPath destination path
     * @return true in case of success, otherwise - false
     */
    protected boolean unpack(final TarArchiveInputStream in, final Path outputPath) throws IOException {
        TarArchiveEntry entry;
        while ((entry = in.getNextTarEntry()) != null) {
            final Path entryPath = outputPath.resolve(entry.getName());
            if (entry.isDirectory()) {
                if (!Files.exists(entryPath)) {
                    Files.createDirectories(entryPath);
                }
            } else if (entry.isFile()) {
                try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(entryPath))) {
                    IOUtils.copy(in, out);
                    setPermissions(entry.getMode(), entryPath);
                }
            }
        }
        return true;
    }

    protected void setPermissions(int mode, Path path) throws IOException {
        PosixFileAttributeView attributeView = Files.getFileAttributeView(path, PosixFileAttributeView.class);
        if(attributeView != null) {
            attributeView.setPermissions(PosixFilePermissionConverter.convertToPermissionsSet(mode));
        }
    }

    public static void main(String[] args) throws IOException {
        TarUnpacker unpacker = new TarUnpacker();
        unpacker.unpack(Paths.get("/tmp/kafka_2.9.2-0.8.2.1.tgz"), Paths.get("/tmp/"), true);
    }

    private static class PosixFilePermissionConverter {

        static Set<PosixFilePermission> convertToPermissionsSet(int mode) {
            Set<PosixFilePermission> result = EnumSet.noneOf(PosixFilePermission.class);

            if (isSet(mode, 0400)) {
                result.add(OWNER_READ);
            }
            if (isSet(mode, 0200)) {
                result.add(OWNER_WRITE);
            }
            if (isSet(mode, 0100)) {
                result.add(OWNER_EXECUTE);
            }

            if (isSet(mode, 040)) {
                result.add(GROUP_READ);
            }
            if (isSet(mode, 020)) {
                result.add(GROUP_WRITE);
            }
            if (isSet(mode, 010)) {
                result.add(GROUP_EXECUTE);
            }
            if (isSet(mode, 04)) {
                result.add(OTHERS_READ);
            }
            if (isSet(mode, 02)) {
                result.add(OTHERS_WRITE);
            }
            if (isSet(mode, 01)) {
                result.add(OTHERS_EXECUTE);
            }
            return result;
        }

        private static boolean isSet(int mode, int testbit) {
            return (mode & testbit) == testbit;
        }

        public static int convertToInt(Set<PosixFilePermission> permissions) {
            int result = 0;
            if (permissions.contains(OWNER_READ)) {
                result = result | 0400;
            }
            if (permissions.contains(OWNER_WRITE)) {
                result = result | 0200;
            }
            if (permissions.contains(OWNER_EXECUTE)) {
                result = result | 0100;
            }
            if (permissions.contains(GROUP_READ)) {
                result = result | 040;
            }
            if (permissions.contains(GROUP_WRITE)) {
                result = result | 020;
            }
            if (permissions.contains(GROUP_EXECUTE)) {
                result = result | 010;
            }
            if (permissions.contains(OTHERS_READ)) {
                result = result | 04;
            }
            if (permissions.contains(OTHERS_WRITE)) {
                result = result | 02;
            }
            if (permissions.contains(OTHERS_EXECUTE)) {
                result = result | 01;
            }
            return result;
        }
    }

}
