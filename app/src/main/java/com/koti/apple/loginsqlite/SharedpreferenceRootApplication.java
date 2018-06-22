package com.koti.apple.loginsqlite;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.net.CookieHandler;
import java.net.CookieManager;

// We need to mention SharedpreferenceRootApplication class in AndroidManifest.xml file like below
// android:name=".SharedpreferenceRootApplication"
public class SharedpreferenceRootApplication extends Application {

    public static SharedPreferences mPref;
    public static SharedPreferences.Editor mEditor;

    @Override
    public void onCreate() {
        super.onCreate();
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mPref.edit();

        // TODO:- Research on CookieManager class and its methods.
//        CookieManager cookieManager = new CookieManager();
//        CookieHandler.setDefault(cookieManager);
    }
}
