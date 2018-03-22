import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PatternRepeaterTest {

    private PatternRepeater patternRepeater;

    @Before
    public void setUp() {

        // set up class for testing
        patternRepeater = new PatternRepeater();
    }

    @After
    public void tearDown() {

        // clean up class
        patternRepeater = null;
    }

    @Test
    public void testRepeatOnce() {

        final String result = patternRepeater.repeatPattern('a', 1);
        Assert.assertEquals("a", result);
    }

}
