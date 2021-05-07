package com.example.labspot_backend_api.test_detail_transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.test_detail_transaction_Beans.Lab_Complete_Test_Process_Bean;
import Java_DAO.test_detail_transaction_DAO.Lab_Complete_Test_Process_DAO;
import send_mail.Lab_Complete_Test_Process_Mail;

@Path("test_detail_transaction")
public class Lab_Complete_Test_Process
{
	Lab_Complete_Test_Process_Bean lab_Complete_Test_Process_Bean;
	Lab_Complete_Test_Process_DAO lab_Complete_Test_Process_DAO;
	
	public String id_temp, user_name_temp, user_email_temp;
	public String test_name_temp, lab_name_temp, lab_email_temp;
	
	public String result, response;
	
	@Path("complete_test_request")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String completeTestRequest(String json)
	{
		System.out.println("\nCOMPLETE TEST REQUEST TO LAB API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			id_temp = jsonObject.get("request_id").getAsString();
			user_name_temp = jsonObject.get("customer_name").getAsString();
			user_email_temp = jsonObject.get("customer_email").getAsString();
			test_name_temp = jsonObject.get("test_name").getAsString();
			lab_name_temp = jsonObject.get("lab_name").getAsString();
			lab_email_temp = jsonObject.get("lab_email").getAsString();
			
			lab_Complete_Test_Process_Bean = new Lab_Complete_Test_Process_Bean(
					id_temp, user_name_temp, user_email_temp, 
					test_name_temp, lab_name_temp, lab_email_temp);
		}
		
		lab_Complete_Test_Process_DAO = new Lab_Complete_Test_Process_DAO();
		
		result = lab_Complete_Test_Process_DAO.complete_Test_Request(lab_Complete_Test_Process_Bean);
		
		System.out.println("\nReturn Message from complete_Test_Request Method : - "+result);
		
		if(result.equals("Test Request Completed"))
		{	
			response = Lab_Complete_Test_Process_Mail.labCompleteTestProcessEmail(lab_Complete_Test_Process_Bean);
			
			System.out.println("\n\nReturn Message from labCompleteTestProcessEmail Method : - "+response);
			
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}