package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseQuery {
	protected DatabaseConnectionService dbService;
	protected CallableStatement callableStatement;
	
	public DatabaseQuery(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public final ResultSet runQuery() throws SQLException {
		prepareCallableStatement();
		return callableStatement.executeQuery();
	}

	protected abstract void prepareCallableStatement() throws SQLException;
}
