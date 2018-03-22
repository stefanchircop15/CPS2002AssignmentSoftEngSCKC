package edu.uom.cps2002;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HelloWorldTests {

    HelloWorld helloWorld;

    @Before
    public void setup() {
        helloWorld = new HelloWorld();
    }

    @After
    public void teardown() {
        helloWorld = null;
    }

    @Test
    public void testSimpleGetMessage() {

        //Exercise
        String message = helloWorld.getMessage();

        //Verify
        assertEquals("Hello World!!", message);

    }

    @Test
    public void testGetMessageByName() {
        //Exercise
        String message = helloWorld.getMessage("Mike");

        //Verify
        assertEquals("Hello Mike!!", message);
    }

    @Test
    public void testGetMessageByNameNull() {
        //Exercise
        String message = helloWorld.getMessage(null);

        //Verify
        assertEquals("Hello World!!", message);
    }

    @Test
    public void testGetMessageByNameWilliam() {
        //Exercise
        String message = helloWorld.getMessage("William");

        //Verify
        assertEquals("Hello Your Majesty!!", message);
    }

    @Test
    public void testGetMessageWithNZero() {
        //Exercise
        String message = helloWorld.getMessage(0);

        //Verify
        assertEquals("", message);
    }

    @Test
    public void testGetMessageWithNLessThanZero() {
        //Exercise
        String message = helloWorld.getMessage(-1);

        //Verify
        assertEquals("", message);
    }

    @Test
    public void testGetMessageWithNBeingOne() {
        //Exercise
        String message = helloWorld.getMessage(1);

        //Verify
        assertEquals("Hello World!!", message);
    }

    @Test
    public void testGetMessageWithNBeingFive() {
        //Exercise
        String message = helloWorld.getMessage(5);

        //Verify
        assertEquals("Hello World!!Hello World!!Hello World!!Hello World!!Hello World!!", message);
    }

    @Test
    public void testGetMessageWithNBeingThree() {
        //Exercise
        String message = helloWorld.getMessage(3);

        //Verify
        assertEquals("Hello World!!Hello World!!Hello World!!", message);
    }


}
