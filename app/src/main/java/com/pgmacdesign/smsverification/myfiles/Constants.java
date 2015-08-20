package com.pgmacdesign.smsverification.myfiles;

/**
 * Created by pmacdowell on 8/13/2015.
 */
public class Constants {

    public static final String SHARED_PREFS_USER_NUMBER = "user_number";
    public static final String SHARED_PREFS_VERIFICATION_CODE = "verification_code";
    public static final String SHARED_PREFS_NAME = "SMSVerification";
    public static final String SHARED_PREFS_MY_DEVICE_ID = "my_device_id";
    public static final String SHARED_PREFS_USER_EMAIL = "user_email";
    public static final String SHARED_PREFS_USER_PASSWORD = "user_password";

    public static final String USER_EMAIL = "myemail@email.com";
    public static final String USER_PASSWORD = "aPassword";

    public static final String MY_DEVICE_ID = "12345";


    public static final String CREATE_SMSGATEWAY_ACCOUNT_URL = "https://smsgateway.me/admin/users/login#signup";
    //Authentication:
    public static final String AUTHENTICATION_PHP = "<?php\n" +
            "include \"smsGateway.php\";\n" +
            "$smsGateway = new SmsGateway('demo@smsgateway.me', 'password');\n" +
            "//Requests to api server after authentication\n" +
            "?>";

    //This is a Post request
    public static final String SEND_MESSAGE_URL = "http://smsgateway.me/api/v3/messages/send";
    public static final String SEND_MESSAGE_PHP = "<?php\n" +
            "include \"smsGateway.php\";\n" +
            "$smsGateway = new SmsGateway('demo@smsgateway.me', 'password');\n" +
            "\n" +
            "$deviceID = 1;\n" +
            "$number = '+44771232343';\n" +
            "$message = 'Hello World!';\n" +
            "\n" +
            "$options = [\n" +
            "'send_at' => strtotime('+10 minutes'), // Send the message in 10 minutes\n" +
            "'expires_at' => strtotime('+1 hour') // Cancel the message in 1 hour if the message is not yet sent\n" +
            "];\n" +
            "\n" +
            "//Please note options is no required and can be left out\n" +
            "$result = $smsGateway->sendMessageToNumber($number, $message, $deviceID, $options);\n" +
            "?>";

    //This is a Get request
    public static final String LIST_DEVICES_URL = "http://smsgateway.me/api/v3/devices";
    public static final String LIST_DEVICES_PHP = "<?php\n" +
            "include \"smsGateway.php\";\n" +
            "$smsGateway = new SmsGateway('demo@smsgateway.me', 'password');\n" +
            "\n" +
            "$page = 1;\n" +
            "\n" +
            "$result = $smsGateway->getDevices($page);\n" +
            "?>";

    //This is a Get request
    public static final String LIST_MESSAGES_URL = "http://smsgateway.me/api/v3/messages";
    public static final String LIST_MESSAGES_PHP = "<?php\n" +
            "include \"smsGateway.php\";\n" +
            "$smsGateway = new SmsGateway('demo@smsgateway.me', 'password');\n" +
            "\n" +
            "$page = 1;\n" +
            "\n" +
            "$result = $smsGateway->getMessages($page);\n" +
            "?>";

    //Include the ID after the view/. Also, this is a Get request
    public static final String FETCH_MESSAGE_URL = "http://smsgateway.me/api/v3/messages/view/"; //Add ID after
    public static final String FETCH_MESSAGE_PHP = "<?php\n" +
            "include \"smsGateway.php\";\n" +
            "$smsGateway = new SmsGateway('demo@smsgateway.me', 'password');\n" +
            "\n" +
            "$id = 9;\n" +
            "\n" +
            "$result = $smsGateway->getMessage($id);\n" +
            "?>";

    //Include the ID after the view/. Also, this is a Get request
    public static final String FETCH_DEVICE_URL = "http://smsgateway.me/api/v3/devices/view/"; //Add ID After
    public static final String FETCH_DEVICE_PHP = "<?php\n" +
            "include \"smsGateway.php\";\n" +
            "$smsGateway = new SmsGateway('demo@smsgateway.me', 'password');\n" +
            "\n" +
            "$id = 9;\n" +
            "\n" +
            "$result = $smsGateway->getDevice($id);\n" +
            "?>";

    public static final String SMS_MESSAGE_PRE_TEXT = "This is PGMacDesign, Your Verification code is:" ;

    //Some Sample JSON send data:
    public static String GET_DEVICES_JSON_EXAMPLE = "http://smsgateway.me/api/v3/devices"
            + "";

    public static final String USER_NUMBER = "2038675309";



    /*
    Send message to number:
    Parameter	Required	Description
    email	YES	Your username for the site
    password	YES	Your password for the site
    device	YES	The ID of device you wish to send the message from
    number	YES	The number to send the message to
    message	YES	The content of the message to be sent
    send_at	NO	Time to send the message in Unix Time format
    expires_at	NO	Time to give up trying to send the message at in Unix Time format
     */
}
