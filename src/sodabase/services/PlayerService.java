package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PlayerService {

	private DatabaseConnectionService dbService = null;

	public PlayerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<String> getPlayerInformation(String firstName, String lastName, boolean game, boolean season,
			boolean career, String year) {
		try {
			CallableStatement callableStatement = null;
			if (game) {
				callableStatement = dbService.getConnection().prepareCall("{?=call view_player_game(?,?,?)}");
				callableStatement.registerOutParameter(1, Types.INTEGER);
				callableStatement.setString(2, firstName);
				callableStatement.setString(3, lastName);
				callableStatement.setInt(4, Integer.valueOf((String) year));
				ResultSet rs = callableStatement.executeQuery();
				ArrayList<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add(rs.getString(3) + " vs. " + rs.getString(4));
				}
				return list;
			} else if (season) {
				callableStatement = dbService.getConnection().prepareCall("{?=call view_player_season(?,?)}");
				callableStatement.registerOutParameter(1, Types.INTEGER);
				callableStatement.setString(2, firstName);
				callableStatement.setString(3, lastName);
				ResultSet rs = callableStatement.executeQuery();
				ArrayList<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add("Season year: " + rs.getString(1));
				}
				return list;
			} else if (career) {
				callableStatement = dbService.getConnection().prepareCall("{?=call view_player_career(?,?)}");
				callableStatement.registerOutParameter(1, Types.INTEGER);
				callableStatement.setString(2, firstName);
				callableStatement.setString(3, lastName);
				ResultSet rs = callableStatement.executeQuery();
				ArrayList<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add("Career Points: " + rs.getString(3) + "\nCareer Rebounds: " +  rs.getString(4) + "\nCareer Assists: " + rs.getString(5));
				}
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

}
