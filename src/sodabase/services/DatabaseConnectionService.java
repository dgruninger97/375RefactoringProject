package sodabase.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionService {

	//DO NOT EDIT THIS STRING, YOU WILL RECEIVE NO CREDIT FOR THIS TASK IF THIS STRING IS EDITED
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		//DO NOT CHANGE THIS METHOD
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		//TODO: Task 1
		//BUILD YOUR CONNECTION STRING HERE USING THE SAMPLE URL ABOVE
		String replaceString = SampleURL.replace("${dbServer}", serverName);
		String replaceString2 = replaceString.replace("${dbName}", databaseName);
		String replaceString3 = replaceString2.replace("${user}", user);
		String connectionString = replaceString3.replace("${pass}", pass);
//		String connectionString = "jdbc:sqlserver://" + serverName + ";databaseName=" + databaseName + ";user=" + user + ";password={" + pass + "}";
		try {
			this.connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		//TODO: Task 1
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
