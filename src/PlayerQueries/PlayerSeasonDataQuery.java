package PlayerQueries;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import DatabaseQueries.DatabaseQuery;
import services.DatabaseConnectionService;

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

	@Override
	protected List<String> addResultStrings() throws SQLException {
		while(resultSet.next()) {
			results.add("Season Points: " + resultSet.getString(1) + "\nSeason Assists: " + resultSet.getString(2)
					+ "\nSeason Rebounds: " + resultSet.getString(3));
		}
		
		return results;
	}

	@Override
	protected String queryToString() {
		return null;
	}
}
