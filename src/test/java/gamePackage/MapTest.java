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
    public void mapIsValid() {
        char tileType = map.getTileType(5,3);
        assertEquals('s', tileType);
    }
}
