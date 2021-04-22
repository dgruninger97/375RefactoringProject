package DataParsing;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import services.DatabaseConnectionService;

public class DataParser {
	// Rename and get rid of String[] args
	public void main(String[] args, DatabaseConnectionService dbService) {
//		String fileName = "DataSheet3.csv";
//		clearDatabase(dbService);
//		reEnableConstraints(dbService);
//		File file = new File(fileName);
//		try {
//			Scanner inputStream = new Scanner(file);
//			inputStream.nextLine();
//			while (inputStream.hasNext()) {
//				insertData(dbService, inputStream);
//			}
//			inputStream.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
	}

	private static void reEnableConstraints(DatabaseConnectionService dbService) {
		CallableStatement callableStatement;
		try {
			callableStatement = dbService.getConnection()
					.prepareCall("alter table Plays_In CHECK constraint FK__Plays_In__Player__2D27B809\r\n" + 
							"alter table On_A CHECK constraint fk_On_A_PlayerID\r\n" + 
							"alter table Plays_A CHECK constraint FK_Plays_A_Team\r\n" + 
							"alter table On_A CHECK constraint fk_On_A_Name\r\n" + 
							"alter table Plays_A CHECK constraint FK_Plays_A_Game\r\n" + 
							"alter table Plays_In CHECK constraint FK__Plays_In__GameID__2C3393D0");
			callableStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void clearDatabase(DatabaseConnectionService dbService) {
		CallableStatement callableStatement;
		try {
			callableStatement = dbService.getConnection()
					.prepareCall("alter table Plays_In NOCHECK constraint FK__Plays_In__Player__2D27B809\r\n" + 
							"alter table On_A NOCHECK constraint fk_On_A_PlayerID\r\n" + 
							"delete from player");
			callableStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			callableStatement = dbService.getConnection()
					.prepareCall("alter table Plays_A NOCHECK constraint FK_Plays_A_Team\r\n" + 
							"alter table On_A NOCHECK constraint fk_On_A_Name\r\n" + 
							"DELETE FROM Team");
			callableStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			callableStatement = dbService.getConnection()
					.prepareCall("alter table Plays_A NOCHECK constraint FK_Plays_A_Game\r\n" + 
							"alter table Plays_In NOCHECK constraint FK__Plays_In__GameID__2C3393D0\r\n" + 
							"DELETE FROM Game");
			callableStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			callableStatement = dbService.getConnection()
					.prepareCall("DELETE FROM On_A");
			callableStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			callableStatement = dbService.getConnection()
					.prepareCall("Delete FROM Plays_A");
			callableStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			callableStatement = dbService.getConnection()
					.prepareCall("DELETE FROM Plays_In");
			callableStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			callableStatement = dbService.getConnection()
					.prepareCall("DELETE FROM Season");
			callableStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
