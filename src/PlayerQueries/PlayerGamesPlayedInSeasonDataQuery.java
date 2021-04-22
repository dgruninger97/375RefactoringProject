package PlayerQueries;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import DomainObjects.PlayerName;
import services.DatabaseConnectionService;

public class PlayerGamesPlayedInSeasonDataQuery extends DatabaseQuery {
	private PlayerName playerName;
	private String seasonYear;
	
	public PlayerGamesPlayedInSeasonDataQuery(DatabaseConnectionService dbService, PlayerName playerName, String seasonYear) {
		super(dbService);
		this.playerName = playerName;
		this.seasonYear = seasonYear;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call view_player_game(?,?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, playerName.firstName);
		callableStatement.setString(3, playerName.lastName);
		callableStatement.setInt(4, Integer.valueOf(seasonYear));
	}

	@Override
	protected List<String> getFormattedResultStrings() throws SQLException {
		while(resultSet.next()) {
			results.add(resultSet.getString(5) + ": " + resultSet.getString(3) + " vs. " + resultSet.getString(4));
		}
		
		return results;
	}

	@Override
	protected String queryToString() {
		return "view_player_game(" + playerName.firstName + "," + playerName.lastName + "," + seasonYear + ")";
	}
}
