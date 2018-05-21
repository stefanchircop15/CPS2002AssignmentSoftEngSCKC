package gamePackage;

import Exceptions.LocationIsOutOfRange;

import java.util.ArrayList;
import java.util.Random;

public class HazardousMapBuilder extends MapBuilder {
    private Map instance;
    private boolean success = false;

    HazardousMapBuilder() throws LocationIsOutOfRange {
        super(2); //Hazardous = 2
        instance = Map.getMapInstance();
        try {
            fillRemainingEmptyLocations();
        }
        catch(LocationIsOutOfRange e) {
            throw e;
        }
    }

    @Override
    boolean getSuccess() {
        return success;
    }

    @Override
    public void fillRemainingEmptyLocations() throws LocationIsOutOfRange {
        Random ran = new Random();
        char[] tileTypes = {'G', 'B'};
        int noOfBlueTiles = 0;
        int maxBlueTiles = (int) (0.35 * instance.getMapSize() * instance.getMapSize());
        int minBlueTiles = (int) (0.25 * instance.getMapSize() * instance.getMapSize());
        ArrayList<Position> greenTiles = new ArrayList<>();
        try {
            for (int i = 0; i < instance.getMapSize(); i++) {
                for (int j = 0; j < instance.getMapSize(); j++) {

                    if (instance.getTileType(i, j) != 'T' && instance.getTileType(i, j) != 'G') {
                        int type = ran.nextInt(2);
                        if (noOfBlueTiles >= maxBlueTiles)
                            type = 0;
                        else if (type == 1)
                            noOfBlueTiles++;

                        instance.setTileType(i, j, tileTypes[type]);
                        if (type == 0) {
                            int[] pos = {i, j};
                            greenTiles.add(new Position(pos));
                        }
                    }

                }
            }
            blueTilesReachQuota(noOfBlueTiles, minBlueTiles, maxBlueTiles, greenTiles);
        } catch (LocationIsOutOfRange e) {
            throw e;
        }

    }

     int blueTilesReachQuota(int noOfBlueTiles,int  minBlueTiles, int maxBlueTiles,  ArrayList<Position> greenTiles ) throws LocationIsOutOfRange{
        Random ran = new Random();
        int randomIndex;
        while(noOfBlueTiles < minBlueTiles) {
            randomIndex = ran.nextInt(greenTiles.size());
            Map.getMapInstance().setTileType(greenTiles.get(randomIndex).getX(), greenTiles.get(randomIndex).getY(), 'B');
            greenTiles.remove(randomIndex);
            noOfBlueTiles++;
        }
        if (noOfBlueTiles >= minBlueTiles && noOfBlueTiles <= maxBlueTiles)
            success = true;
        else
            success = false;
        return noOfBlueTiles;
    }
}
