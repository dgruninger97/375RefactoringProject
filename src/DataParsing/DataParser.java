package DataParsing;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Domain.DatabaseConnectionService;

public class DataParser {

	public static void insertNextLineToDB(DatabaseConnectionService dbService, Scanner inputStream) {
		String data = inputStream.nextLine();
		String[] values = data.split(",");
		
		try {
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
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Invalid File Structure, try again.");
		}
	}
}
