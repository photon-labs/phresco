package com.photon.phresco.apklib;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class APKLibMainActivity extends Activity {
    /** Called when the activity is first created. */
    private static String TAG = "APKLibMainActivity: ";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public String show(String arg) {
        Log.i(TAG, "Message = " + arg);
        return arg;
    }
}