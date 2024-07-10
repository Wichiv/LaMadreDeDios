package org.example.adventurer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class MovementTest {

    @Test
    public void testCorrectMovementSequenceIsCreated() {
        final String locStringMovement = "AGADDAGAA";
        final List<Movement> locExpectedMovementList = Arrays.asList(
                Movement.FORWARD,
                Movement.LEFT_ROTATION,
                Movement.FORWARD,
                Movement.RIGHT_ROTATION,
                Movement.RIGHT_ROTATION,
                Movement.FORWARD,
                Movement.LEFT_ROTATION,
                Movement.FORWARD,
                Movement.FORWARD
        );

        final List<Movement> locResult = Movement.getMovementListByConstNameList(locStringMovement);
        Assertions.assertIterableEquals(locExpectedMovementList, locResult);
    }
}