package com.pgmacdesign.smsverification.myfiles;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Random;

/**
 * Created by pmacdowell on 8/14/2015.
 */
public class Utilities {

    public static int generateRandomCode(){
        Random random = new Random();
        int min = 1000;
        int max = 9999;
        int randomNum = random.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * Checks for network connectivity either via wifi or cellular.
     * @param context The context of the activity doing the checking
     * @return A Boolean. True if they have connection, false if they do not
     */
    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
