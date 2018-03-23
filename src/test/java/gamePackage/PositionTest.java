package gamePackage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    Position pos;

    @Before
    public void setup() { pos = new Position(); }

    @After
    public void teardown() { pos = null; }

    @Test
    public void testSetters() {
        pos.setX(10);
        pos.setY(10);
        assertEquals(10, pos.getX());
        assertEquals(10, pos.getY());

        int[] A = new int[] {20, 20};
        pos.setPosition(20, 20);
        assertEquals(A[0], pos.getPosition()[0]);
        assertEquals(A[1], pos.getPosition()[1]);
    }

    @Test
    public void testGetters() {
        pos.setX(10);
        pos.setY(10);
        int[] A = new int[] {10, 10};

        assertEquals(10, pos.getX());
        assertEquals(10, pos.getY());
        assertEquals(A[0], pos.getPosition()[0]);
        assertEquals(A[1], pos.getPosition()[1]);
    }
}