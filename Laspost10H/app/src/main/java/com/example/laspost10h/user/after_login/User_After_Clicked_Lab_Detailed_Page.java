package com.example.laspost10h.user.after_login;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.codesgood.views.JustifiedTextView;
import com.example.laspost10h.Confidential_Details.DatabaseConnectionDetails;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.user.after_login.usersupportclass.UserAfterClickedLabTestDetailCustomAdapter;
import com.example.laspost10h.user.after_login.usersupportclass.User_Lab_Test_Display_Content;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class User_After_Clicked_Lab_Detailed_Page extends Fragment
{
    View view;

    private ArrayList<User_Lab_Test_Display_Content> lab_test_display_contents;

    private static final int REQUEST_CODE = 777;

    MaterialTextView user_lab_click_name_title, user_lab_click_name, user_lab_click_address_title;
    MaterialTextView user_lab_click_category_title, user_lab_click_category, user_lab_click_phone_title;
    MaterialTextView user_lab_click_phone, user_lab_click_mail_title, user_lab_click_mail;

    JustifiedTextView user_lab_click_address;

    ListView user_lab_click_test_detail_list_view;

    ImageView user_lab_click_logo;

    String user_lab_received_logo_url, user_lab_received_name, user_lab_received_address, user_lab_received_category;
    String user_lab_received_phone, user_lab_received_mail;

    String user_received_email, user_received_name, user_received_city, user_received_address, user_received_phone;

    String lab_name_from_api, lab_mail_from_api, test_name_from_api, test_description_from_api, test_price_from_api;

    UserAfterClickedLabTestDetailCustomAdapter userAfterClickedLabTestDetailCustomAdapter;

    SwipeRefreshLayout user_after_click_refresh;

    private boolean success = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_after_clicked_lab_detailed_page, container, false);

        Bundle  bundle = getArguments();

        user_lab_received_logo_url = bundle.getString("user_lab_logo_url");
        user_lab_received_name = bundle.getString("user_lab_name");
        user_lab_received_address = bundle.getString("user_lab_address");
        user_lab_received_category = bundle.getString("user_lab_category");
        user_lab_received_phone = bundle.getString("user_lab_phone");
        user_lab_received_mail = bundle.getString("user_lab_mail");

        user_received_email = bundle.getString("user_sent_mail");
        user_received_name = bundle.getString("user_sent_name");
        user_received_city = bundle.getString("user_sent_city");
        user_received_address = bundle.getString("user_sent_address");
        user_received_phone = bundle.getString("user_sent_phone");

        // Material TextView
        user_lab_click_name_title = (MaterialTextView) view.findViewById(R.id.user_lab_click_name_title);
        user_lab_click_name = (MaterialTextView) view.findViewById(R.id.user_lab_click_name);
        user_lab_click_address_title = (MaterialTextView) view.findViewById(R.id.user_lab_click_address_title);
        user_lab_click_category_title = (MaterialTextView) view.findViewById(R.id.user_lab_click_category_title);
        user_lab_click_category = (MaterialTextView) view.findViewById(R.id.user_lab_click_category);
        user_lab_click_phone_title = (MaterialTextView) view.findViewById(R.id.user_lab_click_phone_title);
        user_lab_click_phone = (MaterialTextView) view.findViewById(R.id.user_lab_click_phone);
        user_lab_click_mail_title = (MaterialTextView) view.findViewById(R.id.user_lab_click_mail_title);
        user_lab_click_mail = (MaterialTextView) view.findViewById(R.id.user_lab_click_mail);

        // Justified Text View
        user_lab_click_address = (JustifiedTextView) view.findViewById(R.id.user_lab_click_address);

        // ListView
        user_lab_click_test_detail_list_view = (ListView) view.findViewById(R.id.user_lab_click_test_detail_list_view);

        // ImageView
        user_lab_click_logo  = (ImageView) view.findViewById(R.id.user_lab_click_logo);

        lab_test_display_contents = new ArrayList<User_Lab_Test_Display_Content>();

        // Swipe Refresh
        user_after_click_refresh = (SwipeRefreshLayout) view.findViewById(R.id.user_after_click_refresh);

        FragmentManager fragmentManager = getFragmentManager();

        user_after_click_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("user_lab_logo_url", user_lab_received_logo_url);
                bundle1.putString("user_lab_name", user_lab_received_name);
                bundle1.putString("user_lab_address", user_lab_received_address);
                bundle1.putString("user_lab_category", user_lab_received_category);
                bundle1.putString("user_lab_phone", user_lab_received_phone);
                bundle1.putString("user_lab_mail", user_lab_received_mail);

                bundle1.putString("user_sent_mail", user_received_email);
                bundle1.putString("user_sent_name", user_received_name);
                bundle1.putString("user_sent_address", user_received_address);
                bundle1.putString("user_sent_phone", user_received_phone);
                bundle1.putString("user_sent_city", user_received_city);

                User_After_Clicked_Lab_Detailed_Page user_after_clicked_lab_detailed_page = new User_After_Clicked_Lab_Detailed_Page();
                user_after_clicked_lab_detailed_page.setArguments(bundle1);

                fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_after_clicked_lab_detailed_page).detach(user_after_clicked_lab_detailed_page).attach(user_after_clicked_lab_detailed_page).commit();
            }
        });

        if(Utility.isNetworkAvailable(getActivity()))
        {
            // Setting the Data into appropriate Fields.
            Picasso.with(getActivity()).load(user_lab_received_logo_url).into(user_lab_click_logo);
            user_lab_click_name.setText(user_lab_received_name);
            user_lab_click_address.setText(user_lab_received_address);
            user_lab_click_category.setText(user_lab_received_category);
            user_lab_click_phone.setText(user_lab_received_phone);
            user_lab_click_mail.setText(user_lab_received_mail);


            SyncData orderData = new SyncData();
            orderData.execute("");
        }
        else
        {
            Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
        }

        user_lab_click_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(getActivity(), "MSG   " + user_received_city, Toast.LENGTH_SHORT).show();
                requestCallPermissionThenDialOrCallNumber();
            }
        });

        user_lab_click_mail.setOnClickListener(new View.OnClickListener()
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

        user_lab_click_test_detail_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Bundle bundle1 = new Bundle();

                bundle1.putString("user_lab_test_name", lab_test_display_contents.get(position).getLab_test_name());
                bundle1.putString("user_lab_test_description", lab_test_display_contents.get(position).getLab_test_description());
                bundle1.putString("user_lab_test_price", lab_test_display_contents.get(position).getLab_test_price());

                bundle1.putString("user_lab_name", lab_test_display_contents.get(position).getLab_name());
                bundle1.putString("user_lab_address", user_lab_received_address);
                bundle1.putString("user_lab_phone", user_lab_received_phone);
                bundle1.putString("user_lab_mail", lab_test_display_contents.get(position).getLab_email());

                bundle1.putString("user_sent_name", user_received_name);
                bundle1.putString("user_sent_mail", user_received_email);
                bundle1.putString("user_sent_address", user_received_address);
                bundle1.putString("user_sent_phone", user_received_phone);
                bundle1.putString("user_sent_city", user_received_city);

                User_Confirm_Details_For_Test_Order user_confirm_details_for_test_order = new User_Confirm_Details_For_Test_Order();
                user_confirm_details_for_test_order.setArguments(bundle1);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.user_fragment_container, user_confirm_details_for_test_order).addToBackStack(null).commit();
            }
        });
        return view;
    }

    private class SyncData extends AsyncTask<String, String, String>
    {
        String msg;

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() //Starts the progress dialog
        {
            progressDialog.setMessage("Please Wait Fetching Test Details from the Server");
            progressDialog.setTitle("Fetching Test Details");
            progressDialog.show();
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
            progressDialog.setIcon(R.mipmap.logo1);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try
            {
                Class.forName(DatabaseConnectionDetails.JDBC_DRIVER).newInstance();

                Connection connection = DriverManager.getConnection(DatabaseConnectionDetails.DATABASE_URL, DatabaseConnectionDetails.DATABASE_USERNAME, DatabaseConnectionDetails.DATABASE_PASSWORD); //Connection Object

                if (connection == null)
                {
                    success = false;
                }
                else
                {
                    // Change below query according to your own database.
                    String query = "SELECT * FROM test_details where lab_name = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    //Log.d("HARDIKKKK", "AFTER PREPARED");

                    preparedStatement.setString(1, user_lab_received_name);
                    //Log.d("HARDIKKKK", user_lab_received_name);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet != null) // if resultset not null, add items to itemArrayList using class created
                    {
                        while (resultSet.next())
                        {
                            try
                            {
                                lab_name_from_api = resultSet.getString("lab_name");
                                lab_mail_from_api = resultSet.getString("lab_email");
                                test_name_from_api = resultSet.getString("lab_test_name");
                                test_description_from_api = resultSet.getString("lab_test_description");
                                test_price_from_api = resultSet.getString("lab_test_price");
                                //Log.d("HARDIKKKK", test_name_from_api);

                                lab_test_display_contents.add(new User_Lab_Test_Display_Content(lab_name_from_api, lab_mail_from_api, test_name_from_api, test_description_from_api, test_price_from_api));

                                msg = "Found";

                                success = true;
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        msg = "No Data found!";
                        success = false;
                    }
                }
                connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // dismissing the progress dialog, showing error and setting up my ListView
        {
            progressDialog.dismiss();
            System.out.println(msg);
            //Toast.makeText(getActivity(), "msg  " + msg, Toast.LENGTH_SHORT).show();
            if (success == false)
            {
                // Nothing
                user_lab_click_test_detail_list_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is No Test Details are Added by Laboratory yet. Please Try Again After Sometime.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    userAfterClickedLabTestDetailCustomAdapter = new UserAfterClickedLabTestDetailCustomAdapter(getActivity(), lab_test_display_contents);
                    user_lab_click_test_detail_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    user_lab_click_test_detail_list_view.setAdapter(userAfterClickedLabTestDetailCustomAdapter);
                    user_lab_click_test_detail_list_view.setVisibility(View.VISIBLE);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
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