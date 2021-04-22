package Logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DatabaseQueryLogger implements Logger {
	private String databaseQueryLogFilePath = "databaseQueryLogFile.txt";
	
	public void log(String query) {
		try {
			FileWriter fileWriter = new FileWriter(databaseQueryLogFilePath, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(query);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
