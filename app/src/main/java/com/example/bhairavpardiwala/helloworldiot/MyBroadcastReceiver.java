package com.example.bhairavpardiwala.helloworldiot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by Bhairav Pardiwala on 28-10-2017.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String allOk="allOk";
        boolean bln;
        if(intent.getExtras().size()>0)
        {
         bln=intent.getExtras().getBoolean("allOk");
            allOk = String.valueOf(bln);
        }


        Log.d(TAG, allOk);
        Toast.makeText(context, allOk, Toast.LENGTH_SHORT).show();
    }
}
