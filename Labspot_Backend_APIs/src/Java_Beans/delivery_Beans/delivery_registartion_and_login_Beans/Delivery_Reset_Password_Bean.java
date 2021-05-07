package Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans;

public class Delivery_Reset_Password_Bean 
{
	private String email ,new_password;

	public Delivery_Reset_Password_Bean(String email, String new_password)
	{
		this.setEmail(email);
		this.setNew_password(new_password);
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getEmail() 
	{
		return email;
	}

	public void setNew_password(String new_password) 
	{
		this.new_password = new_password;
	}

	public String getNew_password() 
	{
		return new_password;
	}
}