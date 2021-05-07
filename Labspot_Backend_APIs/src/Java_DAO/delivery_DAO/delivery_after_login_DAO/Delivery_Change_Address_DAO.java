package Java_DAO.delivery_DAO.delivery_after_login_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.delivery_Beans.delivery_after_login_Beans.Delivery_Change_Address_Bean;
import database_connection.ConnectionProvider;

public class Delivery_Change_Address_DAO 
{
	public String delivery_Change_Address(Delivery_Change_Address_Bean bean)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "UPDATE delivery_register SET delivery_address = ? where delivery_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, bean.getAddress());
			ps.setString(2, bean.getEmail());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "Delivery Address has been Changed";
			}
			else
			{
				return "Delivery Address has not been Changed";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Delivery Change Address";
	}
}