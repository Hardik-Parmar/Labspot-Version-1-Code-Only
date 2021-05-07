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

public class Delivery_Forgot_Password extends AppCompatActivity
{
    TextInputEditText delivery_forgot_email;
    Button delivery_generate_otp;
    String delivery_forgot_email_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_forgot_password);

        // EditText
        delivery_forgot_email = findViewById(R.id.delivery_forgot_email);

        // Button
        delivery_generate_otp = findViewById(R.id.delivery_generate_otp);

        delivery_generate_otp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delivery_forgot_email_temp = delivery_forgot_email.getText().toString();
                if(delivery_forgot_email_temp.equals(""))
                {
                    Toast.makeText(Delivery_Forgot_Password.this, "Please fill out the E-Mail Field.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(Delivery_Forgot_Password.this))
                    {
                        getDeliveryForgotPassword(delivery_forgot_email_temp);
                    }

                    else
                    {
                        Toast.makeText(Delivery_Forgot_Password.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getDeliveryForgotPassword(String delivery_email)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Delivery_Forgot_Password.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("delivery_email", delivery_email);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(Delivery_Forgot_Password.this);
        progressDialog.setMessage("Please Wait Forgot Password process is in Progress");
        progressDialog.setTitle("Delivery Staff Forgot Password");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(Delivery_Forgot_Password.this);

        JsonObjectRequest delivery_Forgot_Password_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.DELIVERY_FORGOT_PASSWORD, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (delivery_return_message.equals("Something Went Wrong in Delivery_Forgot_OTP_Generate"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Delivery_Forgot_Password.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd Possible Response
                else if (delivery_return_message.equals("Delivery Forgot OTP Not Generated"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Delivery_Forgot_Password.this, "Unable to generate OTP. Please Try Again.", Toast.LENGTH_LONG).show();
                }

                //3rd Possible Response
                else if (delivery_return_message.equals("Delivery Forgot OTP Generated and Delivery Forgot Password Email Sent"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("OTP Generated");
                    builder.setMessage("OTP for Password Reset has been generated and has been mailed to you.\n\nPlease click on 'Verify OTP' to verify OTP ot Reset your Password.");

                    builder.setPositiveButton("Verify OTP", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Delivery_Forgot_Password.this, Delivery_Forgot_Password_otp_Submit.class);
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

                // 4th possible Solution
                else if(delivery_return_message.equals("Please Verify Your Delivery Account First."))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("Delivery Staff Account is Not Verified");
                    builder.setMessage("Oops...!! Your Delivery Staff Account is not Verified.\n\nPlease click on 'Verify Account' to Verify Your Account.");

                    builder.setPositiveButton("Verify Account", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Delivery_Forgot_Password.this, Delivery_Account_Verification.class);
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

                // 5th Possible Solution
                else if (delivery_return_message.equals("You Does Not Exist as Delivery Staff please register yourself first"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("General User Doesn't Exist");
                    builder.setMessage("Sorry you Does not Exist as Delivery Staff into the System.\n\nPlease click on 'Register' to get registered as Delivery Staff.");

                    builder.setPositiveButton("Register", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Delivery_Forgot_Password.this, Delivery_Registration_Page.class);
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

        delivery_Forgot_Password_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(delivery_Forgot_Password_POST_Request);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Delivery_Forgot_Password.this, Delivery_Login_Page.class);
        startActivity(intent);
        finish();
    }
}