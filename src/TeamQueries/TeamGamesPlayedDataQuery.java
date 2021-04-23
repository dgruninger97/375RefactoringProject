package TeamQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import Domain.DatabaseConnectionService;

public class TeamGamesPlayedDataQuery extends DatabaseQuery {
	private String teamName;
	private String year; 

	public TeamGamesPlayedDataQuery(DatabaseConnectionService dbService, String teamName, String year) {
		super(dbService);
		this.teamName = teamName;
		this.year = year;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call view_team_game(?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, teamName);
		callableStatement.setInt(3, Integer.valueOf(year));
	}

	@Override
	protected List<String> getFormattedResultStrings(ResultSet resultSet) throws SQLException {
		List<String> results = new ArrayList<String>();
		while(resultSet.next()) {
			results.add(resultSet.getString(5) + ":" + resultSet.getString(1) + " vs. " + resultSet.getString(2));
		}
		
		return results;
	}

	@Override
	protected String queryToString() {
		return  "view_team_game(" + teamName + "," + year + ")";
	}
}
