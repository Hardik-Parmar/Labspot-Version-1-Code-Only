package com.example.labspot_backend_api.user_api.user_after_login;

public class User_Support_Fetch_Data 
{
	private String user_name, user_address, user_city, user_phone, user_DOB, user_email;
	private String user_password, user_image_name, user_image_url;

	
	// constructor to initialize all variables
	public User_Support_Fetch_Data(String user_name, String user_address, 
			String user_city, String user_phone, String user_dob, String user_email, 
			String user_password, String user_image_name, String user_image_url) 
	{
		this.setUser_name(user_name);
		this.setUser_address(user_address);
		this.setUser_city(user_city);
		this.setUser_phone(user_phone);
		this.setUser_DOB(user_dob);
		this.setUser_email(user_email);
		this.setUser_password(user_password);
		this.setUser_image_name(user_image_name);
		this.setUser_image_url(user_image_url);
	}

	
	// SETTER and GETTER method of all variables
	
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	
	public String getUser_name() 
	{
		return user_name;
	}
	
	public void setUser_address(String user_address) 
	{
		this.user_address = user_address;
	}
	
	public String getUser_address() 
	{
		return user_address;
	}
	
	public void setUser_city(String user_city) 
	{
		this.user_city = user_city;
	}
	
	public String getUser_city() 
	{
		return user_city;
	}
	
	public void setUser_phone(String user_phone) 
	{
		this.user_phone = user_phone;
	}
	
	public String getUser_phone() 
	{
		return user_phone;
	}
	
	public void setUser_DOB(String user_DOB) 
	{
		this.user_DOB = user_DOB;
	}
	
	public String getUser_DOB() 
	{
		return user_DOB;
	}
	
	public void setUser_email(String user_email) 
	{
		this.user_email = user_email;
	}
	
	public String getUser_email() 
	{
		return user_email;
	}
	
	public void setUser_password(String user_password) 
	{
		this.user_password = user_password;
	}
	
	public String getUser_password() {
		return user_password;
	}
	
	public void setUser_image_name(String user_image_name) 
	{
		this.user_image_name = user_image_name;
	}
	
	public String getUser_image_name() 
	{
		return user_image_name;
	}
	
	public void setUser_image_url(String user_image_url) 
	{
		this.user_image_url = user_image_url;
	}
	
	public String getUser_image_url() 
	{
		return user_image_url;
	}
}