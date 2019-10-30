package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TeamService {
	private DatabaseConnectionService dbService = null;

	public TeamService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<String> getTeamInformation(String teamName, boolean game, boolean season,
			boolean career, String year) {
		try {
			CallableStatement callableStatement = null;
			if (game) {
				callableStatement = dbService.getConnection().prepareCall("{?=call view_team_game(?,?)}");
				callableStatement.registerOutParameter(1, Types.INTEGER);
				callableStatement.setString(2, teamName);
				callableStatement.setString(3, year);
				ResultSet rs = callableStatement.executeQuery();
				ArrayList<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add(rs.getString(1) + " vs. " + rs.getString(2));
				}
				return list;
			} else if (season) {
				callableStatement = dbService.getConnection().prepareCall("{?=call view_team_season(?)}");
				callableStatement.registerOutParameter(1, Types.INTEGER);
				callableStatement.setString(2, teamName);
				ResultSet rs = callableStatement.executeQuery();
				ArrayList<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add("Season year: " + rs.getString(1));
				}
				return list;
			} else if (career) {
				callableStatement = dbService.getConnection().prepareCall("{?=call view_team_franchise(?)}");
				callableStatement.registerOutParameter(1, Types.INTEGER);
				callableStatement.setString(2, teamName);
				ResultSet rs = callableStatement.executeQuery();
				ArrayList<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add("Franchise Points For: " + rs.getString(2) + "\nFranchise Points Against: " +  rs.getString(3) + "\nWin Percentage: " + rs.getString(4));
				}
				return list;
			}
			} catch (SQLException e) {
				return null;
			}
			return null;
	}
}
