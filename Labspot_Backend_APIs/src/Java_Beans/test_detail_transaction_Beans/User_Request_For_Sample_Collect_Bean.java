package Java_Beans.test_detail_transaction_Beans;

public class User_Request_For_Sample_Collect_Bean 
{
	private String request_id, user_name, user_email, user_city, lab_name, lab_email;

	public User_Request_For_Sample_Collect_Bean(String request_id, String user_name, String user_email, String user_city, String lab_name, String lab_email)
	{
		this.setRequest_id(request_id);
		this.setUser_name(user_name);
		this.setUser_email(user_email);
		this.setUser_city(user_city);
		this.setLab_name(lab_name);
		this.setLab_email(lab_email);
	}
	
	public void setRequest_id(String request_id) 
	{
		this.request_id = request_id;
	}
	
	public String getRequest_id() 
	{
		return request_id;
	}

	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	
	public String getUser_name() 
	{
		return user_name;
	}

	public void setUser_email(String user_email) 
	{
		this.user_email = user_email;
	}

	public String getUser_email() 
	{
		return user_email;
	}

	public void setUser_city(String user_city) 
	{
		this.user_city = user_city;
	}

	public String getUser_city() 
	{
		return user_city;
	}

	public void setLab_name(String lab_name) 
	{
		this.lab_name = lab_name;
	}

	public String getLab_name() 
	{
		return lab_name;
	}

	public void setLab_email(String lab_email) 
	{
		this.lab_email = lab_email;
	}

	public String getLab_email() 
	{
		return lab_email;
	} 
}