package org.example.adventurer;

import java.util.List;

public class Adventurer {

    final private String name;

    private int posX;

    private int posY;

    private Orientation orientation;

    private final List<Movement> movementList;

    private int nbTreasure;

    public Adventurer(final String parName, final int parPosX, final int parPosY, final Orientation parOrientation, final List<Movement> parMovementList) {
        this.name = parName;
        this.posX = parPosX;
        this.posY = parPosY;
        this.orientation = parOrientation;
        this.movementList = parMovementList;
        this.nbTreasure = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(final int parPosX) {
        this.posX = parPosX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(final int parPosY) {
        this.posY = parPosY;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(final Orientation parOrientation) {
        this.orientation = parOrientation;
    }

    public List<Movement> getMovementList() {
        return movementList;
    }

    public int getNbTreasure() {
        return nbTreasure;
    }

    public void setNbTreasure(final int parNbTreasure) {
        this.nbTreasure = parNbTreasure;
    }

    public String toOutputFileFormatString() {
        return "A - " + getName() + " - " + getPosX() + " - " + getPosY() + " - " + getOrientation().getConstName() + " - " + getNbTreasure();
    }

    public String toConsoleFormatString() {
        return "A(" + name + ")";
    }
}
