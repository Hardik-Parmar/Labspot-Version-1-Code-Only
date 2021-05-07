package Java_DAO.test_detail_transaction_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.test_detail_transaction_Beans.Delivery_Lab_Request_Accept_Bean;
import database_connection.ConnectionProvider;

public class Delivery_Lab_Request_Accept_DAO 
{
	public String delivery_Lab_Accept_Delivery_Request(Delivery_Lab_Request_Accept_Bean bean)
	{
		try
		{	
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "UPDATE test_transaction_details SET delivery_boy_accept_2 =  ?"
					+ " WHERE id = ? AND applicant_name = ? AND applicant_email = ? "
					+ "AND lab_name = ? AND lab_email = ? AND 	test_name = ?";
			
			PreparedStatement ps = connection.prepareStatement(query);
				
			ps.setString(1, "ACCEPTED");
			
			ps.setString(2, bean.getRequest_id());
			
			ps.setString(3, bean.getApplicant_name());
			ps.setString(4, bean.getApplicant_email());
			
			ps.setString(5, bean.getLab_name());
			ps.setString(6, bean.getLab_email());
			
			ps.setString(7, bean.getTest_name());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "Delivery Request from Lab is Accepted Successfully";
			}
			else
			{
				return "Delivery Request from Lab is not Accepted";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Delivery Lab Request Accept";
	}
}