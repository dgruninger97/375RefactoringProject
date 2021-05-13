package DatabaseQueries;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Domain.DatabaseConnectionService;

public class RebuildDatabase {

	public static void reEnableConstraints(DatabaseConnectionService dbService) {
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

	public static void clearDatabase(DatabaseConnectionService dbService) {
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
}
