package org.example.adventurer;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Orientation {
    NORTH('N', 0, -1),
    EAST('E', 1, 0),
    SOUTH('S', 0, 1),
    WEST('O', -1, 0);

    private static final Map<Character, Orientation> ORIENTATION_BY_CONST_NAME_MAP = Arrays.stream(values()).collect(Collectors.toMap(Orientation::getConstName, x -> x));
    private static final Orientation[] ORIENTATION_ARRAY = values();

    private final char constName;
    private final int forwardX;
    private final int forwardY;

    Orientation(final char parConstName, final int parForwardX, final int parForwardY) {
        this.constName = parConstName;
        this.forwardX = parForwardX;
        this.forwardY = parForwardY;
    }

    public char getConstName() {
        return constName;
    }

    public int getForwardX() {
        return forwardX;
    }

    public int getForwardY() {
        return forwardY;
    }

    public static Orientation getOrientationByConstName(final char parConstName) {
        return ORIENTATION_BY_CONST_NAME_MAP.get(parConstName);
    }

    public Orientation getOrientationByRotationLeft() {
        return ORIENTATION_ARRAY[(ordinal() - 1 + ORIENTATION_ARRAY.length) % ORIENTATION_ARRAY.length];
    }

    public Orientation getOrientationByRotationRight() {
        return ORIENTATION_ARRAY[(ordinal() + 1) % ORIENTATION_ARRAY.length];
    }
}
