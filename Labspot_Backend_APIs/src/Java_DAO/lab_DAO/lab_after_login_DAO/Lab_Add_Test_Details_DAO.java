package Java_DAO.lab_DAO.lab_after_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.lab_Beans.lab_after_login_Beans.Lab_Add_Test_Details_Bean;
import database_connection.ConnectionProvider;

public class Lab_Add_Test_Details_DAO 
{
	public String lab_Add_Test_Details(Lab_Add_Test_Details_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "INSERT INTO test_details(lab_name, lab_email, lab_test_name, "
					+ "lab_test_description, lab_test_price) VALUES(?,?,?,?,?)";
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getLab_name());
			ps.setString(2, bean.getLab_email());
			ps.setString(3, bean.getLab_test_name());
			ps.setString(4, bean.getLab_test_description());
			ps.setString(5, bean.getLab_test_price());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "Lab Test Details are Added Successfully";
			}
			else
			{
				return "Lab Test Details are not Added";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Lab Add Test Details";
	}
}