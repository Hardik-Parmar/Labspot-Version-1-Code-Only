package Java_Beans.lab_Beans.lab_after_login_Beans;

public class Lab_Edit_Test_Details_Bean 
{
	private String id, lab_test_name, lab_test_description, lab_test_price;

	public Lab_Edit_Test_Details_Bean(String id, String lab_test_name, String lab_test_description, String lab_test_price)
	{
		this.setId(id);
		this.setLab_test_name(lab_test_name);
		this.setLab_test_description(lab_test_description);
		this.setLab_test_price(lab_test_price);
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getId() 
	{
		return id;
	}
	
	public void setLab_test_name(String lab_test_name) 
	{
		this.lab_test_name = lab_test_name;
	}
	
	public String getLab_test_name() 
	{
		return lab_test_name;
	}

	public void setLab_test_description(String lab_test_description) 
	{
		this.lab_test_description = lab_test_description;
	}
	
	public String getLab_test_description() 
	{
		return lab_test_description;
	}

	public void setLab_test_price(String lab_test_price) 
	{
		this.lab_test_price = lab_test_price;
	}

	public String getLab_test_price() 
	{
		return lab_test_price;
	}
}