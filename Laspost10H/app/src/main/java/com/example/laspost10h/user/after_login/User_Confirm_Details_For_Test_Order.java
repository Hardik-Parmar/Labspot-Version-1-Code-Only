package com.example.laspost10h.user.after_login;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.codesgood.views.JustifiedTextView;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class User_Confirm_Details_For_Test_Order extends Fragment
{
    View view;

    String user_lab_received_name, user_lab_received_address;
    String user_lab_received_phone, user_lab_received_mail;

    String user_received_email, user_received_name;
    String user_received_address, user_received_phone, user_received_city;

    String user_received_test_name,user_received_test_description, user_received_test_price;

    MaterialTextView user_confirm_detail_for_test_request_your_phone, user_confirm_detail_for_test_request_lab_name;
    MaterialTextView user_confirm_detail_for_test_request_lab_phone, user_confirm_detail_for_test_request_lab_mail;
    MaterialTextView user_confirm_detail_for_test_request_test_name, user_confirm_detail_for_test_request_test_price;

    JustifiedTextView user_confirm_detail_for_test_request_your_address;

    Button user_confirm_detail_for_test_request_place_request;

    int test_amount;

    private static final int REQUEST_CODE = 777;

    String payable_amount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_confirm_details_for_test_order, container, false);

        Bundle  bundle = getArguments();

        user_lab_received_name = bundle.getString("user_lab_name");
        user_lab_received_address = bundle.getString("user_lab_address");
        user_lab_received_phone = bundle.getString("user_lab_phone");
        user_lab_received_mail = bundle.getString("user_lab_mail");

        user_received_email = bundle.getString("user_sent_mail");
        user_received_name = bundle.getString("user_sent_name");
        user_received_address = bundle.getString("user_sent_address");
        user_received_phone = bundle.getString("user_sent_phone");
        user_received_city = bundle.getString("user_sent_city");

        user_received_test_name = bundle.getString("user_lab_test_name");
        user_received_test_description = bundle.getString("user_lab_test_description");
        user_received_test_price = bundle.getString("user_lab_test_price");


        // adding 20% as Access Fee and Other Tax
        test_amount = Integer.parseInt(user_received_test_price);
        test_amount = test_amount + ((test_amount * 20) / 100);

        payable_amount = String.valueOf(test_amount);

        // Material TextView
        user_confirm_detail_for_test_request_your_phone = (MaterialTextView) view.findViewById(R.id.user_confirm_detail_for_test_request_your_phone);
        user_confirm_detail_for_test_request_lab_name = (MaterialTextView) view.findViewById(R.id.user_confirm_detail_for_test_request_lab_name);
        user_confirm_detail_for_test_request_lab_phone = (MaterialTextView) view.findViewById(R.id.user_confirm_detail_for_test_request_lab_phone);
        user_confirm_detail_for_test_request_lab_mail = (MaterialTextView) view.findViewById(R.id.user_confirm_detail_for_test_request_lab_mail);
        user_confirm_detail_for_test_request_test_name = (MaterialTextView) view.findViewById(R.id.user_confirm_detail_for_test_request_test_name);
        user_confirm_detail_for_test_request_test_price = (MaterialTextView) view.findViewById(R.id.user_confirm_detail_for_test_request_test_price);

        // Justified TextView
        user_confirm_detail_for_test_request_your_address = (JustifiedTextView) view.findViewById(R.id.user_confirm_detail_for_test_request_your_address);

        //Button
        user_confirm_detail_for_test_request_place_request = (Button) view.findViewById(R.id.user_confirm_detail_for_test_request_place_request);

        //Setting the Data

        user_confirm_detail_for_test_request_your_address.setText("Your Address: - " + user_received_address);
        user_confirm_detail_for_test_request_your_phone.setText("Your Phone: - " + user_received_phone);
        user_confirm_detail_for_test_request_lab_name.setText("Lab Name: - " + user_lab_received_name);
        user_confirm_detail_for_test_request_lab_phone.setText("Lab Contact Number: - " + user_lab_received_phone);
        user_confirm_detail_for_test_request_lab_mail.setText("Lab E-Mail I'd: - " + user_lab_received_mail);
        user_confirm_detail_for_test_request_test_name.setText("Your Selected Test Name: - " + user_received_test_name);
        user_confirm_detail_for_test_request_test_price.setText("Your Payable Amount is: - " + payable_amount+".00 ₹");

        user_confirm_detail_for_test_request_place_request.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(Utility.isNetworkAvailable(getActivity()))
                {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd / MM / yyyy HH : mm : ss", Locale.getDefault());
                    String currentDateAndTime = simpleDateFormat.format(new Date());

                    getSubmitTestRequestToLab(user_received_name, user_received_address, user_received_phone, user_received_email, user_received_city,
                            user_lab_received_name, user_lab_received_address, user_lab_received_phone, user_lab_received_mail,
                            user_received_test_name, user_received_test_description, user_received_test_price,
                            currentDateAndTime);
                }

                else
                {
                    Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        user_confirm_detail_for_test_request_lab_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                requestCallPermissionThenDialOrCallNumber();
            }
        });

        user_confirm_detail_for_test_request_lab_mail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{user_lab_received_mail});
                startActivity(Intent.createChooser(intent, "Send Mail via"));
            }
        });

        return view;
    }

    private void getSubmitTestRequestToLab(String user_name, String user_address, String user_phone, String user_mail, String user_city,
                                           String lab_name, String lab_address, String lab_phone, String lab_mail,
                                           String test_name, String test_description, String test_price,
                                           String currentDateAndTime)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("user_name", user_name);
        jsonParameters.put("user_address", user_address);
        jsonParameters.put("user_phone", user_phone);
        jsonParameters.put("user_email", user_mail);
        jsonParameters.put("transaction_city", user_city);

        jsonParameters.put("lab_name", lab_name);
        jsonParameters.put("lab_address", lab_address);
        jsonParameters.put("lab_phone", lab_phone);
        jsonParameters.put("lab_email", lab_mail);

        jsonParameters.put("test_name", test_name);
        jsonParameters.put("test_description", test_description);
        jsonParameters.put("test_price", payable_amount);

        jsonParameters.put("date_of_test_order", currentDateAndTime);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Submitting Your Test Request to the Lab");
        progressDialog.setTitle("Test Request to Lab");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest user_Place_Test_Request_To_Lab_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.PLACE_TEST_REQUEST_TO_THE_LAB, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String transaction_return_message = null;

                try
                {
                    transaction_return_message = (String) response.get("transaction_return_message");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st possible response
                if (transaction_return_message.equals("Something went wrong in Place Test Request to Lab"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (transaction_return_message.equals("Test Request is not Submitted"))
                {
                    Toast.makeText(getActivity(), "Some How Your Test Request to the Lab didn't Submitted. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (transaction_return_message.equals("Test Request Submitted Successfully and Place Test Request to Lab Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Congrats, Your Request for the Test to the Lab has been Successfully Submitted.", Toast.LENGTH_LONG).show();
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
        user_Place_Test_Request_To_Lab_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(user_Place_Test_Request_To_Lab_POST_Request);
    }

    private void requestCallPermissionThenDialOrCallNumber()
    {
        String dial_num = "tel:" + user_lab_received_phone;

        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE))
        {
            // User denied Permission

            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission Needed")
                    .setMessage("Call Permission needed for Dialling or Calling the Number Displayed on the Screen.")
                    .setPositiveButton("Grant Permission", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // Ask For Permission
                            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CODE);

                            // If Permission is Granted then SelectImage Logic
                            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                            {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(dial_num));
                                startActivity(intent);
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else
        {
            // Ask For Permission
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            // If Permission is Granted then SelectImage Logic
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(dial_num));
                startActivity(intent);
            }
        }
    }
}