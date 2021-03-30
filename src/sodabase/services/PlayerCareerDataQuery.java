package sodabase.services;

import java.sql.SQLException;
import java.sql.Types;

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
}
