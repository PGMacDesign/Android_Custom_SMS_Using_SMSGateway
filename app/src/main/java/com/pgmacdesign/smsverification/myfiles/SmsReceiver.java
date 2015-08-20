package com.pgmacdesign.smsverification.myfiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.pgmacdesign.smsverification.notusedatm.MyApplication;

/**
 * This class will automatically check on inbound SMS and see if they are from me, if so,
 * parse the code and check on the verification for the app.
 * Created by pmacdowell on 8/14/2015.
 */
public class SmsReceiver extends BroadcastReceiver

    {
    private static final String TAG = SmsReceiver.class.getSimpleName();

        public static final String PREFS_NAME = Constants.SHARED_PREFS_NAME;
        SharedPrefs sp = new SharedPrefs();
        SharedPreferences settings;
        SharedPreferences.Editor editor;

    @Override
    public void onReceive(Context context, Intent intent) {

        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);

                    String senderAddress = currentMessage.getDisplayOriginatingAddress();

                    String message = currentMessage.getDisplayMessageBody();

                    String userNumber = sp.getString(settings, Constants.SHARED_PREFS_USER_NUMBER, null);

                    if(senderAddress.equalsIgnoreCase(userNumber)){
                        //Parse out the code
                        String code = parseMessageForCode(message);
                        //If that code is not null
                        if(code != null){
                            //proceed
                            //Get the one in shared prefs first
                            int sharedPrefsInt = sp.getInt(settings, Constants.SHARED_PREFS_VERIFICATION_CODE, -1);
                            L.m("Parsed and received code = " + code);
                            int parsedCodeInt = Integer.parseInt(code);
                            if(sharedPrefsInt != -1){
                                if(parsedCodeInt == sharedPrefsInt){
                                    //SUCCESS!
                                    L.Toast(MyApplication.getInstance().getApplicationContext(), "SUCCESS! App Confirmed");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

        /**
         * This checks the messages for a specific String, cuts it, and returns the verification code
         * @param message The message body to be parsed
         * @return The cut version of the message body after trimming
         */
        private String parseMessageForCode(String message) {
            if(message == null){
                return null;
            }

            String verification_code = null;
            String cut_code = null;
            if(message.contains(Constants.SMS_MESSAGE_PRE_TEXT)){
                try {
                    verification_code = message.replace(Constants.SMS_MESSAGE_PRE_TEXT, "");
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                return null;
            }

            cut_code = verification_code.substring(0,4); //Ours is only 4 spaces long, change to 5 if you make it larger
            cut_code = cut_code.trim(); //Clear off the whitespace
            return cut_code;
        }

        /**
     * Getting the OTP from sms message body
     * ':' is the separator of OTP from the message
     *
     * @param message
     * @return
     */
    private String getVerificationCode(String message) {
        String code = null;
        int index = 0; //message.indexOf(Config111.OTP_DELIMITER);

        if (index != -1) {
            int start = index + 2;
            int length = 6;
            code = message.substring(start, start + length);
            return code;
        }

        return code;
    }
}