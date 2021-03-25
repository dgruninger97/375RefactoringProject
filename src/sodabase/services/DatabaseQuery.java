package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseQuery {
	protected CallableStatement callableStatement;
	
	public final ResultSet runQuery() {
		callableStatement = prepareCallableStatement();
		ResultSet rs = callableStatement.executeQuery();
		return rs;
	}

	protected abstract CallableStatement prepareCallableStatement();
}
