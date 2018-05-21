package gamePackage;
import Exceptions.LocationIsOutOfRange;
import Exceptions.MapSizeNotSet;
import org.junit.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeTrue;

public class MapTest {
    private Map map;

    @Before
    public void setUp() {
        Map.tearDownMap();
        map = null;
        map = Map.getMapInstance();
        File mapsDir = new File("maps");
        if (!mapsDir.exists())
            mapsDir.mkdir();
    }


    @After
    public void tearDown() {
        // clean up class
        Map.tearDownMap();
        map = null;
    }

    @Test
    public void testSingletonUniqueness() {
        Map newInstance1 = Map.getMapInstance();
        Map newInstance2 = Map.getMapInstance();
        assertEquals(System.identityHashCode(newInstance1), System.identityHashCode(newInstance2));
    }

    @Test
    public void testSingletonOverwriteTest() {
        Map newInstance1 = Map.getMapInstance();
        Map newInstance2 = Map.getMapInstance();
        newInstance2.setMapSize(2, 8);
        newInstance1.setMapSize(4, 5);
        assertEquals(newInstance1.getMapSize(), newInstance2.getMapSize());
    }

    //Map size setter tests
    @Test
    public void setMapSizeInsufficientPlayers(){
        map = Map.getMapInstance();
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
        char [][] expected = new char[mapSize][mapSize];
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
            new MapDirector(1).buildMap();
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
            new MapDirector(1).buildMap();
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
            new MapDirector(1).buildMap();
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
        map.createEmptyMap();
        Position previous = new Position(23,23);
        Position current = new Position(23,24);
        Position next = new Position(23,25);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(current,valuesAfterValidation.getCurrent());
    }

    @Test
    public void isIdealLocationNextSucessfulMoveCheckK() {
        map.listOfLocationLeadingToTreasure = new ArrayList<>();
        int noOfPlayers = 5; int mapSize = 25;
        map.setMapSize(noOfPlayers,mapSize);
        map.createEmptyMap();
        Position previous = new Position(7,8);
        Position current = new Position(7,9);
        Position next = new Position(7,10);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(2,valuesAfterValidation.getJ());
    }

    @Test
    public void isIdealLocationNextSucessfulMoveCheckCurrent() {
        map.listOfLocationLeadingToTreasure = new ArrayList<>();
        int noOfPlayers = 5; int mapSize = 25;
        map.setMapSize(noOfPlayers,mapSize);
        map.createEmptyMap();
        Position previous = new Position(7,8);
        Position current = new Position(7,9);
        Position next = new Position(7,10);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(current,valuesAfterValidation.getPrevious());
    }

    @Test
    public void isIdealLocationNextSucessfulMoveCheckNext() {
        map.listOfLocationLeadingToTreasure = new ArrayList<>();
        int noOfPlayers = 5; int mapSize = 25;
        map.setMapSize(noOfPlayers,mapSize);
        map.createEmptyMap();
        Position previous = new Position(7,8);
        Position current = new Position(7,9);
        Position next = new Position(7,10);
        int loopCounter = 2;

        Map.returnLocationCheck valuesAfterValidation = map.checkIfNextLocationIsIdeal(loopCounter,next,previous,current);
        assertEquals(next,valuesAfterValidation.getCurrent());
    }

    @Test
    public void testHazardousMap() {
        map.setMapSize(5,10);
        map.createEmptyMap();

        boolean success = false;

        try {
            success = new MapDirector(2).buildMap();
        }
        catch(LocationIsOutOfRange e) {
            assumeNoException(e);
        }

        assertTrue(success);
    }

