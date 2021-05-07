package Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans;

public class Delivery_Registration_Bean 
{
	private String name, address, city, phone, DOB, email;
	private String password, image_name, image_url;

	
	// constructor to initialize all variables
	public Delivery_Registration_Bean(String name, String address, String city, String phone, 
			String dob, String email, String password, String image_name, 
			String image_url) 
	{
		this.setName(name);
		this.setAddress(address);
		this.setCity(city);
		this.setPhone(phone);
		this.setDOB(dob);
		this.setEmail(email);
		this.setPassword(password);
		this.setImage_name(image_name);
		this.setImage_url(image_url);
	}

	
	// SETTER and GETTER method of all variables
	
	public void setName(String name) 
	{
		this.name = name;
	}


	public String getName() 
	{
		return name;
	}
	
	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getAddress() 
	{
		return address;
	}
	
	public void setCity(String city) 
	{
		this.city = city;
	}

	public String getCity() 
	{
		return city;
	}

	public void setPhone(String phone) 
	{
		this.phone = phone;
	}

	public String getPhone() 
	{
		return phone;
	}

	public void setDOB(String dOB) 
	{
		DOB = dOB;
	}
	
	public String getDOB() 
	{
		return DOB;
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

	public void setImage_name(String image_name) 
	{
		this.image_name = image_name;
	}

	public String getImage_name() 
	{
		return image_name;
	}

	public void setImage_url(String image_url) 
	{
		this.image_url = image_url;
	}

	public String getImage_url() 
	{
		return image_url;
	}
}