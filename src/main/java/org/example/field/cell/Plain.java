package org.example.field.cell;

import org.example.adventurer.Adventurer;

public class Plain extends ACell {

    private int nbTreasure;

    private Adventurer adventurer;

    public Plain(final int parNbTreasure) {
        super.accessible = true;
        this.nbTreasure = parNbTreasure;
    }

    @Override
    public void actionOnCell() {
        if (nbTreasure < 1 || adventurer == null)
            return;

        nbTreasure--;
        adventurer.setNbTreasure(adventurer.getNbTreasure() + 1);
    }

    @Override
    public int getNbTreasure() {
        return nbTreasure;
    }

    @Override
    public void setNbTreasure(int parNbTreasure) {
        nbTreasure = parNbTreasure;
    }

    @Override
    public Adventurer getAdventurer() {
        return adventurer;
    }

    @Override
    public void setAdventurer(Adventurer parAdventurer) {
        adventurer = parAdventurer;
    }

    @Override
    public String toConsoleFormatString() {
        if (adventurer != null)
            return adventurer.toConsoleFormatString();

        if (nbTreasure > 0)
            return "T(" + nbTreasure + ")";

        return ".";
    }
}
