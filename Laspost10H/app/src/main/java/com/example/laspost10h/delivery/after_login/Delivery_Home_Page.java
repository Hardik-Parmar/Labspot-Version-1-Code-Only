package com.example.laspost10h.delivery.after_login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.laspost10h.R;

public class Delivery_Home_Page extends Fragment
{
    View view;

    String delivery_received_name, delivery_received_phone, delivery_received_email, delivery_received_address;

    Button delivery_view_user_request, delivery_view_lab_request;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery_home_page, container, false);

        Bundle bundle = getArguments();
        delivery_received_name = bundle.getString("delivery_sent_name");
        delivery_received_phone = bundle.getString("delivery_sent_phone");
        delivery_received_email = bundle.getString("delivery_sent_mail");
        delivery_received_address = bundle.getString("delivery_sent_address");

        delivery_view_user_request = (Button) view.findViewById(R.id.delivery_view_user_request);
        delivery_view_lab_request = (Button) view.findViewById(R.id.delivery_view_lab_request);

        delivery_view_user_request.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("delivery_sent_name", delivery_received_name);
                bundle1.putString("delivery_sent_phone", delivery_received_phone);
                bundle1.putString("delivery_sent_mail", delivery_received_email);
                bundle1.putString("delivery_sent_address", delivery_received_address);

                Delivery_View_User_Request delivery_view_user_request = new Delivery_View_User_Request();
                delivery_view_user_request.setArguments(bundle1);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_view_user_request).addToBackStack(null).commit();
            }
        });

        delivery_view_lab_request.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle bundle2 = new Bundle();
                bundle2.putString("delivery_sent_name", delivery_received_name);
                bundle2.putString("delivery_sent_phone", delivery_received_phone);
                bundle2.putString("delivery_sent_mail", delivery_received_email);
                bundle2.putString("delivery_sent_address", delivery_received_address);

                Delivery_View_Lab_Request delivery_view_lab_request = new Delivery_View_Lab_Request();
                delivery_view_lab_request.setArguments(bundle2);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_view_lab_request).addToBackStack(null).commit();
            }
        });

        return view;
    }

}