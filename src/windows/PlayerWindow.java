package windows;

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
	/**
	 * Launch the application.
	 */
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
	
	private void setupRadialButtons(JButton btnSearch, JFormattedTextField formattedTextField,
			JFormattedTextField formattedTextField_1) {
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
				searchButton(formattedTextField, formattedTextField_1, rdbtnGame, rdbtnSeason, rdbtnCareer);
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
		super.setReturnedList(playerService.getPlayerInformation(firstName, lastName, super.getGameSelected(), super.getSeasonSelected(), careerIsSelected, super.getYear(), 0));
		if(checkInvalidEntry()) {
			return;
		}
		getAvailableYears();
		if (getGameSelected()) {
			retrieveGameInfo();
		}
		else if (getSeasonSelected()) {
			retrieveSeasonInfo();
		}
		else if(careerIsSelected) {
			retrieveCareerInfo();
		}
	}



	private void retrieveCareerInfo() {
		super.retrieveCareerInfo(firstName+" "+lastName);
	}

	private void retrieveSeasonInfo() {
		try {
			super.setReturnedList(playerService.getSeasonInfo(firstName, lastName, super.getSelectedIndex()));
		} catch (SQLException e) {
			// TODO Figure out the best way to respond to SQLExceptions
			e.printStackTrace();
		}
		super.displayInfo();
	}

	private void retrieveGameInfo() {
		try {
			super.setReturnedList(playerService.getGameInfo(firstName, lastName, super.getSelectedIndex()));
		} catch (SQLException e) {
			// TODO Figure out the best way to respond to SQLExceptions
			e.printStackTrace();
		}
		super.displayInfo();
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
//			displayComparisonSelectionErrors(rdbtnGame, rdbtnSeason, rdbtnCareer);
			return;
		}
		setCurrentPanel();
		retrieveSeasonInfo(formattedTextField, formattedTextField_1, rdbtnGame, rdbtnSeason, rdbtnCareer);
		if (firstName.isEmpty() || lastName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You need to enter a first and last name");
			return;
		}
		while(promptForYear()) {

		}
		if (careerIsSelected) {
			setUpCareerInfoArea();
		}
		setUpInfoPanel();
	}


	//May need to revert to commented code
	private void setUpCareerInfoArea() {
//		curPanel.repaint();
//		textArea = new TextArea();
//		textArea.setBounds(0, 30, 300, 129);
//		textArea.setEditable(false);
//		curPanel.add(textArea);
		super.displayInfo();
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

//	private void displayComparisonSelectionErrors(JRadioButton rdbtnGame, JRadioButton rdbtnSeason,
//			JRadioButton rdbtnCareer) {
//		if(buttonSelection == 1 && (rdbtnCareer.isSelected() || rdbtnSeason.isSelected())) {
//			JOptionPane.showMessageDialog(null, "You must select the same comparison option");
//		}
//		else if(buttonSelection == 2 && (rdbtnCareer.isSelected() || rdbtnGame.isSelected())) {
//			JOptionPane.showMessageDialog(null, "You must select the same comparison option");
//		}
//		else if(buttonSelection == 3 && (rdbtnSeason.isSelected() || rdbtnGame.isSelected())) {
//			JOptionPane.showMessageDialog(null, "You must select the same comparison option");
//		}
//	}

	
}
