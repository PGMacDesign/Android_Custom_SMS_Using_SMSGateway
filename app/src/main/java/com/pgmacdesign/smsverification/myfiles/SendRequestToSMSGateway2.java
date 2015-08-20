package com.pgmacdesign.smsverification.myfiles;

import com.google.gson.Gson;

/**
 * Created by pmacdowell on 8/18/2015.
 */
public class SendRequestToSMSGateway2 {

    static Gson gson = new Gson();

    public static void sendSMS(String numberToSendTo){

        //First check the param, if not good, stop moving forward.
        //Redundant as it is checked earlier, but always better to be safe
        if(numberToSendTo == null || numberToSendTo.equalsIgnoreCase("")){
            return;
        }

        //Create a new object to build a JSON request String
        GSONPOJO pojo = new GSONPOJO();

        //Build the object
        pojo.email = Constants.USER_EMAIL;
        pojo.password = Constants.USER_PASSWORD;
        pojo.device = Constants.MY_DEVICE_ID;
        pojo.number = numberToSendTo;
        pojo.message = "This is our message. Huzzah!";

        //GSON Object
        String json = gson.toJson(pojo);

        //Create a new server request
        ServerRequest serverRequest = new ServerRequest();
        //Get a String from the reply
        String str = null;
        try{
            str = serverRequest.makeRequest(Constants.SEND_MESSAGE_URL, json, ServerRequest.POST);
        } catch (Exception e){
            e.printStackTrace();
        }

        if(str == null){
            return;
        }

        L.m("Response = " + str); //Print out response to log for reading purposes

        //Build a new object to Parse the data
        GSONPOJO pojo2 = gson.fromJson(str, GSONPOJO.class);

        //Set the response from the server from the returned JSON
        String success = pojo2.success; //(Will either be true or false)

        if(success.equalsIgnoreCase("true")){
            //Text succeeded, do something here
        } else {
            //Text failed, do something here
        }

    }
}
