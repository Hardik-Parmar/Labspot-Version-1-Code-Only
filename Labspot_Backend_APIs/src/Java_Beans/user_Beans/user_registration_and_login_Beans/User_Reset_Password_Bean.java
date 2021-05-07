package Java_Beans.user_Beans.user_registration_and_login_Beans;

public class User_Reset_Password_Bean 
{
	private String email ,new_password;

	public User_Reset_Password_Bean(String email, String new_password)
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