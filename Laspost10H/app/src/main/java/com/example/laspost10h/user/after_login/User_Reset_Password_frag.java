package com.example.laspost10h.user.after_login;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class User_Reset_Password_frag extends Fragment
{
    View view;

    String user_received_email, user_reset_password_2_temp;
    TextInputEditText user_reset_password_2;
    Button user_reset_password_submit_2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_reset_password_frag, container, false);

        Bundle bundle = getArguments();
        user_received_email = bundle.getString("user_sent_mail");

        // EditText
        user_reset_password_2 = (TextInputEditText) view.findViewById(R.id.user_reset_password_2);

        // Button
        user_reset_password_submit_2 = (Button) view.findViewById(R.id.user_reset_password_submit_2);

        user_reset_password_submit_2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user_reset_password_2_temp = user_reset_password_2.getText().toString();

                if (user_reset_password_2_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the Reset Password Field.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (Utility.isNetworkAvailable(getActivity()))
                    {
                        getUserResetPassword(user_received_email, user_reset_password_2_temp);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return  view;
    }

    private void getUserResetPassword(String email, String password)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("user_email", email);
        jsonParameters.put("user_new_password", password);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Password Reset process is in Progress");
        progressDialog.setTitle("General User Reset Password");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest user_Reset_Password_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.USER_RESET_PASSWORD, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (user_return_message.equals("Something went wrong in User Password Reset"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd Possible Response
                else if (user_return_message.equals("User Please verify OTP for the reset Password"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Please Verify Your Account First.", Toast.LENGTH_SHORT).show();
                }

                //3rd Possible Response
                else if (user_return_message.equals("You Does Not Exist as General User please register yourself first"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Your Account Did not Found in App, Please register your self First.", Toast.LENGTH_SHORT).show();
                }


                //4th Possible Response
                else if(user_return_message.equals("User Password has not been Reset"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Some How Your Password Didn't Reset. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                //5th Possible Response
                else if (user_return_message.equals("User Password has been Reset and User Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Congrats, Your Password has been Reset Successfully.", Toast.LENGTH_LONG).show();
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

        user_Reset_Password_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(user_Reset_Password_POST_Request);
    }
}