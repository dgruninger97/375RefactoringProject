package characterizationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DataParsing.DataGenerator;
import Domain.DatabaseConnectionService;
import Domain.PlayerService;

class PlayerServiceCharTests {

	PlayerService instance;
	DatabaseConnectionService database;
	
	@BeforeEach
	void setup() {
		String serverName = "";
		String username = "";
		String password = "";
		try {
			File serverFile = new File("server.txt");
			Scanner myReader;
			myReader = new Scanner(serverFile);
			serverName = myReader.nextLine();
			myReader.close();
			File userFile = new File("username.txt");
			myReader = new Scanner(userFile);
			username = myReader.nextLine();
			myReader.close();
			File passFile = new File("password.txt");
			myReader = new Scanner(passFile);
			password = myReader.nextLine();
			myReader.close();
		} catch (FileNotFoundException e) {
			Assertions.fail("Server, username or password file readers failed");
		}
		
		database = new DatabaseConnectionService(serverName, "TestDatabase");
		database.connect(username, password);
		
		/* This isn't how we're supposed to reset the database
		 * 
		RebuildDatabase.clearDatabase(database);
		String[] args = {""};
		try {
			DataGenerator.main(args);
		} catch (IOException e) {
			Assertions.fail("Datagenerator failed");
		}
		*/
		
		instance = new PlayerService(database);
	}

	@AfterEach
	void tearDown() {
		database.closeConnection();
	}
	
	@Test
	void TestGetTeamGamesPlayedInfo() {
		String[] years = DataGenerator.yearList;
		Set<String> names = DataGenerator.playerTeamMap.keySet();
		List<String> firstNames = new ArrayList<String>();
		List<String> lastNames = new ArrayList<String>();
		
		for(String name : names) {
			firstNames.add(name.substring(0, name.indexOf(' ')));
			lastNames.add(name.substring(1 + name.indexOf(' ')));
		}
		
		for(int i = 0; i < firstNames.size(); i++) {
			String firstName = firstNames.get(i), lastName = lastNames.get(i);
			for(String year : years) {
				List<String> resultList = instance.getPlayerInformation(firstName, lastName, true, false, false, year, 0);
				if(resultList != null && resultList.size() != 0) {
					System.out.println("Database didn't fail with TestGetTeamGamesPlayedInfo on " + firstName + " " + lastName);
				}					
			}
		}
	}
	
	@Test
	void TestGetTeamSeasonsPlayedInfo() {
		String[] years = DataGenerator.yearList;
		Set<String> names = DataGenerator.playerTeamMap.keySet();
		List<String> firstNames = new ArrayList<String>();
		List<String> lastNames = new ArrayList<String>();
		
		for(String name : names) {
			firstNames.add(name.substring(0, name.indexOf(' ')));
			lastNames.add(name.substring(1 + name.indexOf(' ')));
		}
		
		for(int i = 0; i < firstNames.size(); i++) {
			String firstName = firstNames.get(i), lastName = lastNames.get(i);
			for(String year : years) {
				List<String> resultList = instance.getPlayerInformation(firstName, lastName, false, true, false, year, 0);
				if(resultList != null && resultList.size() != 0) {
					System.out.println("Database didn't fail with TestGetTeamGamesPlayedInfo on " + firstName + " " + lastName);
				}					
			}
		}
	}
	
	@Test
	void TestGetTeamFranchiseInfo() {
		String[] years = DataGenerator.yearList;
		Set<String> names = DataGenerator.playerTeamMap.keySet();
		List<String> firstNames = new ArrayList<String>();
		List<String> lastNames = new ArrayList<String>();
		
		for(String name : names) {
			firstNames.add(name.substring(0, name.indexOf(' ')));
			lastNames.add(name.substring(1 + name.indexOf(' ')));
		}
		
		for(int i = 0; i < firstNames.size(); i++) {
			String firstName = firstNames.get(i), lastName = lastNames.get(i);
			for(String year : years) {
				List<String> resultList = instance.getPlayerInformation(firstName, lastName, false, false, true, year, 0);
				if(resultList != null && resultList.size() != 0) {
					System.out.println("Database didn't fail with TestGetTeamGamesPlayedInfo on " + firstName + " " + lastName);
				}					
			}
		}
	}
	
	@Test
	void TestGetGameInfo() {
		String[] years = DataGenerator.yearList;
		Set<String> names = DataGenerator.playerTeamMap.keySet();
		List<String> firstNames = new ArrayList<String>();
		List<String> lastNames = new ArrayList<String>();
		
		for(String name : names) {
			firstNames.add(name.substring(0, name.indexOf(' ')));
			lastNames.add(name.substring(1 + name.indexOf(' ')));
		}
		
		for(int i = 0; i < firstNames.size(); i++) {
			String firstName = firstNames.get(i), lastName = lastNames.get(i);
			for(String year : years) {
				try {
					List<String> resultListA = instance.getPlayerInformation(firstName, lastName, true, false, false, year, 0);
					if(resultListA != null) {
						List<String> resultListB = instance.getGameInfo(firstName, lastName, 0);
						System.out.println("Database didn't fail with getGameInfo on " + firstName + " " + lastName);
					}	
				} catch (SQLException e) {
					e.printStackTrace();
				}			
			}
		}
	}
	
	@Test
	void TestGetSeasonInfo() {
		String[] years = DataGenerator.yearList;
		Set<String> names = DataGenerator.playerTeamMap.keySet();
		List<String> firstNames = new ArrayList<String>();
		List<String> lastNames = new ArrayList<String>();
		
		for(String name : names) {
			firstNames.add(name.substring(0, name.indexOf(' ')));
			lastNames.add(name.substring(1 + name.indexOf(' ')));
		}
		
		for(int i = 0; i < firstNames.size(); i++) {
			String firstName = firstNames.get(i), lastName = lastNames.get(i);
			for(String year : years) {
				try {
					List<String> resultListA = instance.getPlayerInformation(firstName, lastName, false, true, false, year, 0);
					if(resultListA != null) {
						List<String> resultListB = instance.getSeasonInfo(firstName, lastName, 0);
						System.out.println("Database didn't fail with getSeasonInfo on " + firstName + " " + lastName);
					}	
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}
	}
	
	@Test
	void TestGetCareerInfo() {
		String[] years = DataGenerator.yearList;
		Set<String> names = DataGenerator.playerTeamMap.keySet();
		List<String> firstNames = new ArrayList<String>();
		List<String> lastNames = new ArrayList<String>();
		
		for(String name : names) {
			firstNames.add(name.substring(0, name.indexOf(' ')));
			lastNames.add(name.substring(1 + name.indexOf(' ')));
		}
		
		for(int i = 0; i < firstNames.size(); i++) {
			String firstName = firstNames.get(i), lastName = lastNames.get(i);
			for(String year : years) {
				try {
					List<String> resultListA = instance.getPlayerInformation(firstName, lastName, false, false, true, year, 0);
					if(resultListA != null) {
						List<String> resultListB = instance.getCareerInfo(firstName, lastName);
						System.out.println("Database didn't fail with getSeasonInfo on " + firstName + " " + lastName);
					}	
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}
	}

}
