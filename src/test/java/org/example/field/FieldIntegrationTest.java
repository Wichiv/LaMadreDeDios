package org.example.field;

import org.example.utils.exception.FileContentException;
import org.example.utils.exception.UnknownFileException;
import org.example.utils.file.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.utils.file.FileUtils.readLinesFromFile;

public class FieldIntegrationTest {

    private final static String RESOURCES_TEST_PATH = "src/test/resources/";

    @Test
    public void testFileWithAHeightOfZeroShouldThrowsException() throws UnknownFileException {
        final String locFileName = "map1incorrect.txt";
        final List<String> locStringRuleList = readLinesFromFile(RESOURCES_TEST_PATH, locFileName);

        final FileContentException locException = Assertions.assertThrows(FileContentException.class, () -> new Field(locStringRuleList));
        final String locExpectedMessage = "The dimensions of the map are incorrect (3 x 0). The trial cannot begin.";

        Assertions.assertEquals(locExpectedMessage, locException.getMessage());
    }

    @Test
    public void testInputFileToTrialToOutputFileIsCorrect() throws UnknownFileException, FileContentException {
        final String locFileName = "map1.txt";
        final List<String> locStringRuleList = readLinesFromFile(RESOURCES_TEST_PATH, locFileName);

        final Field locField = new Field(locStringRuleList);
        locField.startTrial();

        final String locOutputFileName = "output/" + locFileName.replaceAll(".txt", "_output.txt");
        FileUtils.writeIntoFile(RESOURCES_TEST_PATH, locOutputFileName, locField.toOutputFileFormatString());

        final String locExpectedFileName = "expectedOutput/map1.txt";
        final List<String> locExpectedLines = readLinesFromFile(RESOURCES_TEST_PATH, locExpectedFileName);
        final List<String> locResultLines = readLinesFromFile(RESOURCES_TEST_PATH, locOutputFileName);

        Assertions.assertEquals(locExpectedLines, locResultLines);
    }
}
