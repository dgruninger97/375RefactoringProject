package TeamQueries;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import Domain.DatabaseConnectionService;

public class TeamGameDataQuery extends DatabaseQuery {
	private String teamName;
	private String year; 
	private String gameID;

	public TeamGameDataQuery(DatabaseConnectionService dbService, String teamName, String year, String gameID) {
		super(dbService);
		this.teamName = teamName;
		this.year = year;
		this.gameID = gameID;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call team_game_data(?,?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, teamName);
		callableStatement.setInt(3, Integer.valueOf(year));
		callableStatement.setInt(4, Integer.valueOf(gameID));	
	}

	@Override
	protected List<String> getFormattedResultStrings() throws SQLException {
		while(resultSet.next()) {
			results.add("Game Points For: " + resultSet.getString(1) + "\nGame Points Against: " + resultSet.getString(2));
		}
		
		return results;
	}

	@Override
	protected String queryToString() {
		return "team_game_data(" + teamName + "," + year + "," + gameID + ")";
	}

}
