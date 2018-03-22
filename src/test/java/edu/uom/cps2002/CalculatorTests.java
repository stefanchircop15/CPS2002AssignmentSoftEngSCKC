package edu.uom.cps2002;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTests {

    public Calculator calculator;

    @Before
    public void setup() {
        calculator = new Calculator();
    }

    @After
    public void tearDown() {
        calculator = null;
    }

    @Test
    public void testAddWithTwoPositiveNumbers() {
        //Exercise
        int result = calculator.add(5,2);

        //Assert
        assertEquals(7, result);
    }

    @Test
    public void testAddWithTwoNegativeNumbers() {
        //Exercise
        int result = calculator.add(-5,-2);

        //Assert
        assertEquals(-7, result);
    }

    @Test
    public void testAddWithANegativeAndPositiveNumber() {
        //Exercise
        int result = calculator.add(5,-2);

        //Assert
        assertEquals(3, result);
    }



}
