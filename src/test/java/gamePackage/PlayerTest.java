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
        player = new Player();
        game = new Game();
        Game.map = new Map();
        Game.map.setMapSize(5, 40);
        try {
            Game.map.generate();
        } catch (MapSizeNotSet | LocationIsOutOfRange e) {
            throw e;
        }
    }

    @After
    public void teardown() { player = null; }

    @Test
    public void testMoveUniderictional() {
        int n = Game.map.getMapSize();
        Position p = new Position(player.getPosition().getX(), player.getPosition().getY());
        int[] expected = {0, 0};

        //Go to upper limit and try overstep three times
        for(int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();

            if(i < n - 1)
                expected[1]++;

            player.move('U');

            assertArrayEquals(player.getPosition().getCoordinates(), expected);
            expected = player.getPosition().getCoordinates();
        }

        //Go to right limit and try overstep three times
        for(int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();

            if(i < n - 1)
                expected[0]++;

            player.move('R');

            assertArrayEquals(player.getPosition().getCoordinates(), expected);
            expected = player.getPosition().getCoordinates();
        }

        //Go to left limit and try overstep three times
        for(int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();

            if(i < n - 1)
                expected[0]--;

            player.move('L');

            assertArrayEquals(player.getPosition().getCoordinates(), expected);
        }

        //Go to bottom limit and try overstep three times
        for(int i = 0; i < n + 3; i++) {
            expected = player.getPosition().getCoordinates();

            if(i < n - 1)
                expected[1]--;

            player.move('D');

            assertArrayEquals(player.getPosition().getCoordinates(), expected);
        }
    }

    @Test
    public void testSetPosition() {
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

            Game.map.setTileType(0, n - 1, 'T');
            player.setPosition(testTile);

            assertFalse(Arrays.equals(testTile.getCoordinates(), player.getPosition().getCoordinates()));
        }
        catch(LocationIsOutOfRange e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}