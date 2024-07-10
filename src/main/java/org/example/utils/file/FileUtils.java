package org.example.utils.file;

import org.example.utils.exception.FileContentException;
import org.example.utils.exception.UnknownFileException;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

    private final static String RESOURCES_PATH = "src/main/resources/";

    public static List<String> readLinesFromFile(final String parFileName) throws UnknownFileException {
        return readLinesFromFile(RESOURCES_PATH, parFileName);
    }

    public static List<String> readLinesFromFile(final String parResourcesPath, final String parFileName) throws UnknownFileException {
        final File locFile = new File(parResourcesPath + parFileName);
        InputStream locInputStream;

        try {
            locInputStream = new FileInputStream(locFile);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(locInputStream))) {
                return br.lines().collect(Collectors.toList());

            } catch (IOException e) {
                final String locMessage = String.format("Error while reading in %s", locFile.getName());
                throw new FileContentException(locMessage, e);
            }
        } catch (FileNotFoundException | FileContentException e) {
            final String locMessage = String.format("File %s cannot be found.", locFile.getName());
            throw new UnknownFileException(locMessage, e);
        }
    }

    public static void writeIntoFile(final String parFileName, final String parContent) throws UnknownFileException, FileContentException {
        writeIntoFile(RESOURCES_PATH, parFileName, parContent);
    }

    public static void writeIntoFile(final String parResourcesPath, final String parFileName, final String parContent)
            throws UnknownFileException, FileContentException {
        final File locFile = new File(parResourcesPath + parFileName);
        OutputStream locOutputStream;

        try {
            locOutputStream = new FileOutputStream(locFile);
            try (BufferedWriter br = new BufferedWriter(new OutputStreamWriter(locOutputStream))) {
                br.write(parContent);

            } catch (IOException e) {
                final String locMessage = String.format("Error while writing in %s.", locFile.getName());
                throw new FileContentException(locMessage, e);
            }
        } catch (FileNotFoundException e) {
            final String locMessage = String.format("File %s cannot be created.", locFile.getName());
            throw new UnknownFileException(locMessage, e);
        }
    }
}
