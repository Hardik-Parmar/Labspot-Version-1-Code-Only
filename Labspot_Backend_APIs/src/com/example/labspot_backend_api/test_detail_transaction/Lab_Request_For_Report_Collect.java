package com.example.labspot_backend_api.test_detail_transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.test_detail_transaction_Beans.Lab_Request_For_Report_Collect_Bean;
import Java_DAO.test_detail_transaction_DAO.Lab_Request_For_Report_Collect_DAO;

@Path("test_detail_transaction")
public class Lab_Request_For_Report_Collect
{
	Lab_Request_For_Report_Collect_Bean lab_Request_For_Report_Collect_Bean;
	Lab_Request_For_Report_Collect_DAO lab_Request_For_Report_Collect_DAO;
	
	public String request_id_temp, user_name_temp, lab_city_temp, user_email_temp;
	public String lab_name_temp, lab_email_temp;
	
	public String result;
	
	@Path("request_for_report_collect")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String reportCollect(String json)
	{
		System.out.println("\nREQUEST FOR REPORT COLLECT FROM LAB API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			request_id_temp = jsonObject.get("request_id").getAsString();
			lab_name_temp = jsonObject.get("lab_name").getAsString();
			lab_email_temp = jsonObject.get("lab_email").getAsString();
			lab_city_temp = jsonObject.get("lab_city").getAsString();
			user_name_temp = jsonObject.get("customer_name").getAsString();
			user_email_temp = jsonObject.get("customer_email").getAsString();
		
			lab_Request_For_Report_Collect_Bean = new Lab_Request_For_Report_Collect_Bean(
					request_id_temp, lab_name_temp, lab_email_temp, lab_city_temp, 
					user_name_temp, user_email_temp);
		}
		
		lab_Request_For_Report_Collect_DAO = new Lab_Request_For_Report_Collect_DAO();
		
		result = lab_Request_For_Report_Collect_DAO.request_For_Report_Collect(lab_Request_For_Report_Collect_Bean);
		
		System.out.println("\nReturn Message from request_For_Report_Collect Method : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}