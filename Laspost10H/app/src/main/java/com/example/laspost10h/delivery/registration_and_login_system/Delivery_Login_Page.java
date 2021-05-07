package com.example.laspost10h.delivery.registration_and_login_system;

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
import com.example.laspost10h.SupportClass.DeliveryLoginSessionManagement;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.example.laspost10h.delivery.after_login.Welcome_Delivery_Page;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Delivery_Login_Page extends AppCompatActivity
{
    TextInputEditText delivery_email_login, delivery_password_login;
    Button delivery_login;
    TextView delivery_forgot_password, delivery_signup;
    String delivery_email_login_temp, delivery_password_login_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_login_page);

        // EditText
        delivery_email_login = findViewById(R.id.delivery_email_login);
        delivery_password_login = findViewById(R.id.delivery_password_login);

        // Button
        delivery_login = findViewById(R.id.delivery_login);


        // TextView
        delivery_forgot_password = findViewById(R.id.delivery_forgot_password);
        delivery_signup = findViewById(R.id.delivery_signup);

        delivery_forgot_password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Delivery_Login_Page.this, Delivery_Forgot_Password.class);
                startActivity(intent);
                finish();
            }
        });

        delivery_signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Delivery_Login_Page.this, Delivery_Registration_Page.class);
                startActivity(intent);
                finish();
            }
        });

        delivery_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delivery_email_login_temp = delivery_email_login.getText().toString();
                delivery_password_login_temp = delivery_password_login.getText().toString();

                if(delivery_email_login_temp.equals(""))
                {
                    Toast.makeText(Delivery_Login_Page.this, "Please fill out the E-Mail Filed.", Toast.LENGTH_SHORT).show();
                }

                if(delivery_password_login_temp.equals(""))
                {
                    Toast.makeText(Delivery_Login_Page.this, "Please fill out the Password Filed.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(Delivery_Login_Page.this))
                    {
                        getDeliveryLogin(delivery_email_login_temp, delivery_password_login_temp);
                    }

                    else
                    {
                        Toast.makeText(Delivery_Login_Page.this, "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getDeliveryLogin(String delivery_email_login, String delivery_password_login)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Delivery_Login_Page.this);

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("delivery_email_login", delivery_email_login);
        jsonParameters.put("delivery_password_login", delivery_password_login);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(Delivery_Login_Page.this);
        progressDialog.setMessage("Please Wait Log-In process is in Progress");
        progressDialog.setTitle("Delivery Staff Log-In");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(Delivery_Login_Page.this);

        JsonObjectRequest delivery_Login_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.DELIVERY_LOGIN, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String delivery_return_message = null;

                try
                {
                    delivery_return_message =(String) response.get("delivery_return_message");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st Possible Response
                if(delivery_return_message.equals("Please Activate Your Delivery Account"))
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
                            Intent intent = new Intent(Delivery_Login_Page.this, Delivery_Account_Verification.class);
                            intent.putExtra("delivery_sent_mail", delivery_email_login);

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
                else if(delivery_return_message.equals("Delivery Login Fail Wrong Password"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Delivery_Login_Page.this, "Wrong Password. Please Try Again.", Toast.LENGTH_LONG).show();
                }

                //3rd Possible Response
                else if(delivery_return_message.equals("You Does Not Exist as Delivery Staff please register yourself first"))
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
                            Intent intent = new Intent(Delivery_Login_Page.this, Delivery_Registration_Page.class);
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
                else if(delivery_return_message.equals("Something went wrong in Delivery Login"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(Delivery_Login_Page.this, "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //5th Possible Response
                else if(delivery_return_message.equals("Delivery Login Success"))
                {
                    progressDialog.dismiss();

                    DeliveryLoginSessionManagement deliveryLoginSessionManagement = new DeliveryLoginSessionManagement(Delivery_Login_Page.this);
                    deliveryLoginSessionManagement.setDelivery_email_login(delivery_email_login);
                    deliveryLoginSessionManagement.setDelivery_password_login(delivery_password_login);


                    Intent intent = new Intent(Delivery_Login_Page.this, Welcome_Delivery_Page.class);
                    intent.putExtra("delivery_sent_mail", delivery_email_login);
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

        delivery_Login_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(delivery_Login_POST_Request);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Delivery_Login_Page.this, App_Main_Page.class);
        startActivity(intent);
        finish();
    }
}