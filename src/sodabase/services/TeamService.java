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
import TeamQueries.TeamFranchiseDataQuery;
import TeamQueries.TeamGameDataQuery;
import TeamQueries.TeamGamesPlayedDataQuery;
import TeamQueries.TeamSeasonDataQuery;
import TeamQueries.TeamSeasonsPlayedDataQuery;

public class TeamService {
	private DatabaseConnectionService dbService = null;
	private String gameID;
	private ArrayList<String> seasonList;

	public TeamService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public List<String> getTeamInformation(String teamName, boolean game, boolean season,
			boolean career, String year, int choiceIndex) {
		try {
			if (game) {
				return getTeamGamesPlayedInfo(teamName, year);
			} else if (season) {
				return getTeamSeasonsPlayedInfo(teamName);
			} else if (career) {
				return getTeamFranchiseInfo(teamName);
			}
		} catch (SQLException e) {
			return null;
		}
		
		return null;
	}
	
	public List<String> getTeamGamesPlayedInfo(String teamName, String year) throws SQLException {
		DatabaseQuery query = new TeamGamesPlayedDataQuery(dbService, teamName, year);
		return query.getResults();
	}
	
	public List<String> getTeamSeasonsPlayedInfo(String teamName) throws SQLException {
		DatabaseQuery query = new TeamSeasonsPlayedDataQuery(dbService, teamName);
		return query.getResults();
	}
	
	public List<String> getTeamFranchiseInfo(String teamName) throws SQLException {
		DatabaseQuery query = new TeamFranchiseDataQuery(dbService, teamName);
		return query.getResults();
	}
	
	public List<String> getTeamGameInfo(String teamName, String year, int choiceIndex) throws SQLException {
		DatabaseQuery query = new TeamGameDataQuery(dbService, teamName, year, gameID);
		return query.getResults();
	}
	
	public List<String> getTeamSeasonInfo(String teamName, int choiceIndex) throws SQLException {
		String year = seasonList.get(choiceIndex).split(": ")[1];
		DatabaseQuery query = new TeamSeasonDataQuery(dbService, teamName, year);
		return query.getResults();
	}
}
