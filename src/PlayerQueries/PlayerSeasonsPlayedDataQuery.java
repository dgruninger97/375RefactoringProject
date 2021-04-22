package PlayerQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import DomainObjects.PlayerName;

import Domain.DatabaseConnectionService;

public class PlayerSeasonsPlayedDataQuery extends DatabaseQuery {
	private PlayerName playerName;
	
	public PlayerSeasonsPlayedDataQuery(DatabaseConnectionService dbService, PlayerName playerName) {
		super(dbService);
		this.playerName = playerName;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call view_player_season(?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, playerName.firstName);
		callableStatement.setString(3, playerName.lastName);
	}

	@Override
	protected List<String> getFormattedResultStrings(ResultSet resultSet) throws SQLException {
		List<String> results = new ArrayList<String>();
		while(resultSet.next()) {
			results.add("Season year: " + resultSet.getString(1));
		}
		
		return results;
	}

	@Override
	protected String queryToString() {
		return "view_player_season(" + playerName.firstName + "," + playerName.lastName + ")";
	}
}
