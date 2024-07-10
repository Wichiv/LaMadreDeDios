package org.example.adventurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Movement {
    FORWARD('A'),
    LEFT_ROTATION('G'),
    RIGHT_ROTATION('D');

    private static final Map<Character, Movement> MOVEMENT_BY_CONST_NAME_MAP = Arrays.stream(values()).collect(Collectors.toMap(Movement::getConstName, x -> x));

    private final char constName;

    Movement(final char parConstName) {
        this.constName = parConstName;
    }

    public char getConstName() {
        return constName;
    }

    public static Movement getMovementByConstName(final char parConstName) {
        return MOVEMENT_BY_CONST_NAME_MAP.get(parConstName);
    }

    public static List<Movement> getMovementListByConstNameList(final String parConstNameList) {
        final List<Movement> locMovementList = new ArrayList<>();

        for (char locConstName : parConstNameList.toCharArray()) {
            final Movement locMovement = getMovementByConstName(locConstName);
            if (locMovement != null)
                locMovementList.add(locMovement);
        }

        return locMovementList;
    }
}
