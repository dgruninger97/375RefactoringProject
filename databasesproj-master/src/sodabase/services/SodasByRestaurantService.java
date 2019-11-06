package sodabase.services;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sodabase.ui.SodaByRestaurant;

public class SodasByRestaurantService {

	private DatabaseConnectionService dbService = null;

	public SodasByRestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addSodaByRestaurant(String rest, String soda, double price) {
		CallableStatement callableStatement = null;
	try {
		callableStatement = dbService.getConnection().prepareCall("{?=call AddSells(?, ?, ?)}");
		callableStatement.registerOutParameter(1, Types.INTEGER);
		callableStatement.setString(2, soda);
		callableStatement.setString(3, rest);
		callableStatement.setDouble(4, price);
		callableStatement.execute();
		if(callableStatement.getInt(1) == 1) {
			JOptionPane.showMessageDialog(null, "Soda name can't be null or empty. Please try again.");
			return false;
		}
		if(callableStatement.getInt(1) == 2) {
			JOptionPane.showMessageDialog(null, "Restaurant name can't be null or empty. Please try again.");
			return false;
		}
		if(callableStatement.getInt(1) == 3) {
			JOptionPane.showMessageDialog(null, "Price can't be null. Please try again.");
			return false;
		}
		if(callableStatement.getInt(1) == 4) {
			JOptionPane.showMessageDialog(null, "Soda name doesn't exist. Please try again.");
			return false;
		}
		if(callableStatement.getInt(1) == 5) {
			JOptionPane.showMessageDialog(null, "Restaurant name doesn't exists. Please try again.");
			return false;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	return true;
	}

	public ArrayList<SodaByRestaurant> getSodasByRestaurants(String rest, String soda, String price,
			boolean useGreaterThanEqual) {
		// TODO: Task 3 and Task 4
		Double priceDoub = null;
		String newQuery;
		if(price != null && !price.equals("")) {
			priceDoub = Double.parseDouble(price);
		}
		if(rest != null && rest.equals("")) {
			rest = null;
		}
		if(soda != null && soda.equals("")) {
			soda = null;
		}
		newQuery = buildParameterizedSqlStatementString(rest, soda, priceDoub, useGreaterThanEqual);
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			PreparedStatement ps = null;
			ps = this.dbService.getConnection().prepareStatement(newQuery);
			int index = 1;
			if(rest != null) {
				ps.setString(index, rest);
				index++;
			}
			if(soda != null) {
				ps.setString(index, soda);
				index++;
			}
			if (price != null && price.length() > 0) {
				ps.setDouble(index, priceDoub);
			}
			ResultSet rs = ps.executeQuery();
			ArrayList<SodaByRestaurant> list = new ArrayList<SodaByRestaurant>();
			while (rs.next()) {
				SodaByRestaurant newSoda = new SodaByRestaurant(rs.getString("Restaurant"), rs.getString("Soda"),
						rs.getString("Manufacturer"), rs.getString("RestaurantContact"), rs.getDouble("price"));
				list.add(newSoda);
			}
			return list;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve sodas by restaurant.");
			ex.printStackTrace();
			return new ArrayList<SodaByRestaurant>();
		}

	}

	/**
	 * Creates a string containing ? in the correct places in the SQL statement
	 * based on the filter information provided.
	 * 
	 * @param rest
	 *            - The restaurant to match
	 * @param soda
	 *            - The soda to match
	 * @param price
	 *            - The price to compare to
	 * @param useGreaterThanEqual
	 *            - If true, the prices returned should be greater than or equal to
	 *            what's given, otherwise less than or equal.
	 * @return A string representing the SQL statement to be executed
	 */
	private String buildParameterizedSqlStatementString(String rest, String soda, Double price,
			boolean useGreaterThanEqual) {
		String sqlStatement = "SELECT Restaurant, Soda, Manufacturer, RestaurantContact, Price \nFROM SodasByRestaurant\n";
		ArrayList<String> wheresToAdd = new ArrayList<String>();

		if (rest != null) {
			wheresToAdd.add("Restaurant = ?");
		}
		if (soda != null) {
			wheresToAdd.add("Soda = ?");
		}
		if (price != null) {
			if (useGreaterThanEqual) {
				wheresToAdd.add("Price >= ?");
			} else {
				wheresToAdd.add("Price <= ?");
			}
		}
		boolean isFirst = true;
		while (wheresToAdd.size() > 0) {
			if (isFirst) {
				sqlStatement = sqlStatement + " WHERE " + wheresToAdd.remove(0);
				isFirst = false;
			} else {
				sqlStatement = sqlStatement + " AND " + wheresToAdd.remove(0);
			}
		}
		return sqlStatement;
	}

	private ArrayList<SodaByRestaurant> parseResults(ResultSet rs) {
		try {
			ArrayList<SodaByRestaurant> sodasByRestaurants = new ArrayList<SodaByRestaurant>();
			int restNameIndex = rs.findColumn("Restaurant");
			int sodaNameIndex = rs.findColumn("Soda");
			int manfIndex = rs.findColumn("Manufacturer");
			int restContactIndex = rs.findColumn("RestaurantContact");
			int priceIndex = rs.findColumn("Price");
			while (rs.next()) {
				sodasByRestaurants.add(new SodaByRestaurant(rs.getString(restNameIndex), rs.getString(sodaNameIndex),
						rs.getString(manfIndex), rs.getString(restContactIndex), rs.getDouble(priceIndex)));
			}
			System.out.println(sodasByRestaurants.size());
			return sodasByRestaurants;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas by restaurants. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<SodaByRestaurant>();
		}

	}

}
