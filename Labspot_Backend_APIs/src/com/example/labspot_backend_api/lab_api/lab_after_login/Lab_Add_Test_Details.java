package com.example.labspot_backend_api.lab_api.lab_after_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.lab_Beans.lab_after_login_Beans.Lab_Add_Test_Details_Bean;
import Java_DAO.lab_DAO.lab_after_login_DAO.Lab_Add_Test_Details_DAO;

@Path("lab")
public class Lab_Add_Test_Details
{
	Lab_Add_Test_Details_Bean lab_Add_Test_Details_Bean;
	Lab_Add_Test_Details_DAO lab_Add_Test_Details_DAO;
	
	public String lab_name_temp, lab_email_temp, lab_test_name_temp, lab_test_description_temp, lab_test_price_temp;
	
	public String result;
	
	@Path("add_test_details")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String labAddTestDetails(String json)
	{
		System.out.println("\nLAB ADD TEST DETAILS API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			lab_name_temp = jsonObject.get("lab_name").getAsString();
			lab_email_temp = jsonObject.get("lab_email").getAsString();
			lab_test_name_temp = jsonObject.get("lab_test_name").getAsString();
			lab_test_description_temp = jsonObject.get("lab_test_description").getAsString();
			lab_test_price_temp = jsonObject.get("lab_test_price").getAsString();
			
			lab_Add_Test_Details_Bean = new Lab_Add_Test_Details_Bean(lab_name_temp, lab_email_temp, lab_test_name_temp, lab_test_description_temp, lab_test_price_temp);
		}
		
		lab_Add_Test_Details_DAO = new Lab_Add_Test_Details_DAO();
		
		result = lab_Add_Test_Details_DAO.lab_Add_Test_Details(lab_Add_Test_Details_Bean);
		
		System.out.println("\nReturn Message from lab_Add_Test_Details : - "+result);

		return "{'lab_return_message' : '"+result+"'}";
	}
}