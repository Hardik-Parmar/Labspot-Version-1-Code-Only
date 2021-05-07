package com.example.labspot_backend_api.test_detail_transaction.user_request_otp;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.test_detail_transaction_Beans.user_request_otp_Beans.User_Request_Delivery_Verify_otp_Generate_Bean;
import Java_DAO.test_detail_transaction_DAO.user_request_otp_DAO.User_Request_Delivery_Verify_otp_Generate_DAO;
import send_mail.User_Verify_Delivery_Person_OTP_Generate_Mail;

@Path("test_detail_transaction")
public class User_Request_Delivery_Verify_otp_Generate
{
	User_Request_Delivery_Verify_otp_Generate_Bean user_Request_Delivery_Verify_otp_Generate_Bean;
	User_Request_Delivery_Verify_otp_Generate_DAO user_Request_Delivery_Verify_otp_Generate_DAO;
	
	public String request_id_temp, customer_name_temp, customer_email_temp, lab_name_temp, test_name_temp, otp_temp;
	
	public String result, response;
	
	@Path("user_delivery_request_person_verify_otp_generate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userGenerateotpToVerifyDeliveryPerson(String json)
	{
		System.out.println("\nGENERATE OTP FOR DELIVERY PERSON REQUEST FROM USER API");
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
			
			user_Request_Delivery_Verify_otp_Generate_Bean = new User_Request_Delivery_Verify_otp_Generate_Bean(
					request_id_temp, customer_name_temp, customer_email_temp, lab_name_temp, test_name_temp);
		}
		
		Random random = new Random();
		int number = random. nextInt(999999);
		otp_temp = String.format("%06d", number);
		
		user_Request_Delivery_Verify_otp_Generate_DAO = new User_Request_Delivery_Verify_otp_Generate_DAO();
		
		result = user_Request_Delivery_Verify_otp_Generate_DAO.user_Generate_otp_To_Verify_Delivery_Person(user_Request_Delivery_Verify_otp_Generate_Bean, otp_temp);
		
		System.out.println("\nReturn Message from user_Generate_otp_To_Verify_Delivery_Person Method : - "+result);
		
		if(result.equals("OTP to Verify Delivery Person has been generated Successfully"))
		{	
			response = User_Verify_Delivery_Person_OTP_Generate_Mail.userVerifyDeliveryPersonOTPGenerateEmail(user_Request_Delivery_Verify_otp_Generate_Bean, otp_temp);
			
			System.out.println("\n\nReturn Message from userVerifyDeliveryPersonOTPGenerateEmail Method : - "+response);
			
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}