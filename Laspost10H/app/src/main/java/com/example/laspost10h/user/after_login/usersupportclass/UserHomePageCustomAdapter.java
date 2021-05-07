package com.example.laspost10h.user.after_login.usersupportclass;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.laspost10h.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserHomePageCustomAdapter extends BaseAdapter
{
    private static final int REQUEST_CODE = 777;

    public class ViewHolder
    {
        TextView lab_name, lab_category, lab_phone;
        ImageView lab_logo;
    }

    public List<User_Lab_Display_Content> final_list;

    public Activity activity_context;

    ArrayList<User_Lab_Display_Content> arrayList;

    public UserHomePageCustomAdapter(Activity context, List<User_Lab_Display_Content> list_demo)
    {
        this.final_list = list_demo;
        this.activity_context = context;
        arrayList = new ArrayList<User_Lab_Display_Content>();
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
        View labRowView = view;

        ViewHolder viewHolder = null;

        if(labRowView == null)
        {
            LayoutInflater layoutInflater = activity_context.getLayoutInflater();

            labRowView = layoutInflater.inflate(R.layout.user_home_page_lab_row_display, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.lab_name = (TextView) labRowView.findViewById(R.id.lab_row_display_name);
            viewHolder.lab_category  = (TextView) labRowView.findViewById(R.id.lab_row_display_category);
            viewHolder.lab_phone = (TextView) labRowView.findViewById(R.id.lab_row_display_phone);
            viewHolder.lab_logo = (ImageView) labRowView.findViewById(R.id.lab_row_display_logo);

            labRowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        // here setting data into list
        viewHolder.lab_name.setText(final_list.get(position).getLab_name());
        viewHolder.lab_category.setText("Category: - "+final_list.get(position).getLab_category());
        viewHolder.lab_phone.setText("Number: - "+final_list.get(position).getLab_phone());
        Picasso.with(activity_context).load(final_list.get(position).getLab_logo_url()).into(viewHolder.lab_logo);

        viewHolder.lab_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String num = final_list.get(position).getLab_phone();

                requestCallPermissionThenDialOrCallNumber(num);
            }
        });

        return labRowView;
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