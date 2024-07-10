package org.example.field;

import org.example.adventurer.Adventurer;
import org.example.adventurer.Orientation;
import org.example.field.cell.ACell;
import org.example.field.cell.Mountain;
import org.example.field.cell.Plain;
import org.example.utils.exception.FileContentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class FieldTest {

    public List<String> getRuleList1() {
        return Arrays.asList(
                "C - 3 - 2",
                "M - 1 - 0",
                "A - Casper - 0 - 1 - N - GG"
        );
    }

    public List<String> getRuleList2() {
        return Arrays.asList(
                "C - 3 - 3",
                "M - 1 - 1",
                "A - Dumbo - 0 - 0 - S - GAAADADAGA",
                "A - Bob - 1 - 1 - W - AADDGG",
                "T - 2 - 2 - 1"
        );
    }

    public List<String> getRuleList3() {
        return Arrays.asList(
                "C - 3 - 2",
                "M - 1 - 0",
                "A - Simba - 0 - 0 - E - A"
        );
    }

    public List<String> getRuleList4() {
        return Arrays.asList(
                "C - 3 - 3",
                "M - 1 - 1",
                "A - Mickey - 0 - 0 - E - A",
                "T - 1 - 0 - 2"
        );
    }

    @Test
    public void testFieldRuleList1Init() throws FileContentException {
        final Field locField = new Field(getRuleList1());
        final ACell[][] locMap = locField.getMap();

        Assertions.assertEquals(2, locMap.length);
        Assertions.assertEquals(3, locMap[0].length);
        Assertions.assertInstanceOf(Mountain.class, locMap[0][1]);
        Assertions.assertInstanceOf(Plain.class, locMap[1][0]);
        Assertions.assertEquals(1, locField.getAdventurerList().size());

        final Adventurer locAdventurer = locMap[1][0].getAdventurer();

        Assertions.assertNotNull(locAdventurer);
        Assertions.assertEquals("Casper", locAdventurer.getName());
        Assertions.assertEquals(Orientation.NORTH, locAdventurer.getOrientation());
    }

    @Test
    public void testFieldRuleList2WithoutBobOnMountainInit() throws FileContentException {
        final Field locField = new Field(getRuleList2());
        final ACell[][] locMap = locField.getMap();

        Assertions.assertEquals(3, locMap.length);
        Assertions.assertEquals(3, locMap[0].length);
        Assertions.assertInstanceOf(Mountain.class, locMap[1][1]);
        Assertions.assertInstanceOf(Plain.class, locMap[1][0]);
        Assertions.assertEquals(1, locField.getAdventurerList().size());

        final Adventurer locAdventurerBob = locMap[1][1].getAdventurer();
        Assertions.assertNull(locAdventurerBob);

        final Adventurer locAdventurerDumbo = locMap[0][0].getAdventurer();
        Assertions.assertNotNull(locAdventurerDumbo);
        Assertions.assertEquals(locAdventurerDumbo, locField.getAdventurerList().getFirst());
        Assertions.assertEquals("Dumbo", locAdventurerDumbo.getName());
        Assertions.assertEquals(Orientation.SOUTH, locAdventurerDumbo.getOrientation());

        Assertions.assertEquals(0, locMap[0][0].getNbTreasure());
        Assertions.assertEquals(0, locMap[1][1].getNbTreasure());
        Assertions.assertEquals(1, locMap[2][2].getNbTreasure());
    }

    @Test
    public void testAdventurerMovingForwardPlain() throws FileContentException {
        final Field locField = new Field(getRuleList2());
        final ACell[][] locMap = locField.getMap();

        Assertions.assertInstanceOf(Plain.class, locMap[0][0]);
        Assertions.assertInstanceOf(Plain.class, locMap[0][1]);

        final Adventurer locAdventurerDumbo = locMap[0][0].getAdventurer();
        Assertions.assertNotNull(locAdventurerDumbo);
        Assertions.assertEquals("Dumbo", locAdventurerDumbo.getName());

        locAdventurerDumbo.setOrientation(Orientation.EAST);
        locField.moveForward(locAdventurerDumbo);

        Assertions.assertNull(locMap[0][0].getAdventurer());
        Assertions.assertEquals(locAdventurerDumbo, locMap[0][1].getAdventurer());
        Assertions.assertEquals(1, locAdventurerDumbo.getPosX());
        Assertions.assertEquals(0, locAdventurerDumbo.getPosY());
    }

    @Test
    public void testAdventurerMovingForwardMountain() throws FileContentException {
        final Field locField = new Field(getRuleList3());
        final ACell[][] locMap = locField.getMap();

        Assertions.assertInstanceOf(Plain.class, locMap[0][0]);
        Assertions.assertInstanceOf(Mountain.class, locMap[0][1]);

        final Adventurer locAdventurerSimba = locMap[0][0].getAdventurer();
        Assertions.assertNotNull(locAdventurerSimba);
        Assertions.assertEquals("Simba", locAdventurerSimba.getName());

        locField.moveForward(locAdventurerSimba);

        Assertions.assertEquals(locAdventurerSimba, locMap[0][0].getAdventurer());
        Assertions.assertNull(locMap[0][1].getAdventurer());
        Assertions.assertEquals(0, locAdventurerSimba.getPosX());
        Assertions.assertEquals(0, locAdventurerSimba.getPosY());
    }

    @Test
    public void testAdventurerMovingForwardMapBoarder() throws FileContentException {
        final Field locField = new Field(getRuleList3());
        final ACell[][] locMap = locField.getMap();

        Assertions.assertInstanceOf(Plain.class, locMap[0][0]);

        final Adventurer locAdventurerSimba = locMap[0][0].getAdventurer();
        Assertions.assertNotNull(locAdventurerSimba);
        Assertions.assertEquals("Simba", locAdventurerSimba.getName());

        locAdventurerSimba.setOrientation(Orientation.NORTH);
        locField.moveForward(locAdventurerSimba);

        Assertions.assertEquals(locAdventurerSimba, locMap[0][0].getAdventurer());
        Assertions.assertEquals(0, locAdventurerSimba.getPosX());
        Assertions.assertEquals(0, locAdventurerSimba.getPosY());
    }

    @Test
    public void testTreasureIsNotRecoveredIfAdventurerStaysOnIt() throws FileContentException {
        final Field locField = new Field(getRuleList4());
        final ACell[][] locMap = locField.getMap();

        Assertions.assertInstanceOf(Plain.class, locMap[0][0]);
        Assertions.assertInstanceOf(Plain.class, locMap[0][1]);
        Assertions.assertInstanceOf(Plain.class, locMap[0][1]);
        Assertions.assertEquals(2, locMap[0][1].getNbTreasure());

        final Adventurer locAdventurerMickey = locMap[0][0].getAdventurer();
        Assertions.assertNotNull(locAdventurerMickey);
        Assertions.assertEquals("Mickey", locAdventurerMickey.getName());
        Assertions.assertEquals(0, locAdventurerMickey.getNbTreasure());

        locAdventurerMickey.setOrientation(Orientation.EAST);
        locField.moveForward(locAdventurerMickey);

        Assertions.assertEquals(locAdventurerMickey, locMap[0][1].getAdventurer());
        Assertions.assertEquals(1, locAdventurerMickey.getNbTreasure());

        locAdventurerMickey.setOrientation(Orientation.NORTH);
        locField.moveForward(locAdventurerMickey);

        locAdventurerMickey.setOrientation(Orientation.SOUTH);
        locField.moveForward(locAdventurerMickey);

        locAdventurerMickey.setOrientation(Orientation.EAST);
        locField.moveForward(locAdventurerMickey);

        Assertions.assertEquals(locAdventurerMickey, locMap[0][2].getAdventurer());
        Assertions.assertEquals(1, locAdventurerMickey.getNbTreasure());
    }
}