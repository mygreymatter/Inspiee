package com.mayo.inspiee;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;

/** Inspiee is a singleton class which is used across the app
 * Created by mayo on 2/1/15.
 */
public class Inspiee extends Application {

    public static final String TAG_INFO = "INSPIEE";
    public static final String DB = "inspiee";
    public static final String AM_HOURS = "am_hours";
    public static final String AM_MINUTES = "am_minutes";
    public static final String PM_HOURS = "pm_hours";
    public static final String PM_MINUTES = "pm_minutes";
    public static final String TAG_PERSONALITIES = "personalities";
    public static ArrayList<String> SELECTED_PERSONALITIES;
    public static ArrayList<String> PERSONALITIES;

    private SharedPreferences sharedData;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedData = getSharedPreferences(DB, MODE_PRIVATE);
    }
}
