package unitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import Logging.DatabaseQueryLogger;

class DatabaseQueryLoggerTests {

	private String databaseQueryLogFilePath = "databaseQueryLogFile.txt";
	
	@Test
	void TestLogging() {
		String testLog = "This is a test log message";
		
		DatabaseQueryLogger logger = new DatabaseQueryLogger();
		
		logger.log(testLog);
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(databaseQueryLogFilePath));
			assertTrue(bufferedReader.lines().anyMatch(Predicate.isEqual(testLog)));
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Could not find logging file");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Could not read logging file");
		}
		
	}

}
