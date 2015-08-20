package com.pgmacdesign.smsverification.myfiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pgmacdesign.smsverification.R;
import com.pgmacdesign.smsverification.notusedatm.MyApplication;

/**
 * This activity serves as the 'initializer' in that they setup their stuff here
 * Created by pmacdowell on 8/14/2015.
 */
public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Components
    private Button first_create_account, first_validate;
    private TextView first_result_status;
    private EditText first_email, first_password;

    //Shared Preferences
    private SharedPrefs sp = new SharedPrefs();
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Initialize();

        //TESTING
        testing();
    }


    private void testing() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
            // THIS PHONE HAS SMS FUNCTIONALITY
            try {
                L.m("Sending test text message");
                int verificationCode = Utilities.generateRandomCode();
                String messageToSend = Constants.SMS_MESSAGE_PRE_TEXT + verificationCode + "   ";
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(Constants.USER_NUMBER, null, messageToSend, null, null);
                L.m("Finished sending test text message");
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            // NO SMS HERE :(
            L.toast(this, "No Text message functionality available. Maybe airplane mode is on?");
        }

    }


    private void Initialize() {
        //Shared Preferences
        settings = getSharedPreferences(Constants.SHARED_PREFS_NAME, 0);
        editor = settings.edit();

        //UI Components
        first_create_account = (Button) findViewById(R.id.first_create_account);
        first_validate = (Button) findViewById(R.id.first_validate);

        first_result_status = (TextView) findViewById(R.id.first_result_status);

        first_email = (EditText) findViewById(R.id.first_email);
        first_password = (EditText) findViewById(R.id.first_password);

        //Click Listeners
        first_create_account.setOnClickListener(this);
        first_validate.setOnClickListener(this);
    }

    /**
     * This handles the onclick events for the two buttons
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //Open a link to the web
            case R.id.first_create_account:
                Intent webIntent = new Intent(); //need to add flag to come back here when done

                webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT );
                break;

            //Send request, see if they setup account correctly
            case R.id.first_validate:
                boolean proceeed = true;

                //Check network connection first
                proceeed = Utilities.haveNetworkConnection(this);

                //First get Strings
                String username = null;
                String password = null;
                username = first_email.getText().toString();
                password = first_password.getText().toString();

                if(username == null || username.equalsIgnoreCase("")){
                    proceeed = false;
                    L.toast(this, "You must enter a username");
                }
                if(password == null || password.equalsIgnoreCase("")){
                    proceeed = false;
                    L.toast(this, "You must enter a password");
                }

                //Next ping the server
                if(proceeed){
                    String deviceID = null;
                    try {
                        //Remember, since I am calling .get(), this WILL hold up the main thread!
                        deviceID = new CheckForDeviceId(this, username, password).execute().get();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if(deviceID == null){
                        proceeed = false;
                        L.toast(this, "Check username / password combination");
                    }
                    if(proceeed){
                        String didItWork = null;

                        try {
                            didItWork = new SendTestSMS(deviceID, username, password).execute().get();

                        } catch (Exception e){
                            e.printStackTrace();
                        }

                        //Make sure not null first
                        if(didItWork != null){
                            //Check if true or not
                            if(didItWork.equalsIgnoreCase("true")){
                                //Success! Move on to main activity
                                L.toast(this, "Sucess! You successfully validated your account");

                                //Write the device ID, username, and pw to storage
                                sp.putString(editor, Constants.SHARED_PREFS_MY_DEVICE_ID, deviceID);
                                sp.putString(editor, Constants.SHARED_PREFS_USER_EMAIL, username);
                                sp.putString(editor, Constants.SHARED_PREFS_USER_PASSWORD, password);
                                editor.commit();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        Intent intent = new Intent(MyApplication.getInstance().getApplicationContext(),
                                                MainActivity.class);
                                        startActivity(intent);
                                    }
                                }, (1000 * 2));

                            } else {
                                //Fail, try again
                                L.toast(this, "Error with validation, try once more");
                            }
                        }
                    }
                }
                break;
        }
    }


    class CheckForDeviceId extends AsyncTask<Void, String, String>{
        private String username, password;
        private Context context;
        public CheckForDeviceId( Context context, String username, String password){
            this.context = context;
            this.username = username;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            String deviceID = SendRequestToSMSGateway.getDeviceInfo(username, password);
            L.m("Device ID Returned via Async = " + deviceID);
            return deviceID;
        }

        @Override
        protected void onPostExecute(String deviceId) {
            super.onPostExecute(deviceId);

        }
    }

    class SendTestSMS extends AsyncTask<Void, String, String>{
        private String username, password, deviceId;
        public SendTestSMS( String deviceId, String username, String password){
            this.deviceId = deviceId;
            this.username = username;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            String didPingWork = SendRequestToSMSGateway.sendTestSMS(username, password, deviceId);
            return didPingWork;
        }

        @Override
        protected void onPostExecute(String deviceId) {
            super.onPostExecute(deviceId);

        }
    }
}
