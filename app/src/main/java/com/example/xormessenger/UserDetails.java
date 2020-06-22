package com.example.xormessenger;

public class UserDetails {
    static String username = "";
    static String password = "";
    static String chatWith = "";

    public static String checkPassword(String ps) {
        if(ps.equals("")) {
            return "can't be blank";
        }
        else if(ps.length()<5) {
            return "at least 5 characters long";
        }
        return "";
    }

    public static String checkUsername(String us) {
        if(us.equals("")) {
            return "can't be blank";
        }
        else if(!us.matches("[A-Za-z0-9]+")) {
            return "only alphabet or number allowed";
        }
        else if(us.length()<5) {
            return "at least 5 characters long";
        }
        return "";
    }
}
