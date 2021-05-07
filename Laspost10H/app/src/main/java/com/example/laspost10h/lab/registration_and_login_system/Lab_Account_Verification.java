package com.example.laspost10h.lab.registration_and_login_system;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Lab_Account_Verification extends AppCompatActivity
{
    TextInputEditText lab_account_verify_otp;
    Button lab_verify_account;
    String lab_account_verify_otp_temp, lab_received_email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_account_verification);

        // EditText
        lab_account_verify_otp = findViewById(R.id.lab_account_verify_otp);

        // Button
        lab_verify_account = findViewById(R.id.lab_verify_account);

        Bundle bundle = getIntent().getExtras();
        lab_received_email = bundle.getString("lab_sent_mail");

        lab_verify_account.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lab_account_verify_otp_temp = lab_account_verify_otp.getText().toString();

                if (lab_account_verify_otp_temp.equals(""))
                {
                    Toast.makeText(Lab_Account_Verification.this, "Please fill out the OTP Field.\n\nOTP has been mailed to you at your Registered Mail I'D.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if (Utility.isNetworkAvailable(Lab_Account_Verification.this))
                    {
                        getLabVerified(lab_received_email, lab_account_verify_otp_temp);
                    }

                    else
                    {
                        Toast.makeText(Lab_Account_Verification.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getLabVerified(String lab_email, String lab_otp)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Lab_Account_Verification.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("lab_email", lab_email);
        jsonParameters.put("lab_otp", lab_otp);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(Lab_Account_Verification.this);
        progressDialog.setMessage("Please Wait Account Verification process is in Progress");
        progressDialog.setTitle("Lab Account Verification");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(Lab_Account_Verification.this);

        JsonObjectRequest lab_Account_Verification_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_ACCOUNT_VERIFICATION, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String lab_return_message = null;

                try
                {
                    lab_return_message = (String) response.get("lab_return_message");
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st Possible Response
                if (lab_return_message.equals("Something went wrong in Lab Account Verification"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Lab_Account_Verification.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd Possible Response
                else if (lab_return_message.equals("Lab Account Not Verified Wrong OTP"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Lab_Account_Verification.this, "Wrong OTP Your Account Didn't Verified. Please Try Again.", Toast.LENGTH_LONG).show();
                }

                //3rd Possible Response
                else if (lab_return_message.equals("You Does Not Exist as Lab please register yourself first"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("Lab Doesn't Exist");
                    builder.setMessage("Sorry you Does not Exist as Lab User into the System.\n\nPlease click on 'Register' to get registered as Lab User.");

                    builder.setPositiveButton("Register", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Lab_Account_Verification.this, Lab_Registration_Page.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

                //4th Possible Response
                else if (lab_return_message.equals("Lab Account Verified"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("Lab Account Verified");
                    builder.setMessage("Woo-hoo Your Account is Verified.\n\nPlease click on 'Log-In' to go into Log-In Page.");

                    builder.setPositiveButton("Log-In", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Lab_Account_Verification.this, Lab_Login_Page.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });

        lab_Account_Verification_POST_Request.setRetryPolicy(new RetryPolicy()
        {
            @Override
            public int getCurrentTimeout()
            {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount()
            {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError
            {

            }
        });

        requestQueue.add(lab_Account_Verification_POST_Request);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Lab_Account_Verification.this, Lab_Login_Page.class);
        startActivity(intent);
        finish();
    }
}