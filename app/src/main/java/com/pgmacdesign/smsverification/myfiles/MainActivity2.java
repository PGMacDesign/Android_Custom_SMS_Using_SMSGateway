package com.pgmacdesign.smsverification.myfiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pgmacdesign.smsverification.R;
import com.pgmacdesign.smsverification.notusedatm.MyApplication;

public class MainActivity2 extends AppCompatActivity {

    private TextView tv;
    private EditText et;
    private Button button, button2;
    private String numberToSendTo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //UI Components
        et = (EditText) findViewById(R.id.main_edit_text);
        tv = (TextView) findViewById(R.id.main_text_view);
        button = (Button) findViewById(R.id.main_button_confirm);
        button2 = (Button) findViewById(R.id.main_button_send_text);

        //Click elements
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            SendRequestToSMSGateway2.sendSMS(Constants.USER_NUMBER);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gateway boolean
                boolean proceed = false;

                //String to be used for input from the user
                String input = null;

                //Get the text from the EditText
                try {
                    input = et.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Check to make sure not null values
                if (input == null || input.equalsIgnoreCase(" ") || input.equalsIgnoreCase("")) {
                    proceed = false;
                    //Remember the MyApplication post a while back? Here is where it is useful
                    L.toast(MyApplication.getInstance().getApplicationContext(), "You must enter " +
                            "a value into the text field. Please enter a number to send a text to");
                } else {
                    proceed = true;
                }

                //If all is good, proceed
                if (proceed) {
                    numberToSendTo = input.trim(); //Trim whitespace
                    //New Thread to run on background so that this network call will not be on the main UI
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                SendRequestToSMSGateway2.sendSMS(numberToSendTo);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                }
            }
        });
    }
}
