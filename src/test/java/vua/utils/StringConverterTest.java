package vua.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringConverterTest {

    @Test
    public void testInt() {
        try {
            assertEquals(11, StringConverter.convert("11", int.class));
            assertEquals(-11, StringConverter.convert("-11", int.class));
            assertEquals(42, StringConverter.convert("42", Integer.class));
            assertEquals(null, StringConverter.convert(null, Integer.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDouble() {
        try {
            assertEquals(11d, StringConverter.convert("11", double.class));
            assertEquals(-11d, StringConverter.convert("-11", double.class));
            assertEquals(42d, StringConverter.convert("42", Double.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFloat() {
        try {
            assertEquals(11.0f, StringConverter.convert("11", float.class));
            assertEquals(-11.0f, StringConverter.convert("-11", float.class));
            assertEquals(42.0f, StringConverter.convert("42", Float.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}