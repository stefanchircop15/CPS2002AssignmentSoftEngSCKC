package gamePackage;

import Exceptions.LocationIsOutOfRange;
import Exceptions.MapSizeNotSet;

import java.util.ArrayList;

import java.util.List;

class Map {
    private static Map mapInstance = null;
    private int size;
    private static int type = 0;

    char[][] map;
    Position treasureLocation;
    ArrayList<Position> listOfLocationLeadingToTreasure = new ArrayList<>();

    private Map() {
        this.treasureLocation = new Position();
        this.size = 0;
        this.map = null;
    }

    static Map getMapInstance(){
        if(mapInstance == null)
            mapInstance = new Map();

        return mapInstance;
    }

    static void setMapType(int t) {
        type = t;
    }

    static void tearDownMap(){
        mapInstance = null;
    }

    boolean setMapSize(int noOfPlayers, int mapSize) {
        if (mapSize > 50 || mapSize < 5 || (mapSize < 8 && noOfPlayers > 4) || noOfPlayers < 2 || noOfPlayers > 8) {
            System.out.println("Invalid map Size");
            return false;
        }
        size = mapSize;
        return true;
    }

    int getMapSize() {
        return this.size;
    }

    ArrayList<Position> generate() throws MapSizeNotSet, LocationIsOutOfRange {
        //create sure paths
        Position previous = new Position();
        Position current;
        Position next;
        Position[] possibleLocations = new Position[4];

        int pathSize;

        createEmptyMap();
        try {
            generateTreasureLocation();
        } catch (MapSizeNotSet | LocationIsOutOfRange e) {
            throw e;
        }

        for (int i = 0; i < getMapSize(); i++) {
            pathSize = (int) (Math.random() * getMapSize());
            current = treasureLocation;
            for (int j = 0; j < pathSize; j++) {

                if (!mapRestricttion()) {
                    possibleLocations[0] = new Position(current.getX() - 1, current.getY());
                    possibleLocations[1] = new Position(current.getX() + 1, current.getY());
                    possibleLocations[2] = new Position(current.getX(), current.getY() - 1);
                    possibleLocations[3] = new Position(current.getX(), current.getY() + 1);

                    next = possibleLocations[(int) Math.floor(Math.random() * 4)];
                    returnLocationCheck valuesAfterValidation = checkIfNextLocationIsIdeal(j, next, previous, current);
                    j = valuesAfterValidation.getJ();
                    current = valuesAfterValidation.getCurrent();
                    previous = valuesAfterValidation.getPrevious();
                }
            }
        }
        return listOfLocationLeadingToTreasure;
    }


    boolean mapRestricttion (){
        if (type == 2 && listOfLocationLeadingToTreasure.size() >= 0.64 * getMapSize() * getMapSize())
            return true;
        else
            return false;
    }

    returnLocationCheck checkIfNextLocationIsIdeal (int j, Position next, Position previous, Position current){
        if (next.getX() == previous.getX() && next.getY() == previous.getY()) {
            j--;
            return new returnLocationCheck(j, current,previous);
        }
        try {
            if (getTileType(next.getX(), next.getY()) != 'T') {
                setTileType(next.getX(), next.getY(), 'G');
                listOfLocationLeadingToTreasure.add(next);
            }
        } catch (LocationIsOutOfRange e) {
            j--;
            return new returnLocationCheck(j, current,previous);
        }
        return new returnLocationCheck(j,next, current);
    }

    void generateTreasureLocation() throws MapSizeNotSet, LocationIsOutOfRange {
        if (getMapSize() == 0)
            throw new MapSizeNotSet("Map Size is not Set");
        this.treasureLocation.setCoordinates((int) (Math.floor(Math.random() * (getMapSize()))), (int) (Math.floor(Math.random() * (getMapSize()))));
        try {
            setTileType(treasureLocation.getX(), treasureLocation.getY(), 'T');
        } catch (LocationIsOutOfRange e) {
            throw e;
        }
    }

    char getTileType(int x, int y) throws LocationIsOutOfRange {
        if (x < 0 || x > this.getMapSize() - 1 || y < 0 || y > this.getMapSize() - 1 || map == null)
            throw new LocationIsOutOfRange("Location is out of reach.");
        return this.map[x][y];
    }

    void setTileType(int x, int y, char input) throws LocationIsOutOfRange {
        if (x < 0 || x > this.getMapSize() - 1 || y < 0 || y > this.getMapSize() - 1 || map == null)
            throw new LocationIsOutOfRange("Location is out of reach.");
        this.map[x][y] = input;
    }

    void createEmptyMap() {
        this.map = new char[size][size];
    }

    class returnLocationCheck{
        private int j;
        private Position current;
        private Position previous;

        returnLocationCheck(int nextInc , Position cur, Position prev){
            j=nextInc;
            current= cur;
            previous = prev;
        }
        int getJ(){return j;}
        Position getCurrent(){return current;}
        Position getPrevious(){return previous;}
    }
}
