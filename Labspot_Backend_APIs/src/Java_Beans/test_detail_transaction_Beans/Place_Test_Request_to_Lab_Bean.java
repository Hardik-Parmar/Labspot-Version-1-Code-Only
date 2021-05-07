package Java_Beans.test_detail_transaction_Beans;

public class Place_Test_Request_to_Lab_Bean 
{
	private String user_name, user_address, test_transaction_city, user_phone, user_email;
	private String lab_name, lab_address, lab_phone, lab_email;
	private String test_name, test_description, test_price;
	private String date;
	
	public Place_Test_Request_to_Lab_Bean(String user_name, String user_address, String test_transaction_city, String user_phone, String user_email, String lab_name, String lab_address, String lab_phone, String lab_email, String test_name, String test_description, String test_price, String date)
	{
		this.setUser_name(user_name);
		this.setUser_address(user_address);
		this.setTest_transaction_city(test_transaction_city);
		this.setUser_phone(user_phone);
		this.setUser_email(user_email);
		this.setLab_name(lab_name);
		this.setLab_address(lab_address);
		this.setLab_phone(lab_phone);
		this.setLab_email(lab_email);
		this.setTest_name(test_name);
		this.setTest_description(test_description);
		this.setTest_price(test_price);
		this.setDate(date);
	}
	
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
	
	public void setTest_transaction_city(String test_transaction_city) 
	{
		this.test_transaction_city = test_transaction_city;
	}
	
	public String getTest_transaction_city() 
	{
		return test_transaction_city;
	}
	
	public void setUser_phone(String user_phone) 
	{
		this.user_phone = user_phone;
	}
	
	public String getUser_phone() 
	{
		return user_phone;
	}
	
	public void setUser_email(String user_email) 
	{
		this.user_email = user_email;
	}
	
	public String getUser_email() 
	{
		return user_email;
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

	public void setLab_phone(String lab_phone) 
	{
		this.lab_phone = lab_phone;
	}
	
	public String getLab_phone() 
	{
		return lab_phone;
	}
	
	public void setLab_email(String lab_email) 
	{
		this.lab_email = lab_email;
	}
	
	public String getLab_email() 
	{
		return lab_email;
	}
	
	public void setTest_name(String test_name) 
	{
		this.test_name = test_name;
	}
	
	public String getTest_name() 
	{
		return test_name;
	}
	
	public void setTest_description(String test_description) 
	{
		this.test_description = test_description;
	}
	
	public String getTest_description() 
	{
		return test_description;
	}
	
	public void setTest_price(String test_price) 
	{
		this.test_price = test_price;
	}
	
	public String getTest_price() 
	{
		return test_price;
	}

	public void setDate(String date) 
	{
		this.date = date;
	}
	
	public String getDate() 
	{
		return date;
	}
}