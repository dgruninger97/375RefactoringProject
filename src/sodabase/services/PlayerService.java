package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PlayerService {

	private DatabaseConnectionService dbService = null;
	private String gameID;
	private String seasonYear;
	private ArrayList<String> gameList;
	private ArrayList<String> seasonList;

	public PlayerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public List<String> getPlayerInformation(String firstName, String lastName, boolean game, boolean season,
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

	private List<String> getPlayerCareerInformation(String firstName, String lastName) throws SQLException {
		DatabaseQuery query = new PlayerCareerDataQuery(dbService, firstName, lastName);
		return query.getResults();
//		ResultSet rs = callViewPlayerCareer(firstName, lastName);
//		ArrayList<String> list = new ArrayList<String>();
//		while (rs.next()) {
//			list.add("Career Points: " + rs.getString(3) + "\nCareer Assists: " + rs.getString(4)
//					+ "\nCareer Rebounds: " + rs.getString(5));
//		}
//		return list;
	}

	private List<String> getPlayerSeasonInformation(String firstName, String lastName, int choiceIndex)
			throws SQLException {
		DatabaseQuery query = new PlayerSeasonsPlayedDataQuery(dbService, firstName, lastName);
		return query.getResults();
	}

	private List<String> getPlayerGameInformation(String firstName, String lastName, String year, int choiceIndex)
			throws SQLException {
		DatabaseQuery query = new PlayerGamesPlayedInSeasonDataQuery(dbService, firstName, lastName, seasonYear);
		return query.getResults();
//		ResultSet rs = callViewPlayerGame(firstName, lastName, year);
//		gameList = new ArrayList<String>();
//		while (rs.next()) {
//			gameList.add(rs.getString(5) + ": " + rs.getString(3) + " vs. " + rs.getString(4));
//		}
//		if (gameList.size() == 0) {
//			return null;
//		}
//		gameID = gameList.get(choiceIndex).split(":")[0];
//		return gameList;
	}

	public List<String> getGameInfo(String firstName, String lastName, int choiceIndex) throws SQLException {
		DatabaseQuery query = new PlayerGameDataQuery(dbService, firstName, lastName, seasonYear);
		return query.getResults();
//		try {
//			ResultSet rs = callPlayerGameData(firstName, lastName);
//			gameID = gameList.get(choiceIndex).split(":")[0];
//			ArrayList<String> list = new ArrayList<String>();
//			while (rs.next()) {
//				list.add("Game Points: " + rs.getString(1) + "\nGame Assists: " + rs.getString(2) + "\nGame Rebounds: "
//						+ rs.getString(3));
//			}
//			return list;
//		} catch (SQLException e) {
//			return null;
//		}
	}

	public List<String> getSeasonInfo(String firstName, String lastName, int choiceIndex) throws SQLException {
		DatabaseQuery query = new PlayerSeasonDataQuery(dbService, firstName, lastName, seasonYear);
		return query.getResults();
//		try {
//			ResultSet rs = callPlayerSeasonData(firstName, lastName);
//			ArrayList<String> list = new ArrayList<String>();
//			while (rs.next()) {
//				list.add("Season Points: " + rs.getString(1) + "\nSeason Assists: " + rs.getString(2)
//						+ "\nSeason Rebounds: " + rs.getString(3));
//			}
//			return list;
//		} catch (SQLException e) {
//			return null;
//		}
	}
}
