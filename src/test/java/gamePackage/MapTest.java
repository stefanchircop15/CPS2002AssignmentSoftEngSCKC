package gamePackage;
import Exceptions.LocationIsOutOfRange;
import org.junit.*;

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
        assertFalse("Index is correct",thrown);
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











  /*  @Test
    public void treasureExists(){
        map.setTreasurePosition();
        Position setLocation = map.treasureLocation;
        char result = map.getTileType(setLocation.getX(),setLocation.getY());
        assertEquals('t', result);
    }
*/









}
