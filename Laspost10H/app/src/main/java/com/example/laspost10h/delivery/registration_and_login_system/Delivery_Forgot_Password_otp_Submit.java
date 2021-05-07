package com.example.laspost10h.delivery.registration_and_login_system;

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

public class Delivery_Forgot_Password_otp_Submit extends AppCompatActivity
{
    TextInputEditText delivery_forgot_password_otp;
    Button delivery_forgot_password_otp_submit;
    String delivery_forgot_password_otp_temp, delivery_received_email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_forgot_password_otp_submit);

        // EditText
        delivery_forgot_password_otp = findViewById(R.id.delivery_forgot_password_otp);

        // Button
        delivery_forgot_password_otp_submit = findViewById(R.id.delivery_forgot_password_otp_submit);

        Bundle bundle = getIntent().getExtras();

        delivery_received_email = bundle.getString("delivery_sent_mail");

        delivery_forgot_password_otp_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delivery_forgot_password_otp_temp = delivery_forgot_password_otp.getText().toString();

                if(delivery_forgot_password_otp_temp.equals(""))
                {
                    Toast.makeText(Delivery_Forgot_Password_otp_Submit.this, "Please fill out the OTP Field. \n\nCheck Mail for OTP.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(Delivery_Forgot_Password_otp_Submit.this))
                    {
                        getDeliveryForgotPasswordOTPVerify(delivery_received_email, delivery_forgot_password_otp_temp);
                    }

                    else
                    {
                        Toast.makeText(Delivery_Forgot_Password_otp_Submit.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getDeliveryForgotPasswordOTPVerify(String delivery_email, String delivery_forgot_password_otp)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Delivery_Forgot_Password_otp_Submit.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("delivery_email", delivery_email);
        jsonParameters.put("delivery_forgot_password_otp", delivery_forgot_password_otp);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(Delivery_Forgot_Password_otp_Submit.this);
        progressDialog.setMessage("Please Wait OTP Verification process is in Progress");
        progressDialog.setTitle("Delivery Staff OTP Verification");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(Delivery_Forgot_Password_otp_Submit.this);

        JsonObjectRequest delivery_Forgot_Password_OTP_Verification_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.DELIVERY_FORGOT_PASSWORD_OTP_VERIFY, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String delivery_return_message = null;

                try
                {
                    delivery_return_message = (String) response.get("delivery_return_message");
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st Possible Response
                if (delivery_return_message.equals("Something went wrong in Delivery Forgot Password OTP Verification"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Delivery_Forgot_Password_otp_Submit.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd Possible Response
                else if (delivery_return_message.equals("Delivery Forgot Password OTP Not Verified Wrong OTP"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Delivery_Forgot_Password_otp_Submit.this, "Wrong OTP. Please Try Again.", Toast.LENGTH_LONG).show();
                }

                //3rd Possible Response
                else if (delivery_return_message.equals("You Does Not Exist as Delivery Staff please register yourself first"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("Delivery Staff Doesn't Exist");
                    builder.setMessage("Sorry you Does not Exist as Delivery Staff into the System.\n\nPlease click on 'Register' to get registered as Delivery Staff.");

                    builder.setPositiveButton("Register", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Delivery_Forgot_Password_otp_Submit.this, Delivery_Registration_Page.class);
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
                else if (delivery_return_message.equals("Delivery Forgot Password OTP Verified"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("OTP for Reset Password Verified");
                    builder.setMessage("Woo-hoo Your OTP is Verified.\n\nPlease click on 'Reset Password' to Reset Your Password.");

                    builder.setPositiveButton("Reset Password", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Delivery_Forgot_Password_otp_Submit.this, Delivery_Reset_Password.class);
                            intent.putExtra("delivery_sent_mail", delivery_email);
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

        delivery_Forgot_Password_OTP_Verification_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(delivery_Forgot_Password_OTP_Verification_POST_Request);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Delivery_Forgot_Password_otp_Submit.this, Delivery_Login_Page.class);
        startActivity(intent);
        finish();
    }
}