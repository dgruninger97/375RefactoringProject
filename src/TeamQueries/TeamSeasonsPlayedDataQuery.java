package TeamQueries;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import services.DatabaseConnectionService;

public class TeamSeasonsPlayedDataQuery extends DatabaseQuery {
	private String teamName;

	public TeamSeasonsPlayedDataQuery(DatabaseConnectionService dbService, String teamName) {
		super(dbService);
		this.teamName = teamName;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call view_team_season(?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, teamName);
	}

	@Override
	protected List<String> addResultStrings() throws SQLException {
		while(resultSet.next()) {
			results.add("Season year: " + resultSet.getString(1));
		}
		return results;
	}

	@Override
	protected String queryToString() {
		return "view_team_season(" + teamName + ")";
	}
}
