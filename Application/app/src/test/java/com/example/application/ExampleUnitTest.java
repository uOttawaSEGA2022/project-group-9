package com.example.application;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testemptypassword()
    {
        String password;
        password="";
        String email="xyz@gmail.com";
        String out;
        out=authenticator.validatePassword(email,password);
        boolean b;
        b=out.equals("Valid Password");
        assertEquals(false,b);
    }
    @Test
    public void testshortpassword()
    {
        String password;
        password="Aa123";
        String email="xyz@gmail.com";
        String out;

        out=authenticator.validatePassword(email,password);
        boolean b;
        b=out.equals("Valid Password");
        assertEquals(false,b);
    }
    @Test
    public void passwordemailsame()
    {
        String password;
        password="xyAz123@gmail.com";
        String email="xyAz123@gmail.com";
        String out;

        out=authenticator.validatePassword(email,password);
        boolean b;
        b=out.equals("Valid Password");
        assertEquals(false,b);
    }
    @Test
    public void testpasswordwithoutnumber()
    {
        String password;
        password="Ee@wqa";
        String email="xyAz123@gmail.com";
        String out;

        out=authenticator.validatePassword(email,password);
        boolean b;
        b=out.equals("Valid Password");
        assertEquals(false,b);
    }
    @Test
    public void testpasswordwithoutsmalletter()
    {
        String password;
        password="E12@4712WER12";
        String email="xyAz123@gmail.com";
        String out;

        out=authenticator.validatePassword(email,password);
        boolean b;
        b=out.equals("Valid Password");
        assertEquals(false,b);
    }
    @Test
    public void testpasswordwithoutcapitalletter()
    {
        String password;
        password="e12@4712wqa";
        String email="xyAz123@gmail.com";
        String out;

        out=authenticator.validatePassword(email,password);
        boolean b;
        b=out.equals("Valid Password");
        assertEquals(false,b);
    }
    @Test
    public void testpasswordwithoutspecialcharacter()
    {
        String password;
        password="e124712wqa";
        String email="xyAz123@gmail.com";
        String out;

        out=authenticator.validatePassword(email,password);
        boolean b;
        b=out.equals("Valid Password");
        assertEquals(false,b);
    }
    @Test
    public void testemailwithoutattheratecharacter()
    {
        String email="xyAz123gmail.com";
        String out;

        out=authenticator.validateEmail(email);
        boolean b;
        b=out.equals("Valid Email");
        assertEquals(false,b);
    }
    @Test
    public void testvalidpassword()
    {
        String password;
        password="Ee12@4712wqa";
        String email="xyAz123@gmail.com";
        String out;

        out=authenticator.validatePassword(email,password);
        boolean b;
        b=out.equals("Valid Password");
        assertEquals(true,b);
    }
    @Test
    public void testvalidemail()
    {
        String email="xyAz123@gmail.com";
        String out;

        out=authenticator.validateEmail(email);
        boolean b;
        b=out.equals("Valid Email");
        assertEquals(true,b);
    }
}