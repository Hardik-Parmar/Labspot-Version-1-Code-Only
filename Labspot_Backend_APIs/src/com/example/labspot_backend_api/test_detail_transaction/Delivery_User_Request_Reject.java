package com.example.labspot_backend_api.test_detail_transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.test_detail_transaction_Beans.Delivery_User_Request_Reject_Bean;
import Java_DAO.test_detail_transaction_DAO.Delivery_User_Request_Reject_DAO;
import send_mail.Delivery_User_Request_Reject_Generate_Mail;

@Path("test_detail_transaction")
public class Delivery_User_Request_Reject
{
	Delivery_User_Request_Reject_Bean delivery_User_Request_Reject_Bean;
	Delivery_User_Request_Reject_DAO delivery_User_Request_Reject_DAO;
	
	public String id_temp, user_name_temp, user_email_temp, lab_name_temp, lab_email_temp;
	public String test_name_temp;
	
	public String result, response;
	
	@Path("user_reject_delivery_request")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userRejectDeliveryRequest(String json)
	{
		System.out.println("\nREJECT DELIVERY REQUEST FROM USER API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			id_temp = jsonObject.get("request_id").getAsString();
			user_name_temp = jsonObject.get("user_name").getAsString();
			user_email_temp = jsonObject.get("user_email").getAsString();
			lab_name_temp = jsonObject.get("lab_name").getAsString();
			lab_email_temp = jsonObject.get("lab_email").getAsString();
			test_name_temp = jsonObject.get("test_name").getAsString();
			
			delivery_User_Request_Reject_Bean = new Delivery_User_Request_Reject_Bean(
					id_temp, user_name_temp, user_email_temp, lab_name_temp, 
					lab_email_temp, test_name_temp);
		}
		
		delivery_User_Request_Reject_DAO = new Delivery_User_Request_Reject_DAO();
		
		result = delivery_User_Request_Reject_DAO.delivery_User_Reject_Delivery_Request(delivery_User_Request_Reject_Bean);
		
		System.out.println("\nReturn Message from delivery_User_Reject_Delivery_Request Method : - "+result);
		
		if(result.equals("Delivery Request from User is Rejected Successfully"))
		{
			response = Delivery_User_Request_Reject_Generate_Mail.deliveryUserRejectGenerateEmail(delivery_User_Request_Reject_Bean);
			
			System.out.println("\n\nReturn Message from deliveryUserRejectGenerateEmail Method : - "+response);
			
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}