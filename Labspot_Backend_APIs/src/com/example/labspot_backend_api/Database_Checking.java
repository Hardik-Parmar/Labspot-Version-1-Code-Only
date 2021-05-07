package com.example.labspot_backend_api;

import java.sql.Connection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import database_connection.ConnectionProvider;

@Path("Database_Connection_Checking")
public class Database_Checking 
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String data()
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			if(connection != null)
			{
				System.out.println("Connection Established with Database Succsessfully");
				
				return "Connection Established with Database Succsessfully";
			}
		}
	
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Failed to connect Database";
	}
}