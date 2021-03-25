package sodabase.services;

import java.sql.CallableStatement;

public class PlayerSeasonDataQuery extends DatabaseQuery {

	@Override
	protected CallableStatement prepareCallableStatement() {
		dbService.getConnection().prepareCall("{?=call player_season_data(?,?,?)}");
		registerPlayerName(firstName, lastName, callableStatement);
		callableStatement.setInt(4, Integer.valueOf(seasonYear));
	}

}
