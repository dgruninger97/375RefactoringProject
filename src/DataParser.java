import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;
import java.util.StringTokenizer;

import sodabase.services.DatabaseConnectionService;

public class DataParser {
	// Rename and get rid of String[] args
	public static void main(String[] args, DatabaseConnectionService dbService) {
		String fileName = "DataSheet.csv";
		File file = new File(fileName);
		try {
			Scanner inputStream = new Scanner(file);
			inputStream.nextLine();
			while (inputStream.hasNext()) {
				insertData(dbService, inputStream);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void insertData(DatabaseConnectionService dbService, Scanner inputStream) {
		String data = inputStream.nextLine();
		String[] values = data.split(",");
		try {
			executeInsertDataStatement(dbService, values);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void executeInsertDataStatement(DatabaseConnectionService dbService, String[] values)
			throws SQLException {
		CallableStatement callableStatement;
		callableStatement = dbService.getConnection()
				.prepareCall("{?=call insertData(?,?,?,?,?,?,?,?,?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, values[0]);
		callableStatement.setString(3, values[1]);
		callableStatement.setString(4, values[2]);
		callableStatement.setString(5, values[3]);
		callableStatement.setString(6, values[4]);
		callableStatement.setString(7, values[5]);
		callableStatement.setString(8, values[6]);
		callableStatement.setString(9, values[7]);
		callableStatement.setString(10, values[8]);
		callableStatement.setString(11, values[9]);
		callableStatement.execute();
	}
}
