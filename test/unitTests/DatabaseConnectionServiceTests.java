package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import services.DatabaseConnectionService;

public class DatabaseConnectionServiceTests {

	private DatabaseConnectionService instance;
	
	private String serverName = "nba-database.csse.rose-hulman.edu";
	private String databaseName = "TestDatabase";
	private String user = "sa";
	private String pass = "Thesquarerootoftheradiusofthesun123";
	
	private String expectedURL = "jdbc:sqlserver://nba-database.csse.rose-hulman.edu:1433;";
			
	@BeforeEach
	public void Setup() {
		instance = new DatabaseConnectionService(serverName, databaseName);
		instance.connect(user, pass);
	}
	
	@Test
	public void TestConnection() {
		Connection connection = instance.getConnection();
		
		try {
			assertTrue(connection.getMetaData().getURL().startsWith(expectedURL));
		} catch (SQLException e) {
			fail("SQLException :: " + e.getMessage());
		}
	}

}
