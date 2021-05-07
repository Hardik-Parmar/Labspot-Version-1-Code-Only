package com.example.laspost10h;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laspost10h.delivery.registration_and_login_system.Delivery_Login_Page;
import com.example.laspost10h.lab.registration_and_login_system.Lab_Login_Page;
import com.example.laspost10h.user.registration_and_login_system.User_Login_Page;

public class App_Main_Page extends AppCompatActivity
{
    Button user_button, lab_button, delivery_button;

    private static final int REQUEST_CODE = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main_page);

        // Checking that some permission is allowed by user or not

        // if condition for checking read and call permission is granted or not
        if((ContextCompat.checkSelfPermission(App_Main_Page.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(App_Main_Page.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(App_Main_Page.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CALL_PHONE, Manifest.permission.INTERNET}, REQUEST_CODE);
        }

        user_button = findViewById(R.id.user_button);
        lab_button = findViewById(R.id.lab_button);
        delivery_button = findViewById(R.id.delivery_button);

        user_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(App_Main_Page.this, User_Login_Page.class);
                startActivity(intent);
                finish();
            }
        });

        lab_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(App_Main_Page.this, Lab_Login_Page.class);
                startActivity(intent);
                finish();
            }
        });

        delivery_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(App_Main_Page.this, Delivery_Login_Page.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}