    @Test
    public void testHazardousMapLocationOutOfRange() {
        boolean thrown = false;
        map.setMapSize(5,10);

        try {
            new HazardousMapBuilder();
        }
        catch(LocationIsOutOfRange e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void testMapRestriction() {
        boolean restrictionApplied;
        map.setMapSize(5,8);
        map.createEmptyMap();


        try {
            new HazardousMapBuilder();
        }
        catch(LocationIsOutOfRange e) {
            assumeNoException(e);
        }

        map.listOfLocationLeadingToTreasure = new ArrayList<>(49);
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                map.listOfLocationLeadingToTreasure.add(new Position(i,j));
            }
        }

        restrictionApplied = map.mapRestricttion();
        assertTrue(restrictionApplied);
    }

    @Test
    public void testMapRestrictionNotApplied() {
        boolean restrictionApplied;
        map.setMapSize(5,50);
        map.createEmptyMap();
        map.listOfLocationLeadingToTreasure = new ArrayList<>(200);
        try {
            new MapDirector(2).buildMap();
        }
        catch(LocationIsOutOfRange e) {
            assumeNoException(e);
        }
        restrictionApplied = map.mapRestricttion();
        assertFalse(restrictionApplied);
    }

    @Test
    public void fillBlueTilesExplicitly() {
        int noOfBlueTiles = 15;
        int minBlueTiles = 30;
        int maxNofOfBlueTiles = 40;
        int result = 0;
        ArrayList<Position> greenTiles = new ArrayList<>(100);
        map.setMapSize(5,50);
        map.createEmptyMap();

        greenTiles = new ArrayList<>(49);
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                greenTiles.add(new Position(i,j));
            }
        }
        try {
            new MapDirector(2).buildMap();
            HazardousMapBuilder instance = new HazardousMapBuilder();
            result = instance.blueTilesReachQuota(noOfBlueTiles, minBlueTiles,maxNofOfBlueTiles, greenTiles );
        }
        catch(LocationIsOutOfRange e) {
            assumeNoException(e);
        }
        assertEquals(result, 30);
    }

    @Test
    public void fillBlueTilesExplicitlyIncorrectMaxMinValues() {
        int noOfBlueTiles = 15;
        int minBlueTiles = 30;
        int maxNofOfBlueTiles = 10;
        boolean result = true ;
        ArrayList<Position> greenTiles = new ArrayList<>(100);
        map.setMapSize(5,50);
        map.createEmptyMap();

        greenTiles = new ArrayList<>(49);
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                greenTiles.add(new Position(i,j));
            }
        }
        try {
            new MapDirector(2).buildMap();
            HazardousMapBuilder instance = new HazardousMapBuilder();
            instance.blueTilesReachQuota(noOfBlueTiles, minBlueTiles,maxNofOfBlueTiles, greenTiles );
            result = instance.getSuccess();

        }
        catch(LocationIsOutOfRange e) {
            assumeNoException(e);
        }
        assertFalse(result);
    }

    @Test
    public void testSafeMap() {
        map.setMapSize(5,10);
        map.createEmptyMap();

        boolean success = false;

        try {
            success = new MapDirector(1).buildMap();
        }
        catch(LocationIsOutOfRange e) {
            assumeNoException(e);
        }

        assertTrue(success);
    }

    @Test
    public void testSafeMapGetSuccessMethodCorrectNoOfBlueTiles() {
        boolean result = false;
        int noOfBlueTiles = 5;
        map.setMapSize(5,10);
        map.createEmptyMap();

        try {
            SafeMapBuilder instance = new SafeMapBuilder();
            instance.setNoOfBlueTiles(noOfBlueTiles);
            result = instance.getSuccess();

        }catch (LocationIsOutOfRange e){
            assumeNoException(e);
        }

        assertTrue(result);
    }

    @Test
    public void testSafeMapGetSuccessMethodIncorrectNoOfBlueTiles() {
        boolean result = false;
        int noOfBlueTiles = 15;
        map.setMapSize(4,8);
        map.createEmptyMap();

        try {
            SafeMapBuilder instance = new SafeMapBuilder();
            instance.setNoOfBlueTiles(noOfBlueTiles);
            result = instance.getSuccess();

        }catch (LocationIsOutOfRange e){
            assumeNoException(e);
        }

        assertFalse(result);
    }

















}
