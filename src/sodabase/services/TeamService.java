package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DatabaseQueries.DatabaseQuery;
import PlayerQueries.PlayerGamesPlayedInSeasonDataQuery;
import TeamQueries.TeamGameDataQuery;

public class TeamService {
	private DatabaseConnectionService dbService = null;
	private String gameID;
	private ArrayList<String> seasonList;
	private ArrayList<String> gameList;

	public TeamService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<String> getTeamInformation(String teamName, boolean game, boolean season,
			boolean career, String year, int choiceIndex) {
		try {
			CallableStatement callableStatement = null;
			if (game) {
				callableStatement = dbService.getConnection().prepareCall("{?=call view_team_game(?,?)}");
				callableStatement.registerOutParameter(1, Types.INTEGER);
				callableStatement.setString(2, teamName);
				callableStatement.setInt(3, Integer.valueOf(year));
				ResultSet rs = callableStatement.executeQuery();
				gameList = new ArrayList<String>();
				while(rs.next()) {
					gameList.add(rs.getString(5) + ":" + rs.getString(1) + " vs. " + rs.getString(2));
				}
				gameID = gameList.get(choiceIndex).split(":")[0];
				return gameList;
			} else if (season) {
				callableStatement = dbService.getConnection().prepareCall("{?=call view_team_season(?)}");
				callableStatement.registerOutParameter(1, Types.INTEGER);
				callableStatement.setString(2, teamName);
				ResultSet rs = callableStatement.executeQuery();
				seasonList = new ArrayList<String>();
				while(rs.next()) {
					seasonList.add("Season year: " + rs.getString(1));
				}
				return seasonList;
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
	public List<String> getTeamGameInfo(String teamName, String year, int choiceIndex) throws SQLException {
		gameID = gameList.get(choiceIndex).split(":")[0];
		DatabaseQuery query = new TeamGameDataQuery(dbService, teamName, year, gameID);
		return query.getResults();
	}
	
	public ArrayList<String> getTeamSeasonInfo(String teamName, int choiceIndex){
		CallableStatement callableStatement = null;
		try {
			String year = seasonList.get(choiceIndex).split(": ")[1];
			callableStatement = dbService.getConnection().prepareCall("{?=call team_season_data(?, ?)}");
			callableStatement.registerOutParameter(1, Types.INTEGER);
			callableStatement.setString(2, teamName);
			callableStatement.setString(3, year);
			ResultSet rs = callableStatement.executeQuery();
			ArrayList<String> list = new ArrayList<String>();
			while(rs.next()) {
				list.add("Season Points For: " + rs.getString(1) + "\nSeason Points Against: " + rs.getString(2) + "\nWin Percentage: " + rs.getString(3));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
