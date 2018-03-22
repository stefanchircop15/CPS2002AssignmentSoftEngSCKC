package gamePackage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class testClasstest {
    testClass instance;

    @Before
    public void setup() {
        instance = new testClass();
    }

    @After
    public void teardown() {
        instance = null;
    }

    @Test
    public void testSimpleGetMessage() {
        //Exercise
        String message = instance.getMessage();

        //Verify
        assertEquals("hey", message);
    }
}
