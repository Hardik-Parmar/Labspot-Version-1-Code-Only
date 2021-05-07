package com.example.laspost10h.delivery.after_login.user_side_request_otp_section;

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
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_Delivery_Boy_Confirm_otp_Generate extends Fragment
{
    View view;

    private static final int REQUEST_CODE = 777;

    String delivery_received_request_id, delivery_received_applicant_name, delivery_received_applicant_address;
    String delivery_received_applicant_phone, delivery_received_applicant_email, delivery_received_lab_name;
    String delivery_received_lab_address, delivery_received_lab_phone, delivery_received_lab_email, delivery_received_test_name;
    String delivery_received_test_price, delivery_received_test_50_price;

    MaterialTextView user_lab_delivery_boy_confirm_otp_generate_title, user_lab_delivery_boy_confirm_otp_generate_title_2;
    MaterialTextView user_lab_delivery_boy_confirm_otp_generate_request_id, user_lab_delivery_boy_confirm_otp_generate_applicant_name;
    MaterialTextView user_lab_delivery_boy_confirm_otp_generate_applicant_phone, user_lab_delivery_boy_confirm_otp_generate_applicant_email;
    MaterialTextView user_lab_delivery_boy_confirm_otp_generate_test_name, user_lab_delivery_boy_confirm_otp_generate_test_price;
    MaterialTextView user_lab_delivery_boy_confirm_otp_generate_instruction;

    Button user_lab_delivery_boy_confirm_otp_generate_reached_otp;

    JustifiedTextView user_lab_delivery_boy_confirm_otp_generate_applicant_address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_delivery_boy_confirm_otp_generate, container, false);

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

        // Material Text View
        user_lab_delivery_boy_confirm_otp_generate_title = (MaterialTextView) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_title);
        user_lab_delivery_boy_confirm_otp_generate_title_2 = (MaterialTextView) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_title_2);

        user_lab_delivery_boy_confirm_otp_generate_request_id = (MaterialTextView) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_request_id);
        user_lab_delivery_boy_confirm_otp_generate_applicant_name = (MaterialTextView) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_applicant_name);
        user_lab_delivery_boy_confirm_otp_generate_applicant_phone = (MaterialTextView) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_applicant_phone);
        user_lab_delivery_boy_confirm_otp_generate_applicant_email = (MaterialTextView) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_applicant_email);

        user_lab_delivery_boy_confirm_otp_generate_test_name = (MaterialTextView) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_test_name);
        user_lab_delivery_boy_confirm_otp_generate_test_price = (MaterialTextView) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_test_price);
        user_lab_delivery_boy_confirm_otp_generate_instruction = (MaterialTextView) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_instruction);

        // Justified TextView
        user_lab_delivery_boy_confirm_otp_generate_applicant_address = (JustifiedTextView) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_applicant_address);

        //Button
        user_lab_delivery_boy_confirm_otp_generate_reached_otp = (Button) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_generate_reached_otp);

        //Setting the Data to the Widgets
        user_lab_delivery_boy_confirm_otp_generate_request_id.setText("Request I'D: - " + delivery_received_request_id);
        user_lab_delivery_boy_confirm_otp_generate_applicant_name.setText("Customer Name: - " + delivery_received_applicant_name);
        user_lab_delivery_boy_confirm_otp_generate_applicant_address.setText("Customer Address: - " + delivery_received_applicant_address);
        user_lab_delivery_boy_confirm_otp_generate_applicant_phone.setText("Customer Phone Number: - " + delivery_received_applicant_phone);
        user_lab_delivery_boy_confirm_otp_generate_applicant_email.setText("Customer Mail I'D: - " + delivery_received_applicant_email);
        user_lab_delivery_boy_confirm_otp_generate_test_name.setText("Requested Test Name: - " + delivery_received_test_name);
        user_lab_delivery_boy_confirm_otp_generate_test_price.setText("Request Test Price: - " + delivery_received_test_price + ".00 ₹");
        user_lab_delivery_boy_confirm_otp_generate_instruction.setText("* You have to Collect Only "+ delivery_received_test_50_price + ".00 ₹ Amount From Customer.");

        user_lab_delivery_boy_confirm_otp_generate_reached_otp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(Utility.isNetworkAvailable(getActivity()))
                {
                    getDeliveryBoyVerifyAtCustomerLocation(delivery_received_request_id, delivery_received_applicant_name, delivery_received_applicant_email, delivery_received_lab_name, delivery_received_test_name);
                }

                else
                {
                    Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        user_lab_delivery_boy_confirm_otp_generate_applicant_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String num = delivery_received_applicant_phone;
                requestCallPermissionThenDialOrCallNumber(num);
            }
        });

        user_lab_delivery_boy_confirm_otp_generate_applicant_email.setOnClickListener(new View.OnClickListener()
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

        return view;
    }

    private void getDeliveryBoyVerifyAtCustomerLocation(String request_id, String applicant_name, String applicant_email, String lab_name, String test_name)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("request_id", request_id);

        jsonParameters.put("customer_name", applicant_name);
        jsonParameters.put("customer_email", applicant_email);

        jsonParameters.put("lab_name", lab_name);

        jsonParameters.put("test_name", test_name);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait OTP is being Generated to Verify You");
        progressDialog.setTitle("Generating OTP");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest delivery_Generate_OTP_To_Verify_Himself_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.USER_SIDE_OTP_GENERATION_TO_VERIFY_DELIVERY_BOY, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (transaction_return_message.equals("Something went wrong in User Request Delivery Verify otp Generate"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (transaction_return_message.equals("OTP to Verify Delivery Person has not been generated"))
                {
                    Toast.makeText(getActivity(), "Some How OTP to Verify you has not been Generated. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (transaction_return_message.equals("OTP to Verify Delivery Person has been generated Successfully and Delivery Boy Confirm OTP Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "OTP to Authenticate you been Generated.", Toast.LENGTH_LONG).show();

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

                    User_Delivery_Boy_Confirm_otp_Verify user_delivery_boy_confirm_otp_verify = new User_Delivery_Boy_Confirm_otp_Verify();
                    user_delivery_boy_confirm_otp_verify.setArguments(bundle1);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, user_delivery_boy_confirm_otp_verify).addToBackStack(null).commit();
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
        delivery_Generate_OTP_To_Verify_Himself_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(delivery_Generate_OTP_To_Verify_Himself_POST_Request);
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