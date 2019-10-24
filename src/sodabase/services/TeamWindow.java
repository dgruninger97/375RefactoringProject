package sodabase.services;

import java.awt.Choice;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Font;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class TeamWindow {

	private JFrame frame;
	private String teamName;
	private boolean game;
	private boolean season;
	private boolean franchise;
	private String year;
	private Choice choice;
	private TextArea textArea;
	private DatabaseConnectionService dbService;
	private ArrayList<String> returnedList;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, DatabaseConnectionService newService) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeamWindow window = new TeamWindow();
					window.setService(newService);
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
	public TeamWindow() {
		initialize();
	}
	
	public void setService(DatabaseConnectionService newService) {
		this.dbService = newService;
	}
	
	public DatabaseConnectionService getService() {
		return this.dbService;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 585, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblTeamName = new JLabel("Team Name:");
		lblTeamName.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTeamName.setBounds(12, 42, 85, 16);
		frame.getContentPane().add(lblTeamName);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(109, 39, 193, 22);
		frame.getContentPane().add(formattedTextField);

		JButton button = new JButton("Search");
		button.setBounds(314, 38, 151, 25);
		frame.getContentPane().add(button);

		JButton button_1 = new JButton("Clear All");
		button_1.setBounds(139, 104, 97, 25);
		frame.getContentPane().add(button_1);
		
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.removeAll();
			}
		});
		
		panel = new JPanel();
		panel.setBounds(12, 144, 544, 221);
		frame.getContentPane().add(panel);
		
		JRadioButton rdbtnGame = new JRadioButton("Game");
		rdbtnGame.setBounds(18, 72, 61, 25);
		frame.getContentPane().add(rdbtnGame);
		
		JRadioButton rdbtnSeason = new JRadioButton("Season");
		rdbtnSeason.setBounds(106, 72, 71, 25);
		frame.getContentPane().add(rdbtnSeason);
		
		JRadioButton rdbtnFranchise = new JRadioButton("Franchise");
		rdbtnFranchise.setBounds(202, 72, 127, 25);
		frame.getContentPane().add(rdbtnFranchise);
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnGame);
		group.add(rdbtnSeason);
		group.add(rdbtnFranchise);
		rdbtnGame.setSelected(true);
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.removeAll();
				teamName = formattedTextField.getText();
				game = rdbtnGame.isSelected();
				season = rdbtnSeason.isSelected();
				franchise = rdbtnFranchise.isSelected();
				
				if(teamName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "You need to enter a team name");
				}
				if (game) {
					System.out.println("here");
					year = JOptionPane.showInputDialog(frame, "Enter a year");
				}
				if (game || season) {
					choice = new Choice();
					choice.setBounds(0, 30, 171, 22);
					panel.add(choice);
				}

				Label label = new Label(teamName);
				label.setAlignment(Label.CENTER);
				label.setFont(new Font("Arial", Font.BOLD, 12));
				label.setBounds(0, 0, 165, 24);
				panel.add(label);
				if (franchise) {
					textArea = new TextArea();
					textArea.setBounds(0, 30, 221, 129);
					textArea.setEditable(false);
					panel.add(textArea);
				}
				
				callTeamService();

			}

		});
	}

	protected void callTeamService() {
		// TODO Auto-generated method stub
		TeamService teamService = new TeamService(this.dbService);
		System.out.println("got to the call in team");
		returnedList = teamService.getTeamInformation(teamName, game, season, franchise, (String) year);
		if (game || season) {
			for (int i = 0; i < returnedList.size(); i++) {
				choice.add(returnedList.get(i));
			}
		} else {
			String franchiseInfo = "";
			for (int i = 0; i < returnedList.size(); i++) {
				franchiseInfo += returnedList.get(i);
			}
			textArea.setText(franchiseInfo);
		}
	}
}
