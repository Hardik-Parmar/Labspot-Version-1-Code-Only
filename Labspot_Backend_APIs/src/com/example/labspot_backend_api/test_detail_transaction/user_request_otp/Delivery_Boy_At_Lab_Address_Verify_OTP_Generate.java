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

import Java_Beans.test_detail_transaction_Beans.user_request_otp_Beans.Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Bean;
import Java_DAO.test_detail_transaction_DAO.user_request_otp_DAO.Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_DAO;
import send_mail.Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Mail;

@Path("test_detail_transaction")
public class Delivery_Boy_At_Lab_Address_Verify_OTP_Generate
{
	Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Bean delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Bean;
	Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_DAO delivery_Boy_At_Lab_Address_Verify_OTP_Generate_DAO;
	
	public String request_id_temp, customer_name_temp, lab_name_temp, lab_email_temp, test_name_temp, otp_temp;
	
	public String result, response;
	
	@Path("lab_delivery_request_person_verify_otp_generate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deliveryBoyAtLabAddressverifyOTPGenerate(String json)
	{
		System.out.println("\nGENERATE OTP FOR DELIVERY PERSON  TO VERIFY AT LAB REQUEST FROM USER API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			request_id_temp = jsonObject.get("request_id").getAsString();
			customer_name_temp = jsonObject.get("customer_name").getAsString();
			lab_name_temp = jsonObject.get("lab_name").getAsString();
			lab_email_temp = jsonObject.get("lab_email").getAsString();
			test_name_temp = jsonObject.get("test_name").getAsString();
			
			delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Bean = new Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Bean(
					request_id_temp, customer_name_temp, lab_name_temp, lab_email_temp, test_name_temp);
		}
		
		Random random = new Random();
		int number = random. nextInt(999999);
		otp_temp = String.format("%06d", number);
		
		delivery_Boy_At_Lab_Address_Verify_OTP_Generate_DAO = new Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_DAO();
		
		result = delivery_Boy_At_Lab_Address_Verify_OTP_Generate_DAO.delivery_Boy_At_Lab_Address_Verify_OTP_Generate(delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Bean, otp_temp);
		
		System.out.println("\nReturn Message from delivery_Boy_At_Lab_Address_Verify_OTP_Generate Method : - "+result);
		
		if(result.equals("OTP to Verify Delivery Person at Lab has been generated Successfully"))
		{	
			response = Delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Mail.deliveryBoyAtLabAddressVerifyOTPGenerateEmail(delivery_Boy_At_Lab_Address_Verify_OTP_Generate_Bean, otp_temp);
			
			System.out.println("\n\nReturn Message from deliveryBoyAtLabAddressVerifyOTPGenerateEmail Method : - "+response);
			
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}