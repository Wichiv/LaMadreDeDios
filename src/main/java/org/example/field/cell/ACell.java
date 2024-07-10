package org.example.field.cell;

import org.example.adventurer.Adventurer;

public abstract class ACell {

    protected boolean accessible;

    /**
     * @return true if an adventurer can step into this cell
     */
    public boolean isAccessible() {
        return accessible;
    }

    /**
     * Event when an adventurer reaches this cell
     */
    public abstract void actionOnCell();

    public abstract Adventurer getAdventurer();

    public abstract void setAdventurer(final Adventurer parAdventurer);

    public int getNbTreasure() { return 0; }

    public void setNbTreasure(final int parNbTreasure) { }

    /**
     * @return a String of the cell in the output file format
     */
    public String toOutputFileFormatString(final int parPosX, final int parPosY) {
        return null;
    }

    /**
     * @return a String of the cell in the console format
     */
    public abstract String toConsoleFormatString();
}
