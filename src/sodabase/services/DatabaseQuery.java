package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseQuery {
	protected DatabaseConnectionService dbService;
	protected CallableStatement callableStatement;
	protected List<String> results;
	protected ResultSet resultSet;
	
	public DatabaseQuery(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		this.results = new ArrayList<String>();
	}
	
	public final List<String> getResults() throws SQLException {
		resultSet = runQuery();
		results = addResultStrings();
		return results;
	}
	
	public final ResultSet runQuery() throws SQLException {
		prepareCallableStatement();
		return callableStatement.executeQuery();
	}

	protected abstract void prepareCallableStatement() throws SQLException;
	protected abstract List<String> addResultStrings() throws SQLException;
}
