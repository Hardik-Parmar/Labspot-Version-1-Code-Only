package com.example.laspost10h.delivery.after_login.lab_side_request_otp_section;

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

public class Lab_Delivery_Boy_Confirm_otp_Generate_Part_2 extends Fragment
{
    View view;

    private static final int REQUEST_CODE = 777;

    String delivery_received_request_id, delivery_received_applicant_name, delivery_received_applicant_address;
    String delivery_received_applicant_phone, delivery_received_applicant_email, delivery_received_lab_name;
    String delivery_received_lab_address, delivery_received_lab_phone, delivery_received_lab_email, delivery_received_test_name;
    String delivery_received_test_price, delivery_received_test_50_price;

    MaterialTextView lab_user_delivery_boy_confirm_otp_generate_title, lab_user_delivery_boy_confirm_otp_generate_request_id;
    MaterialTextView lab_user_delivery_boy_confirm_otp_generate_lab_name, lab_user_delivery_boy_confirm_otp_generate_lab_phone;
    MaterialTextView lab_user_delivery_boy_confirm_otp_generate_lab_email, lab_user_delivery_boy_confirm_otp_generate_test_name;
    MaterialTextView lab_user_delivery_boy_confirm_otp_generate_test_price;

    JustifiedTextView lab_user_delivery_boy_confirm_otp_generate_lab_address;

    Button lab_user_delivery_boy_confirm_otp_generate_reached_otp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lab_delivery_boy_confirm_otp_generate_part_2, container, false);

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

        //MaterialTextView
        lab_user_delivery_boy_confirm_otp_generate_title = (MaterialTextView) view.findViewById(R.id.lab_user_delivery_boy_confirm_otp_generate_title);
        lab_user_delivery_boy_confirm_otp_generate_request_id = (MaterialTextView) view.findViewById(R.id.lab_user_delivery_boy_confirm_otp_generate_request_id);
        lab_user_delivery_boy_confirm_otp_generate_lab_name = (MaterialTextView) view.findViewById(R.id.lab_user_delivery_boy_confirm_otp_generate_lab_name);
        lab_user_delivery_boy_confirm_otp_generate_lab_phone = (MaterialTextView) view.findViewById(R.id.lab_user_delivery_boy_confirm_otp_generate_lab_phone);
        lab_user_delivery_boy_confirm_otp_generate_lab_email = (MaterialTextView) view.findViewById(R.id.lab_user_delivery_boy_confirm_otp_generate_lab_email);
        lab_user_delivery_boy_confirm_otp_generate_test_name = (MaterialTextView) view.findViewById(R.id.lab_user_delivery_boy_confirm_otp_generate_test_name);
        lab_user_delivery_boy_confirm_otp_generate_test_price = (MaterialTextView) view.findViewById(R.id.lab_user_delivery_boy_confirm_otp_generate_test_price);

        //JustifiedTextView
        lab_user_delivery_boy_confirm_otp_generate_lab_address = (JustifiedTextView) view.findViewById(R.id.lab_user_delivery_boy_confirm_otp_generate_lab_address);

        //Button
        lab_user_delivery_boy_confirm_otp_generate_reached_otp = (Button) view.findViewById(R.id.lab_user_delivery_boy_confirm_otp_generate_reached_otp);

        lab_user_delivery_boy_confirm_otp_generate_request_id.setText("Request I'd: - " + delivery_received_request_id);
        lab_user_delivery_boy_confirm_otp_generate_lab_name.setText("Lab Name: - " + delivery_received_lab_name);
        lab_user_delivery_boy_confirm_otp_generate_lab_address.setText("Lab Address: - " + delivery_received_lab_address);
        lab_user_delivery_boy_confirm_otp_generate_lab_phone.setText("Lab Phone: - " + delivery_received_lab_phone);
        lab_user_delivery_boy_confirm_otp_generate_lab_email.setText("Lab E-Mail I'D: - " + delivery_received_lab_email);
        lab_user_delivery_boy_confirm_otp_generate_test_name.setText("Test Name: - " + delivery_received_test_name);
        lab_user_delivery_boy_confirm_otp_generate_test_price.setText("Test Price: - " + delivery_received_test_price + ".00 ???");


        lab_user_delivery_boy_confirm_otp_generate_reached_otp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(Utility.isNetworkAvailable(getActivity()))
                {
                    getDeliveryBoyVerifyAtLabLocationPart2(delivery_received_request_id, delivery_received_applicant_name, delivery_received_lab_name, delivery_received_lab_email, delivery_received_test_name);
                }

                else
                {
                    Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lab_user_delivery_boy_confirm_otp_generate_lab_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String num = delivery_received_lab_phone;
                requestCallPermissionThenDialOrCallNumber(num);
            }
        });

        lab_user_delivery_boy_confirm_otp_generate_lab_email.setOnClickListener(new View.OnClickListener()
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

        return view;
    }

    private void getDeliveryBoyVerifyAtLabLocationPart2(String request_id, String applicant_name, String lab_name, String lab_email, String test_name)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("request_id", request_id);

        jsonParameters.put("customer_name", applicant_name);

        jsonParameters.put("lab_name", lab_name);
        jsonParameters.put("lab_email", lab_email);

        jsonParameters.put("test_name", test_name);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait OTP is being Generated to Verify You");
        progressDialog.setTitle("Generating OTP");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest delivery_Person_At_Lab_Part_2_Generate_OTP_To_Verify_Himself_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.DELIVERY_BOY_AT_LAB_OTP_GENERATION_TO_VERIFY_DELIVERY_BOY_PART_2, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (transaction_return_message.equals("Something went wrong in Delivery Boy At Lab Address Verify OTP Generate Part 2"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (transaction_return_message.equals("OTP to Verify Delivery Person at Lab Part 2 has not been generated"))
                {
                    Toast.makeText(getActivity(), "Some How OTP to Verify you has not been Generated. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (transaction_return_message.equals("OTP to Verify Delivery Person at Lab Part 2 has been generated Successfully and Delivery Boy Confirm OTP at Lab Part 2 Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "OTP to Authenticate you has been Generated.", Toast.LENGTH_LONG).show();

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

                    Lab_Delivery_Boy_Confirm_otp_Verify_Part_2 lab_delivery_boy_confirm_otp_verify_part_2 = new Lab_Delivery_Boy_Confirm_otp_Verify_Part_2();
                    lab_delivery_boy_confirm_otp_verify_part_2.setArguments(bundle1);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, lab_delivery_boy_confirm_otp_verify_part_2).addToBackStack(null).commit();
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
        delivery_Person_At_Lab_Part_2_Generate_OTP_To_Verify_Himself_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(delivery_Person_At_Lab_Part_2_Generate_OTP_To_Verify_Himself_POST_Request);
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