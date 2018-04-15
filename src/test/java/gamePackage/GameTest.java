package gamePackage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static gamePackage.Game.setNumberOfPlayers;
import static org.junit.Assert.assertEquals;

public class GameTest {
    private Game game;

    @Before
    public void setUp() {

        // set up class for testing
        game = new Game();
    }

    @After
    public void tearDown() {

        // clean up class
        game = null;
    }


    @Test
    public void setNumberOfPlayersTrue() {
        boolean result = setNumberOfPlayers(5);
        assertEquals(true, result);
    }

    @Test
    public void setNumberOfPlayersFalse() {
        boolean result = setNumberOfPlayers(1);
        assertEquals(false, result);
    }
    @Test
    public void setNumberOfPlayersFalse2() {
        boolean result = setNumberOfPlayers(9);
        assertEquals(false, result);
    }








}