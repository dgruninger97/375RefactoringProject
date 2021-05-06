package Domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DatabaseConnectionService {

	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		String connectionString = prepareSQLString(user, pass);
		try {
			this.connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Issue connecting to the database. Please double check your files and try connecting again. The system will now safely shut down.");
			System.exit(0);
		}
		return true;
	}

	private String prepareSQLString(String user, String pass) {
		String replaceString = SampleURL.replace("${dbServer}", serverName);
		replaceString = replaceString.replace("${dbName}", databaseName);
		replaceString = replaceString.replace("${user}", user);
		replaceString = replaceString.replace("${pass}", pass);
		return replaceString;
	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
