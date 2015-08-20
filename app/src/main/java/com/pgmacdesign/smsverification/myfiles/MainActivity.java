package com.pgmacdesign.smsverification.myfiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pgmacdesign.smsverification.R;
import com.pgmacdesign.smsverification.notusedatm.PrefManager;

/*
IMPORTANT!!!!!!!!!!!!!!!!!!!
NEED TO ADD A SAVE STATE FEATURE WHERE IF THEY TAB OUT OF THE APP AND BACK IN, IT WILL NOT
RESEND AND REWRITE THE CODE.
 */
public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = Constants.SHARED_PREFS_NAME;
    private SharedPrefs sp = new SharedPrefs();
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private TextView tv;
    private EditText et;
    private Button button;

    //Change this line here if you want to enable self checking
    private boolean selfAutoCheck = false;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Setup the shared Preferences
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        context = this;

        et = (EditText) findViewById(R.id.main_edit_text);
        tv = (TextView) findViewById(R.id.main_text_view);
        button = (Button) findViewById(R.id.main_button_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean proceed = false;
                //Check if the verification code matches
                String input = null;
                int userInput = -1;

                //Get Shared prefs
                int sharedPrefsCode = settings.getInt("verification_code", 0);

                try {
                    input = et.getText().toString();
                } catch (Exception e){
                    e.printStackTrace();
                }

                if(input == null || input.equalsIgnoreCase(" ") || input.equalsIgnoreCase("")) {
                    proceed = false;
                } else {
                    proceed = true;
                }

                if(proceed) {
                    userInput = Integer.parseInt(input);

                    if (sharedPrefsCode == 0) {
                        tv.setText("Error! Verification stored is at 0");
                    }


                    if (sharedPrefsCode == userInput) {
                        tv.setText("Codes Match!!!");
                    } else {
                        tv.setText("Codes Don't match...");
                    }
                }

                if(userInput == -1){
                    tv.setText("You need to enter a code");
                }

                L.m("Actual saved verification code = " + sharedPrefsCode);
            }
        });

        SharedPrefs.putString(editor, Constants.SHARED_PREFS_USER_NUMBER, Constants.USER_NUMBER);
        editor.commit();


        //New Thread to run on background
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    //SendRequestToSMSGateway.getDeviceInfo();
                    //SendRequestToSMSGateway.sendSMS();
                    //Can make this into a button so that it will run again upon request
                    SendRequestToSMSGateway.sendVerificationSMS(context, editor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Logging out user
     * will clear all user shared preferences and navigate to
     * sms activation screen
     */
    private void logout() {
        PrefManager pref = new PrefManager(this);
        pref.clearSession();

        Intent intent = new Intent(MainActivity.this, FirstActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        finish();
    }
}
