package gamePackage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class PositionTest {
    Position pos;

    @Before
    public void setup() {
        pos = new Position();
    }

    @After
    public void teardown() {
        pos = null;
    }

    @Test
    public void testSetters() {
        pos.setX(10);
        pos.setY(10);
        assertEquals(10, pos.getX());
        assertEquals(10, pos.getY());

        int[] A = new int[] {20, 20};
        pos.setCoordinates(20, 20);
        assertEquals(A[0], pos.getCoordinates()[0]);
        assertEquals(A[1], pos.getCoordinates()[1]);
    }

    @Test
    public void testConstructors() {
        int rand = new Random(System.currentTimeMillis() / 1000).nextInt();

        pos = new Position(new int[] {rand, rand});
        assertArrayEquals(pos.getCoordinates(), new int[] {rand, rand});

        pos = new Position(new int[] {rand, rand, rand});
        assertFalse(Arrays.equals(pos.getCoordinates(), new int[] {rand, rand}));

        pos = new Position(rand, rand);
        assertArrayEquals(pos.getCoordinates(), new int[] {rand, rand});

        pos = new Position();
        assertArrayEquals(pos.getCoordinates(), new int[] {0, 0});
    }

    @Test
    public void testGetters() {
        pos.setX(10);
        pos.setY(10);
        int[] A = new int[] {10, 10};

        assertEquals(10, pos.getX());
        assertEquals(10, pos.getY());
        assertEquals(A[0], pos.getCoordinates()[0]);
        assertEquals(A[1], pos.getCoordinates()[1]);
    }
}