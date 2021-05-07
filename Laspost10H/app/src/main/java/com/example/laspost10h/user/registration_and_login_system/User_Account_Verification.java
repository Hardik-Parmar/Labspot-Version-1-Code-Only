package com.example.laspost10h.user.registration_and_login_system;

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

public class User_Account_Verification extends AppCompatActivity
{
    TextInputEditText user_account_verify_otp;
    Button user_verify_account;

    String user_account_verify_otp_temp, user_received_email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_verification);

        // EditText
        user_account_verify_otp = findViewById(R.id.user_account_verify_otp);

        // Button
        user_verify_account = findViewById(R.id.user_verify_account);

        Bundle bundle = getIntent().getExtras();
        user_received_email = bundle.getString("user_sent_mail");

        user_verify_account.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user_account_verify_otp_temp = user_account_verify_otp.getText().toString();

                if (user_account_verify_otp_temp.equals(""))
                {
                    Toast.makeText(User_Account_Verification.this, "Please fill out the OTP Field.\n\nOTP has been mailed to you at your Registered Mail I'D.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if (Utility.isNetworkAvailable(User_Account_Verification.this))
                    {
                        getUserVerified(user_received_email, user_account_verify_otp_temp);
                    }

                    else
                    {
                        Toast.makeText(User_Account_Verification.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getUserVerified(String user_email, String user_otp)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(User_Account_Verification.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("user_email", user_email);
        jsonParameters.put("user_otp", user_otp);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(User_Account_Verification.this);
        progressDialog.setMessage("Please Wait Account Verification process is in Progress");
        progressDialog.setTitle("General User Account Verification");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(User_Account_Verification.this);

        JsonObjectRequest user_Account_Verification_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.USER_ACCOUNT_VERIFICATION, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String user_return_message = null;

                try
                {
                    user_return_message = (String) response.get("user_return_message");
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st Possible Response
                if (user_return_message.equals("Something went wrong in User Account Verification"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(User_Account_Verification.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd Possible Response
                else if (user_return_message.equals("User Account Not Verified Wrong OTP"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(User_Account_Verification.this, "Wrong OTP Your Account Didn't Verified. Please Try Again.", Toast.LENGTH_LONG).show();
                }

                //3rd Possible Response
                else if (user_return_message.equals("You Does Not Exist as General User please register yourself first"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("General User Doesn't Exist");
                    builder.setMessage("Sorry you Does not Exist as General User into the System.\n\nPlease click on 'Register' to get registered as General User.");

                    builder.setPositiveButton("Register", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(User_Account_Verification.this, User_Registration_Page.class);
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
                else if (user_return_message.equals("User Account Verified"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("User Account Verified");
                    builder.setMessage("Woo-hoo Your Account is Verified.\n\nPlease click on 'Log-In' to go into Log-In Page.");

                    builder.setPositiveButton("Log-In", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(User_Account_Verification.this, User_Login_Page.class);
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

        user_Account_Verification_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(user_Account_Verification_POST_Request);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(User_Account_Verification.this, User_Login_Page.class);
        startActivity(intent);
        finish();
    }
}