package gamePackage;
import Exceptions.LocationIsOutOfRange;
import Exceptions.MapSizeNotSet;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeTrue;

public class MapTest {
    private Map map;

    @Before
    public void setUp() {

        // set up class for testing
        map = new Map();
    }

    @After
    public void tearDown() {

        // clean up class
        map = null;
    }

    //Map size setter tests
    @Test
    public void setMapSizeInsufficientPlayers(){
        int noOfPlayers = 1;
        int mapSize = 5;
        boolean result = map.setMapSize(noOfPlayers,mapSize);
        assertFalse("Insufficient amount of players ", result);
    }

    @Test
    public void setMapSizeExtraPlayers(){
        int noOfPlayers = 9;
        int mapSize = 30;
        boolean result = map.setMapSize(noOfPlayers,mapSize);
        assertFalse("Excessive amount of players ", result);
    }

    @Test
    public void setMapSizeCorrectlyFor2To4(){
        int noOfPlayers = 2;
        int mapSize = 5;
        boolean result = map.setMapSize(noOfPlayers,mapSize);
        assertTrue("Map size set correctly ", result);
    }

    @Test
    public void setMapSizeCorrectlyFor5To8(){
        int noOfPlayers = 7;
        int mapSize = 50;
        boolean result = map.setMapSize(noOfPlayers,mapSize);
        assertTrue("Map size set correctly ", result);
    }

    @Test
    public void setMapSizeTooSmall(){
        int noOfPlayers = 2;
        int mapSize = 4;
        boolean result = map.setMapSize(noOfPlayers,mapSize);
        assertFalse("Map size too Small ", result);

    }

    @Test
    public void setMapSizeTooSmallFor5To8(){
        int noOfPlayers = 5;
        int mapSize = 7;
        boolean result = map.setMapSize(noOfPlayers,mapSize);
        assertFalse("Map size too Small For 5 to 8 players. ", result);
    }

    @Test
    public void setMapSizeTooLarge(){
        int noOfPlayers = 8;
        int mapSize = 51;
        boolean result = map.setMapSize(noOfPlayers,mapSize);
        assertFalse("Map size too Large. ", result);

    }

    //Map size getter Test
    @Test
    public void getMapSizeAssumingSetterWorks() {
        int noOfPlayers = 5;
        int mapSize = 42;
        assumeTrue(map.setMapSize(noOfPlayers, mapSize));
        assertEquals(mapSize, map.getMapSize());
    }

    @Test
    public void setMapSizeAssumingGetterWorks(){
        int noOfPlayers = 8;
        int mapSize = 32;
        map.setMapSize(noOfPlayers,mapSize);
        int result =map.getMapSize();
        assertEquals(32, result);

    }

    //Map get tile type tests
    @Test
    public void getTileTypeLocationExceedsRange() {
        boolean thrown = false;
        map.setMapSize(4,9);
        map.createEmptyMap();
        int x = 0;
        int y = 10;
        try {
            map.getTileType(x,y);
        } catch (LocationIsOutOfRange e) {
            thrown = true;
        }
        assertTrue("Index is out of bounds",thrown);
    }

    @Test
    public void getTileTypeLocationNegative() {
        boolean thrown = false;
        map.setMapSize(7,30);
        map.createEmptyMap();
        int x = -1;
        int y = 25;
        try {
            map.getTileType(x,y);
        } catch (LocationIsOutOfRange e) {
            thrown = true;
        }
        assertTrue("Index is out of bounds",thrown);
    }

    @Test
    public void getTileTypeLocationCorrect() {
        boolean thrown = false;
        map.setMapSize(7,30);
        map.createEmptyMap();
        int x = 15;
        int y = 25;
        try {
            map.getTileType(x,y);
        } catch (LocationIsOutOfRange e) {
            thrown = true;
        }
        assertFalse("Index is correct",thrown);
    }

    @Test
    public void getMapTileTypeReturnTest() {
        char tileType = 'c';
        char result = 'f';
        int x = 15;
        int y = 25;
        int noOfPlayers = 7;
        int mapSize = 30;
        map.setMapSize(noOfPlayers,mapSize);
        map.createEmptyMap();
        try {
            map.setTileType(x,y,tileType);
        } catch (LocationIsOutOfRange e) {
            assumeNoException(e);
        }
        try {
            result = map.getTileType(x,y);
        } catch (LocationIsOutOfRange e) {
            assumeNoException(e);
        }
        assertEquals(tileType, result);
    }

