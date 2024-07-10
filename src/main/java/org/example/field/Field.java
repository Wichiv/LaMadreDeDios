package org.example.field;

import org.example.adventurer.Adventurer;
import org.example.adventurer.Movement;
import org.example.adventurer.Orientation;
import org.example.field.cell.ACell;
import org.example.field.cell.Mountain;
import org.example.field.cell.Plain;
import org.example.utils.exception.FileContentException;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final ACell[][] map;

    private final List<Adventurer> adventurerList;

    public Field(final List<String> parRuleList) throws FileContentException {
        int locLength = 0;
        int locHeight = 0;

        // We retrieve first the dimensions of the map
        for (final String locRule : parRuleList) {
            if (locRule.charAt(0) == 'C') {
                final String[] locMapDataList = locRule.split(" - ");
                if (locMapDataList.length < 2)
                    break;

                locLength = Integer.parseInt(locMapDataList[1].trim());
                locHeight = Integer.parseInt(locMapDataList[2].trim());
                break;
            }
        }

        if (locLength == 0 || locHeight == 0) {
            final String locMessage = String.format("The dimensions of the map are incorrect (%d x %d). The trial cannot begin.",
                    locLength,
                    locHeight);
            throw new FileContentException(locMessage);
        }

        // Init class fields
        map = createPlainMapCells(locLength, locHeight);
        adventurerList = new ArrayList<>();

        buildOptionalRulesFromRuleList(parRuleList);
        checkAdventurerSpawn();
    }

    //region Building

    /**
     * @param parLength length of the map
     * @param parHeight height of the map
     * @return return a map of plain cells
     */
    public ACell[][] createPlainMapCells(final int parLength, final int parHeight) {
        final ACell[][] locMapCells = new ACell[parHeight][parLength];
        for (int i = 0; i < parHeight; i++) {
            for (int j = 0; j < parLength; j++) {
                locMapCells[i][j] = new Plain(0);
            }
        }
        return locMapCells;
    }

    /**
     * We retrieve here all the building rules that aren't map dimensions rule
     * @param parRuleList a list of rules
     */
    public void buildOptionalRulesFromRuleList(final List<String> parRuleList) {
        for (final String locRule : parRuleList) {
            switch (locRule.charAt(0)) {
                case 'A' -> createAdventurerFromRule(locRule);
                case 'M'-> createMountainFromRule(locRule);
                case 'T'-> createTreasureFromRule(locRule);
            }
        }
    }

    private void createAdventurerFromRule(final String parRule) {
        final String[] locAdventurerDataList = parRule.split(" - ");
        if (locAdventurerDataList.length < 6)
            return;

        final String locName = locAdventurerDataList[1].trim();
        final int locPosX = Integer.parseInt(locAdventurerDataList[2].trim());
        final int locPosY = Integer.parseInt(locAdventurerDataList[3].trim());
        if (locPosX < 0 || locPosY < 0 || locPosX >= map[0].length || locPosY >= map.length)
            return;

        final Orientation locOrientation = Orientation.getOrientationByConstName(locAdventurerDataList[4].trim().charAt(0));
        if (locOrientation == null)
            return;

        final List<Movement> locMovementList = Movement.getMovementListByConstNameList(locAdventurerDataList[5].trim());

        adventurerList.add(new Adventurer(locName, locPosX, locPosY, locOrientation, locMovementList));
    }

    private void createMountainFromRule(final String parRule) {
        final String[] locMountainDataList = parRule.split(" - ");
        if (locMountainDataList.length < 3)
            return;

        final int locPosX = Integer.parseInt(locMountainDataList[1].trim());
        final int locPosY = Integer.parseInt(locMountainDataList[2].trim());
        if (locPosX < 0 || locPosY < 0 || locPosX >= map[0].length || locPosY >= map.length)
            return;

        map[locPosY][locPosX] = new Mountain();
    }

    private void createTreasureFromRule(final String parRule) {
        final String[] locTreasureDataList = parRule.split(" - ");
        if (locTreasureDataList.length < 4)
            return;

        final int locPosX = Integer.parseInt(locTreasureDataList[1].trim());
        final int locPosY = Integer.parseInt(locTreasureDataList[2].trim());
        if (locPosX < 0 || locPosY < 0 || locPosX >= map[0].length || locPosY >= map.length)
            return;

        final int locNbTreasure = Integer.parseInt(locTreasureDataList[3].trim());

        map[locPosY][locPosX].setNbTreasure(locNbTreasure);
    }

    /**
     * We make sure that all adventurers spawn in an accessible cell
     * If it isn't the case, we remove them of the trial
     */
    public void checkAdventurerSpawn() {
        for (int i = 0; i < adventurerList.size(); i++) {
            final Adventurer locAdventurer = adventurerList.get(i);
            if (!map[locAdventurer.getPosY()][locAdventurer.getPosX()].isAccessible()) {
                adventurerList.remove(locAdventurer);
                i--;
            } else {
                map[locAdventurer.getPosY()][locAdventurer.getPosX()].setAdventurer(locAdventurer);
            }
        }
    }

    //endregion

    //region Treasure hunt iteration

    public void startTrial() {
        while (!isOver()) {
            step();
        }
    }

    /**
     * @return true if there is no more movement to apply
     */
    public boolean isOver() {
        int locNbWaitingAdventurer = adventurerList.size();

        for (final Adventurer locAdventurer : adventurerList) {
            if (locAdventurer.getMovementList().isEmpty())
                locNbWaitingAdventurer--;
        }

        return locNbWaitingAdventurer == 0;
    }

    /**
     * Execute 1 iteration of Movement for each adventurer on the field
     */
    public void step() {
        for (final Adventurer locAdventurer : adventurerList) {
            if (locAdventurer.getMovementList().isEmpty())
                continue;

            final Movement locMovement = locAdventurer.getMovementList().removeFirst();
            switch (locMovement) {
                case LEFT_ROTATION:
                    locAdventurer.setOrientation(locAdventurer.getOrientation().getOrientationByRotationLeft());
                    break;
                case RIGHT_ROTATION:
                    locAdventurer.setOrientation(locAdventurer.getOrientation().getOrientationByRotationRight());
                    break;
                case FORWARD:
                    moveForward(locAdventurer);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Move an adventurer on the cell forward depending on his orientation
     * If he cannot move, he stays on his current cell and does nothing
     * Otherwise, he moves and {@link ACell#actionOnCell()} is called on the destination cell
     * @param parAdventurer to move
     */
    public void moveForward(final Adventurer parAdventurer) {
        final int locDestinationX = parAdventurer.getPosX() + parAdventurer.getOrientation().getForwardX();
        final int locDestinationY = parAdventurer.getPosY() + parAdventurer.getOrientation().getForwardY();
        if (locDestinationX < 0 || locDestinationX >= map[0].length || locDestinationY < 0 || locDestinationY >= map.length)
            return;

        final ACell locDestinationCell = map[locDestinationY][locDestinationX];
        if (!locDestinationCell.isAccessible() || locDestinationCell.getAdventurer() != null)
            return;

        map[parAdventurer.getPosY()][parAdventurer.getPosX()].setAdventurer(null);
        parAdventurer.setPosX(locDestinationX);
        parAdventurer.setPosY(locDestinationY);
        locDestinationCell.setAdventurer(parAdventurer);

        locDestinationCell.actionOnCell();
    }

    //endregion

    // region Printing

    public String toOutputFileFormatString() {
        final StringBuilder locResult = new StringBuilder();

        locResult.append("C - ")
                .append(map[0].length)
                .append(" - ")
                .append(map.length)
                .append("\n");

        final StringBuilder locMountains = new StringBuilder();
        final StringBuilder locTreasures = new StringBuilder();
        final StringBuilder locAdventurers = new StringBuilder();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                final ACell locCell = map[i][j];

                if (locCell instanceof Mountain)
                    locMountains.append(locCell.toOutputFileFormatString(j, i)).append("\n");

                if (locCell.getNbTreasure() > 0)
                    locTreasures.append("T - ").append(j).append(" - ").append(i).append(" - ").append(locCell.getNbTreasure()).append("\n");
            }
        }

        for (final Adventurer locAdventurer : adventurerList) {
            locAdventurers.append(locAdventurer.toOutputFileFormatString()).append("\n");
        }

        locResult.append(locMountains);

        if (!locTreasures.isEmpty()) {
            locResult.append("# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants}\n");
            locResult.append(locTreasures);
        }

        if (!locAdventurers.isEmpty()) {
            locResult.append("# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe vertical} - {Orientation} - {Nb. trésors ramassés}\n");
            locResult.append(locAdventurers);
        }

        return locResult.toString();
    }

    public String toConsoleFormatString() {
        StringBuilder locResult = new StringBuilder();
        final String[][] locStringArray = new String[map.length][map[0].length];
        final int[] maxSizeCellArray = new int[map[0].length];


        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                final String locString = map[i][j].toConsoleFormatString();
                locStringArray[i][j] = locString;

                if (maxSizeCellArray[j] < locString.length())
                    maxSizeCellArray[j] = locString.length();
            }
        }

        for (int i = 0; i < locStringArray.length; i++) {
            for (int j = 0; j < locStringArray[i].length; j++) {
                locStringArray[i][j] = locStringArray[i][j] + " ".repeat(maxSizeCellArray[j] - locStringArray[i][j].length());
            }
            locResult.append(String.join(" ", locStringArray[i])).append("\n");
        }

        return locResult.toString();
    }

    // endregion

    //region Getter

    public ACell[][] getMap() {
        return map;
    }

    public List<Adventurer> getAdventurerList() {
        return adventurerList;
    }

    //endregion
    
    
}
