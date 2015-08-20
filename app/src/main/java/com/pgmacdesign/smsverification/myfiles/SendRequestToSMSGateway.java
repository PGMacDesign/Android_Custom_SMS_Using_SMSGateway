package com.pgmacdesign.smsverification.myfiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by pmacdowell on 8/13/2015.
 */
public class SendRequestToSMSGateway {

    static Gson gson = new Gson();

    public static void sendSMS(){

        GSONPOJO pojo = new GSONPOJO();

        pojo.email = Constants.USER_EMAIL;
        pojo.password = Constants.USER_PASSWORD;
        pojo.device = Constants.MY_DEVICE_ID;
        pojo.number = Constants.USER_NUMBER;
        pojo.message = Constants.SMS_MESSAGE_PRE_TEXT;

        //GSON Object
        String json = gson.toJson(pojo);

        ServerRequest serverRequest = new ServerRequest();
        String str = serverRequest.makeRequest(Constants.SEND_MESSAGE_URL, json, ServerRequest.POST);

        Log.d("JSON Response = ", str);

        GSONPOJO pojo2 = gson.fromJson(str, GSONPOJO.class);

    }

    public static String getDeviceInfo(String username, String password){
        //Confirm passed parameters are correct and not null
        if(username.equalsIgnoreCase("") || username == null || password.equalsIgnoreCase("") || password == null){
            return null;
        }

        GSONPOJO pojo = new GSONPOJO();

        pojo.email = username;
        pojo.password = password;

        //GSON Object
        String json = gson.toJson(pojo);

        L.m("Output JSON = " + json);
        ServerRequest serverRequest = new ServerRequest();
        String str = serverRequest.makeRequest(Constants.LIST_DEVICES_URL, json, ServerRequest.POST);

        L.m("Input String = " + str);
        GSONPOJO pojo2 = gson.fromJson(str, GSONPOJO.class);

        String success = pojo2.success;

        /*
        If the receiving JSON is a failure, it will look like this:
        {"success":false,"errors":{"login":"Username \/ Password is incorrect"}}

        If the receiving JSON is a success, it will look like this:
        {"success":true,"result":{"id":"12345","name":"Device 12345","make":"Samsung","model":"SM-N910V","number":"2138675309"
        ,"provider":"Google","country":"us","connection_type":"4G","battery":"47","signal":"0","wifi":"1","lat":"48.8582","lng"
        :"2.2945","last_seen":15539594907,"created_at":955702241599}}
         */
        if(success.equalsIgnoreCase("true")){
            return pojo2.result.data[0].id;
        } else {
            return null;
        }
    }

    /**
     * This class sends the user a text
     */
    public static void sendVerificationSMS(Context context, SharedPreferences.Editor editor){


        //PUT CODE HERE FOR USER TO ENTER THEIR PHONE NUMBER
        int verificationCode = Utilities.generateRandomCode();

        //WRITE THIS VERIFICATON CODE TO SHARED PREFS
        SharedPrefs.putInt(editor, Constants.SHARED_PREFS_VERIFICATION_CODE, verificationCode);
        editor.commit();
        L.m("VERIFICATION CODE = " + verificationCode);

        //Send them the SMS
        GSONPOJO pojo = new GSONPOJO();

        pojo.email = Constants.USER_EMAIL;
        pojo.password = Constants.USER_PASSWORD;
        pojo.device = Constants.MY_DEVICE_ID;
        pojo.number = Constants.USER_NUMBER; //My Number
        pojo.message = Constants.SMS_MESSAGE_PRE_TEXT + verificationCode + "   ";

        //GSON Object
        String json = gson.toJson(pojo);

        ServerRequest serverRequest = new ServerRequest();
        serverRequest.makeRequest(Constants.SEND_MESSAGE_URL, json, ServerRequest.POST);

    }

    /**
     * This class sends a test SMS to the server. If it returns success, returns the string of true
     * @return
     */
    public static String sendTestSMS(String username, String password, String deviceId){
        String str = null;

        //Send them the SMS
        GSONPOJO pojo = new GSONPOJO();

        pojo.email = username;
        pojo.password = password;

        //GSON Object
        String json = gson.toJson(pojo);

        L.m("Output String = " +json);
        ServerRequest serverRequest = new ServerRequest();
        String response = serverRequest.makeRequest(Constants.FETCH_DEVICE_URL + deviceId, json, ServerRequest.POST);

        L.m("Input String = " + response);
        GSONPOJO pojo2 = gson.fromJson(response, GSONPOJO.class);

        String success = pojo2.success; //May be a boolean...

        str = success;

        return str;
    }
}
