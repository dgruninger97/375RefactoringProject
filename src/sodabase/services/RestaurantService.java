package sodabase.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class RestaurantService {

	private DatabaseConnectionService dbService = null;
	
	public RestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addResturant(String restName, String addr, String contact) {
		//TODO: Task 5
			CallableStatement callableStatement = null;
		try {
			callableStatement = dbService.getConnection().prepareCall("{?=call AddRestaurant(?, ?, ?)}");
			callableStatement.registerOutParameter(1, Types.INTEGER);
			callableStatement.setString(2, restName);
			callableStatement.setString(3, addr);
			callableStatement.setString(4, contact);
			callableStatement.execute();
			if(callableStatement.getInt(1) == 1) {
				JOptionPane.showMessageDialog(null, "Restaurant name can't be null or empty. Please try again.");
				return false;
			}
			if(callableStatement.getInt(1) == 2) {
				JOptionPane.showMessageDialog(null, "Restaurant name already exists. Please try again.");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	public ArrayList<String> getRestaurants() throws SQLException {	
		//TODO: Task 2 
		ArrayList<String> rests = new ArrayList<String>();
//		rests.add("First");
//		rests.add("Second");
//		rests.add("Third");
//		rests.add("Last");
//		return rests;
		Statement statement = null;
		String query = "select name from rest";
		Connection connection;
		try {
			connection = dbService.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				String restName = rs.getString("name");
				rests.add(restName);
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception");
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return rests;
	}
}
