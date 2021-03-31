package sodabase.services;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class PlayerGameDataQuery extends DatabaseQuery {
	private String firstName;
	private String lastName;
	private String seasonYear;
	
	public PlayerGameDataQuery(DatabaseConnectionService dbService, String firstName, String lastName, String seasonYear) {
		super(dbService);
		this.firstName = firstName;
		this.lastName = lastName;
		this.seasonYear = seasonYear;
	}

	@Override
	protected void prepareCallableStatement() throws SQLException {
		callableStatement = dbService.getConnection().prepareCall("{?=call player_game_data(?,?,?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, firstName);
		callableStatement.setString(3, lastName);
		callableStatement.setInt(4, Integer.valueOf(seasonYear));
	}

	@Override
	protected List<String> addResultStrings() {
		// TODO Auto-generated method stub
		return null;
	}
}
