package Java_Beans.lab_Beans.lab_after_login_Beans;

public class Lab_Change_Password_Bean 
{
	private String email, old_password, new_password, name;
	
	public Lab_Change_Password_Bean(String email, String old_password, String new_password, String name)
	{
		this.setEmail(email);
		this.setOld_password(old_password);
		this.setNew_password(new_password);
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

	public void setOld_password(String old_password) 
	{
		this.old_password = old_password;
	}
	
	public String getOld_password() 
	{
		return old_password;
	}

	public void setNew_password(String new_password) 
	{
		this.new_password = new_password;
	}

	public String getNew_password() 
	{
		return new_password;
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