package com.example.xormessenger;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UserDetailsUnitTest {

    @Test
    public void ValidUsername() {
        assertEquals("", UserDetails.checkUsername("testusername123"));
    }

    @Test
    public void EmptyUsername() { assertEquals("can't be blank", UserDetails.checkUsername(""));}

    @Test
    public void ShortUsername() { assertEquals("at least 5 characters long", UserDetails.checkUsername("abc"));}

    @Test
    public void AlphanumericUsername() { assertEquals("only alphabet or number allowed", UserDetails.checkUsername("!@#$%^"));}

    @Test
    public void ValidPassword() {
        assertEquals("", UserDetails.checkPassword("testpassword789"));
    }

    @Test
    public void EmptyPassword() { assertEquals("can't be blank", UserDetails.checkPassword(""));}

    @Test
    public void ShortPassword() { assertEquals("at least 5 characters long", UserDetails.checkPassword("abc"));}
}
