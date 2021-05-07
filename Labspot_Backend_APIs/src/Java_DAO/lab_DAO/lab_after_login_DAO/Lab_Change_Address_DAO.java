package Java_DAO.lab_DAO.lab_after_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.lab_Beans.lab_after_login_Beans.Lab_Change_Address_Bean;
import database_connection.ConnectionProvider;

public class Lab_Change_Address_DAO 
{
	public String lab_Change_Address(Lab_Change_Address_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "UPDATE lab_register SET lab_address = ? where lab_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getAddress());
			ps.setString(2, bean.getEmail());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "Lab Address has been Changed";
			}
			else
			{
				return "Lab Address has not been Changed";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Lab Change Address";
	}
}