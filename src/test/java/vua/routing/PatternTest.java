package vua.routing;

import org.junit.Test;

import static org.junit.Assert.*;

public class PatternTest {


    @Test
    public void testPatternCreation() {

        Pattern p = new Pattern("{hello}");

        assertEquals(p.getType(), PatternType.PARAM);
    }

    @Test
    public void testOptionalPattern() {
        Pattern p;

        p = new Pattern("{hello?}");
        assertTrue(p.isOptional());
        p = new Pattern("{hello}");
        assertFalse(p.isOptional());
    }

    @Test
    public void testRegexPattern() {
        Pattern p;

        p = new Pattern("{user}-{age}");
        assertEquals(p.getType(), PatternType.REGEX);
    }

    @Test
    public void testPatternMatch() {
        Pattern p;

        p = new Pattern("{user}-{age}");
        MatchResult result = p.match("ionut-10");

        System.out.println(result.getResult());
    }
}