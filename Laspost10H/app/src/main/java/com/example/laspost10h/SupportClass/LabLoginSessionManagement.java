package com.example.laspost10h.SupportClass;

import android.content.Context;
import android.content.SharedPreferences;

public class LabLoginSessionManagement
{
    Context context;

    SharedPreferences sharedPreferences;

    public LabLoginSessionManagement(Context context)
    {
        this.context = context;
        sharedPreferences= context.getSharedPreferences("lab_login_details", Context.MODE_PRIVATE);
    }

    private String lab_email_login, lab_password_login;

    public void setLab_email_login(String lab_email_login)
    {
        this.lab_email_login = lab_email_login;

        sharedPreferences.edit().putString("lab email login", lab_email_login).commit();
    }

    public String getLab_email_login()
    {
        lab_email_login = sharedPreferences.getString("lab email login", "");

        return lab_email_login;
    }

    public void setLab_password_login(String lab_password_login)
    {
        this.lab_password_login = lab_password_login;

        sharedPreferences.edit().putString("lab password login", lab_password_login).commit();
    }

    public String getLab_password_login()
    {
        lab_password_login = sharedPreferences.getString("lab password login", "");

        return lab_password_login;
    }

    public void removeLabDetailsFromSession()
    {
        sharedPreferences.edit().clear().commit();

    }
}