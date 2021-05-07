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
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Lab_Edit_Test_Detail extends Fragment
{
    View view;

    String lab_test_received_id, lab_test_received_mail, lab_test_received_name, lab_test_received_description, lab_test_received_price;

    TextInputEditText lab_edit_test_name, lab_edit_test_description, lab_edit_test_price;
    String lab_edit_test_name_temp, lab_edit_test_description_temp, lab_edit_test_price_temp;

    Button lab_edit_test_details_submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lab_edit_test_detail, container, false);

        Bundle bundle = getArguments();
        lab_test_received_id = bundle.getString("lab_test_sent_id");
        lab_test_received_mail = bundle.getString("lab_test_sent_mail");
        lab_test_received_name  = bundle.getString("lab_test_sent_name");
        lab_test_received_description = bundle.getString("lab_test_sent_description");
        lab_test_received_price = bundle.getString("lab_test_sent_price");

        // EditText
        lab_edit_test_name = (TextInputEditText) view.findViewById(R.id.lab_edit_test_name);
        lab_edit_test_description = (TextInputEditText) view.findViewById(R.id.lab_edit_test_description);
        lab_edit_test_price = (TextInputEditText) view.findViewById(R.id.lab_edit_test_price);

        // Button
        lab_edit_test_details_submit = (Button) view.findViewById(R.id.lab_edit_test_details_submit);

        // Setting the Received Data into the form
        lab_edit_test_name.setText(lab_test_received_name);
        lab_edit_test_description.setText(lab_test_received_description);
        lab_edit_test_price.setText(lab_test_received_price);

        lab_edit_test_details_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lab_edit_test_name_temp = lab_edit_test_name.getText().toString();
                lab_edit_test_description_temp = lab_edit_test_description.getText().toString();
                lab_edit_test_price_temp = lab_edit_test_price.getText().toString();

                if(Utility.isNetworkAvailable(getActivity()))
                {
                    getLabUpdateTestDetail(lab_test_received_id, lab_edit_test_name_temp, lab_edit_test_description_temp, lab_edit_test_price_temp);
                }

                else
                {
                    Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void getLabUpdateTestDetail(String id, String lab_test_name, String lab_test_description, String lab_test_price)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("lab_test_id", id);
        jsonParameters.put("lab_test_name", lab_test_name);
        jsonParameters.put("lab_test_description", lab_test_description);
        jsonParameters.put("lab_test_price", lab_test_price);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait New Test Details are being updating into the Server");
        progressDialog.setTitle("Updating New Test Details");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest lab_Edit_Test_Details_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_EDIT_TEST_DETAILS, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (lab_return_message.equals("Something went wrong in Lab Edit Test Details"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (lab_return_message.equals("Lab Test Details are not Updated"))
                {
                    Toast.makeText(getActivity(), "Some how your new Test Details Didn't updated to the Server. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (lab_return_message.equals("Lab Test Details are Updated Successfully"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Congrats, Your Test Details are Successfully updated into the Server.", Toast.LENGTH_LONG).show();
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
        lab_Edit_Test_Details_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(lab_Edit_Test_Details_POST_Request);
    }
}