package com.example.laspost10h.user.registration_and_login_system;

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
import com.example.laspost10h.SupportClass.UserLoginSessionManagement;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;

import com.example.laspost10h.user.after_login.Welcome_User_Page;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_Login_Page extends AppCompatActivity
{
    TextInputEditText user_email_login, user_password_login;
    Button user_login;
    TextView user_forgot_password, user_signup;

    String user_email_login_temp, user_password_login_temp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_page);

        // EditText
        user_email_login = findViewById(R.id.user_email_login);
        user_password_login = findViewById(R.id.user_password_login);

        // Button
        user_login = findViewById(R.id.user_login);


        // TextView
        user_forgot_password = findViewById(R.id.user_forgot_password);
        user_signup = findViewById(R.id.user_signup);
        
        user_forgot_password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(User_Login_Page.this, User_Forgot_Password.class);
                startActivity(intent);
                finish();
            }
        });

        user_signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(User_Login_Page.this, User_Registration_Page.class);
                startActivity(intent);
                finish();
            }
        });

        user_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user_email_login_temp = user_email_login.getText().toString();
                user_password_login_temp = user_password_login.getText().toString();
                
                if(user_email_login_temp.equals(""))
                {
                    Toast.makeText(User_Login_Page.this, "Please fill out the E-Mail Filed.", Toast.LENGTH_SHORT).show();
                }
                
                if(user_password_login_temp.equals(""))
                {
                    Toast.makeText(User_Login_Page.this, "Please fill out the Password Filed.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(User_Login_Page.this))
                    {
                        getUserLogin(user_email_login_temp, user_password_login_temp);
                    }

                    else
                    {
                        Toast.makeText(User_Login_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getUserLogin(String user_email_login, String user_password_login)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(User_Login_Page.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("user_email_login", user_email_login);
        jsonParameters.put("user_password_login", user_password_login);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(User_Login_Page.this);
        progressDialog.setMessage("Please Wait Log-In process is in Progress");
        progressDialog.setTitle("General User Log-In");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(User_Login_Page.this);

        JsonObjectRequest user_Login_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.USER_LOGIN, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String user_return_message = null;

                try
                {
                    user_return_message =(String) response.get("user_return_message");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st Possible Response
                if(user_return_message.equals("Please Verify Your User Account"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("Verify Your Account");
                    builder.setMessage("Please Verify your Account First then Log-In will be Done.\n\nPlease Check Your Mail for Verification of Account.");

                    builder.setPositiveButton("Verify Account", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(User_Login_Page.this, User_Account_Verification.class);
                            intent.putExtra("user_sent_mail", user_email_login);

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
                else if(user_return_message.equals("User Login Fail Wrong Password"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(User_Login_Page.this, "Wrong Password. Please Try Again.", Toast.LENGTH_LONG).show();
                }

                //3rd Possible Response
                else if(user_return_message.equals("You Does Not Exist as General User please register yourself first"))
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
                            Intent intent = new Intent(User_Login_Page.this, User_Registration_Page.class);
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
                else if(user_return_message.equals("Something went wrong in User Login"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(User_Login_Page.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //5th Possible Response
                else if(user_return_message.equals("User Login Success"))
                {
                    progressDialog.dismiss();

                    UserLoginSessionManagement userLoginSessionManagement = new UserLoginSessionManagement(User_Login_Page.this);
                    userLoginSessionManagement.setUser_email_login(user_email_login);
                    userLoginSessionManagement.setUser_password_login(user_password_login);


                    Intent intent = new Intent(User_Login_Page.this, Welcome_User_Page.class);
                    intent.putExtra("user_sent_mail", user_email_login);
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

        user_Login_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(user_Login_POST_Request);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(User_Login_Page.this, App_Main_Page.class);
        startActivity(intent);
        finish();
    }
}