    //Map set tile type tests
    @Test
    public void setTileTypeLocationExceedsRange() {
        boolean thrown = false;
        char tileType = 't';
        map.setMapSize(4,9);
        map.createEmptyMap();
        int x = 0;
        int y = 10;
        try {
            map.setTileType(x,y,tileType);
        } catch (LocationIsOutOfRange e) {
            thrown = true;
        }
        assertTrue("Index is out of bounds",thrown);
    }

    @Test
    public void setTileTypeLocationNegative() {
        boolean thrown = false;
        char tileType = 't';
        map.setMapSize(7,30);
        map.createEmptyMap();
        int x = -1;
        int y = 25;
        try {
            map.setTileType(x,y,tileType);
        } catch (LocationIsOutOfRange e) {
            thrown = true;
        }
        assertTrue("Index is out of bounds",thrown);
    }

    @Test
    public void setTileTypeLocationCorrect() {
        boolean thrown = false;
        char tileType = 't';
        map.setMapSize(7,30);
        map.createEmptyMap();
        int x = 15;
        int y = 25;
        try {
            map.setTileType(x,y,tileType);
        } catch (LocationIsOutOfRange e) {
            thrown = true;
        }
        assertFalse("Tile is set with correct location.",thrown);
    }

    @Test
    public void setTileTypeLocationMapNotCreated() {
        boolean thrown = false;
        char tileType = 't';
        map.setMapSize(4,9);
        int x = 0;
        int y = 6;
        try {
            map.setTileType(x,y,tileType);
        } catch (LocationIsOutOfRange e) {
            thrown = true;
        }
        assertTrue("Map Not Created",thrown);
    }

    //Create Map
    @Test
    public void createMap() {
        int noOfPlayers = 8;
        int mapSize = 15;
        char [][] expected = new char[mapSize][mapSize];;
        map.setMapSize(noOfPlayers,mapSize);
        map.createEmptyMap();
        char [][] result = map.map;
        assertArrayEquals( expected, result );
    }

    //Generate Treasure Location
    @Test
    public void generateTreasureMapSizeNotSet() {
        boolean thrown = false;
        try {
            map.generateTreasureLocation();
        } catch (MapSizeNotSet e) {
            thrown = true;
        }catch (LocationIsOutOfRange e){
            assumeNoException(e);
        }
        assertTrue("Treasure location generation but map size not set.",thrown);
    }

    @Test
    public void generateTreasureMapSizeSetMapNotCreated() {
        boolean thrown = false;
        map.setMapSize(7,30);
        try {
            map.generateTreasureLocation();
        } catch (MapSizeNotSet e) {
            assumeNoException(e);
        }catch (LocationIsOutOfRange e){
            thrown = true;
        }
        assertTrue("Treasure location generation but map not created",thrown);
    }

    @Test
    public void generateTreasureSuccessfully () {
        char result = 'a';
        map.setMapSize(7,30);
        map.createEmptyMap();
        try {
            map.generateTreasureLocation();
        } catch (MapSizeNotSet | LocationIsOutOfRange e) {
            assumeNoException(e);
        }
        try{
            result=map.getTileType(map.treasureLocation.getX(),map.treasureLocation.getY());
        }catch (LocationIsOutOfRange e){
            assumeNoException(e);
        }
        assertEquals('T',result);
    }

    //Generate fill random


    @Test
    public void fillRemainingLocMapWithNoTreasureCheckLocationsAreAllFilled () {
        int noOfPlayers = 8;
        int mapSize = 50;
        map.setMapSize(noOfPlayers,mapSize);
        map.createEmptyMap();
        try {
            map.fillRemainingEmptyLocations();
        } catch (LocationIsOutOfRange e) {
            assumeNoException(e);
        }
        char result = 'a';
        for(int i = 0; i < map.getMapSize(); i++){
            for(int j = 0; j < map.getMapSize(); j++){
                try{
                    result = map.getTileType(i,j);
                }catch (LocationIsOutOfRange e){
                    assumeNoException(e);
                }
                assertTrue("All tiles are either Grass, or Water", result == 'G' || result == 'B');
            }
        }
    }

    @Test
    public void fillRemainingLoMapWithNoTreasureAndMapNotCreated() {
        boolean thrown = false;
        map.setMapSize(7,30);
        try {
            map.fillRemainingEmptyLocations();
        } catch (LocationIsOutOfRange e) {
            thrown = true;
        }
        assertTrue("Map Not Created", thrown);

    }

    @Test
    public void generateMapSizeNotSet () {
        boolean thrown = false;
        try {
            map.generate();
        } catch (MapSizeNotSet | LocationIsOutOfRange e) {
            thrown = true;
        }
        assertTrue("Map size not Set in map generate", thrown);
    }

