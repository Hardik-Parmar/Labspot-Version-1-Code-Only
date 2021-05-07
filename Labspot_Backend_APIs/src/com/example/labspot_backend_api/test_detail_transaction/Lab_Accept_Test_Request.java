package com.example.labspot_backend_api.test_detail_transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.test_detail_transaction_Beans.Lab_Take_Action_On_Test_Request_Bean;
import Java_DAO.test_detail_transaction_DAO.Lab_Accept_Test_Request_DAO;
import send_mail.Lab_Accept_Test_Request_Generate_Mail;

@Path("test_detail_transaction")
public class Lab_Accept_Test_Request
{
	Lab_Take_Action_On_Test_Request_Bean lab_Take_Action_On_Test_Request_Bean;
	Lab_Accept_Test_Request_DAO lab_Accept_Test_Request_DAO;
	
	public String id_temp, user_name_temp, user_phone_temp, user_email_temp;
	public String test_name_temp, date_temp, date_of_action_temp;
	
	public String result, response;
	
	@Path("accept_test_request")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String placeTestRequestToLab(String json)
	{
		System.out.println("\nACCEPT TEST REQUEST TO LAB API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			id_temp = jsonObject.get("applicant_id").getAsString();
			user_name_temp = jsonObject.get("user_name").getAsString();
			user_phone_temp = jsonObject.get("user_phone").getAsString();
			user_email_temp = jsonObject.get("user_email").getAsString();
			test_name_temp = jsonObject.get("test_name").getAsString();
			date_temp = jsonObject.get("date_of_test_order").getAsString();
			date_of_action_temp = jsonObject.get("date_of_action").getAsString();
			
			lab_Take_Action_On_Test_Request_Bean = new Lab_Take_Action_On_Test_Request_Bean(
					id_temp, user_name_temp, user_phone_temp, user_email_temp, 
					test_name_temp,date_temp, date_of_action_temp);
		}
		
		lab_Accept_Test_Request_DAO = new Lab_Accept_Test_Request_DAO();
		
		result = lab_Accept_Test_Request_DAO.accept_Test_Request(lab_Take_Action_On_Test_Request_Bean);
		
		System.out.println("\nReturn Message from reject_Test_Request Method : - "+result);
		
		if(result.equals("Test Request Accepted"))
		{
			response = Lab_Accept_Test_Request_Generate_Mail.labAcceptTestGenerateEmail(lab_Take_Action_On_Test_Request_Bean);
			
			System.out.println("\n\nReturn Message from labAcceptTestGenerateEmail Method : - "+response);
			
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}