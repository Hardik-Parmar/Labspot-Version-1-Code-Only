package com.example.laspost10h.user.after_login.usersupportclass;

public class User_Lab_Display_Content
{
    private String lab_name, lab_address, lab_city, lab_phone, lab_category, lab_established_year, lab_email, lab_logo_name, lab_logo_url;

    public User_Lab_Display_Content(String lab_name, String lab_address, String lab_city, String lab_phone,
                                  String lab_category, String lab_established_year, String lab_email,
                                    String lab_logo_name, String lab_logo_url)
    {

        this.setLab_name(lab_name);
        this.setLab_address(lab_address);
        this.setLab_city(lab_city);
        this.setLab_phone(lab_phone);
        this.setLab_category(lab_category);
        this.setLab_established_year(lab_established_year);
        this.setLab_email(lab_email);
        this.setLab_logo_name(lab_logo_name);
        this.setLab_logo_url(lab_logo_url);
    }

    public void setLab_name(String lab_name)
    {
        this.lab_name = lab_name;
    }

    public String getLab_name()
    {
        return lab_name;
    }


    public void setLab_address(String lab_address)
    {
        this.lab_address = lab_address;
    }

    public String getLab_address()
    {
        return lab_address;
    }

    public void setLab_city(String lab_city)
    {
        this.lab_city = lab_city;
    }

    public String getLab_city()
    {
        return lab_city;
    }

    public void setLab_phone(String lab_phone)
    {
        this.lab_phone = lab_phone;
    }

    public String getLab_phone()
    {
        return lab_phone;
    }

    public void setLab_category(String lab_category)
    {
        this.lab_category = lab_category;
    }

    public String getLab_category()
    {
        return lab_category;
    }

    public void setLab_established_year(String lab_established_year)
    {
        this.lab_established_year = lab_established_year;
    }

    public String getLab_established_year()
    {
        return lab_established_year;
    }

    public void setLab_email(String lab_email)
    {
        this.lab_email = lab_email;
    }

    public String getLab_email()
    {
        return lab_email;
    }

    public void setLab_logo_name(String lab_logo_name)
    {
        this.lab_logo_name = lab_logo_name;
    }

    public String getLab_logo_name()
    {
        return lab_logo_name;
    }

    public void setLab_logo_url(String lab_logo_url)
    {
        this.lab_logo_url = lab_logo_url;
    }

    public String getLab_logo_url()
    {
        return lab_logo_url;
    }
}