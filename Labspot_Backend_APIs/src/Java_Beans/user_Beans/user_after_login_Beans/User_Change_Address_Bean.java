package Java_Beans.user_Beans.user_after_login_Beans;

public class User_Change_Address_Bean 
{
	private String email, address, name;
	
	public User_Change_Address_Bean(String email, String address, String name)
	{
		this.setEmail(email);
		this.setAddress(address);
		this.setName(name);
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getAddress() 
	{
		return address;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getName() 
	{
		return name;
	}
}