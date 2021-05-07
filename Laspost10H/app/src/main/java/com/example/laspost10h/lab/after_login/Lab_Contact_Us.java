package com.example.laspost10h.lab.after_login;

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

public class Lab_Contact_Us extends Fragment
{
    View view;
    TextInputEditText lab_contact_name, lab_contact_email, lab_contact_feedback;
    Button lab_contact_us_submit;
    String lab_contact_name_temp, lab_contact_email_temp, lab_contact_feedback_temp, lab_category_temp, lab_received_name, lab_received_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lab_contact_us, container, false);

        Bundle bundle = getArguments();
        lab_received_email = bundle.getString("lab_sent_mail");
        lab_received_name = bundle.getString("lab_sent_name");

        //EditText
        lab_contact_name = (TextInputEditText) view.findViewById(R.id.lab_contact_name);
        lab_contact_email = (TextInputEditText) view.findViewById(R.id.lab_contact_email);
        lab_contact_feedback = (TextInputEditText) view.findViewById(R.id.lab_contact_feedback);

        lab_contact_name_temp = lab_received_name;
        lab_contact_email_temp = lab_received_email;

        lab_contact_name.setText(lab_contact_name_temp);
        lab_contact_email.setText(lab_contact_email_temp);

        //Button
        lab_contact_us_submit = (Button) view.findViewById(R.id.lab_contact_us_submit);

        lab_contact_us_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lab_contact_name_temp = lab_contact_name.getText().toString();
                lab_contact_email_temp = lab_contact_email.getText().toString();
                lab_contact_feedback_temp = lab_contact_feedback.getText().toString();

                lab_category_temp = "Laboratory";

                if(lab_contact_name_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the Name Field.", Toast.LENGTH_SHORT).show();
                }

                if(lab_contact_email_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the E-Mail Field.", Toast.LENGTH_SHORT).show();
                }

                if(lab_contact_feedback_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the Feedback Field.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(getActivity()))
                    {
                        getLabFeedbackRegister(lab_contact_name_temp, lab_contact_email_temp, lab_contact_feedback_temp, lab_category_temp);
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

    private void getLabFeedbackRegister(String name, String email, String feedback, String user_type)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("name", name);
        jsonParameters.put("email", email);
        jsonParameters.put("feedback", feedback);
        jsonParameters.put("user_type", user_type);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Submitting of Feedback is in Progress");
        progressDialog.setTitle("Feedback");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest lab_Contact_Us_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.CONTACT_US, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String return_message = null;

                try
                {
                    return_message = (String) response.get("return_message");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st possible response
                if (return_message.equals("Something went wrong in Contact Us"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (return_message.equals("Feedback is not Submitted"))
                {
                    Toast.makeText(getActivity(), "Some How Your Password Didn't Changed. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (return_message.equals("Feedback Submitted Successfully and Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Congrats, Your Feedback has been Submitted Successfully.", Toast.LENGTH_LONG).show();
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


        // User Forgot Password API CALL
        lab_Contact_Us_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(lab_Contact_Us_POST_Request);
    }
}