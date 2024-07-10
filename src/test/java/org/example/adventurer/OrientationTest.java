package org.example.adventurer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrientationTest {

    @Test
    public void testLeftRotation() {
        Orientation locOrientation = Orientation.NORTH;

        locOrientation = locOrientation.getOrientationByRotationLeft();
        Assertions.assertEquals(Orientation.WEST, locOrientation);

        locOrientation = locOrientation.getOrientationByRotationLeft();
        Assertions.assertEquals(Orientation.SOUTH, locOrientation);

        locOrientation = locOrientation.getOrientationByRotationLeft();
        Assertions.assertEquals(Orientation.EAST, locOrientation);

        locOrientation = locOrientation.getOrientationByRotationLeft();
        Assertions.assertEquals(Orientation.NORTH, locOrientation);
    }

    @Test
    public void testRightRotation() {
        Orientation locOrientation = Orientation.NORTH;

        locOrientation = locOrientation.getOrientationByRotationRight();
        Assertions.assertEquals(Orientation.EAST, locOrientation);

        locOrientation = locOrientation.getOrientationByRotationRight();
        Assertions.assertEquals(Orientation.SOUTH, locOrientation);

        locOrientation = locOrientation.getOrientationByRotationRight();
        Assertions.assertEquals(Orientation.WEST, locOrientation);

        locOrientation = locOrientation.getOrientationByRotationRight();
        Assertions.assertEquals(Orientation.NORTH, locOrientation);
    }
}