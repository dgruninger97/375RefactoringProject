package DatabaseQueries;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import Domain.DatabaseConnectionService;
import Logging.DatabaseQueryLogger;
import Logging.Logger;

public abstract class DatabaseQuery {
	protected DatabaseConnectionService dbService;
	protected CallableStatement callableStatement;
	protected Logger logger;
	
	public DatabaseQuery(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		this.logger = new DatabaseQueryLogger();
	}
	
	public final List<String> getResults() throws SQLException {
		List<String> results;
		try {
			results = getFormattedResultStrings(runQuery());
			logger.log("SUCCESS: " + queryToString() + " " + Instant.now().toString());
		} catch (SQLException sqlException) {
			logger.log("ERROR: " + queryToString() + " " + Instant.now().toString());
			throw sqlException;
		}
		
		return results;
	}
	
	public final ResultSet runQuery() throws SQLException {
		prepareCallableStatement();
		return callableStatement.executeQuery();
	}

	protected abstract void prepareCallableStatement() throws SQLException;
	protected abstract List<String> getFormattedResultStrings(ResultSet resultSet) throws SQLException;
	protected abstract String queryToString();
}
