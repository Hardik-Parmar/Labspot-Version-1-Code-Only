package com.example.labspot_backend_api.delivery_api.delivery_after_login;

public class Delivery_Support_Fetch_Data 
{
	private String delivery_name, delivery_address, delivery_city, delivery_phone, delivery_dob, delivery_email, delivery_password, delivery_image_name, delivery_image_url, delivery_available, delivery_busy;

	public Delivery_Support_Fetch_Data(String delivery_name, String delivery_address, String delivery_city, String delivery_phone, String delivery_dob, String delivery_email, String delivery_password, String delivery_image_name, String delivery_image_url, String delivery_available, String delivery_busy)
	{
		this.setDelivery_name(delivery_name);
		this.setDelivery_address(delivery_address);
		this.setDelivery_city(delivery_city);
		this.setDelivery_phone(delivery_phone);
		this.setDelivery_dob(delivery_dob);
		this.setDelivery_email(delivery_email);
		this.setDelivery_password(delivery_password);
		this.setDelivery_image_name(delivery_image_name);
		this.setDelivery_image_url(delivery_image_url);
		this.setDelivery_available(delivery_available);
		this.setDelivery_busy(delivery_busy);
	}
	
	public void setDelivery_name(String delivery_name) 
	{
		this.delivery_name = delivery_name;
	}
	
	public String getDelivery_name() 
	{
		return delivery_name;
	}

	public void setDelivery_address(String delivery_address) 
	{
		this.delivery_address = delivery_address;
	}

	public String getDelivery_address() 
	{
		return delivery_address;
	}

	public void setDelivery_city(String delivery_city) 
	{
		this.delivery_city = delivery_city;
	}

	public String getDelivery_city() 
	{
		return delivery_city;
	}

	public void setDelivery_phone(String delivery_phone) 
	{
		this.delivery_phone = delivery_phone;
	}

	public String getDelivery_phone() 
	{
		return delivery_phone;
	}

	public void setDelivery_dob(String delivery_dob) 
	{
		this.delivery_dob = delivery_dob;
	}

	public String getDelivery_dob() 
	{
		return delivery_dob;
	}

	public void setDelivery_email(String delivery_email) 
	{
		this.delivery_email = delivery_email;
	}

	public String getDelivery_email() 
	{
		return delivery_email;
	}

	public void setDelivery_password(String delivery_password) 
	{
		this.delivery_password = delivery_password;
	}

	public String getDelivery_password() 
	{
		return delivery_password;
	}

	public void setDelivery_image_name(String delivery_image_name) 
	{
		this.delivery_image_name = delivery_image_name;
	}

	public String getDelivery_image_name() 
	{
		return delivery_image_name;
	}

	public void setDelivery_image_url(String delivery_image_url) 
	{
		this.delivery_image_url = delivery_image_url;
	}

	public String getDelivery_image_url() 
	{
		return delivery_image_url;
	}

	public void setDelivery_available(String delivery_available) 
	{
		this.delivery_available = delivery_available;
	}

	public String getDelivery_available() 
	{
		return delivery_available;
	}

	public void setDelivery_busy(String delivery_busy) 
	{
		this.delivery_busy = delivery_busy;
	}

	public String getDelivery_busy() 
	
	{
		return delivery_busy;
	}
}