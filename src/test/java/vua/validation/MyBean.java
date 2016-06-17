package vua.validation;

import vua.validation.annotations.*;

public class MyBean {

    /** Validation rules **/

    @Required
    @Matches(regexp = "[a-z]*")
    private String username;

    @Required
    @Confirmed
    private String password;

    @Required
    private String passwordConfirm;

    @Between(min = 1, max = 200)
    private int age;


    /** Getters and Setters for Bean **/

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String password) {
        this.passwordConfirm = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String toString() {
        return String.format("%s - %s - %s - %d", username, password, passwordConfirm, age);
    }
}