package org.example;

import org.example.field.Field;
import org.example.utils.exception.FileContentException;
import org.example.utils.exception.UnknownFileException;
import org.example.utils.file.FileUtils;

import java.util.List;

public class Main {

    public static void main(String[] args) throws UnknownFileException, FileContentException {

        final String locFileName = args.length > 0 ? args[0] : "map1.txt";

        final List<String> locStringRuleList = FileUtils.readLinesFromFile(locFileName);

        final Field locField = new Field(locStringRuleList);
        System.out.println("INIT ------------------");
        System.out.println(locField.toOutputFileFormatString());
        System.out.println(locField.toConsoleFormatString());

        // start
        locField.startTrial();

        System.out.println("END ------------------");
        System.out.println();
        System.out.println(locField.toOutputFileFormatString());
        System.out.println(locField.toConsoleFormatString());

        final String locOutputFileName = "output/" + locFileName.replaceAll(".txt", "_output.txt");
        FileUtils.writeIntoFile(locOutputFileName, locField.toOutputFileFormatString());
    }
}