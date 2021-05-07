package com.example.laspost10h.lab.registration_and_login_system;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.laspost10h.App_Main_Page;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.LabLoginSessionManagement;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.example.laspost10h.lab.after_login.Welcome_Lab_Page;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Lab_Login_Page extends AppCompatActivity
{
    TextInputEditText lab_email_login, lab_password_login;
    Button lab_login;
    TextView lab_forgot_password, lab_signup;
    String lab_email_login_temp, lab_password_login_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_login_page);

        // EditText
        lab_email_login = findViewById(R.id.lab_email_login);
        lab_password_login = findViewById(R.id.lab_password_login);

        // Button
        lab_login = findViewById(R.id.lab_login);

        // TextView
        lab_forgot_password = findViewById(R.id.lab_forgot_password);
        lab_signup = findViewById(R.id.lab_signup);

        lab_forgot_password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Lab_Login_Page.this, Lab_Forgot_Password.class);
                startActivity(intent);
                finish();
            }
        });

        lab_signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Lab_Login_Page.this, Lab_Registration_Page.class);
                startActivity(intent);
                finish();
            }
        });

        lab_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lab_email_login_temp = lab_email_login.getText().toString();
                lab_password_login_temp = lab_password_login.getText().toString();

                if(lab_email_login_temp.equals(""))
                {
                    Toast.makeText(Lab_Login_Page.this, "Please fill out the E-Mail Filed.", Toast.LENGTH_SHORT).show();
                }

                if(lab_password_login_temp.equals(""))
                {
                    Toast.makeText(Lab_Login_Page.this, "Please fill out the Password Filed.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(Lab_Login_Page.this))
                    {
                        getLabLogin(lab_email_login_temp, lab_password_login_temp);
                    }

                    else
                    {
                        Toast.makeText(Lab_Login_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getLabLogin(String lab_email_login, String lab_password_login)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Lab_Login_Page.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("lab_email_login", lab_email_login);
        jsonParameters.put("lab_password_login", lab_password_login);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(Lab_Login_Page.this);
        progressDialog.setMessage("Please Wait Log-In process is in Progress");
        progressDialog.setTitle("Lab Log-In");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(Lab_Login_Page.this);

        JsonObjectRequest lab_Login_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_LOGIN, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if(lab_return_message.equals("Please Verify Your Lab Account"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("Verify Your Account");
                    builder.setMessage("Please Verify your Account First then Log-In will be Done.\n\nPlease click on 'Verify Account' to Verify Your Account.");

                    builder.setPositiveButton("Verify Account", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Lab_Login_Page.this, Lab_Account_Verification.class);
                            intent.putExtra("lab_sent_mail", lab_email_login);

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

                // 2nd Possible Response
                else if(lab_return_message.equals("Lab Login Fail Wrong Password"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Lab_Login_Page.this, "Wrong Password. Please Try Again.", Toast.LENGTH_LONG).show();
                }

                //3rd Possible Response
                else if(lab_return_message.equals("You Does Not Exist as Lab please register yourself first"))
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
                            Intent intent = new Intent(Lab_Login_Page.this, Lab_Registration_Page.class);
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
                else if(lab_return_message.equals("Something went wrong in Lab Login"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Lab_Login_Page.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //5th Possible Response
                else if(lab_return_message.equals("Lab Login Success"))
                {
                    progressDialog.dismiss();

                    LabLoginSessionManagement labLoginSessionManagement = new LabLoginSessionManagement(Lab_Login_Page.this);
                    labLoginSessionManagement.setLab_email_login(lab_email_login);
                    labLoginSessionManagement.setLab_password_login(lab_password_login);


                    Intent intent = new Intent(Lab_Login_Page.this, Welcome_Lab_Page.class);
                    intent.putExtra("lab_sent_mail", lab_email_login);
                    startActivity(intent);
                    finish();
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

        lab_Login_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(lab_Login_POST_Request);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Lab_Login_Page.this, App_Main_Page.class);
        startActivity(intent);
        finish();
    }
}