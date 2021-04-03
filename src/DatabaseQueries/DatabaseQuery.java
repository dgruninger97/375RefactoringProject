package DatabaseQueries;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import Logging.DatabaseQueryLogger;
import Logging.Logger;
import services.DatabaseConnectionService;

public abstract class DatabaseQuery {
	protected DatabaseConnectionService dbService;
	protected CallableStatement callableStatement;
	protected List<String> results;
	protected ResultSet resultSet;
	protected Logger logger;
	
	public DatabaseQuery(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		this.results = new ArrayList<String>();
		this.logger = new DatabaseQueryLogger();
	}
	
	public final List<String> getResults() throws SQLException {
		try {
			resultSet = runQuery();
			results = addResultStrings();
			logger.log("SUCCESS: " + queryToString() + Instant.now().toString());
		} catch (SQLException sqlException) {
			logger.log("ERROR: " + queryToString() + Instant.now().toString());
			throw sqlException;
		}
		
		return results;
	}
	
	public final ResultSet runQuery() throws SQLException {
		prepareCallableStatement();
		return callableStatement.executeQuery();
	}

	protected abstract void prepareCallableStatement() throws SQLException;
	protected abstract List<String> addResultStrings() throws SQLException;
	protected abstract String queryToString();
}
