package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCApp {
	public static void main(String args[]) throws SQLException
	{
		//gather (connection) information
		String ip = "192.168.1.5";  //ip to connect: change it according to your ip
		String port = "3306";  //default mysql port is 3306
		String url = String.format("jdbc:mysql://%s:%s/sakila", ip, port);  //url to connect
		String uname = "andro";  //sample username
		String password = "andro";  //sample password
		String query = "select * " +
                		"from country ";  //query to a sample mysql database (sakila)
		
		//check if the driver exists
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//These objects must be closed explicitly
		Connection con = null; //con object handles the connection: eg closes connection, request, get schema etc
		PreparedStatement statement = null;  //is used to execute queries
		ResultSet result = null; //get results of the query
		
		//establish connection
		//in every important connection we create a try catch block
		try
		{
			con = DriverManager.getConnection(url, uname, password);  //actual connection
			statement = con.prepareStatement(query);

            result = statement.executeQuery();
			
			while(result.next())
			{				
				String row = "";
				int cols = result.getMetaData().getColumnCount();
				for(int i=1; i<=cols; i++)  //column indexing starts at 1
				{
					row += result.getString(i) + " ";
				}
				System.out.println(row);
			}
		
		}//try block
		
		catch(SQLException e)
		{
			e.printStackTrace();
		}//catch block
		
		finally
		{
			try {
				result.close();
			}catch(Exception e) {}
			
			try {
				statement.close();
			}catch(Exception e) {}
			
			try {
				con.close();
			}catch(Exception e) {}
			
		}//finally block
	}
}
