package edu.uom.cps2002;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTests {
    Calculator calculator;

    @Before
    public void setup() {
        calculator = new Calculator();
    }

    @After
    public void takedown() {
        calculator = null;
    }

    @Test
    public void testAdd() {
        //Exercise
        int answer = calculator.add(5,3);

        //Verify
        assertEquals(8, answer);
    }

    @Test
    public void testSubtract() {
        //Exercise
        int answer = calculator.subtract(5, 3);

        //Verify
        assertEquals(2, answer);
    }

    @Test
    public void testMultiply() {
        //Exercise
        int answer = calculator.multiply(5, 3);

        //Verify
        assertEquals(15, answer);
    }

    @Test
    public void testDivide() {
        //Exercise
        int answer = calculator.divide(5, 3);

        //Verify
        assertEquals(5/3, answer);
    }

    @Test
    public void testDivide0() {
        //Exercise
        int answer = calculator.divide(5, 0);

        //Verify
        assertEquals(-999, answer);
    }
}
