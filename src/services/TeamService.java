package services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
			if (game) {
				return getTeamGameInformation(teamName, year, choiceIndex);
			} else if (season) {
				return getTeamSeasonInformation(teamName);
			} else if (career) {
				return getTeamFranchiseInformation(teamName);
			}
			} catch (SQLException e) {
				return null;
			}
			return null;
	}

	private ArrayList<String> getTeamFranchiseInformation(String teamName) throws SQLException {
		ResultSet rs = callViewTeamFranchise(teamName);
		ArrayList<String> list = new ArrayList<String>();
		while(rs.next()) {
			list.add("Franchise Points For: " + rs.getString(2) + "\nFranchise Points Against: " +  rs.getString(3) + "\nWin Percentage: " + rs.getString(4));
		}
		return list;
	}

	private ArrayList<String> getTeamSeasonInformation(String teamName) throws SQLException {
		ResultSet rs = callViewTeamSeason(teamName);
		seasonList = new ArrayList<String>();
		while(rs.next()) {
			seasonList.add("Season year: " + rs.getString(1));
		}
		return seasonList;
	}

	private ArrayList<String> getTeamGameInformation(String teamName, String year, int choiceIndex) throws SQLException {
		ResultSet rs = callViewTeamGame(teamName, year);
		gameList = new ArrayList<String>();
		while(rs.next()) {
			gameList.add(rs.getString(5) + ":" + rs.getString(1) + " vs. " + rs.getString(2));
		}
		gameID = gameList.get(choiceIndex).split(":")[0];
		return gameList;
	}

	public ArrayList<String> getTeamGameInfo(String teamName, String year, int choiceIndex) {
		try {
			gameID = gameList.get(choiceIndex).split(":")[0];
			ResultSet rs = callTeamGameData(teamName, year);
			ArrayList<String> list = new ArrayList<String>();
			while(rs.next()) {
				list.add("Game Points For: " + rs.getString(1) + "\nGame Points Against: " + rs.getString(2));
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	public ArrayList<String> getTeamSeasonInfo(String teamName, int choiceIndex){
		try {
			String year = seasonList.get(choiceIndex).split(": ")[1];
			ResultSet rs = callTeamSeasonData(teamName, year);
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
	
	private ResultSet callViewTeamFranchise(String teamName) throws SQLException {
		CallableStatement callableStatement = dbService.getConnection().prepareCall("{?=call view_team_franchise(?)}");
		registerTeamName(teamName, callableStatement);
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}

	private ResultSet callViewTeamSeason(String teamName) throws SQLException {
		CallableStatement callableStatement = dbService.getConnection().prepareCall("{?=call view_team_season(?)}");
		registerTeamName(teamName, callableStatement);
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}

	private void registerTeamName(String teamName, CallableStatement callableStatement) throws SQLException {
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, teamName);
	}

	private ResultSet callViewTeamGame(String teamName, String year) throws SQLException {
		CallableStatement callableStatement = dbService.getConnection().prepareCall("{?=call view_team_game(?,?)}");
		registerTeamName(teamName, callableStatement);
		callableStatement.setInt(3, Integer.valueOf(year));
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}

	private ResultSet callTeamGameData(String teamName, String year) throws SQLException {
		CallableStatement callableStatement = dbService.getConnection().prepareCall("{?=call team_game_data(?,?,?)}");
		registerTeamName(teamName, callableStatement);
		callableStatement.setInt(3, Integer.valueOf(year));
		callableStatement.setInt(4, Integer.valueOf(gameID));
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}
	

	private ResultSet callTeamSeasonData(String teamName, String year) throws SQLException {
		CallableStatement callableStatement = dbService.getConnection().prepareCall("{?=call team_season_data(?, ?)}");
		registerTeamName(teamName, callableStatement);
		callableStatement.setString(3, year);
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}
}
