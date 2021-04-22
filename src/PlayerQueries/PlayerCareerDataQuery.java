package PlayerQueries;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import Domain.DatabaseConnectionService;

public class PlayerCareerDataQuery extends DatabaseQuery {
	private String firstName;
	private String lastName;
	
	public PlayerCareerDataQuery(DatabaseConnectionService dbService, String firstName, String lastName) {
		super(dbService);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call view_player_career(?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, firstName);
		callableStatement.setString(3, lastName);
	}

	@Override
	protected List<String> getFormattedResultStrings() throws SQLException {
		while(resultSet.next()) {
			results.add("Career Points: " + resultSet.getString(3) + "\nCareer Assists: " + resultSet.getString(4)
					+ "\nCareer Rebounds: " + resultSet.getString(5));
		}
		
		return results;
	}

	@Override
	protected String queryToString() {
		return "view_player_career(" + firstName + "," + lastName + ")";
	}
}
