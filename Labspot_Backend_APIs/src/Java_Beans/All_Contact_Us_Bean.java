package Java_Beans;

public class All_Contact_Us_Bean 
{
	private String name, email, feedback, user_type_name;

	public All_Contact_Us_Bean(String name, String email, String feedback, String user_type_name)
	{
		this.setName(name);
		this.setEmail(email);
		this.setFeedback(feedback);
		this.setUser_type_name(user_type_name);
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getName() 
	{
		return name;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setFeedback(String feedback) 
	{
		this.feedback = feedback;
	}

	public String getFeedback() 
	{
		return feedback;
	}

	public void setUser_type_name(String user_type_name) 
	{
		this.user_type_name = user_type_name;
	}

	public String getUser_type_name() 
	{
		return user_type_name;
	}
}