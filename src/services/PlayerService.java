package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import DomainObjects.PlayerName;
import PlayerQueries.PlayerCareerDataQuery;
import PlayerQueries.PlayerGameDataQuery;
import PlayerQueries.PlayerGamesPlayedInSeasonDataQuery;
import PlayerQueries.PlayerSeasonDataQuery;
import PlayerQueries.PlayerSeasonsPlayedDataQuery;

public class PlayerService{
	private DatabaseConnectionService dbService = null;
	private String seasonYear;
	private List<String> gameList;
	private List<String> seasonList;
	private String gameID;

	public PlayerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		this.gameList = new ArrayList<String>();
		this.seasonList = new ArrayList<String>();
	}

	public List<String> getPlayerInformation(String firstName, String lastName, boolean game, boolean season,
			boolean career, String year, int choiceIndex) {
		seasonYear = year;
		
		try {
			if (game) {
				gameList = getPlayerGameInformation(firstName, lastName, year, choiceIndex);
				gameID = gameList.get(choiceIndex).split(":")[0];
				return gameList;
			} else if (season) {
				seasonList = getPlayerSeasonInformation(firstName, lastName, choiceIndex);
				return seasonList;
			} else if (career) {
				return getPlayerCareerInformation(firstName, lastName);
			}
		} catch (SQLException e) {
			return null;
		}
		
		return null;
	}

	private List<String> getPlayerCareerInformation(String firstName, String lastName) throws SQLException {
		DatabaseQuery query = new PlayerCareerDataQuery(dbService, new PlayerName(firstName, lastName));
		return query.getResults();
	}

	private List<String> getPlayerSeasonInformation(String firstName, String lastName, int choiceIndex) throws SQLException {
		DatabaseQuery query = new PlayerSeasonsPlayedDataQuery(dbService, new PlayerName(firstName, lastName));
		return query.getResults();
	}

	private List<String> getPlayerGameInformation(String firstName, String lastName, String year, int choiceIndex) throws SQLException {
		DatabaseQuery query = new PlayerGamesPlayedInSeasonDataQuery(dbService, new PlayerName(firstName, lastName), seasonYear);
		return query.getResults();
	}

	public List<String> getGameInfo(String firstName, String lastName, int choiceIndex) throws SQLException {
		String gameID = gameList.get(choiceIndex).split(":")[0];
		DatabaseQuery query = new PlayerGameDataQuery(dbService, new PlayerName(firstName, lastName), gameID);
		return query.getResults();
	}

	public List<String> getSeasonInfo(String firstName, String lastName, int choiceIndex) throws SQLException {
		String year = seasonList.get(choiceIndex).split(": ")[1];
		DatabaseQuery query = new PlayerSeasonDataQuery(dbService, new PlayerName(firstName, lastName), year);
		return query.getResults();
	}
	
	public List<String> getCareerInfo(String firstName, String lastName) throws SQLException{
		DatabaseQuery query = new PlayerCareerDataQuery(dbService, new PlayerName(firstName, lastName));
		return query.getResults();
	}
}
