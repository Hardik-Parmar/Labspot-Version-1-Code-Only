package Java_DAO.test_detail_transaction_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.test_detail_transaction_Beans.Lab_Complete_Test_Process_Bean;
import database_connection.ConnectionProvider;

public class Lab_Complete_Test_Process_DAO 
{
	public String complete_Test_Request(Lab_Complete_Test_Process_Bean bean)
	{
		try
		{	
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "UPDATE test_transaction_details SET test_order_complete_by_lab = ?"
					+ "WHERE id = ? AND applicant_name = ? AND applicant_email = ? "
					+ "AND test_name = ? AND lab_name = ? AND lab_email = ?";
			
			PreparedStatement ps = connection.prepareStatement(query);
				
			ps.setString(1, "COMPLETED");
			
			ps.setString(2, bean.getId());
			ps.setString(3, bean.getApplicant_name());
			ps.setString(4, bean.getApplicant_email());
			
			ps.setString(5, bean.getTest_name());
			
			ps.setString(6, bean.getLab_name());
			ps.setString(7, bean.getLab_email());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "Test Request Completed";
			}
			else
			{
				return "Test Request is not Completed";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Lab Complete Test Process";
	}
}