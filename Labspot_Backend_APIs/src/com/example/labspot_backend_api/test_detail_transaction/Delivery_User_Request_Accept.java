package com.example.labspot_backend_api.test_detail_transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.test_detail_transaction_Beans.Delivery_User_Request_Accept_Bean;
import Java_DAO.test_detail_transaction_DAO.Delivery_User_Request_Accept_DAO;
import send_mail.Delivery_User_Request_Accept_Generate_Mail;

@Path("test_detail_transaction")
public class Delivery_User_Request_Accept
{
	Delivery_User_Request_Accept_Bean delivery_User_Request_Accept_Bean;
	Delivery_User_Request_Accept_DAO delivery_User_Request_Accept_DAO;
	
	public String id_temp, user_name_temp, user_email_temp, lab_name_temp, lab_email_temp;
	public String test_name_temp;
	
	public String result, response;
	
	@Path("user_accept_delivery_request")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userAcceptDeliveryRequest(String json)
	{
		System.out.println("\nACCEPT DELIVERY REQUEST FROM USER API");
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
			
			delivery_User_Request_Accept_Bean = new Delivery_User_Request_Accept_Bean(
					id_temp, user_name_temp, user_email_temp, lab_name_temp, 
					lab_email_temp, test_name_temp);
		}
		
		delivery_User_Request_Accept_DAO = new Delivery_User_Request_Accept_DAO();
		
		result = delivery_User_Request_Accept_DAO.delivery_User_Accept_Delivery_Request(delivery_User_Request_Accept_Bean);
		
		System.out.println("\nReturn Message from delivery_User_Accept_Delivery_Request Method : - "+result);
		
		if(result.equals("Delivery Request from User is Accepted Successfully"))
		{
			response = Delivery_User_Request_Accept_Generate_Mail.deliveryUserAcceptGenerateEmail(delivery_User_Request_Accept_Bean);
			
			System.out.println("\n\nReturn Message from deliveryUserAcceptGenerateEmail Method : - "+response);
			
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}