package vua.validation;

import org.junit.Test;
import vua.utils.ListUtil;
import vua.validation.annotations.Matches;
import vua.validation.annotations.Required;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

class MyBean {

    @Required
    @Matches(regexp = "[a-z]*")
    public String username;

    @Required
    public String password;

    public int age;

    public String toString() {
        return String.format("%s - %s - %d", username, password, age);
    }
}

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
        params.put("age", new String[] {"22"});

        Validator<MyBean> validator = new Validator<>(new MyBean(), params);
        assertTrue(validator.passes());
    }
}