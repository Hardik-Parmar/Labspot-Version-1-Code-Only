package Java_DAO.test_detail_transaction_DAO.user_request_otp_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.test_detail_transaction_Beans.user_request_otp_Beans.User_Request_Delivery_Verify_otp_Generate_Bean;
import database_connection.ConnectionProvider;

public class User_Request_Delivery_Verify_otp_Generate_DAO
{
	public String user_Generate_otp_To_Verify_Delivery_Person(User_Request_Delivery_Verify_otp_Generate_Bean bean, String otp)
	{
		try
		{	
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "UPDATE test_transaction_details SET applicant_confirm_delivery_boy_otp_1 =  ?"
					+ " WHERE id = ? AND applicant_name = ? AND lab_name = ? "
					+ "AND test_name = ?";
			
			PreparedStatement ps = connection.prepareStatement(query);
				
			ps.setString(1, otp);
			
			ps.setString(2, bean.getRequest_id());
			
			ps.setString(3, bean.getCustomer_name());
			
			ps.setString(4, bean.getLab_name());
			
			ps.setString(5, bean.getTest_name());
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "OTP to Verify Delivery Person has been generated Successfully";
			}
			else
			{
				return "OTP to Verify Delivery Person has not been generated";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in User Request Delivery Verify otp Generate";
	}
}