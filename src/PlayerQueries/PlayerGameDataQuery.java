package PlayerQueries;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import services.DatabaseConnectionService;

public class PlayerGameDataQuery extends DatabaseQuery {
	private String firstName;
	private String lastName;
	private String gameID;
	
	public PlayerGameDataQuery(DatabaseConnectionService dbService, String firstName, String lastName, String gameID) {
		super(dbService);
		this.firstName = firstName;
		this.lastName = lastName;
		this.gameID = gameID;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call player_game_data(?,?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, firstName);
		callableStatement.setString(3, lastName);
		callableStatement.setInt(4, Integer.valueOf(gameID));
	}

	@Override
	protected List<String> addResultStrings() throws SQLException {
		while(resultSet.next()) {
			results.add("Game Points: " + resultSet.getString(1) + "\nGame Assists: " + resultSet.getString(2) + "\nGame Rebounds: "
					+ resultSet.getString(3));
		}
		
		return results;
	}

	@Override
	protected String queryToString() {
		return null;
	}
}
