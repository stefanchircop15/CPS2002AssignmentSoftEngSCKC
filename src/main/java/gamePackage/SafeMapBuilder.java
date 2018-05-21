package gamePackage;

import Exceptions.LocationIsOutOfRange;

import java.util.Random;

public class SafeMapBuilder extends MapBuilder {
    private Map instance;
    private int noOfBlueTiles = 0;
    private int maxBlueTiles;

    SafeMapBuilder() throws LocationIsOutOfRange {
        super(1); //Safe = 1
        instance = Map.getMapInstance();
        maxBlueTiles = (int) (0.1 * instance.getMapSize() * instance.getMapSize());

        try {
            fillRemainingEmptyLocations();
        }
        catch(LocationIsOutOfRange e) {
            throw e;
        }
    }

    @Override
    boolean getSuccess() {
        return greenTilesReachQuota();
    }

    private boolean greenTilesReachQuota() {
        if(noOfBlueTiles > 0.1 * Map.getMapInstance().getMapSize() * Map.getMapInstance().getMapSize())
            return false;
        else
            return true;
    }

    @Override
    public void fillRemainingEmptyLocations() throws LocationIsOutOfRange {
        char[] tileTypes = {'G', 'B'};
        Random ran = new Random();

        for (int i = 0; i < instance.getMapSize(); i++) {
            for (int j = 0; j < instance.getMapSize(); j++) {
                try {
                    if (instance.getTileType(i, j) != 'T' && instance.getTileType(i, j) != 'G') {
                        int type = ran.nextInt(2);
                        if(noOfBlueTiles >= maxBlueTiles)
                            type = 0;
                        else if(type == 1)
                            noOfBlueTiles++;

                        instance.setTileType(i, j, tileTypes[type]);
                    }
                } catch (LocationIsOutOfRange e) {
                    throw e;
                }
            }
        }
    }

    void setNoOfBlueTiles(int n){noOfBlueTiles= n;}
}
