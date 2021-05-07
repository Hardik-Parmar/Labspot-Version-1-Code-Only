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

public class LabRequestForReportCollectCustomAdapter extends BaseAdapter
{
    private static final int REQUEST_CODE = 777;

    public class ViewHolder
    {
        MaterialTextView applicant_id, applicant_name, applicant_phone, applicant_email, test_name, test_request_date, delivery_boy_status_title, delivery_boy_status;
    }

    public List<Lab_Request_For_Report_Collect_Contents> final_list;

    public Activity activity_context;

    ArrayList<Lab_Request_For_Report_Collect_Contents> arrayList;

    public LabRequestForReportCollectCustomAdapter(Activity activity_context, List<Lab_Request_For_Report_Collect_Contents> list_demo)
    {
        this.final_list = list_demo;
        this.activity_context = activity_context;
        arrayList = new ArrayList<Lab_Request_For_Report_Collect_Contents>();
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
        View testRequestStatusRowView = view;

        ViewHolder viewHolder = null;

        if(testRequestStatusRowView == null)
        {
            LayoutInflater layoutInflater = activity_context.getLayoutInflater();

            testRequestStatusRowView = layoutInflater.inflate(R.layout.lab_request_for_report_collect, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.applicant_id = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_view_pending_order_id);
            viewHolder.applicant_name = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_view_pending_order_applicant_name);
            viewHolder.applicant_phone = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_view_pending_order_applicant_phone);
            viewHolder.applicant_email = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_view_pending_order_applicant_mail);
            viewHolder.test_name = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_view_pending_order_test_name);
            viewHolder.test_request_date = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_view_pending_order_date_of_request);
            viewHolder.delivery_boy_status_title = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.user_accept_request_delivery_action_take_title);
            viewHolder.delivery_boy_status = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.user_accept_request_delivery_action_take);

            testRequestStatusRowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        // here setting data into list
        viewHolder.applicant_id.setText(final_list.get(position).getRequest_id());
        viewHolder.applicant_name.setText(final_list.get(position).getApplicant_name());
        viewHolder.applicant_phone.setText(final_list.get(position).getApplicant_phone());
        viewHolder.applicant_email.setText(final_list.get(position).getApplicant_email());
        viewHolder.test_name.setText(final_list.get(position).getTest_name());
        viewHolder.test_request_date.setText(final_list.get(position).getDate_of_request());

        if(final_list.get(position).getDelivery_boy_accept().equals("PENDING"))
        {
            viewHolder.delivery_boy_status.setText(final_list.get(position).getDelivery_boy_accept());
            int color = Integer.parseInt("FF0000", 16)+0xFF000000;
            viewHolder.delivery_boy_status.setTextColor(color);
            viewHolder.delivery_boy_status_title.setVisibility(View.VISIBLE);
            viewHolder.delivery_boy_status.setVisibility(View.VISIBLE);
        }

        else if(final_list.get(position).getDelivery_boy_accept().equals("ACCEPTED"))
        {
            int color = Integer.parseInt("00FF00", 16)+0xFF000000;
            viewHolder.delivery_boy_status.setTextColor(color);
            viewHolder.delivery_boy_status.setText(final_list.get(position).getDelivery_boy_accept());
            viewHolder.delivery_boy_status_title.setVisibility(View.VISIBLE);
            viewHolder.delivery_boy_status.setVisibility(View.VISIBLE);
        }

        else if(final_list.get(position).getDelivery_boy_accept().equals("REJECTED"))
        {
            viewHolder.delivery_boy_status.setText(final_list.get(position).getDelivery_boy_accept());
            int color = Integer.parseInt("FF0000", 16)+0xFF000000;
            viewHolder.delivery_boy_status.setTextColor(color);
            viewHolder.delivery_boy_status_title.setVisibility(View.VISIBLE);
            viewHolder.delivery_boy_status.setVisibility(View.VISIBLE);
        }

        else
        {
            viewHolder.delivery_boy_status.setVisibility(View.GONE);
            viewHolder.delivery_boy_status_title.setVisibility(View.GONE);
        }

        viewHolder.applicant_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String num = final_list.get(position).getApplicant_phone();

                requestCallPermissionThenDialOrCallNumber(num);
            }
        });

        viewHolder.applicant_email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{final_list.get(position).getApplicant_email()});
                activity_context.startActivity(Intent.createChooser(intent, "Send Mail via"));
            }
        });

        return testRequestStatusRowView;
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