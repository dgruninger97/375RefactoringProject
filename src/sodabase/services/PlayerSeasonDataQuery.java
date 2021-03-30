package sodabase.services;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class PlayerSeasonDataQuery extends DatabaseQuery {
	private String firstName;
	private String lastName;
	private String seasonYear;
	
	public PlayerSeasonDataQuery(DatabaseConnectionService dbService, String firstName, String lastName, String seasonYear) {
		super(dbService);
		this.firstName = firstName;
		this.lastName = lastName;
		this.seasonYear = seasonYear;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call player_season_data(?,?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, firstName);
		callableStatement.setString(3, lastName);
		callableStatement.setInt(4, Integer.valueOf(seasonYear));
	}
}
