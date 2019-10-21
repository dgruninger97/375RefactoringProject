package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PlayerService {

	private DatabaseConnectionService dbService = null;

	public PlayerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public ArrayList<String> getPlayerInformation(String firstName, String lastName, boolean game, boolean season,
			boolean career) {
		try {
			CallableStatement callableStatement = null;
			if ((game && season) || (game && career) || (career && season)) {
				JOptionPane.showMessageDialog(null, "You can only select one checkbox");
				return null;
			}
			if (game) {
				JFrame frame = new JFrame();
				Object year = JOptionPane.showInputDialog(frame, "Enter a year");
				callableStatement = dbService.getConnection().prepareCall("{?=call view_player_game(?,?,?)}");
				callableStatement.registerOutParameter(1, Types.INTEGER);
				callableStatement.setString(2, firstName);
				callableStatement.setString(3, lastName);
				callableStatement.setInt(4, Integer.valueOf((String) year));
				ResultSet rs = callableStatement.executeQuery();
				ArrayList<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add(rs.getString(3) + " vs. " + rs.getString(4));
				}
				System.out.println(list);
				return list;
			} else if (season) {

			} else if (career) {

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

}
