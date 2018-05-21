package gamePackage;

import Exceptions.LocationIsOutOfRange;
import Exceptions.MapSizeNotSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;

public class PlayerTest {
    private Player player;
    private Game game;

    @Before
    public void setup() throws MapSizeNotSet, LocationIsOutOfRange {
        //Setup to test Player - including game instance, map generation
        player = new Player();
        game = new Game();

        //Game.getMap() = new Map();
        Game.getMap().setMapSize(5, 40);
        try {
            Game.getMap().generate();
            new MapDirector(1).buildMap();
        } catch (MapSizeNotSet | LocationIsOutOfRange e) {
            throw e;
        }
    }

    @After
    public void teardown() { player = null; }

    @Test
    public void testMoveUnidirectionalU() {
        //Test unidirectional movement, U
        int n = Game.getMap().getMapSize();
        int[] expected;

        //Try to go to upper limit and try overstep three times - unless there is water along the way
        for (int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();

            if (y < n - 1)
                expected[1]++;

            player.move('U');

            //If fell in water, assert starting position, else assert expected position
            try {
                if (x >= 0 && x < n && y + 1 >= 0 && y + 1 < n && Game.getMap().getTileType(x, y + 1) != 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), expected);
                else if (x >= 0 && x < n && y + 1 >= 0 && y + 1 < n && Game.getMap().getTileType(x, y + 1) == 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), player.getStart().getCoordinates());
            } catch (LocationIsOutOfRange e) {
                System.out.println(e.getMessage());
                fail();
            }
        }
    }

    @Test
    public void testMoveUnidirectionalR() {
        //Test unidirectional movement, R
        int n = Game.getMap().getMapSize();
        int[] expected;

        //Go to right limit and try overstep three times - unless there is water along the way
        for (int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();

            if (x < n - 1)
                expected[0]++;

            player.move('R');

            //If fell in water, assert starting position, else assert expected position
            try {
                if (x + 1 >= 0 && x + 1 < n && y >= 0 && y < n && Game.getMap().getTileType(x + 1, y) != 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), expected);
                else if (x + 1 >= 0 && x + 1 < n && y >= 0 && y < n && Game.getMap().getTileType(x + 1, y) == 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), player.getStart().getCoordinates());
            } catch (LocationIsOutOfRange e) {
                System.out.println(e.getMessage());
                fail();
            }
        }
    }

    @Test
    public void testMoveUnidirectionalL() {
        //Test unidirectional movement, L
        int n = Game.getMap().getMapSize();
        int[] expected;

        try {
            Game.getMap().setTileType(n - 1, n - 1, 'G');
            player.setPosition(new Position(n - 1, n - 1));
        }
        catch(LocationIsOutOfRange e) {
            System.out.println(e.getMessage());
            fail();
        }

        //Go to left limit and try overstep three times - unless there is water along the way
        for (int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();

            if (x > 0)
                expected[0]--;

            player.move('L');

            //If fell in water, assert starting position, else assert expected position
            try {
                if (x - 1 >= 0 && x - 1 < n && y >= 0 && y < n && Game.getMap().getTileType(x - 1, y) != 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), expected);
                else if (x - 1 >= 0 && x - 1 < n && y >= 0 && y < n && Game.getMap().getTileType(x - 1, y) == 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), player.getStart().getCoordinates());
            } catch (LocationIsOutOfRange e) {
                System.out.println(e.getMessage());
                fail();
            }
        }
    }

    @Test
    public void testMoveUnidirectionalD() {
        //Test unidirectional movement, D
        int n = Game.getMap().getMapSize();
        int[] expected;

        try {
            Game.getMap().setTileType(n - 1, n - 1, 'G');
            player.setPosition(new Position(n - 1, n - 1));
        }
        catch(LocationIsOutOfRange e) {
            System.out.println(e.getMessage());
            fail();
        }

        //Go to bottom limit and try overstep three times - unless there is water along the way
        for(int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();

            if(y > 0)
                expected[1]--;

            player.move('D');

            //If fell in water, assert starting position, else assert expected position
            try {
                if(x >= 0 && x < n && y - 1 >= 0 && y - 1 < n && Game.getMap().getTileType(x, y - 1) != 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), expected);
                else if(x >= 0 && x < n && y - 1 >= 0 && y - 1 < n && Game.getMap().getTileType(x, y - 1) == 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), player.getStart().getCoordinates());
            }
            catch(LocationIsOutOfRange e) {
                System.out.println(e.getMessage());
                fail();
            }
        }
    }

    @Test
    public void testMoveOutOfRange() {
        Position expected = new Position(0,0);
        try {
            Game.getMap().setTileType(0, 0, 'G');
            player.setPosition(new Position(0, 0));
            player.move('L');
        }
        catch(LocationIsOutOfRange e) {
            assumeNoException(e);
        }

        assertArrayEquals(player.getPosition().getCoordinates(), expected.getCoordinates());
    }

    @Test
    public void testMoveIncorrectParameter(){
        Position expected = new Position(player.getPosition().getX(),player.getPosition().getY());
        player.move('z');
        assertArrayEquals(player.getPosition().getCoordinates(),expected.getCoordinates());
    }
    @Test
    public void testGetVisited() {
        try {
            player.setPosition(new Position(0, 0));
        }
        catch(LocationIsOutOfRange e) {
            System.out.println(e.getMessage());
        }

        player.move('R');

        ArrayList<Position> expected = new ArrayList<>();
        expected.add(new Position(0, 0));
        expected.add(new Position(1, 0));

        assertArrayEquals(player.getVisited().get(0).getCoordinates(), expected.get(0).getCoordinates());
        assertArrayEquals(player.getVisited().get(1).getCoordinates(), expected.get(1).getCoordinates());
    }

    @Test
    public void testSetPosition() {
        //Test setPosition constraints
        int n = Game.getMap().getMapSize();
        int[] expected = {0, 0};

        //Try setting out of bounds
        try {
            player.setPosition(new Position(n, n));
            fail();
        }
        catch (LocationIsOutOfRange e){
            System.out.println(e.getMessage());
            assertArrayEquals(expected, player.getPosition().getCoordinates());
        }

        //Try setting on all types of tiles
        try {
            Position origin = new Position(0, 0);
            Position testTile = new Position(0, n - 1);

            Game.getMap().setTileType(0, 0, 'G');
            player.setPosition(origin);

            Game.getMap().setTileType(0, n - 1, 'G');
            player.setPosition(testTile);

            assertArrayEquals(player.getPosition().getCoordinates(), testTile.getCoordinates());

            player.setPosition(origin);

            Game.getMap().setTileType(0, n - 1, 'B');
            player.setPosition(testTile);

            assertFalse(Arrays.equals(testTile.getCoordinates(), player.getPosition().getCoordinates()));
            assertTrue(Arrays.equals(player.getPosition().getCoordinates(), player.getStart().getCoordinates()));

            Game.getMap().setTileType(0, n - 1, 'T');
            player.setPosition(testTile);

            assertTrue(Arrays.equals(testTile.getCoordinates(), player.getPosition().getCoordinates()));
        }
        catch(LocationIsOutOfRange e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void testAddVisited() {
        Position a = new Position(0, 0);
        Position b = new Position(1, 0);
        Position c = new Position(0, 5);
        player.addVisited(a);
        player.addVisited(b);
        player.addVisited(c);

        ArrayList<Position> expected = new ArrayList<>();
        expected.add(a);
        expected.add(b);
        expected.add(c);

        assertArrayEquals(player.getVisited().toArray(), expected.toArray());
    }
}
