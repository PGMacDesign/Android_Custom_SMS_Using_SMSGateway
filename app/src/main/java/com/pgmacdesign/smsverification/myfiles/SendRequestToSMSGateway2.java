package com.pgmacdesign.smsverification.myfiles;

import com.google.gson.Gson;

/**
 * Created by pmacdowell on 8/18/2015.
 */
public class SendRequestToSMSGateway2 {

    static Gson gson = new Gson();

    /**
     * This class sends an SMS. It takes in the number being sent to as a String
     * @param numberToSendTo Number being SMSd
     */
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

    /**
     * This class gets the device info from the server
     * @return The String of the device ID
     */
    public static String getDeviceInfo(){
        GSONPOJO pojo = new GSONPOJO();
        //Create the object and set the email/ password
        pojo.email = Constants.USER_EMAIL;
        pojo.password = Constants.USER_PASSWORD;

        //GSON Object
        String json = gson.toJson(pojo);

        //Send the request to teh server
        ServerRequest serverRequest = new ServerRequest();
        String str = serverRequest.makeRequest(Constants.LIST_DEVICES_URL, json, ServerRequest.POST);

        L.m("Input String = " + str);
        GSONPOJO pojo2 = gson.fromJson(str, GSONPOJO.class);

        String success = pojo2.success;

        /*
        If the receiving JSON is a failure, it will look like this:
        {"success":false,"errors":{"login":"Username \/ Password is incorrect"}}

        If the receiving JSON is a success, it will look like this:
        {"success":true,"result":{"id":"12345","name":"Device 12345","make":"Samsung","model":"SM-N910V"
        ,"number":"2138675309" ,"provider":"Google","country":"us","connection_type":"4G","battery"
        :"47","signal":"0","wifi":"1","lat":"48.8582","lng" :"2.2945","last_seen":15539594907,"
        created_at":955702241599}}
         */
        if(success.equalsIgnoreCase("true")){
            return pojo2.result.data[0].id; //If successful, return the device ID
        } else {
            return null; //If a failure, return null
        }
    }
}
