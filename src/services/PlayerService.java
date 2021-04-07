package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import PlayerQueries.PlayerCareerDataQuery;
import PlayerQueries.PlayerGameDataQuery;
import PlayerQueries.PlayerGamesPlayedInSeasonDataQuery;
import PlayerQueries.PlayerSeasonDataQuery;
import PlayerQueries.PlayerSeasonsPlayedDataQuery;

public class PlayerService{
	private DatabaseConnectionService dbService = null;
	private String seasonYear;
	private List<String> gameList;
	private String gameID;

	public PlayerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		this.gameList = new ArrayList<String>();
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
	}

	private List<String> getPlayerSeasonInformation(String firstName, String lastName, int choiceIndex) throws SQLException {
		DatabaseQuery query = new PlayerSeasonsPlayedDataQuery(dbService, firstName, lastName);
		return query.getResults();
	}

	private List<String> getPlayerGameInformation(String firstName, String lastName, String year, int choiceIndex) throws SQLException {
		DatabaseQuery query = new PlayerGamesPlayedInSeasonDataQuery(dbService, firstName, lastName, seasonYear);
		return query.getResults();
	}

	public List<String> getGameInfo(String firstName, String lastName, int choiceIndex) throws SQLException {
		String gameID = gameList.get(choiceIndex).split(":")[0];
		DatabaseQuery query = new PlayerGameDataQuery(dbService, firstName, lastName, gameID);
		return query.getResults();
	}

	public List<String> getSeasonInfo(String firstName, String lastName, int choiceIndex) throws SQLException {
		DatabaseQuery query = new PlayerSeasonDataQuery(dbService, firstName, lastName, seasonYear);
		return query.getResults();
	}
	
	public List<String> getCareerInfo(String firstName, String lastName) throws SQLException{
		DatabaseQuery query = new PlayerCareerDataQuery(dbService, firstName, lastName);
		return query.getResults();
	}
}
