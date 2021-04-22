package TeamQueries;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import services.DatabaseConnectionService;

public class TeamFranchiseDataQuery extends DatabaseQuery {
	private String teamName;

	public TeamFranchiseDataQuery(DatabaseConnectionService dbService, String teamName) {
		super(dbService);
		this.teamName = teamName;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call view_team_franchise(?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, teamName);
	}

	@Override
	protected List<String> getFormattedResultStrings() throws SQLException {
		while(resultSet.next()) {
			results.add("Franchise Points For: " + resultSet.getString(2) + "\nFranchise Points Against: " +  resultSet.getString(3) + "\nWin Percentage: " + resultSet.getString(4));
		}
		return results;
	}

	@Override
	protected String queryToString() {
		return "view_team_franchise(" + teamName + ")";
	}
}
