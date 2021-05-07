package com.example.laspost10h.lab.after_login.labsupportclass;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.laspost10h.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class LabNewTestRequestCustomAdapter extends BaseAdapter
{
    private static final int REQUEST_CODE = 777;

    public class ViewHolder
    {
        MaterialTextView id, user_name, user_phone, user_email, test_name, date_of_request;
    }

    public List<Lab_New_Test_Request_Contents> final_list;

    public Activity activity_context;

    ArrayList<Lab_New_Test_Request_Contents> arrayList;

    public LabNewTestRequestCustomAdapter(Activity activity_context, List<Lab_New_Test_Request_Contents> list_demo)
    {
        this.final_list = list_demo;
        this.activity_context = activity_context;
        arrayList = new ArrayList<Lab_New_Test_Request_Contents>();
        arrayList.addAll(final_list);
    }


    @Override
    public int getCount()
    {
        return final_list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent)
    {
        View newTestRequestRowView = view;

        ViewHolder viewHolder = null;

        if(newTestRequestRowView == null)
        {
            LayoutInflater layoutInflater = activity_context.getLayoutInflater();

            newTestRequestRowView = layoutInflater.inflate(R.layout.lab_new_test_requests_display_row_view, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.id = (MaterialTextView) newTestRequestRowView.findViewById(R.id.lab_view_new_test_request_test_id);
            viewHolder.user_name = (MaterialTextView) newTestRequestRowView.findViewById(R.id.lab_view_new_test_request_user_name);
            viewHolder.user_phone = (MaterialTextView) newTestRequestRowView.findViewById(R.id.lab_view_new_test_request_user_phone);
            viewHolder.user_email = (MaterialTextView) newTestRequestRowView.findViewById(R.id.lab_view_new_test_request_user_mail);
            viewHolder.test_name  = (MaterialTextView) newTestRequestRowView.findViewById(R.id.lab_view_new_test_request_test_name);
            viewHolder.date_of_request = (MaterialTextView) newTestRequestRowView.findViewById(R.id.lab_view_new_test_request_date_of_request);

            newTestRequestRowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        // here setting data into list
        viewHolder.id.setText(final_list.get(position).getApplicant_id());
        viewHolder.user_name.setText(final_list.get(position).getUser_name());
        viewHolder.user_phone.setText(final_list.get(position).getUser_phone());
        viewHolder.user_email.setText(final_list.get(position).getUser_email_temp());
        viewHolder.test_name.setText(final_list.get(position).getTest_name());
        viewHolder.date_of_request.setText(final_list.get(position).getDate_of_request());

        viewHolder.user_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String num = final_list.get(position).getUser_phone();

                requestCallPermissionThenDialOrCallNumber(num);
            }
        });

        viewHolder.user_email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{final_list.get(position).getUser_email_temp()});
                activity_context.startActivity(Intent.createChooser(intent, "Send Mail via"));
            }
        });

        return newTestRequestRowView;
    }

    private void requestCallPermissionThenDialOrCallNumber(String num)
    {
        String dial_num = "tel:" + num;

        if(ActivityCompat.shouldShowRequestPermissionRationale(activity_context, Manifest.permission.CALL_PHONE))
        {
            // User denied Permission

            new AlertDialog.Builder(activity_context)
                    .setTitle("Permission Needed")
                    .setMessage("Call Permission needed for Dialling or Calling the Number Displayed on the Screen.")
                    .setPositiveButton("Grant Permission", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // Ask For Permission
                            ActivityCompat.requestPermissions(activity_context, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CODE);

                            // If Permission is Granted then SelectImage Logic
                            if(ContextCompat.checkSelfPermission(activity_context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                            {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(dial_num));
                                activity_context.startActivity(intent);
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
            ActivityCompat.requestPermissions(activity_context, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            // If Permission is Granted then SelectImage Logic
            if(ContextCompat.checkSelfPermission(activity_context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(dial_num));
                activity_context.startActivity(intent);
            }
        }
    }
}