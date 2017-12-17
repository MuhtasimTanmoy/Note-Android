package com.example.t.note;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorSingleton;
import im.delight.android.ddp.ResultListener;



public class SignUp extends AppCompatActivity {


    private EditText etUsername, etPassword, etConfirmPassword, etCoNtactNo;
    private Button btnSingUp,btnLogIn;
    private Meteor mMeteor;
    private static String TAG = "signUpPage";
    private ProgressDialog pDialog;

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();


        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "SignUpPress: " + mMeteor.isConnected());

                if (mMeteor.isConnected() ) {
                    if(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                        showpDialog();
                        final Map<String, Object> values = new HashMap<String, Object>();
                        values.put("username", etUsername.getText().toString());
                        values.put("password", etPassword.getText().toString());
                        values.put("password1", etConfirmPassword.getText().toString());
                        values.put("contactNo", etCoNtactNo.getText().toString());


                        Object[] queryParams = {values};

                        mMeteor.call("user.create", queryParams, new ResultListener() {

                            @Override
                            public void onSuccess(String result) {
                                hidepDialog();
                                Log.d(TAG, "success  in inserting");
                                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(intent);

                            }

                            @Override
                            public void onError(String error, String reason, String details) {
                                hidepDialog();
                                Log.d(TAG, "Error: " + error + " " + reason + " " + details);

                                Toast.makeText(getApplicationContext(), "Enter valid information"+ reason + error, Toast.LENGTH_SHORT).show();


                            }
                        });

                    }else{
                        Toast.makeText(getApplicationContext(), "Password not matched", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    mMeteor.connect();

                }
            }

        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });
    }


    private void init() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        etUsername = (EditText) findViewById(R.id.etSignUpUsername);
        etPassword = (EditText) findViewById(R.id.etSignUpPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etSignUpConfirmPasword);
        etCoNtactNo = (EditText) findViewById(R.id.etSignUpConfirmPasword);

        btnSingUp = (Button) findViewById(R.id.signUp);
        btnLogIn= (Button) findViewById(R.id.signIn);


        // create a new instance
        mMeteor = MeteorSingleton.getInstance();

        // establish the connection
        mMeteor.connect();
    }

}
