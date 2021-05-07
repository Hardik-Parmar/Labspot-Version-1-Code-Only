package com.example.laspost10h.SupportClass;

import android.content.Context;
import android.content.SharedPreferences;

public class DeliveryLoginSessionManagement
{
    Context context;

    SharedPreferences sharedPreferences;

    public DeliveryLoginSessionManagement(Context context)
    {
        this.context = context;
        sharedPreferences= context.getSharedPreferences("delivery_login_details", Context.MODE_PRIVATE);
    }

    private String delivery_email_login, delivery_password_login;

    public void setDelivery_email_login(String delivery_email_login)
    {
        this.delivery_email_login = delivery_email_login;

        sharedPreferences.edit().putString("delivery email login", delivery_email_login).commit();
    }

    public String getDelivery_email_login()
    {
        delivery_email_login = sharedPreferences.getString("delivery email login", "");

        return delivery_email_login;
    }

    public void setDelivery_password_login(String delivery_password_login)
    {
        this.delivery_password_login = delivery_password_login;

        sharedPreferences.edit().putString("delivery password login", delivery_password_login).commit();
    }

    public String getDelivery_password_login()
    {
        delivery_password_login = sharedPreferences.getString("delivery password login", "");

        return delivery_password_login;
    }

    public void removeDeliveryDetailsFromSession()
    {
        sharedPreferences.edit().clear().commit();

    }
}