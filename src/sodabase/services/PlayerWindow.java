package sodabase.services;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JTable;

public class PlayerWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerWindow window = new PlayerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlayerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 586, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSearchForPlayer = new JLabel("First Name:");
		lblSearchForPlayer.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSearchForPlayer.setBounds(12, 33, 85, 16);
		frame.getContentPane().add(lblSearchForPlayer);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLastName.setBounds(222, 33, 85, 16);
		frame.getContentPane().add(lblLastName);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(367, 72, 151, 25);
		frame.getContentPane().add(btnSearch);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(93, 30, 117, 22);
		frame.getContentPane().add(formattedTextField);
		
		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBounds(301, 30, 177, 22);
		frame.getContentPane().add(formattedTextField_1);
		
		JCheckBox chckbxGame = new JCheckBox("Game");
		chckbxGame.setBounds(12, 72, 113, 25);
		frame.getContentPane().add(chckbxGame);
		
		JCheckBox chckbxSeason = new JCheckBox("Season");
		chckbxSeason.setBounds(129, 72, 113, 25);
		frame.getContentPane().add(chckbxSeason);
		
		JCheckBox chckbxCareer = new JCheckBox("Career");
		chckbxCareer.setBounds(246, 72, 113, 25);
		frame.getContentPane().add(chckbxCareer);
		
		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.setBounds(210, 106, 97, 25);
		frame.getContentPane().add(btnClearAll);
	}
}
