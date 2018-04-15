package gamePackage;

import Exceptions.LocationIsOutOfRange;
import Exceptions.MapSizeNotSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player;
    Game game;

    @Before
    public void setup() throws MapSizeNotSet, LocationIsOutOfRange {
        //Setup to test Player - including game instance, map generation
        player = new Player();
        game = new Game();
        Game.map = new Map();
        Game.map.setMapSize(5, 40);
        try {
            Game.map.generate();
            Game.map.fillRemainingEmptyLocations();
        } catch (MapSizeNotSet | LocationIsOutOfRange e) {
            throw e;
        }
    }

    @After
    public void teardown() { player = null; }

    @Test
    public void testMoveUnidirectional() {
        //Test unidirectional movement, U, D, L, R
        int n = Game.map.getMapSize();
        Position p = new Position(player.getPosition().getX(), player.getPosition().getY());
        int[] expected = {0, 0};

        //Try to go to upper limit and try overstep three times - unless there is water along the way
        for(int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();

            if(y < n - 1)
                expected[1]++;

            player.move('U');

            //If fell in water, assert starting position, else assert expected position
            try {
                if(x >= 0 && x < n && y + 1 >= 0 && y + 1 < n && Game.map.getTileType(x, y + 1) != 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), expected);
                else if(x >= 0 && x < n && y + 1 >= 0 && y + 1 < n && Game.map.getTileType(x, y + 1) == 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), player.getStart().getCoordinates());
            }
            catch(LocationIsOutOfRange e) {
                System.out.println(e.getMessage());
                fail();
            }
        }

        //Go to right limit and try overstep three times - unless there is water along the way
        for(int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();

            if(x < n - 1)
                expected[0]++;

            player.move('R');

            //If fell in water, assert starting position, else assert expected position
            try {
                if(x + 1 >= 0 && x + 1 < n && y >= 0 && y < n && Game.map.getTileType(x + 1, y) != 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), expected);
                else if(x + 1 >= 0 && x + 1 < n && y >= 0 && y < n && Game.map.getTileType(x + 1, y) == 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), player.getStart().getCoordinates());
            }
            catch(LocationIsOutOfRange e) {
                System.out.println(e.getMessage());
                fail();
            }
        }

        //Go to left limit and try overstep three times - unless there is water along the way
        for(int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();

            if(x > 0)
                expected[0]--;

            player.move('L');

            //If fell in water, assert starting position, else assert expected position
            try {
                if(x - 1 >= 0 && x - 1 < n && y >= 0 && y < n && Game.map.getTileType(x - 1, y) != 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), expected);
                else if(x - 1 >= 0 && x - 1 < n && y >= 0 && y < n && Game.map.getTileType(x - 1, y) == 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), player.getStart().getCoordinates());
            }
            catch(LocationIsOutOfRange e) {
                System.out.println(e.getMessage());
                fail();
            }
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
                if(x >= 0 && x < n && y - 1 >= 0 && y - 1 < n && Game.map.getTileType(x, y - 1) != 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), expected);
                else if(x >= 0 && x < n && y - 1 >= 0 && y - 1 < n && Game.map.getTileType(x, y - 1) == 'B')
                    assertArrayEquals(player.getPosition().getCoordinates(), player.getStart().getCoordinates());
            }
            catch(LocationIsOutOfRange e) {
                System.out.println(e.getMessage());
                fail();
            }
        }
    }

    @Test
    public void testSetPosition() {
        //Test setPosition constraints
        int n = Game.map.getMapSize();
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

            Game.map.setTileType(0, 0, 'G');
            player.setPosition(origin);

            Game.map.setTileType(0, n - 1, 'G');
            player.setPosition(testTile);

            assertArrayEquals(player.getPosition().getCoordinates(), testTile.getCoordinates());

            player.setPosition(origin);

            Game.map.setTileType(0, n - 1, 'B');
            player.setPosition(testTile);

            assertFalse(Arrays.equals(testTile.getCoordinates(), player.getPosition().getCoordinates()));
            assertTrue(Arrays.equals(player.getPosition().getCoordinates(), player.getStart().getCoordinates()));

            Game.map.setTileType(0, n - 1, 'T');
            player.setPosition(testTile);

            assertTrue(Arrays.equals(testTile.getCoordinates(), player.getPosition().getCoordinates()));
        }
        catch(LocationIsOutOfRange e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}