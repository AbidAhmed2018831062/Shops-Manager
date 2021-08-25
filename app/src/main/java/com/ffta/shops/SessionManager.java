package com.ffta.shops;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sh;
    SharedPreferences.Editor ed;
    Context c;
    public static final String USERSESSION = "userLogIn";
    public static final String REMEMBERME = "userRemember";


    public static final String ISLOGGED = "ISLOGGEDIN";
    public static final String signupdate = "signupdate";
    public static final String NAME = "NAME";
    public static final String BUSINESSNAME = "BUSINESSNAME";
    public static final String PHONE = "PHONE";


    public static final String ISREMEMBERME = "REMEMBERME";
    public static final String REMEMBERMEPHONE = "PHONE";
    public static final String REMEMBERMEPASS = "PASS";

    public SessionManager(Context c, String session) {
        this.c = c;
        sh = c.getSharedPreferences(session, Context.MODE_PRIVATE);
        ed = sh.edit();
    }

    public void loginSession(String phone) {

        ed.putBoolean(ISLOGGED, true);


        ed.putString(PHONE, phone);

        ed.commit();
    }
    public void updateSession(String name,String BUSINESSNAME) {

        ed.putBoolean(ISLOGGED, true);


        ed.putString(BUSINESSNAME, BUSINESSNAME);
        ed.putString(NAME, NAME);

        ed.commit();
    }


    public HashMap<String, String> returnData() {
        HashMap<String, String> hm = new HashMap<String, String>();

        hm.put(PHONE, sh.getString(PHONE, null));
        hm.put(BUSINESSNAME, sh.getString(BUSINESSNAME, null));
        hm.put(NAME, sh.getString(NAME, null));


        return hm;
    }

    public boolean checkLogin() {
        if (sh.getBoolean(ISLOGGED, true)) {
            return true;
        } else
            return false;
    }

    public void logOut() {
        ed.clear();
        ed.commit();
    }

    public void rememberMeSession(String phone, String pass) {

        ed.putBoolean(ISREMEMBERME, true);


        ed.putString(REMEMBERMEPHONE, phone);
        ed.putString(REMEMBERMEPASS, pass);

        ed.commit();
    }

    public HashMap<String, String> returnDataRememberMe() {
        HashMap<String, String> hm = new HashMap<String, String>();

        hm.put(REMEMBERMEPHONE, sh.getString(REMEMBERMEPHONE, null));

        hm.put(REMEMBERMEPASS, sh.getString(REMEMBERMEPASS, null));

        return hm;
    }

    public boolean checkRememberMe() {
        if (sh.getBoolean(ISREMEMBERME, true)) {
            return true;
        } else
            return false;
    }


}
