package Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans;

public class Delivery_Login_Bean 
{
	private String email, password;
	
	public Delivery_Login_Bean(String email, String password)
	{
		this.setEmail(email);
		this.setPassword(password);
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getEmail() 
	{
		return email;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public String getPassword() 
	{
		return password;
	}
}