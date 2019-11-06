package sodabase.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SodaService {

	private DatabaseConnectionService dbService = null;

	public SodaService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addSoda(String sodaName, String manf) {
		CallableStatement callableStatement = null;
		try {
			callableStatement = dbService.getConnection().prepareCall("{?=call AddSoda(?, ?)}");
			callableStatement.registerOutParameter(1, Types.INTEGER);
			callableStatement.setString(2, sodaName);
			callableStatement.setString(3, manf);
			callableStatement.execute();
			if (callableStatement.getInt(1) == 1) {
				JOptionPane.showMessageDialog(null, "Soda name can't be null or empty. Please try again.");
				return false;
			}
			if (callableStatement.getInt(1) == 2) {
				JOptionPane.showMessageDialog(null, "Soda already exists. Please try again.");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<String> getSodas() throws SQLException {
		// TODO: Task 2
		ArrayList<String> sodas = new ArrayList<String>();
		// sodas.add("FirstSoda");
		// sodas.add("SecondSoda");
		// sodas.add("ThirdSoda");
		// sodas.add("LastSoda");
		// return sodas;
		Connection connection;
		Statement statement = null;
		String query = "select name from soda";
		try {
			connection = dbService.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				String coffeeName = rs.getString("name");
				sodas.add(coffeeName);
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception");
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return sodas;
	}
}
