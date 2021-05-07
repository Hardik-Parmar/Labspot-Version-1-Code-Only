package Java_DAO.test_detail_transaction_DAO.lab_request_otp_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.test_detail_transaction_Beans.lab_request_otp_Beans.User_Delivery_Confirm_50_Percent_Payment_OTP_Generate_Part_2_Bean;
import database_connection.ConnectionProvider;

public class User_Delivery_Confirm_50_Percent_Payment_OTP_Generate_Part_2_DAO
{
	public String user_Delivery_Confirm_50_Percent_Payment_OTP_Generate_Part_2(User_Delivery_Confirm_50_Percent_Payment_OTP_Generate_Part_2_Bean bean, String otp)
	{
		try
		{	
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "UPDATE test_transaction_details SET applicant_second_50_percent_cod_payment_otp_2 =  ?"
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
				return "OTP for Confirm 50 Percent Payment Part 2 has been generated Successfully";
			}
			else
			{
				return "OTP for Confirm 50 Percent Payment Part 2 has not been generated";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in User Delivery Confirm 50 Percent Payment OTP Generate Part 2";
	}
}