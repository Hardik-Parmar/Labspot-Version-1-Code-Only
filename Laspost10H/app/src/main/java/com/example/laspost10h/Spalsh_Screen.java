package com.example.laspost10h;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laspost10h.SupportClass.DeliveryLoginSessionManagement;
import com.example.laspost10h.SupportClass.LabLoginSessionManagement;
import com.example.laspost10h.SupportClass.UserLoginSessionManagement;
import com.example.laspost10h.delivery.after_login.Welcome_Delivery_Page;
import com.example.laspost10h.lab.after_login.Welcome_Lab_Page;
import com.example.laspost10h.user.after_login.Welcome_User_Page;

import java.util.Timer;
import java.util.TimerTask;

public class Spalsh_Screen extends AppCompatActivity
{
    Animation top, bottom;
    ImageView imageView;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        // Animation Part
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView = findViewById(R.id.imageView);
        text = findViewById(R.id.text);

        top = AnimationUtils.loadAnimation(this,R.anim.top);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom);

        imageView.setAnimation(top);
        text.setAnimation(bottom);

        // Timer Part which define which activity will open after spalsh screen

        Timer timer = new Timer();
        timer.schedule( new TimerTask()
        {
            @Override
            public void run()
            {
                UserLoginSessionManagement userLoginSessionManagement = new UserLoginSessionManagement(Spalsh_Screen.this);

                LabLoginSessionManagement labLoginSessionManagement = new LabLoginSessionManagement(Spalsh_Screen.this);

                DeliveryLoginSessionManagement deliveryLoginSessionManagement = new DeliveryLoginSessionManagement(Spalsh_Screen.this);

                if((userLoginSessionManagement.getUser_email_login() != "") && (userLoginSessionManagement.getUser_password_login() != ""))
                {
                    Intent intent = new Intent(Spalsh_Screen.this, Welcome_User_Page.class);
                    intent.putExtra("user_sent_mail", userLoginSessionManagement.getUser_email_login());
                    startActivity(intent);
                    finish();
                }

                else if((labLoginSessionManagement.getLab_email_login() != "") && (labLoginSessionManagement.getLab_password_login() != ""))
                {
                    Intent intent = new Intent(Spalsh_Screen.this, Welcome_Lab_Page.class);
                    intent.putExtra("lab_sent_mail", labLoginSessionManagement.getLab_email_login());
                    startActivity(intent);
                    finish();
                }

                else if((deliveryLoginSessionManagement.getDelivery_email_login() != "") && (deliveryLoginSessionManagement.getDelivery_password_login() != ""))
                {
                    Intent intent = new Intent(Spalsh_Screen.this, Welcome_Delivery_Page.class);
                    intent.putExtra("delivery_sent_mail", deliveryLoginSessionManagement.getDelivery_email_login());
                    startActivity(intent);
                    finish();
                }

                else
                {
                    Intent intent = new Intent(Spalsh_Screen.this, App_Main_Page.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2500);
    }
}