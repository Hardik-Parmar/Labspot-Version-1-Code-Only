package com.example.laspost10h.lab.after_login;

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
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.example.laspost10h.R;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Lab_Take_Action_On_Test_Request extends Fragment
{
    View view;

    private static final int REQUEST_CODE = 777;

    MaterialTextView take_action_test_id, take_action_user_name, take_action_user_mail;
    MaterialTextView take_action_user_phone, take_action_test_name, take_action_date_of_request;

    Button take_action_reject_button, take_action_accept_button;

    String lab_received_request_id, lab_received_user_name, lab_received_user_mail, lab_received_user_phone, lab_received_test_name, lab_received_date_of_request;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lab_take_action_on_test_request, container, false);

        Bundle bundle = getArguments();
        lab_received_request_id = bundle.getString("request_id");
        lab_received_user_name = bundle.getString("user_name");
        lab_received_user_phone = bundle.getString("user_phone");
        lab_received_user_mail = bundle.getString("user_mail");
        lab_received_test_name = bundle.getString("test_name");
        lab_received_date_of_request = bundle.getString("date_of_request");

        // TextView
        take_action_test_id = (MaterialTextView) view.findViewById(R.id.take_action_test_id);
        take_action_user_name = (MaterialTextView) view.findViewById(R.id.take_action_user_name);
        take_action_user_mail = (MaterialTextView) view.findViewById(R.id.take_action_user_mail);
        take_action_user_phone = (MaterialTextView) view.findViewById(R.id.take_action_user_phone);
        take_action_test_name = (MaterialTextView) view.findViewById(R.id.take_action_test_name);
        take_action_date_of_request = (MaterialTextView) view.findViewById(R.id.take_action_date_of_request);

        //Button
        take_action_reject_button = (Button) view.findViewById(R.id.take_action_reject_button);
        take_action_accept_button = (Button) view.findViewById(R.id.take_action_accept_button);

        //Setting the Data to all Widgets
        take_action_test_id.setText(lab_received_request_id);
        take_action_user_name.setText(lab_received_user_name);
        take_action_user_phone.setText(lab_received_user_phone);
        take_action_user_mail.setText(lab_received_user_mail);
        take_action_test_name.setText(lab_received_test_name);
        take_action_date_of_request.setText(lab_received_date_of_request);

        take_action_reject_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd / MM / yyyy HH : mm : ss", Locale.getDefault());
                String currentDateAndTime = simpleDateFormat.format(new Date());

                getLabRejectTestRequest(lab_received_request_id, lab_received_user_name, lab_received_user_phone, lab_received_user_mail, lab_received_test_name, lab_received_date_of_request, currentDateAndTime);
            }
        });

        take_action_accept_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd / MM / yyyy HH : mm : ss", Locale.getDefault());
                String currentDateAndTime = simpleDateFormat.format(new Date());

                getLabAcceptTestRequest(lab_received_request_id, lab_received_user_name, lab_received_user_phone, lab_received_user_mail, lab_received_test_name, lab_received_date_of_request, currentDateAndTime);
            }
        });

        take_action_user_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                requestCallPermissionThenDialOrCallNumber(lab_received_user_phone);
            }
        });

        take_action_user_mail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{lab_received_user_mail});
                startActivity(Intent.createChooser(intent, "Send Mail via"));
            }
        });

        return view;
    }

    private void getLabRejectTestRequest(String id, String user_name, String user_phone, String user_mail, String test_name, String date_of_request, String currentDate)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("applicant_id", id);
        jsonParameters.put("user_name", user_name);
        jsonParameters.put("user_phone", user_phone);
        jsonParameters.put("user_email", user_mail);
        jsonParameters.put("test_name", test_name);
        jsonParameters.put("date_of_test_order", date_of_request);
        jsonParameters.put("date_of_action", currentDate);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Rejecting Test Request");
        progressDialog.setTitle("Rejecting Test Request");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest lab_Reject_Test_Request_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_REJECT_TEST_REQUEST, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (transaction_return_message.equals("Something went wrong in Lab Reject Test Request"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (transaction_return_message.equals("Test Request is not Rejected"))
                {
                    Toast.makeText(getActivity(), "Some How Test Request is Not Rejected. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (transaction_return_message.equals("Test Request Rejected and Lab Reject Test Request Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Test Request has been Rejected Successfully.", Toast.LENGTH_LONG).show();
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
        lab_Reject_Test_Request_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(lab_Reject_Test_Request_POST_Request);
    }

    private void  getLabAcceptTestRequest(String id, String user_name, String user_phone, String user_mail, String test_name, String date_of_request, String currentDate)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("applicant_id", id);
        jsonParameters.put("user_name", user_name);
        jsonParameters.put("user_phone", user_phone);
        jsonParameters.put("user_email", user_mail);
        jsonParameters.put("test_name", test_name);
        jsonParameters.put("date_of_test_order", date_of_request);
        jsonParameters.put("date_of_action", currentDate);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait Accepting Test Request");
        progressDialog.setTitle("Accepting Test Request");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest lab_Accept_Test_Request_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.LAB_ACCEPT_TEST_REQUEST, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
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
                if (transaction_return_message.equals("Something went wrong in Lab Accept Test Request"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (transaction_return_message.equals("Test Request is not Accepted"))
                {
                    Toast.makeText(getActivity(), "Some How Test Request is Not Accepted. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (transaction_return_message.equals("Test Request Accepted and Lab Accept Test Request Email Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Test Request has been Accepted Successfully.", Toast.LENGTH_LONG).show();
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
        lab_Accept_Test_Request_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(lab_Accept_Test_Request_POST_Request);
    }

    private void requestCallPermissionThenDialOrCallNumber(String number)
    {
        String dial_num = "tel:" + number;

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