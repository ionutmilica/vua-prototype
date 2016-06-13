package vua.validation;

import org.junit.Test;
import vua.validation.annotations.Required;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

class MyBean {

    @Required
    public String username;

    @Required
    public String password;

    public int age;
}

public class ValidatorTest {

    @Test
    public void createValidator() {
        Map<String, String[]> params = new HashMap<>();
        Validator<MyBean> validator = new Validator<>(new MyBean(), params);
        validator.passes();


    }
}