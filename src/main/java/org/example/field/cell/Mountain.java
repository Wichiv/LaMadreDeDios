package org.example.field.cell;

import org.example.adventurer.Adventurer;

public class Mountain extends ACell {

    public Mountain() {
        super.accessible = false;
    }

    @Override
    public void actionOnCell() { }

    @Override
    public Adventurer getAdventurer() {
        return null;
    }

    @Override
    public void setAdventurer(Adventurer parAdventurer) { }

    @Override
    public String toOutputFileFormatString(int parPosX, int parPosY) {
        return "M - " + parPosX + " - " + parPosY;
    }

    @Override
    public String toConsoleFormatString() {
        return "M";
    }
}
