package com.pgmacdesign.smsverification.myfiles;

/**
 * Created by pmacdowell on 8/13/2015.
 */
public class GSONPOJO {

    String success;
    Result result;
    Errors errors;

    //These 2 are for logins
    String email;
    String password;

    //These are for sending requests
    String device; //The ID being sent FROM (In constants)
    String number;
    String message;

    //Need to add @Serialized here for lowercase r
    class Result{
        int total;
        int per_page;
        int current_page;
        int last_page;
        int from;
        int to;

        long last_seen;
        long created_at;

        String id;
        String device_id;
        String message;
        String status;
        String send_at;
        String delivered_at;
        String failed_at;
        String received_at;
        String error;
        String make;
        String model;
        String number;
        String provider;
        String country;
        String connection_type;
        String battery;
        String signal;
        String wifi;
        String lat;
        String lng;

        Contact contact;

        Data[] data;
        Success[] success;
    }

    //Need to add @Serialized here for lowercase d
    class Data{
        String id;
        String name;
        String make;
        String model;
        String number;
        String provider;
        String connection_type;
        String battery;
        String signal;
        String wifi;
        String lat;
        String lng;
        long last_seen;
        long created_at;

        Contact contact;
    }

    //Need to add @Serialized here for lowercase s
    class Success{
        String id;
        String device_id;
        String message;
        String status;
        String send_at;
        String queued_at;
        String sent_at;
        String delivered_at;
        String expires_at;
        String canceled_at;
        String failed_at;
        String received_at;
        String error;
        String created_at;

        Contact contact;
    }

    //Need to add @Serialized here for lowercase f
    class Fails{
        String number;
        String message;
        int device;

        Errors errors;
    }

    //Need to add @Serialized here for lowercase e
    class Errors{
        String[] device;
        String[] id;
    }

    //Need to add @Serialized here for lowercase c
    class Contact{
        String id;
        String name;
        String number;
    }
}
