package services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PlayerService{

	private DatabaseConnectionService dbService = null;
	private String gameID;
	private String seasonYear;
	private ArrayList<String> gameList;
	private ArrayList<String> seasonList;
	public PlayerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<String> getPlayerInformation(String firstName, String lastName, boolean game, boolean season,
			boolean career, String year, int choiceIndex) {
		try {
			if (game) {
				return getPlayerGameInformation(firstName, lastName, year, choiceIndex);
			} else if (season) {
				return getPlayerSeasonInformation(firstName, lastName, choiceIndex);
			} else if (career) {
				return getPlayerCareerInformation(firstName, lastName);
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}

	private ArrayList<String> getPlayerCareerInformation(String firstName, String lastName) throws SQLException {
		ResultSet rs = callViewPlayerCareer(firstName, lastName);
		ArrayList<String> list = new ArrayList<String>();
		while (rs.next()) {
			list.add("Career Points: " + rs.getString(3) + "\nCareer Assists: " + rs.getString(4)
					+ "\nCareer Rebounds: " + rs.getString(5));
		}
		return list;
	}

	private ArrayList<String> getPlayerSeasonInformation(String firstName, String lastName, int choiceIndex)
			throws SQLException {
		ResultSet rs = callViewPlayerSeason(firstName, lastName);
		seasonList = new ArrayList<String>();
		while (rs.next()) {
			seasonList.add("Season year: " + rs.getString(1));
		}
		if(seasonList.size() == 0) {
			return null;
		}
		seasonYear = seasonList.get(choiceIndex).split(":")[1];
		seasonYear = seasonYear.substring(1);
		return seasonList;
	}

	private ArrayList<String> getPlayerGameInformation(String firstName, String lastName, String year, int choiceIndex)
			throws SQLException {
		ResultSet rs = callViewPlayerGame(firstName, lastName, year);
		gameList = new ArrayList<String>();
		while (rs.next()) {
			gameList.add(rs.getString(5) + ": " + rs.getString(3) + " vs. " + rs.getString(4));
		}
		if(gameList.size() == 0) {
			return null;
		}
		gameID = gameList.get(choiceIndex).split(":")[0];
		return gameList;
	}

	public ArrayList<String> getGameInfo(String firstName, String lastName, int choiceIndex) {
		try {
			ResultSet rs = callPlayerGameData(firstName, lastName);
			gameID = gameList.get(choiceIndex).split(":")[0];
			ArrayList<String> list = new ArrayList<String>();
			while(rs.next()) {
				list.add("Game Points: " + rs.getString(1) + "\nGame Assists: " + rs.getString(2) + "\nGame Rebounds: " + rs.getString(3));
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	public ArrayList<String> getSeasonInfo(String firstName, String lastName, int choiceIndex){
		try {
			ResultSet rs = callPlayerSeasonData(firstName, lastName);
			ArrayList<String> list = new ArrayList<String>();
			while(rs.next()) {
				list.add("Season Points: " + rs.getString(1) + "\nSeason Assists: " + rs.getString(2) + "\nSeason Rebounds: " + rs.getString(3));
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}

	private ResultSet callPlayerGameData(String firstName, String lastName) throws SQLException {
		CallableStatement callableStatement;
		callableStatement = dbService.getConnection().prepareCall("{?=call player_game_data(?,?,?)}");
		registerPlayerName(firstName, lastName, callableStatement);
		callableStatement.setInt(4, Integer.valueOf(gameID));
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}

	private void registerPlayerName(String firstName, String lastName, CallableStatement callableStatement)
			throws SQLException {
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, firstName);
		callableStatement.setString(3, lastName);
	}
	

	private ResultSet callPlayerSeasonData(String firstName, String lastName) throws SQLException {
		CallableStatement callableStatement;
		callableStatement = dbService.getConnection().prepareCall("{?=call player_season_data(?,?,?)}");
		registerPlayerName(firstName, lastName, callableStatement);
		callableStatement.setInt(4, Integer.valueOf(seasonYear));
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}
	

	private ResultSet callViewPlayerCareer(String firstName, String lastName) throws SQLException {
		CallableStatement callableStatement = dbService.getConnection().prepareCall("{?=call view_player_career(?,?)}");
		registerPlayerName(firstName, lastName, callableStatement);
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}

	private ResultSet callViewPlayerSeason(String firstName, String lastName) throws SQLException {
		CallableStatement callableStatement = dbService.getConnection().prepareCall("{?=call view_player_season(?,?)}");
		registerPlayerName(firstName, lastName, callableStatement);
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}

	private ResultSet callViewPlayerGame(String firstName, String lastName, String year) throws SQLException {
		CallableStatement callableStatement = dbService.getConnection().prepareCall("{?=call view_player_game(?,?,?)}");
		registerPlayerName(firstName, lastName, callableStatement);
		callableStatement.setInt(4, Integer.valueOf((String) year));
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}

}
