package gamePackage;
import Exceptions.LocationIsOutOfRange;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    public void setTreasurePositionCorrect() {
        boolean thrown = false;
        map.setMapSize(8,48);
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
    public void mapTestSetterTrue(){
        map.setTileType(4,3,'a');
        char result = map.map[4][3];
        assertEquals('a', result);
    }


    @Test
    public void mapSizeTestGetter(){
        map.size = 5;
        assertEquals(map.getMapSize(), 5);
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
