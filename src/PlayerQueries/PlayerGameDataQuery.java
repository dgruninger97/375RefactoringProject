package PlayerQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import Domain.DatabaseConnectionService;
import Domain.PlayerName;

public class PlayerGameDataQuery extends DatabaseQuery {
	private PlayerName playerName;
	private String gameID;
	
	public PlayerGameDataQuery(DatabaseConnectionService dbService, PlayerName playerName, String gameID) {
		super(dbService);
		this.playerName = playerName;
		this.gameID = gameID;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call player_game_data(?,?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, playerName.firstName);
		callableStatement.setString(3, playerName.lastName);
		callableStatement.setInt(4, Integer.valueOf(gameID));
	}

	@Override
	protected List<String> getFormattedResultStrings(ResultSet resultSet) throws SQLException {
		List<String> results = new ArrayList<String>();
		while(resultSet.next()) {
			results.add("Game Points: " + resultSet.getString(1) + "\nGame Assists: " + resultSet.getString(2) + "\nGame Rebounds: "
					+ resultSet.getString(3));
		}
		
		return results;
	}

	@Override
	protected String queryToString() {
		return "player_game_data(" + playerName.firstName + "," + playerName.lastName + "," + gameID + ")";
	}
}
