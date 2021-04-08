package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import services.DatabaseConnectionService;

class DatabaseConnectionServiceTests {

	static DatabaseConnectionService instance;
	
	static String serverName = "nba-database.csse.rose-hulman.edu";
	static String databaseName = "TestDatabase";
	static String user = "sa";
	static String pass = "Thesquarerootoftheradiusofthesun123";
	
	String expectedURL = "jdbc:sqlserver://nba-database.csse.rose-hulman.edu:1433;";
			
	@BeforeAll
	static void Setup() {
		instance = new DatabaseConnectionService(serverName, databaseName);
		instance.connect(user, pass);
	}
	
	@Test
	void TestConnection() {
		Connection connection = instance.getConnection();
		
		try {
			assertTrue(connection.getMetaData().getURL().startsWith(expectedURL));
		} catch (SQLException e) {
			fail("SQLException :: " + e.getMessage());
		}
	}

}
