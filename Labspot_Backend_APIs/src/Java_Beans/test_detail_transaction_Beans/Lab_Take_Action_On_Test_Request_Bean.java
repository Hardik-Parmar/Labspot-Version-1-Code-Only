package Java_Beans.test_detail_transaction_Beans;

public class Lab_Take_Action_On_Test_Request_Bean 
{
	private String id, user_name, user_phone, user_mail, test_name, date, date_of_action;

	public Lab_Take_Action_On_Test_Request_Bean(String id, String user_name, String user_phone, String user_mail, String test_name, String date, String date_of_action)
	{
		this.setId(id);
		this.setUser_name(user_name);
		this.setUser_phone(user_phone);
		this.setUser_mail(user_mail);
		this.setTest_name(test_name);
		this.setDate(date);
		this.setDate_of_action(date_of_action);
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	
	public String getUser_name() 
	{
		return user_name;
	}
	
	public void setUser_phone(String user_phone) 
	{
		this.user_phone = user_phone;
	}
	
	public String getUser_phone() 
	{
		return user_phone;
	}
	
	public void setUser_mail(String user_mail) 
	{
		this.user_mail = user_mail;
	}
	
	public String getUser_mail() 
	{
		return user_mail;
	}
	
	public void setTest_name(String test_name) 
	{
		this.test_name = test_name;
	}
	
	public String getTest_name() 
	{
		return test_name;
	}
	
	public void setDate(String date) 
	{
		this.date = date;
	}
	
	public String getDate() 
	{
		return date;
	}
	
	public void setDate_of_action(String date_of_action) 
	{
		this.date_of_action = date_of_action;
	}
	
	public String getDate_of_action() 
	{
		return date_of_action;
	}
}