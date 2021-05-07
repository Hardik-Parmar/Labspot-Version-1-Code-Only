package com.example.laspost10h.delivery.after_login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Delivery_Password_Change extends Fragment
{
    View view;
    String delivery_received_email, delivery_received_name, delivery_old_password_temp, delivery_new_password_temp;
    TextInputEditText delivery_old_password, delivery_new_password;
    TextView delivery_forgot_password_2;
    Button delivery_change_password_submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery_password_change, container, false);

        Bundle bundle = getArguments();
        delivery_received_email = bundle.getString("delivery_sent_mail");
        delivery_received_name = bundle.getString("delivery_sent_name");

        // EditText
        delivery_old_password = (TextInputEditText) view.findViewById(R.id.delivery_old_password);
        delivery_new_password = (TextInputEditText) view.findViewById(R.id.delivery_new_password);

        // TextView
        delivery_forgot_password_2 = (TextView) view.findViewById(R.id.delivery_forgot_password_2);

        // Button
        delivery_change_password_submit = (Button) view.findViewById(R.id.delivery_change_password_submit);

        delivery_forgot_password_2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(Utility.isNetworkAvailable(getActivity()))
                {
                    getDeliveryForgotPassword(delivery_received_email);
                }

                else
                {
                    Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delivery_change_password_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delivery_old_password_temp = delivery_old_password.getText().toString();
                delivery_new_password_temp = delivery_new_password.getText().toString();

                if(delivery_old_password_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the Old Password Field.", Toast.LENGTH_SHORT).show();
                }

                if(delivery_new_password_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the New Password Field.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(getActivity()))
                    {
                        getDeliveryPasswordChange(delivery_old_password_temp, delivery_new_password_temp, delivery_received_email, delivery_received_name);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    // User Password Change API CALL
    private void getDeliveryPasswordChange(String old_password, String new_password, String email, String name)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("delivery_email", email);
        jsonParameters.put("delivery_name", name);
        jsonParameters.put("delivery_old_password", old_password);
        jsonParameters.put("delivery_new_password", new_password);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Password Change Process for Delivery Staff is in Progress");
        progressDialog.setTitle("General Delivery Staff Change");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest delivery_Password_Change_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.DELIVERY_CHANGE_PASSWORD, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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

                // 1st possible response
                if (delivery_return_message.equals("Something went wrong in Delivery Change Password"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (delivery_return_message.equals("You Does Not Exist as Delivery Staff please register yourself first"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Your Account Did not Found in App, Please register your self First.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (delivery_return_message.equals("Delivery Please Enter the Correct Old Password"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "You have entered a Wrong Old Password, Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 4th possible response
                else if (delivery_return_message.equals("Delivery Password has not been Changed"))
                {
                    Toast.makeText(getActivity(), "Some How Your Password Didn't Changed. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 5th possible response
                else if (delivery_return_message.equals("Delivery Password has been Changed and Delivery Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Congrats, Your Password has been Changed Successfully.", Toast.LENGTH_LONG).show();
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

        delivery_Password_Change_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(delivery_Password_Change_POST_Request);
    }

    // User Forgot Password API Call
    private void getDeliveryForgotPassword(String email)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("delivery_email", email);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Forgot Password process is in Progress");
        progressDialog.setTitle("Delivery Staff Forgot Password");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd Possible Response
                else if (delivery_return_message.equals("Delivery Forgot OTP Not Generated"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unable to generate OTP. Please Try Again.", Toast.LENGTH_LONG).show();
                }

                //3rd Possible Response
                else if (delivery_return_message.equals("Delivery Forgot OTP Generated and Delivery Forgot Password Email Sent"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("OTP Generated");
                    builder.setMessage("OTP for Reset Password has been generated and has been mailed to you.\n\nPlease click on 'Verify OTP' to verify OTP to Reset your Password.");

                    builder.setPositiveButton("Verify OTP", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Bundle bundle = new Bundle();
                            bundle.putString("delivery_sent_mail", email);

                            Delivery_Forgot_Password_otp_Submit_frag delivery_forgot_password_otp_submit_frag  = new Delivery_Forgot_Password_otp_Submit_frag();
                            delivery_forgot_password_otp_submit_frag.setArguments(bundle);

                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_forgot_password_otp_submit_frag).commit();
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

                    Toast.makeText(getActivity(), "Please Verify Your Account First.", Toast.LENGTH_SHORT).show();
                }

                // 5th Possible Solution
                else if (delivery_return_message.equals("You Does Not Exist as Delivery Staff please register yourself first"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Your Account Did not Found in App, Please register your self First.", Toast.LENGTH_SHORT).show();
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
}