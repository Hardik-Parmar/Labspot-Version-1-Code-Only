package com.example.labspot_backend_api.test_detail_transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.test_detail_transaction_Beans.User_Request_For_Sample_Collect_Bean;
import Java_DAO.test_detail_transaction_DAO.User_Request_For_Sample_Collect_DAO;

@Path("test_detail_transaction")
public class User_Request_For_Sample_Collect
{
	User_Request_For_Sample_Collect_Bean user_Request_For_Sample_Collect_Bean;
	User_Request_For_Sample_Collect_DAO user_Request_For_Sample_Collect_DAO;
	
	public String request_id_temp, user_name_temp, user_city_temp, user_email_temp;
	public String lab_name_temp, lab_email_temp;
	
	public String result;
	
	@Path("request_for_sample_collect")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String sampleCollect(String json)
	{
		System.out.println("\nREQUEST FOR SAMPLE COLLECT TO LAB API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			request_id_temp = jsonObject.get("request_id").getAsString();
			user_name_temp = jsonObject.get("user_name").getAsString();
			user_email_temp = jsonObject.get("user_email").getAsString();
			user_city_temp = jsonObject.get("user_city").getAsString();
			lab_name_temp = jsonObject.get("lab_name").getAsString();
			lab_email_temp = jsonObject.get("lab_email").getAsString();
		
			user_Request_For_Sample_Collect_Bean = new User_Request_For_Sample_Collect_Bean(
					request_id_temp, user_name_temp, user_email_temp, user_city_temp,
					lab_name_temp, lab_email_temp);
		}
		
		user_Request_For_Sample_Collect_DAO = new User_Request_For_Sample_Collect_DAO();
		
		result = user_Request_For_Sample_Collect_DAO.request_For_Sample_Collect(user_Request_For_Sample_Collect_Bean);
		
		System.out.println("\nReturn Message from request_For_Sample_Collect Method : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}