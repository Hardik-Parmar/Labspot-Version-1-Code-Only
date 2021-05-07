package Java_DAO.test_detail_transaction_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Java_Beans.test_detail_transaction_Beans.Place_Test_Request_to_Lab_Bean;
import database_connection.ConnectionProvider;

public class Place_Test_Request_to_Lab_DAO 
{
	public String place_Test_Request_To_Lab(Place_Test_Request_to_Lab_Bean bean)
	{
		try
		{	
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "INSERT INTO test_transaction_details(applicant_name, "
					+ "applicant_address, test_transaction_city, applicant_phone, "
					+ "applicant_email, "
					+ "lab_name, lab_address, lab_phone, lab_email, "
					+ "test_name, test_description, test_price, "
					+ "first_50_percent_price, second_50_percent_price, "
					+ "date_time_of_test_order, lab_accept_order, "
					+ "date_time_of_order_accepted, "
					+ "delivery_boy_name_1, delivery_boy_phone_1, delivery_boy_email_1,"
					+ "delivery_boy_address_1, delivery_boy_accept_1, "
					+ "applicant_confirm_delivery_boy_otp_1, "
					+ "applicant_confirm_delivery_boy_otp_status_1, "
					+ "date_time_of_delivery_boy_arrive_1, "
					+ "applicant_first_50_percent_cod_payment_otp_1, "
					+ "applicant_first_50_percent_cod_payment_otp_status_1, "
					+ "lab_confirm_delivery_boy_otp_1, "
					+ "lab_confirm_delivery_boy_otp_status_1, "
					+ "date_time_of_delivery_boy_submit_sample_1, "
					+ "test_order_complete_by_lab, delivery_boy_name_2, "
					+ "delivery_boy_phone_2, delivery_boy_email_2, "
					+ "delivery_boy_address_2, delivery_boy_accept_2, "
					+ "lab_confirm_delivery_boy_otp_2, "
					+ "lab_confirm_delivery_boy_otp_status_2, "
					+ "date_time_of_delivery_boy_collect_report_2, "
					+ "applicant_confirm_delivery_boy_otp_2, "
					+ "applicant_confirm_delivery_boy_otp_status_2, "
					+ "date_time_of_delivery_boy_arrive_2, "
					+ "applicant_second_50_percent_cod_payment_otp_2, "
					+ "applicant_second_50_percent_cod_payment_otp_status_2, "
					+ "whole_cycle_completed) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?)";
			
			PreparedStatement ps = connection.prepareStatement(query);
				
			ps.setString(1, bean.getUser_name());
			ps.setString(2, bean.getUser_address());
			ps.setString(3, bean.getTest_transaction_city());
			ps.setString(4, bean.getUser_phone());
			ps.setString(5, bean.getUser_email());
			
			ps.setString(6, bean.getLab_name());
			ps.setString(7, bean.getLab_address());
			ps.setString(8, bean.getLab_phone());
			ps.setString(9, bean.getLab_email());
			
			ps.setString(10, bean.getTest_name());
			ps.setString(11, bean.getTest_description());
			ps.setString(12, bean.getTest_price());
			
			
			String price = bean.getTest_price();
			int demo = Integer.parseInt(price);
			
			int first_50 = demo / 2;
			
			String price_50 = String.valueOf(first_50);
			
			ps.setString(13, price_50);
			ps.setString(14, price_50);
			
			ps.setString(15, bean.getDate());
			
			ps.setString(16, "PENDING");
			ps.setString(17, "null");
			
			ps.setString(18, "null");
			ps.setString(19, "null");
			ps.setString(20, "null");
			ps.setString(21, "null");
			ps.setString(22, "null");
			
			ps.setString(23, "null");
			ps.setString(24, "null");
			ps.setString(25, "null");
			
			ps.setString(26, "null");
			ps.setString(27, "null");
			
			ps.setString(28, "null");
			ps.setString(29, "null");
			ps.setString(30, "null");
			
			ps.setString(31, "null");
			
			ps.setString(32, "null");
			ps.setString(33, "null");
			ps.setString(34, "null");
			ps.setString(35, "null");
			ps.setString(36, "null");
			
			ps.setString(37, "null");
			ps.setString(38, "null");
			ps.setString(39, "null");
			
			ps.setString(40, "null");
			ps.setString(41, "null");
			ps.setString(42, "null");
			
			ps.setString(43, "null");
			ps.setString(44, "null");
			
			ps.setString(45, "null");
			
			int temp = ps.executeUpdate();
			
			if(temp == 1)
			{
				return "Test Request Submitted Successfully";
			}
			else
			{
				return "Test Request is not Submitted";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Something went wrong in Place Test Request to Lab";
	}
}