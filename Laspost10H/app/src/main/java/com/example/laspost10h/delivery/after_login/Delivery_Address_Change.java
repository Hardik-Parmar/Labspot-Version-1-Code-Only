package com.example.laspost10h.delivery.after_login;

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

public class Delivery_Address_Change extends Fragment
{
    TextInputEditText delivery_change_address;
    Button delivery_change_address_submit;
    View view;
    String delivery_change_address_temp, delivery_received_email, delivery_received_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery_address_change, container, false);

        Bundle bundle = getArguments();
        delivery_received_email = bundle.getString("delivery_sent_mail");
        delivery_received_name = bundle.getString("delivery_sent_name");

        // EditText
        delivery_change_address = (TextInputEditText) view.findViewById(R.id.delivery_change_address);

        // Button
        delivery_change_address_submit = (Button) view.findViewById(R.id.delivery_change_address_submit);

        delivery_change_address_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delivery_change_address_temp = delivery_change_address.getText().toString();

                if(delivery_change_address_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the New Address Field.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(getActivity()))
                    {
                        getDeliveryChangeAddress(delivery_received_email, delivery_change_address_temp, delivery_received_name);
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

    private void getDeliveryChangeAddress(String email, String address, String name)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("delivery_email", email);
        jsonParameters.put("delivery_new_address", address);
        jsonParameters.put("delivery_name", name);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Address Change Process for Delivery Staff is in Progress");
        progressDialog.setTitle("Delivery Staff Address Change");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest delivery_Address_Change_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.DELIVERY_CHANGE_ADDRESS, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (delivery_return_message.equals("Something went wrong in Delivery Change Address"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (delivery_return_message.equals("Delivery Address has not been Changed"))
                {
                    Toast.makeText(getActivity(), "Some How Your Address Didn't Changed. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (delivery_return_message.equals("Delivery Address has been Changed and Delivery Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Congrats, Your Address has been Changed Successfully.", Toast.LENGTH_LONG).show();
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
        delivery_Address_Change_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(delivery_Address_Change_POST_Request);
    }
}