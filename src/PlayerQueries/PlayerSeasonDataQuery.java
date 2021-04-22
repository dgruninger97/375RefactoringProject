package PlayerQueries;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import DomainObjects.PlayerName;
import services.DatabaseConnectionService;

public class PlayerSeasonDataQuery extends DatabaseQuery {
	private PlayerName playerName;
	private String seasonYear;
	
	public PlayerSeasonDataQuery(DatabaseConnectionService dbService, PlayerName playerName, String seasonYear) {
		super(dbService);
		this.playerName = playerName;
		this.seasonYear = seasonYear;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call player_season_data(?,?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, playerName.firstName);
		callableStatement.setString(3, playerName.lastName);
		callableStatement.setInt(4, Integer.valueOf(seasonYear));
	}

	@Override
	protected List<String> getFormattedResultStrings() throws SQLException {
		while(resultSet.next()) {
			results.add("Season Points: " + resultSet.getString(1) + "\nSeason Assists: " + resultSet.getString(2)
					+ "\nSeason Rebounds: " + resultSet.getString(3));
		}
		
		return results;
	}

	@Override
	protected String queryToString() {
		return "player_season_data(" + playerName.firstName + "," + playerName.lastName + "," + seasonYear + ")";
	}
}
