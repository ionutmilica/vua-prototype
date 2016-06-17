package vua.validation;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void testEmptyValidator() {
        Map<String, String[]> params = new HashMap<>();
        Validator<MyBean> validator = new Validator<>(new MyBean(), params);
        assertFalse(validator.passes());
    }

    @Test
    public void testValidValidator() {
        Map<String, String[]> params = new HashMap<>();
        params.put("username", new String[] {"ionut"});
        params.put("password", new String[] {"password"});
        params.put("passwordConfirm", new String[]{"password"});
        params.put("age", new String[] {"18"});

        Validator<MyBean> validator = new Validator<>(new MyBean(), params);
        assertTrue(validator.passes());
    }
}