    @Test
    public void generateMapSizeSetIncorrectly () {
        boolean thrown = false;
        int noOfPlayers = 8;
        int mapSize = 51;
        map.setMapSize(noOfPlayers,mapSize);
        try {
            map.generate();
        } catch (MapSizeNotSet | LocationIsOutOfRange e) {
            thrown = true;
        }
        assertTrue("Map Not Created", thrown);
    }

    @Test
    public void generateMapCheckEveryLocationIsFilled () {
        int noOfPlayers = 8;
        int mapSize = 50;
        map.setMapSize(noOfPlayers,mapSize);
        try {
            map.generate();
            map.fillRemainingEmptyLocations();
        } catch (MapSizeNotSet | LocationIsOutOfRange e) {
            assumeNoException(e);
        }
        char result = 'a';
        for(int i = 0; i < map.getMapSize(); i++){
            for(int j = 0; j < map.getMapSize(); j++){
                try{
                    result = map.getTileType(i,j);
                }catch (LocationIsOutOfRange e){
                    assumeNoException(e);
                }
                assertTrue("All tiles are either Treasure, Grass, or Water",result == 'T' || result == 'G' || result == 'B');
            }
        }
    }

    @Test
    public void isIdealLocationNextEqualsPreviousCheckJIsDecremented () {
        Position previous = new Position(1,2);
        Position current = new Position(2,1);
        Position next = new Position(1,2);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(1,valuesAfterValidation.getJ());
    }

    @Test
    public void isIdealLocationNextEqualsPreviousCheckPreviousRemainsTheSame() {
        Position previous = new Position(1,2);
        Position current = new Position(2,1);
        Position next = new Position(1,2);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(previous,valuesAfterValidation.getPrevious());
    }

    @Test
    public void isIdealLocationNextEqualsPreviousCheckCurrentRemainsTheSame() {
        Position previous = new Position(1,2);
        Position current = new Position(2,1);
        Position next = new Position(1,2);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(current,valuesAfterValidation.getCurrent());
    }

    @Test
    public void isIdealLocationNextIsOutOfRangeJIsDecremented () {
        Position previous = new Position(1,2);
        Position current = new Position(0,2);
        Position next = new Position(-1,2);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(1,valuesAfterValidation.getJ());
    }

    @Test
    public void isIdealLocationNextIsOutOfRangeJCheckPreviousRemainsTheSame() {
        Position previous = new Position(1,2);
        Position current = new Position(0,2);
        Position next = new Position(-1,2);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(previous,valuesAfterValidation.getPrevious());
    }

    @Test
    public void isIdealLocationNextIsOutOfRangeJCheckCurrentRemainsTheSameNegativeJ() {
        Position previous = new Position(1,2);
        Position current = new Position(0,2);
        Position next = new Position(-1,2);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(current,valuesAfterValidation.getCurrent());
    }

    @Test
    public void isIdealLocationNextIsOutOfRangeJCheckCurrentRemainsTheSameExceedingSize() {
        int noOfPlayers = 5; int mapSize = 25;
        map.setMapSize(noOfPlayers,mapSize);
        createMap();
        Position previous = new Position(23,23);
        Position current = new Position(23,24);
        Position next = new Position(23,25);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(current,valuesAfterValidation.getCurrent());
    }

    @Test
    public void isIdealLocationNextSucessfulMoveCheckK() {
        map.listOfLocationLeadingToTreasure = new ArrayList(1);
        int noOfPlayers = 5; int mapSize = 25;
        map.setMapSize(noOfPlayers,mapSize);
        createMap();
        Position previous = new Position(7,8);
        Position current = new Position(7,9);
        Position next = new Position(7,10);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(2,valuesAfterValidation.getJ());
    }

    @Test
    public void isIdealLocationNextSucessfulMoveCheckCurrent() {
        map.listOfLocationLeadingToTreasure = new ArrayList(1);
        int noOfPlayers = 5; int mapSize = 25;
        map.setMapSize(noOfPlayers,mapSize);
        createMap();
        Position previous = new Position(7,8);
        Position current = new Position(7,9);
        Position next = new Position(7,10);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(current,valuesAfterValidation.getPrevious());
    }

    @Test
    public void isIdealLocationNextSucessfulMoveCheckNext() {
        map.listOfLocationLeadingToTreasure = new ArrayList(1);
        int noOfPlayers = 5; int mapSize = 25;
        map.setMapSize(noOfPlayers,mapSize);
        createMap();
        Position previous = new Position(7,8);
        Position current = new Position(7,9);
        Position next = new Position(7,10);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(next,valuesAfterValidation.getCurrent());
    }


















}
