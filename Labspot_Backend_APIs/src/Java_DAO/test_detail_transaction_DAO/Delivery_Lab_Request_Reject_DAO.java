package Java_DAO.test_detail_transaction_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.test_detail_transaction_Beans.Delivery_Lab_Request_Reject_Bean;
import database_connection.ConnectionProvider;

public class Delivery_Lab_Request_Reject_DAO 
{
	public String delivery_Lab_Reject_Delivery_Request(Delivery_Lab_Request_Reject_Bean bean)
	{
		try
		{	
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "UPDATE test_transaction_details SET delivery_boy_accept_2 =  ?"
					+ ", delivery_boy_name_2 = ?, delivery_boy_phone_2 = ?, "
					+ "delivery_boy_email_2 = ?, delivery_boy_address_2 = ? WHERE "
					+ "id = ? AND applicant_name = ? AND applicant_email = ? "
					+ "AND lab_name = ? AND lab_email = ? AND 	test_name = ?";
			
			PreparedStatement ps = connection.prepareStatement(query);
				
			ps.setString(1, "REJECTED");
			
			ps.setString(2, "null");
			ps.setString(3, "null");
			ps.setString(4, "null");
			ps.setString(5, "null");
			
			ps.setString(6, bean.getRequest_id());
			
			ps.setString(7, bean.getApplicant_name());
			ps.setString(8, bean.getApplicant_email());
			
			ps.setString(9, bean.getLab_name());
			ps.setString(10, bean.getLab_email());
			
			ps.setString(11, bean.getTest_name());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "Delivery Request from Lab is Rejected Successfully";
			}
			else
			{
				return "Delivery Request from Lab is not Rejected";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Delivery Lab Request Reject";
	}
}