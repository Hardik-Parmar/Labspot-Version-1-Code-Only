package Java_DAO.lab_DAO.lab_after_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.lab_Beans.lab_after_login_Beans.Lab_Edit_Test_Details_Bean;
import database_connection.ConnectionProvider;

public class Lab_Edit_Test_Details_DAO 
{
	public String lab_Edit_Test_Details(Lab_Edit_Test_Details_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "UPDATE test_details set lab_test_name = ?, lab_test_description = ?, lab_test_price = ? WHERE id = ?";
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getLab_test_name());
			ps.setString(2, bean.getLab_test_description());
			ps.setString(3, bean.getLab_test_price());
			ps.setString(4, bean.getId());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "Lab Test Details are Updated Successfully";
			}
			else
			{
				return "Lab Test Details are not Updated";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Lab Edit Test Details";
	}
}