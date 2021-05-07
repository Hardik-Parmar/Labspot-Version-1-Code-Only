package Java_DAO.test_detail_transaction_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.test_detail_transaction_Beans.Lab_Take_Action_On_Test_Request_Bean;
import database_connection.ConnectionProvider;

public class Lab_Reject_Test_Request_DAO 
{
	public String reject_Test_Request(Lab_Take_Action_On_Test_Request_Bean bean)
	{
		try
		{	
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "UPDATE test_transaction_details SET lab_accept_order = ?"
					+ ", date_time_of_order_accepted = ? WHERE id = ? AND "
					+ "applicant_name = ? AND applicant_phone = ? AND applicant_email = ? "
					+ "AND test_name = ? AND date_time_of_test_order = ?";
			
			PreparedStatement ps = connection.prepareStatement(query);
				
			ps.setString(1, "REJECTED");
			ps.setString(2, bean.getDate_of_action());
			
			ps.setString(3, bean.getId());
			ps.setString(4, bean.getUser_name());
			ps.setString(5, bean.getUser_phone());
			ps.setString(6, bean.getUser_mail());
			ps.setString(7, bean.getTest_name());
			ps.setString(8, bean.getDate());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "Test Request Rejected";
			}
			else
			{
				return "Test Request is not Rejected";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Lab Reject Test Request";
	}
}