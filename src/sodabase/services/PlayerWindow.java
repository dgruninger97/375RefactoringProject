package sodabase.services;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;

import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.Choice;
import java.awt.Label;
import java.awt.TextArea;

public class PlayerWindow {

	private JFrame frame;
	private DatabaseConnectionService dbservice = null;
	private String firstName;
	private String lastName;
	private boolean game;
	private boolean season;
	private boolean career;
	private Object year;
	private ArrayList<String> returnedList;
	private Choice choice;
	private TextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, DatabaseConnectionService dbService) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerWindow window = new PlayerWindow();
					window.setService(dbService);
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

	public void setService(DatabaseConnectionService dbService) {
		this.dbservice = dbService;
		System.out.println("player set " + dbservice);
	}

	public DatabaseConnectionService getService() {
		return this.dbservice;
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

		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lastName = formattedTextField_1.getText();
				firstName = formattedTextField.getText();
				game = chckbxGame.isSelected();
				season = chckbxSeason.isSelected();
				career = chckbxCareer.isSelected();
				if ((game && season) || (game && career) || (career && season)) {
					JOptionPane.showMessageDialog(null, "You can only select one checkbox");
					
				}
				if (game) {
					System.out.println("here");
					year = JOptionPane.showInputDialog(frame, "Enter a year");
				}
				if (game || season) {
					choice = new Choice();
					choice.setBounds(49, 158, 171, 22);
					frame.getContentPane().add(choice);
				}

				Label label = new Label(firstName + " " + lastName);
				label.setAlignment(Label.CENTER);
				label.setFont(new Font("Arial", Font.BOLD, 12));
				label.setBounds(39, 128, 165, 24);
				frame.getContentPane().add(label);

				if (career) {
					textArea = new TextArea();
					textArea.setBounds(12, 171, 221, 129);
					textArea.setEditable(false);
					frame.getContentPane().add(textArea);
				}
				callPlayerService();

			}
		});

		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.setBounds(210, 106, 97, 25);
		frame.getContentPane().add(btnClearAll);

	}

	protected void callPlayerService() {
		// TODO Auto-generated method stub
		System.out.println(firstName + " " + lastName + " " + game);
		PlayerService playerServe = new PlayerService(this.dbservice);
		returnedList = playerServe.getPlayerInformation(firstName, lastName, game, season, career, (String) year);
		if (game || season) {
			for (int i = 0; i < returnedList.size(); i++) {
				choice.add(returnedList.get(i));
			}
		} else {
			String careerInfo = "";
			for (int i = 0; i < returnedList.size(); i++) {
				careerInfo += returnedList.get(i);
			}
			textArea.setText(careerInfo);
		}
	}
}
