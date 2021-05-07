package com.example.laspost10h.delivery.after_login;

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
import androidx.fragment.app.FragmentManager;

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
import com.example.laspost10h.delivery.after_login.user_side_request_otp_section.User_Delivery_Boy_Confirm_otp_Generate;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Delivery_User_Request_Take_Action extends Fragment
{
    View view;

    private static final int REQUEST_CODE = 777;

    MaterialTextView user_delivery_request_id, user_delivery_request_applicant_name, user_delivery_request_applicant_phone;
    MaterialTextView user_delivery_request_applicant_email, user_delivery_request_lab_name, user_delivery_request_lab_phone;
    MaterialTextView user_delivery_request_lab_email, user_delivery_request_test_name, user_delivery_request_test_price;
    MaterialTextView user_delivery_request_instruction;

    JustifiedTextView  user_delivery_request_applicant_address, user_delivery_request_lab_address;

    Button user_delivery_request_reject_button, user_delivery_request_accept_button;

    String delivery_received_request_id, delivery_received_applicant_name, delivery_received_applicant_address;
    String delivery_received_applicant_phone, delivery_received_applicant_email, delivery_received_lab_name;
    String delivery_received_lab_address, delivery_received_lab_phone, delivery_received_lab_email, delivery_received_test_name;
    String delivery_received_test_price, delivery_received_test_50_price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery_user_request_take_action, container, false);

        //Receiving Data from Previous Fragments
        Bundle bundle = getArguments();
        delivery_received_request_id = bundle.getString("request_id");

        delivery_received_applicant_name = bundle.getString("applicant_name");
        delivery_received_applicant_address = bundle.getString("applicant_address");
        delivery_received_applicant_phone = bundle.getString("applicant_phone");
        delivery_received_applicant_email = bundle.getString("applicant_mail");

        delivery_received_lab_name = bundle.getString("lab_name");
        delivery_received_lab_address = bundle.getString("lab_address");
        delivery_received_lab_phone = bundle.getString("lab_phone");
        delivery_received_lab_email = bundle.getString("lab_mail");

        delivery_received_test_name = bundle.getString("test_name");
        delivery_received_test_price = bundle.getString("test_price");
        delivery_received_test_50_price = bundle.getString("50_price");

        // Material TextView
        user_delivery_request_id = (MaterialTextView) view.findViewById(R.id.user_delivery_request_id);

        user_delivery_request_applicant_name = (MaterialTextView) view.findViewById(R.id.user_delivery_request_applicant_name);
        user_delivery_request_applicant_phone = (MaterialTextView) view.findViewById(R.id.user_delivery_request_applicant_phone);
        user_delivery_request_applicant_email = (MaterialTextView) view.findViewById(R.id.user_delivery_request_applicant_email);

        user_delivery_request_lab_name = (MaterialTextView) view.findViewById(R.id.user_delivery_request_lab_name);
        user_delivery_request_lab_phone = (MaterialTextView) view.findViewById(R.id.user_delivery_request_lab_phone);
        user_delivery_request_lab_email = (MaterialTextView) view.findViewById(R.id.user_delivery_request_lab_email);

        user_delivery_request_test_name = (MaterialTextView) view.findViewById(R.id.user_delivery_request_test_name);
        user_delivery_request_test_price = (MaterialTextView) view.findViewById(R.id.user_delivery_request_test_price);
        user_delivery_request_instruction = (MaterialTextView) view.findViewById(R.id.user_delivery_request_instruction);

        // Justified Text
        user_delivery_request_applicant_address = (JustifiedTextView) view.findViewById(R.id.user_delivery_request_applicant_address);
        user_delivery_request_lab_address = (JustifiedTextView) view.findViewById(R.id.user_delivery_request_lab_address);

        //Button
        user_delivery_request_reject_button = (Button) view.findViewById(R.id.user_delivery_request_reject_button);
        user_delivery_request_accept_button = (Button) view.findViewById(R.id.user_delivery_request_accept_button);

        // Setting the Data
        user_delivery_request_id.setText("Request I'D: - " + delivery_received_request_id);

        user_delivery_request_applicant_name.setText("Applicant Name: - " + delivery_received_applicant_name);
        user_delivery_request_applicant_address.setText("Applicant Address: - " + delivery_received_applicant_address);
        user_delivery_request_applicant_phone.setText("Applicant Phone: - " + delivery_received_applicant_phone);
        user_delivery_request_applicant_email.setText("Applicant E-Mail I'D: - " + delivery_received_applicant_email);

        user_delivery_request_lab_name.setText("Lab Name: - " + delivery_received_lab_name);
        user_delivery_request_lab_address.setText("Lab Address: - " + delivery_received_lab_address);
        user_delivery_request_lab_phone.setText("Lab Phone: - " + delivery_received_lab_phone);
        user_delivery_request_lab_email.setText("Lab E-Mail I'D: - " + delivery_received_lab_email);

        user_delivery_request_test_name.setText("Test Name: - " + delivery_received_test_name);
        user_delivery_request_test_price.setText("Test Price: - " + delivery_received_test_price + ".00 ₹");

        user_delivery_request_instruction.setText("* You have to Collect Only "+ delivery_received_test_50_price + ".00 ₹ Amount From Customer.");

        user_delivery_request_reject_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(Utility.isNetworkAvailable(getActivity()))
                {
                    getUserDeliveryRejectRequest(delivery_received_request_id, delivery_received_applicant_name, delivery_received_applicant_email, delivery_received_lab_name, delivery_received_lab_email, delivery_received_test_name);
                }

                else
                {
                    Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        user_delivery_request_accept_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(Utility.isNetworkAvailable(getActivity()))
                {
                    getUserDeliveryAcceptRequest(delivery_received_request_id, delivery_received_applicant_name, delivery_received_applicant_email, delivery_received_lab_name, delivery_received_lab_email, delivery_received_test_name);
                }
                else
                {
                    Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        user_delivery_request_applicant_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String num = delivery_received_applicant_phone;
                requestCallPermissionThenDialOrCallNumber(num);
            }
        });

        user_delivery_request_lab_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String num = delivery_received_lab_phone;
                requestCallPermissionThenDialOrCallNumber(num);
            }
        });

        user_delivery_request_applicant_email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{delivery_received_applicant_email});
                startActivity(Intent.createChooser(intent, "Send Mail via"));
            }
        });

        user_delivery_request_lab_email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{delivery_received_lab_email});
                startActivity(Intent.createChooser(intent, "Send Mail via"));
            }
        });

        return  view;
    }

    private void getUserDeliveryRejectRequest(String request_id, String applicant_name, String applicant_email, String lab_name, String lab_email, String test_name)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("request_id", request_id);

        jsonParameters.put("user_name", applicant_name);
        jsonParameters.put("user_email", applicant_email);

        jsonParameters.put("lab_name", lab_name);
        jsonParameters.put("lab_email", lab_email);

        jsonParameters.put("test_name", test_name);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Rejecting Delivery Request from General User");
        progressDialog.setTitle("Rejecting Delivery Request");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest delivery_Reject_Delivery_Request_From_User_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.REJECT_DELIVERY_REQUEST_FROM_USER, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (transaction_return_message.equals("Something went wrong in Delivery User Request Reject"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (transaction_return_message.equals("Delivery Request from User is not Rejected"))
                {
                    Toast.makeText(getActivity(), "Some How Delivery Request is Not Rejected. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (transaction_return_message.equals("Delivery Request from User is Rejected Successfully and Delivery Boy Reject Request Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Delivery Request from General User has been Rejected Successfully.", Toast.LENGTH_LONG).show();
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
        delivery_Reject_Delivery_Request_From_User_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(delivery_Reject_Delivery_Request_From_User_POST_Request);
    }

    private void getUserDeliveryAcceptRequest(String request_id, String applicant_name, String applicant_email, String lab_name, String lab_email, String test_name)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("request_id", request_id);

        jsonParameters.put("user_name", applicant_name);
        jsonParameters.put("user_email", applicant_email);

        jsonParameters.put("lab_name", lab_name);
        jsonParameters.put("lab_email", lab_email);

        jsonParameters.put("test_name", test_name);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Accepting Delivery Request from General User");
        progressDialog.setTitle("Accepting Delivery Request");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest delivery_Reject_Delivery_Accept_From_User_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.ACCEPT_DELIVERY_REQUEST_FROM_USER, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (transaction_return_message.equals("Something went wrong in Delivery User Request Accept"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (transaction_return_message.equals("Delivery Request from User is not Accepted"))
                {
                    Toast.makeText(getActivity(), "Some How Delivery Request is Not Accepted. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (transaction_return_message.equals("Delivery Request from User is Accepted Successfully and Delivery Boy Accept Request Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Delivery Request has been Accepted Successfully.", Toast.LENGTH_LONG).show();

                    Bundle bundle1 = new Bundle();
                    bundle1.putString("request_id", delivery_received_request_id);

                    bundle1.putString("applicant_name", delivery_received_applicant_name);
                    bundle1.putString("applicant_address", delivery_received_applicant_address);
                    bundle1.putString("applicant_phone", delivery_received_applicant_phone);
                    bundle1.putString("applicant_mail", delivery_received_applicant_email);

                    bundle1.putString("lab_name", delivery_received_lab_name);
                    bundle1.putString("lab_address", delivery_received_lab_address);
                    bundle1.putString("lab_phone", delivery_received_lab_phone);
                    bundle1.putString("lab_mail", delivery_received_lab_email);

                    bundle1.putString("test_name", delivery_received_test_name);
                    bundle1.putString("test_price", delivery_received_test_price);
                    bundle1.putString("50_price", delivery_received_test_50_price);

                    User_Delivery_Boy_Confirm_otp_Generate user_delivery_boy_confirm_otp_generate = new User_Delivery_Boy_Confirm_otp_Generate();
                    user_delivery_boy_confirm_otp_generate.setArguments(bundle1);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, user_delivery_boy_confirm_otp_generate).addToBackStack(null).commit();
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
        delivery_Reject_Delivery_Accept_From_User_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(delivery_Reject_Delivery_Accept_From_User_POST_Request);
    }

    private void requestCallPermissionThenDialOrCallNumber(String num)
    {
        String dial_num = "tel:" + num;

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