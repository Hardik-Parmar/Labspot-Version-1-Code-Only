package com.example.laspost10h.SupportClass;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLoginSessionManagement
{
    Context context;

    SharedPreferences sharedPreferences;

    public UserLoginSessionManagement(Context context)
    {
        this.context = context;
        sharedPreferences= context.getSharedPreferences("user_login_details", Context.MODE_PRIVATE);
    }

    private String user_email_login, user_password_login;

    public void setUser_email_login(String user_email_login)
    {
        this.user_email_login = user_email_login;

        sharedPreferences.edit().putString("user email login", user_email_login).commit();
    }

    public String getUser_email_login()
    {
        user_email_login = sharedPreferences.getString("user email login", "");

        return user_email_login;
    }

    public void setUser_password_login(String user_password_login)
    {
        this.user_password_login = user_password_login;

        sharedPreferences.edit().putString("user password login", user_password_login).commit();
    }

    public String getUser_password_login()
    {
        user_password_login = sharedPreferences.getString("user password login", "");

        return user_password_login;
    }

    public void removeUserDetailsFromSession()
    {
        sharedPreferences.edit().clear().commit();

    }
}