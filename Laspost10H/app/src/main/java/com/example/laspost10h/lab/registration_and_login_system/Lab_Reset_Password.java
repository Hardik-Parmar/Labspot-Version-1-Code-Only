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

public class Lab_Reset_Password extends AppCompatActivity
{
    TextInputEditText lab_reset_password;
    Button lab_reset_password_submit;
    String lab_reset_password_temp, lab_received_email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_reset_password);

        // EditText
        lab_reset_password = findViewById(R.id.lab_reset_password);

        // Button
        lab_reset_password_submit = findViewById(R.id.lab_reset_password_submit);

        Bundle bundle = getIntent().getExtras();
        lab_received_email = bundle.getString("lab_sent_mail");

        lab_reset_password_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lab_reset_password_temp = lab_reset_password.getText().toString();

                if(lab_reset_password_temp.equals(""))
                {
                    Toast.makeText(Lab_Reset_Password.this, "Please fill out the Reset Password Field.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(Lab_Reset_Password.this))
                    {
                        getLabResetPassword(lab_received_email, lab_reset_password_temp);
                    }

                    else
                    {
                        Toast.makeText(Lab_Reset_Password.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getLabResetPassword(String lab_email, String lab_new_password)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Lab_Reset_Password.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("lab_email", lab_email);
        jsonParameters.put("lab_new_password", lab_new_password);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(Lab_Reset_Password.this);
        progressDialog.setMessage("Please Wait Password Reset process is in Progress");
        progressDialog.setTitle("Lab Reset Password");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(Lab_Reset_Password.this);

        JsonObjectRequest lab_Reset_Password_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_RESET_PASSWORD, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (lab_return_message.equals("Something went wrong in Lab Password Reset"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Lab_Reset_Password.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd Possible Response
                else if (lab_return_message.equals("Lab Please verify OTP for the reset Password"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("Verify OTP First");
                    builder.setMessage("Sorry you Didn't Verified OTP yet.\n\nPlease click on 'Verify OTP' to verify your OTP then Reset your Password.");

                    builder.setPositiveButton("Verify OTP", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Lab_Reset_Password.this, Lab_Forgot_Password_otp_Submit.class);
                            intent.putExtra("lab_sent_mail", lab_email);
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
                            Intent intent = new Intent(Lab_Reset_Password.this, Lab_Registration_Page.class);
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
                else if(lab_return_message.equals("Lab Password has not been Reset"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Lab_Reset_Password.this, "Some How Your Password Didn't Reset. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //5th Possible Response
                else if (lab_return_message.equals("Lab Password has been Reset and Lab Email Sent"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("Password Reset Successfully");
                    builder.setMessage("Woo-hoo Your New Password has been Reset.\n\nPlease click on 'Log-In' to Log-In with your new Password.");

                    builder.setPositiveButton("Log-In", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Lab_Reset_Password.this, Lab_Login_Page.class);
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

        lab_Reset_Password_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(lab_Reset_Password_POST_Request);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Lab_Reset_Password.this, Lab_Login_Page.class);
        startActivity(intent);
        finish();
    }
}