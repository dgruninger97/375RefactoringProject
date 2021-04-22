package presentationWindows;

import java.awt.EventQueue;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import java.awt.Label;
import javax.swing.JRadioButton;
import services.DatabaseConnectionService;
import services.PlayerService;

public class PlayerWindow extends AbstractWindow{
	private String firstName;
	private String lastName;
	private boolean careerIsSelected;
	private static PlayerService playerService;
	private int buttonSelection = -1;

	public void startWindow(DatabaseConnectionService dbService) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerWindow window = new PlayerWindow();
					window.initializeServiceAndFrame(dbService, window);
					playerService = new PlayerService(dbService);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PlayerWindow() {
		initialize();
	}


	private void initialize() {
		setUpFrame();
		JFormattedTextField firstNameTextField = createFirstNameSearchBox();
		JFormattedTextField lastNameTextField = createLastNameSearchBox();
		setupNameButtons();
		JButton btnSearch = createSearchButton();
		setupPanels();
		setupRadialButtons(btnSearch, firstNameTextField, lastNameTextField);
		setUpClearButton();
		setUpHomeButton();
		setupAddButton();
	}

	private void setupAddButton() {
		super.setupAddButton("Add New Player");
	}
	
	private void setupRadialButtons(JButton btnSearch, JFormattedTextField formattedFirstName,
			JFormattedTextField formattedLastName) {
		JRadioButton rdbtnGame = setUpGameRadialButton();
		JRadioButton rdbtnSeason = setUpSeasonRadialButton();
		JRadioButton rdbtnCareer = setUpOverallRadialButton();
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnGame);
		group.add(rdbtnSeason);
		group.add(rdbtnCareer);
		rdbtnGame.setSelected(true);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchButton(formattedFirstName, formattedLastName, rdbtnGame, rdbtnSeason, rdbtnCareer);
			}
		});
	}

	
	private JFormattedTextField createLastNameSearchBox() {
		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBounds(371, 30, 185, 22);
		super.getFrame().getContentPane().add(formattedTextField_1);
		return formattedTextField_1;
	}

	private JFormattedTextField createFirstNameSearchBox() {
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(97, 30, 155, 22);
		super.getFrame().getContentPane().add(formattedTextField);
		return formattedTextField;
	}


	private void setupNameButtons() {
		JLabel lblSearchForPlayer = new JLabel("First Name:");
		lblSearchForPlayer.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSearchForPlayer.setBounds(12, 33, 85, 16);
		super.getFrame().getContentPane().add(lblSearchForPlayer);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLastName.setBounds(283, 33, 85, 16);
		super.getFrame().getContentPane().add(lblLastName);
	}

	
	@Override
	protected void callService(int methodType) {
		if (methodType == 1) {
			
			super.setReturnedList(playerService.getPlayerInformation(firstName, lastName, super.getGameSelected(), super.getSeasonSelected(), careerIsSelected, super.getYear(), 0));
			if(checkInvalidEntry()) {
				return;
			}
			getAvailableYears();
			if (careerIsSelected) {
				super.displayInfo(firstName + " " + lastName);
			}
			return;
		}
		else if (methodType == 2) {
			retrieveGameInfo();
		}
		else if (methodType == 3) {
			retrieveSeasonInfo();
		}
		else {
			retrieveOverallInfo();
		}
		super.displayInfo(firstName + " " + lastName);
	}



	private void retrieveOverallInfo() {
		try {
			super.setReturnedList(playerService.getCareerInfo(firstName, lastName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void retrieveSeasonInfo() {
		try {
			super.setReturnedList(playerService.getSeasonInfo(firstName, lastName, super.getSelectedIndex()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.displayInfo(firstName + " " + lastName);
	}

	private void retrieveGameInfo() {
		try {
			super.setReturnedList(playerService.getGameInfo(firstName, lastName, super.getSelectedIndex()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.displayInfo(firstName + " " + lastName);
	}
	@Override
	protected void backButton() {
		super.backButton(firstName+" "+lastName);
	}
	
	private void searchButton(JFormattedTextField formattedTextField, JFormattedTextField formattedTextField_1,
			JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnCareer) {
		if(buttonSelection == -1) {
			getSelectedButton(rdbtnGame, rdbtnSeason, rdbtnCareer);
		}else{
			return;
		}
		setCurrentPanelToOpenSlot();
		retrieveSeasonInfo(formattedTextField, formattedTextField_1, rdbtnGame, rdbtnSeason, rdbtnCareer);
		if (firstName.isEmpty() || lastName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You need to enter a first and last name");
			return;
		}
		while(promptForYear()) {

		}
		setUpInfoPanel();
	}

	private void setUpInfoPanel() {
		Label label = new Label(firstName + " " + lastName);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(0, 0, 165, 24);
		super.addLabelToPanel(label);
		callService(1);
	}


	private void retrieveSeasonInfo(JFormattedTextField formattedTextField, JFormattedTextField formattedTextField_1,
			JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnCareer) {
		super.removeAllFromPanel();
		lastName = formattedTextField_1.getText();
		firstName = formattedTextField.getText();
		getGameAndSeasonSelections(rdbtnGame, rdbtnSeason);
		careerIsSelected = rdbtnCareer.isSelected();
	}

}
