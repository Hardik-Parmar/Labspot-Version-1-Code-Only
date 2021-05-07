package com.example.labspot_backend_api.test_detail_transaction.user_request_otp;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.test_detail_transaction_Beans.user_request_otp_Beans.User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Bean;
import Java_DAO.test_detail_transaction_DAO.user_request_otp_DAO.User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_DAO;
import send_mail.User_Confirm_50_Percent_Payment_otp_Verify_Mail;

@Path("test_detail_transaction")
public class User_Delivery_Confirm_50_Percent_Payment_OTP_Verify
{
	User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Bean user_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Bean;
	User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_DAO user_Delivery_Confirm_50_Percent_Payment_OTP_Verify_DAO;
	
	public String request_id_temp, customer_name_temp, customer_email_temp, lab_name_temp, test_name_temp, otp_temp;
	
	public String result, response;
	
	@Path("user_confirm_50_percent_payment_otp_verify")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userConfirm50PercentPaymentOTPVerify(String json)
	{
		System.out.println("\nCONFIRM 50 PERCENT PAYMENT OTP VERIFY REQUEST FROM USER API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			request_id_temp = jsonObject.get("request_id").getAsString();
			customer_name_temp = jsonObject.get("customer_name").getAsString();
			customer_email_temp = jsonObject.get("customer_email").getAsString();
			lab_name_temp = jsonObject.get("lab_name").getAsString();
			test_name_temp = jsonObject.get("test_name").getAsString();
			otp_temp = jsonObject.get("otp_from_delivery").getAsString();
			
			user_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Bean = new User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Bean(
					request_id_temp, customer_name_temp, customer_email_temp, lab_name_temp, test_name_temp, otp_temp);
		}
		
		user_Delivery_Confirm_50_Percent_Payment_OTP_Verify_DAO = new User_Delivery_Confirm_50_Percent_Payment_OTP_Verify_DAO();
		
		result = user_Delivery_Confirm_50_Percent_Payment_OTP_Verify_DAO.user_Confirm_50_Percent_OTP_Verify(user_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Bean);
		
		System.out.println("\nReturn Message from user_Confirm_50_Percent_OTP_Verify Method : - "+result);
		
		if(result.equals("50 Percent OTP Verified"))
		{	
			response = User_Confirm_50_Percent_Payment_otp_Verify_Mail.userConfirm50PercentPaymentOTPVerifyEmail(user_Delivery_Confirm_50_Percent_Payment_OTP_Verify_Bean);
			
			System.out.println("\n\nReturn Message from userConfirm50PercentPaymentOTPVerifyEmail Method : - "+response);
			
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}