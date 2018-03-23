package gamePackage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    public void setMapSizePass1(){
        boolean result = map.setMapSize(2,5);
        assertEquals(true, result);
    }

    @Test
    public void setMapSizePass2(){
        boolean result = map.setMapSize(5,50);
        assertEquals(true, result);
    }

    @Test
    public void setMapSizeFail1(){
        boolean result = map.setMapSize(2,4);
        assertEquals(false, result);
    }

    @Test
    public void setMapSizeFail2(){
        boolean result = map.setMapSize(2,51);
        assertEquals(false, result);
    }

    @Test
    public void setMapSizeFail3(){
        boolean result = map.setMapSize(8,51);
        assertEquals(false, result);
    }


    @Test
    public void mapIsValid() {
        char tileType = map.getTileType(5,3);
        assertEquals('s', tileType);
    }

    @Test
    public void mapTestGetter(){
        map.size = 5;
        assertEquals(map.getMapSize(), 5);
    }









}
