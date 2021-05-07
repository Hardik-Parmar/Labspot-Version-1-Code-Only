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

public class Lab_Add_New_Test_Details extends Fragment
{
    View view;
    TextInputEditText lab_test_name, lab_test_description, lab_test_price;
    String lab_test_name_temp, lab_test_description_temp, lab_test_price_temp, lab_received_name, lab_received_email;
    Button lab_add_new_test_details_submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lab_add_new_test_details, container, false);

        Bundle bundle = getArguments();
        lab_received_email = bundle.getString("lab_sent_mail");
        lab_received_name = bundle.getString("lab_sent_name");

        // EditText
        lab_test_name = (TextInputEditText) view.findViewById(R.id.lab_test_name);
        lab_test_description = (TextInputEditText) view.findViewById(R.id.lab_test_description);
        lab_test_price = (TextInputEditText) view.findViewById(R.id.lab_test_price);

        // Button
        lab_add_new_test_details_submit = (Button) view.findViewById(R.id.lab_add_new_test_details_submit);

        lab_add_new_test_details_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lab_test_name_temp = lab_test_name.getText().toString();
                lab_test_description_temp = lab_test_description.getText().toString();
                lab_test_price_temp = lab_test_price.getText().toString();

                if(lab_test_name_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the Test Name Field.", Toast.LENGTH_SHORT).show();
                }

                if(lab_test_description_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the Test Description Field.", Toast.LENGTH_SHORT).show();
                }

                if(lab_test_price_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the Test Price Field.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(getActivity()))
                    {
                        getLabAddNewTestDetailSubmit(lab_received_name, lab_received_email, lab_test_name_temp, lab_test_description_temp, lab_test_price_temp);
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

    private void getLabAddNewTestDetailSubmit(String lab_name, String lab_email, String lab_test_name, String lab_test_description, String lab_test_price)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("lab_name", lab_name);
        jsonParameters.put("lab_email", lab_email);
        jsonParameters.put("lab_test_name", lab_test_name);
        jsonParameters.put("lab_test_description", lab_test_description);
        jsonParameters.put("lab_test_price", lab_test_price);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait New Test Details are being added to the Server");
        progressDialog.setTitle("Adding New Test Details");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest lab_Add_New_Test_Details_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_ADD_TEST_DETAILS, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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

                // 1st possible response
                if (lab_return_message.equals("Something went wrong in Lab Add Test Details"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (lab_return_message.equals("Lab Test Details are not Added"))
                {
                    Toast.makeText(getActivity(), "Some how your new Test Details Didn't added to the Server. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (lab_return_message.equals("Lab Test Details are Added Successfully"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Congrats, Your Test Details are Successfully added into the Server.", Toast.LENGTH_LONG).show();
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
        lab_Add_New_Test_Details_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(lab_Add_New_Test_Details_POST_Request);
    }
}