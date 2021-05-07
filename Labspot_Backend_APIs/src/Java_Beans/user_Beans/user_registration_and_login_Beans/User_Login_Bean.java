package Java_Beans.user_Beans.user_registration_and_login_Beans;

public class User_Login_Bean 
{
private String email, password;
	
	public User_Login_Bean(String email, String password)
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