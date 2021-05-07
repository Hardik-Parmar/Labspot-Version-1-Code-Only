package Java_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.All_Contact_Us_Bean;
import database_connection.ConnectionProvider;

public class All_Contact_Us_DAO 
{
	public String all_Contact_Us(All_Contact_Us_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "INSERT INTO contact_us(type_of_user, name, email, "
					+ "feedback) VALUES(?,?,?,?)";
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getUser_type_name());
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getEmail());
			ps.setString(4, bean.getFeedback());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "Feedback Submitted Successfully";
			}
			else
			{
				return "Feedback is not Submitted";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Contact Us";
	}
}