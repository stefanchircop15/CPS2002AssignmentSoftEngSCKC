package gamePackage;

import Exceptions.HTMLGenerationFailure;
import Exceptions.LocationIsOutOfRange;
import Exceptions.MapSizeNotSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static gamePackage.Game.*;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;

public class GameTest {
    private Game game;
    private Scanner sc;

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
    public void testStartGameIncorrectPlayers() {
        Game.map = new Map();
        String instructions = "1\n2\n";
        ByteArrayInputStream in = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(in);

        Game.sc = new Scanner(in);
        assertFalse(Game.initialisePlayersAndMap());
    }

    @Test
    public void testStartGameIncorrectPlayersNonInteger() {
        Game.map = new Map();
        String instructions = "a\n2\n";
        ByteArrayInputStream in = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(in);

        Game.sc = new Scanner(in);
        assertFalse(Game.initialisePlayersAndMap());
    }

    @Test
    public void testStartGameCorrectPlayersIncorrectMapSize() {
        Game.map = new Map();
        String instructions = "2\n4\n";
        ByteArrayInputStream in = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(in);

        Game.sc = new Scanner(in);
        assertFalse(Game.initialisePlayersAndMap());
    }

    @Test
    public void testStartGameCorrectPlayersIncorrectMapSizeNonInteger() {
        Game.map = new Map();
        String instructions = "2\na\n";
        ByteArrayInputStream in = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(in);

        Game.sc = new Scanner(in);
        assertFalse(Game.initialisePlayersAndMap());
    }

    @Test
    public void testStartGameCorrectPlayersCorrectMap() {
        Game.map = new Map();
        String instructions = "2\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(in);

        Game.sc = new Scanner(in);
        assertTrue(Game.initialisePlayersAndMap());
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

    @Test
    public void testHTMLGeneration() {
        //Attempt to generate maps for each player
        String instructions = "2\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(in);

        Game.sc = new Scanner(in);
        Game.map = new Map();
        Game.initialisePlayersAndMap();
        try {
            Game.generateHTMLFiles();
        }
        catch(HTMLGenerationFailure e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSetColour() {
        //Set colour according to tile type
        Game.map = new Map();
        Game.map.setMapSize(5, 10);

        try {
            Game.map.generate();
        }
        catch(MapSizeNotSet | LocationIsOutOfRange e) {
            assumeNoException(e);
        }

        try {
            String g = "33cc33", b = "33ccff", t = "ffff00", d = "808080";

            map.setTileType(0, 0, 'G');
            assertEquals(g, Game.setColour(new Position(0, 0), g, t, b));

            map.setTileType(0, 0, 'T');
            assertEquals(t, Game.setColour(new Position(0, 0), g, t, b));

            map.setTileType(0, 0, 'B');
            assertEquals(b, Game.setColour(new Position(0, 0), g, t, b));

            map.setTileType(0, 0, 'S');
            assertEquals(d, Game.setColour(new Position(0, 0), g, t, b));
        }
        catch(LocationIsOutOfRange e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSetColourOutOfRange() {
        //Set colour according to tile type
        Game.map = new Map();
        Game.map.setMapSize(5, 10);

        try {
            Game.map.generate();
        }
        catch(MapSizeNotSet | LocationIsOutOfRange e) {
            assumeNoException(e);
        }


        String g = "33cc33", b = "33ccff", t = "ffff00", d = "808080";

        assertEquals(Game.setColour(new Position(-1, -1), g, t , b), "Out of Range");
    }

    @Test
    public void testDeleteFilesNonExistent() {
        //Create two files, and then attempt to delete them twice
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

            Game.deleteHTMLFiles(htmlFiles);
            assertTrue(!a.exists());
            assertTrue(!b.exists());
        }
        catch(FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGameTurn() {
        Game.map = new Map();
        try {
            Game.map.setMapSize(2, 5);
            Game.map.generate();
            Game.map.fillRemainingEmptyLocations();
            Game.map.setTileType(0, 0, 'T');
            Game.map.setTileType(1, 1, 'B');
            Game.map.setTileType(1, 0, 'G');
            Game.map.setTileType(2,0, 'G');
            Game.map.treasureLocation = new Position(0, 0);

            Player p1 = new Player();
            Player p2 = new Player();
            p1.setStart(new Position(1, 0));
            p2.setStart(new Position(1, 0));

            Game.players = new Player[2];
            Game.players[0] = p1;
            Game.players[1] = p2;
        }
        catch(MapSizeNotSet | LocationIsOutOfRange e) {
            assumeNoException(e);
        }

        String instructions = "R\nR\nL\nL\nU\nU\nD\nD\nD\nD\nL\nL\n";
        ByteArrayInputStream in = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(in);

        Game.sc = new Scanner(in);

        int[] expected = {1, 1};
        int[] winners = {0, 0};

        Game.playTurns(winners);
        assertArrayEquals(winners, expected);
    }

    @Test
    public void testEndGame() {
        Game.map = new Map();
        try {
            Game.map.setMapSize(2, 5);
            Game.map.generate();
            Game.map.fillRemainingEmptyLocations();
            Game.map.setTileType(0, 0, 'T');
            Game.map.setTileType(1, 1, 'B');
            Game.map.setTileType(1, 0, 'G');
            Game.map.setTileType(2,0, 'G');
            Game.map.treasureLocation = new Position(0, 0);

            Player p1 = new Player();
            Player p2 = new Player();
            p1.setStart(new Position(1, 0));
            p2.setStart(new Position(1, 0));

            Game.players = new Player[2];
            Game.players[0] = p1;
            Game.players[1] = p2;
        }
        catch(MapSizeNotSet | LocationIsOutOfRange e) {
            assumeNoException(e);
        }

        int[] winners = {1, 0};

        String instructions = "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(in);

        Game.sc = new Scanner(in);

        assertEquals(Game.endGame(winners), 0);
    }

    @Test
    public void testStartGame() {
        String instructions = "2\n5\nR\nR\nL\nL\nU\nU\nD\nD\nD\nD\nL\nL\n\n";
        ByteArrayInputStream in = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(in);

        Game.sc = new Scanner(in);

        int[] expected = {1, 1};

        Game.mapFlag = 1;
        Game.main(null);
        assertArrayEquals(Game.winners, expected);
    }

}