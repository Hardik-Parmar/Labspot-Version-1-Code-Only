package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;

public final class ConnectionProvider implements Confidential_Details 
{
	
	static Connection connection;
	
	private ConnectionProvider()
	{
		
	}
	
	public static Connection getConnection()
	{
		if(connection == null)
		{
			try
			{
				Class.forName(Confidential_Details.JDBC_DRIVER);
				connection = DriverManager.getConnection(Confidential_Details.DATABASE_URL, Confidential_Details.DATABASE_USERNAME, Confidential_Details.DATABASE_PASSWORD);
					
				return connection;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		else
		{
			return connection;
		}
		
		return connection;
	}

}