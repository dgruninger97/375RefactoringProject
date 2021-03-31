package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;

import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.Choice;
import java.awt.Label;
import java.awt.TextArea;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import services.DatabaseConnectionService;
import services.PlayerService;

public class PlayerWindow {

	private JFrame frame;
	private DatabaseConnectionService dbservice = null;
	private String firstName;
	private String lastName;
	private boolean gameIsSelected;
	private boolean seasonIsSelected;
	private boolean careerIsSelected;
	private String year;
	private ArrayList<String> returnedList;
	private Choice choice;
	private TextArea textArea;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel curPanel;
	private int openSlot = 1;
	private JButton btnGo;
	private JButton backBtn;
	private int choiceIndex;
	private static PlayerService playerService;
	private int buttonSelection = -1;
	/**
	 * Launch the application.
	 */
	public static void startWindow(DatabaseConnectionService dbService) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerWindow window = new PlayerWindow();
					window.setService(dbService);
					window.frame.setVisible(true);
					playerService = new PlayerService(dbService);
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
	}

	public DatabaseConnectionService getService() {
		return this.dbservice;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setUpFrame();
		JFormattedTextField firstNameTextField = createFirstNameSearchBox();
		JFormattedTextField lastNameTextField = createLastNameSearchBox();
		setupNameButtons();
		JButton btnSearch = createSearchButton();
		setupPanels();
		setupRadialButtons(btnSearch, firstNameTextField, lastNameTextField);
		setupClearButton();
		setupHomeButton();
		setupAddButton();
	}

	private void setupAddButton() {
		JButton btnAddNew = new JButton("Add new player");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (openSlot >= 3) {
					JOptionPane.showMessageDialog(null, "You are at max view");
					return;
				} else {
					openSlot++;
				}
			}
		});
		btnAddNew.setBounds(497, 72, 126, 25);
		frame.getContentPane().add(btnAddNew);
	}

	private JButton setupHomeButton() {
		JButton btnHomeButton = new JButton("Home");
		btnHomeButton.setBounds(12, 4, 67, 20);
		frame.getContentPane().add(btnHomeButton);
		btnHomeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				NBADatabaseWindow newWin = new NBADatabaseWindow();
				newWin.startMainWindow(getService());
			}
		});
		return btnHomeButton;
	}

	private void setupClearButton() {
		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.setBounds(356, 72, 97, 25);
		frame.getContentPane().add(btnClearAll);
		btnClearAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearButton();
			}

		});
	}

	private void setupRadialButtons(JButton btnSearch, JFormattedTextField formattedTextField,
			JFormattedTextField formattedTextField_1) {
		JRadioButton rdbtnGame = setupGameRadialButton();
		JRadioButton rdbtnSeason = setupSeasonRadialButton();
		JRadioButton rdbtnCareer = setupCareerRadialButton();
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnGame);
		group.add(rdbtnSeason);
		group.add(rdbtnCareer);
		rdbtnGame.setSelected(true);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchButton(formattedTextField, formattedTextField_1, rdbtnGame, rdbtnSeason, rdbtnCareer);
			}
		});
	}

	private JRadioButton setupCareerRadialButton() {
		JRadioButton rdbtnCareer = new JRadioButton("Career");
		rdbtnCareer.setBounds(237, 72, 76, 25);
		frame.getContentPane().add(rdbtnCareer);
		return rdbtnCareer;
	}

	private JRadioButton setupSeasonRadialButton() {
		JRadioButton rdbtnSeason = new JRadioButton("Season");
		rdbtnSeason.setBounds(117, 72, 71, 25);
		frame.getContentPane().add(rdbtnSeason);
		return rdbtnSeason;
	}

	private JRadioButton setupGameRadialButton() {
		JRadioButton rdbtnGame = new JRadioButton("Game");
		rdbtnGame.setBounds(18, 72, 61, 25);
		frame.getContentPane().add(rdbtnGame);
		return rdbtnGame;
	}

	private JButton createSearchButton() {
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(573, 29, 97, 25);
		frame.getContentPane().add(btnSearch);
		return btnSearch;
	}

	private JFormattedTextField createLastNameSearchBox() {
		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBounds(371, 30, 185, 22);
		frame.getContentPane().add(formattedTextField_1);
		return formattedTextField_1;
	}

	private JFormattedTextField createFirstNameSearchBox() {
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(97, 30, 155, 22);
		frame.getContentPane().add(formattedTextField);
		return formattedTextField;
	}

	private void setupPanels() {
		panel_1 = new JPanel();
		panel_1.setBounds(5, 110, 400, 328);
		frame.getContentPane().add(panel_1);

		panel_2 = new JPanel();
		panel_2.setBounds(405, 110, 400, 328);
		frame.getContentPane().add(panel_2);

		panel_3 = new JPanel();
		panel_3.setBounds(810, 110, 400, 328);
		frame.getContentPane().add(panel_3);
	}

	private void setupNameButtons() {
		JLabel lblSearchForPlayer = new JLabel("First Name:");
		lblSearchForPlayer.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSearchForPlayer.setBounds(12, 33, 85, 16);
		frame.getContentPane().add(lblSearchForPlayer);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLastName.setBounds(283, 33, 85, 16);
		frame.getContentPane().add(lblLastName);
	}

	private void setUpFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1225, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	protected void callPlayerService(int methodType) {
		if (methodType == 1) {
			returnedList = playerService.getPlayerInformation(firstName, lastName, gameIsSelected, seasonIsSelected, careerIsSelected, (String) year, 0);
			if(checkInvalidTeam()) {
				return;
			}
			if (gameIsSelected || seasonIsSelected) {
				getAvailableYears();
				return;
			}
		} else if (methodType == 2) {
			retrieveGameInfo();
		} else if (methodType == 3) {
			retrieveSeasonInfo();
		}
		retrieveCareerInfo();
	}

	private boolean checkInvalidTeam() {
		if (returnedList == null) {
			JOptionPane.showMessageDialog(null, "Invalid Entry, try again.");
			curPanel.removeAll();
			return true;
		}
		return false;
	}

	private void getAvailableYears() {
		for (int i = 0; i < returnedList.size(); i++) {
			choice.add(returnedList.get(i));
		}
		choiceIndex = choice.getSelectedIndex();
	}

	private void retrieveCareerInfo() {
		String careerInfo = "";
		for (int i = 0; i < returnedList.size(); i++) {
			careerInfo += returnedList.get(i);
		}
		textArea.setText(careerInfo);
		Label label = new Label(firstName + " " + lastName);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(0, 0, 165, 24);
		curPanel.add(label);
	}

	private void retrieveSeasonInfo() {
		returnedList = playerService.getSeasonInfo(firstName, lastName, choiceIndex);
		curPanel.removeAll();
		textArea = new TextArea();
		textArea.setBounds(0, 30, 310, 129);
		textArea.setEditable(false);
		curPanel.add(textArea);
	}

	private void retrieveGameInfo() {
		returnedList = playerService.getGameInfo(firstName, lastName, choiceIndex);
		curPanel.removeAll();
		textArea = new TextArea();
		textArea.setBounds(0, 30, 310, 129);
		textArea.setEditable(false);
		curPanel.add(textArea);
	}
	
	private void backButton() {
		curPanel.removeAll();
		curPanel.add(btnGo);
		curPanel.add(choice);
		Label label = new Label(firstName + " " + lastName);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(0, 0, 165, 24);
		curPanel.add(label);
		curPanel.repaint();
	}
	private void goButton() {
		choiceIndex = choice.getSelectedIndex();
		if(gameIsSelected) callPlayerService(2);
		else if(seasonIsSelected) callPlayerService(3);
		backBtn = new JButton("Back");
		backBtn.setBounds(310, 30, 80, 20);
		curPanel.add(backBtn);
		curPanel.repaint();
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backButton();
			}
		});
	}
	
	private void searchButton(JFormattedTextField formattedTextField, JFormattedTextField formattedTextField_1,
			JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnCareer) {
		if(buttonSelection == -1) {
			getSelectedButton(rdbtnGame, rdbtnSeason, rdbtnCareer);
		}else{
			displayComparisonSelectionErrors(rdbtnGame, rdbtnSeason, rdbtnCareer);
			return;
		}
		setCurPanel();
		if(curPanel == null) {
			JOptionPane.showMessageDialog(null, "You are at max view");
			return;
		}
		retrieveSeasonInfo(formattedTextField, formattedTextField_1, rdbtnGame, rdbtnSeason, rdbtnCareer);
		if (firstName.isEmpty() || lastName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You need to enter a first and last name");
			return;
		}
		if (gameIsSelected) {
			year = JOptionPane.showInputDialog(frame, "Enter a year (2000 - 2019)");
			if(checkYearInput()) {
				return;
			}
		}
		setUpSearchSelection();
	}

	private void setUpSearchSelection() {
		if (gameIsSelected || seasonIsSelected) {
			setUpYearSelection();
		}
		if (careerIsSelected) {
			setUpCareerInfoArea();
		}
		setUpInfoPanel();
	}

	private void setUpCareerInfoArea() {
		curPanel.repaint();
		textArea = new TextArea();
		textArea.setBounds(0, 30, 300, 129);
		textArea.setEditable(false);
		curPanel.add(textArea);
	}

	private void setUpYearSelection() {
		choice = new Choice();
		choice.setBounds(0, 30, 300, 22);
		btnGo = new JButton("Go");
		btnGo.setBounds(310, 30, 50, 20);
		
		btnGo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goButton();
			}
		});
		curPanel.add(choice);
		curPanel.add(btnGo);
		curPanel.repaint();
	}

	private void setUpInfoPanel() {
		Label label = new Label(firstName + " " + lastName);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(0, 0, 165, 24);
		curPanel.add(label);
		callPlayerService(1);
	}

	private boolean checkYearInput() {
		if (year == null || year.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You didn't enter a valid year");
			return true;
		}
		for (int i = 0; i < year.length(); i++) {
			if (!Character.isDigit(year.charAt(i))) {
				JOptionPane.showMessageDialog(null, "You didn't enter a valid year");
				return true;
			}
		}
		if (Integer.parseInt(year) < 2000 || Integer.parseInt(year) > 2019) {
			JOptionPane.showMessageDialog(null, "You didn't enter a valid year");
			return true;
		}
		return false;
	}

	private void retrieveSeasonInfo(JFormattedTextField formattedTextField, JFormattedTextField formattedTextField_1,
			JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnCareer) {
		curPanel.removeAll();
		lastName = formattedTextField_1.getText();
		firstName = formattedTextField.getText();
		gameIsSelected = rdbtnGame.isSelected();
		seasonIsSelected = rdbtnSeason.isSelected();
		careerIsSelected = rdbtnCareer.isSelected();
	}

	private void setCurPanel() {
		if (openSlot == 1)
			curPanel = panel_1;
		else if (openSlot == 2)
			curPanel = panel_2;
		else if (openSlot == 3)
			curPanel = panel_3;
		else {
			curPanel = null;
		}
	}

	private void displayComparisonSelectionErrors(JRadioButton rdbtnGame, JRadioButton rdbtnSeason,
			JRadioButton rdbtnCareer) {
		if(buttonSelection == 1 && (rdbtnCareer.isSelected() || rdbtnSeason.isSelected())) {
			JOptionPane.showMessageDialog(null, "You must select the same comparison option");
		}
		else if(buttonSelection == 2 && (rdbtnCareer.isSelected() || rdbtnGame.isSelected())) {
			JOptionPane.showMessageDialog(null, "You must select the same comparison option");
		}
		else if(buttonSelection == 3 && (rdbtnSeason.isSelected() || rdbtnGame.isSelected())) {
			JOptionPane.showMessageDialog(null, "You must select the same comparison option");
		}
	}

	private void getSelectedButton(JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnCareer) {
		if(rdbtnGame.isSelected()){
			buttonSelection = 1;
		}
		if(rdbtnSeason.isSelected()) {
			buttonSelection = 2;
		}
		if(rdbtnCareer.isSelected()) {
			buttonSelection = 3;
		}
	}
	
	private void clearButton() {
		panel_1.removeAll();
		panel_2.removeAll();
		panel_3.removeAll();
		panel_1.repaint();
		panel_2.repaint();
		panel_3.repaint();
		openSlot = 1;
		buttonSelection = -1;
	}
}
