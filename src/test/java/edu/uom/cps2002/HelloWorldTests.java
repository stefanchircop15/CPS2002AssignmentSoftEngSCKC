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
        String message = helloWorld.getMessageByName("Nigger");

        //Verify
        assertEquals("Hello Nigger!!", message);
    }

    @Test
    public void testGetMessageByName2() {
        //Exercise
        String message = helloWorld.getMessageByName(null);

        //Verify
        assertEquals(helloWorld.getMessage(), message);
    }

    @Test
    public void testGetMessageByName3() {
        //Exercise
        String message = helloWorld.getMessageByName("William");

        //Verify
        assertEquals("Hello Your Majesty!!", message);
    }
}
