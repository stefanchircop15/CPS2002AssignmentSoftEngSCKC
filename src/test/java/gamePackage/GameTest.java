package gamePackage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static gamePackage.Game.setNumberOfPlayers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {
    private Game game;

    @Before
    public void setUp() {
        //Set up new instance of game to test
        game = new Game();
    }

    @After
    public void tearDown() {
        game = null;
    }


    @Test
    public void setNumberOfPlayersCorrect() {
        boolean result = setNumberOfPlayers(5);
        assertEquals(true, result);
    }

    @Test
    public void setNumberOfPlayersIncorrectLessThanMin() {
        boolean result = setNumberOfPlayers(1);
        assertEquals(false, result);
    }

    @Test
    public void setNumberOfPlayersIncorrectMoreThanMax() {
        boolean result = setNumberOfPlayers(9);
        assertEquals(false, result);
    }

    @Test
    public void testDeleteFiles() {
        //Create two files, and then attempt to delete them
        try {
            File a = new File("a.html");
            PrintWriter writer = new PrintWriter(a, "UTF-8");
            writer.close();
            File b = new File("b.html");
            writer = new PrintWriter(b, "UTF-8");
            writer.close();
            File[] htmlFiles = {a, b};
            Game.deleteHTMLFiles(htmlFiles);
            assertTrue(!a.exists());
            assertTrue(!b.exists());
        }
        catch(FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
    }
}