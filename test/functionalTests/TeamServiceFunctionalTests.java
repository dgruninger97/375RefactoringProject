package functionalTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import DataParsing.DataGenerator;
import Domain.DatabaseConnectionService;
import Domain.TeamService;

class TeamServiceFunctionalTests {

	static TeamService instance;
	static DatabaseConnectionService database;
	
	@BeforeAll
	static void setup() {
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
		
		instance = new TeamService(database);
	}

	@AfterAll
	static void tearDown() {
		database.closeConnection();
	}
	
	@Test
	void TestGetTeamGamesPlayedInfo() {
		String[] teamList = DataGenerator.teamList;
		String[] yearList = DataGenerator.yearList;
		for(String teamName : teamList) {
			for(String year : yearList) {
				List<String> resultList = instance.getTeamInformation(teamName, true, false, false, year, 0);
				if(resultList == null) {
					Assertions.fail("An Error occured and no data was received");
					continue;
				}
				for(int i = 0; i < resultList.size(); i++) {
					assertTrue(resultList.get(i).contains(teamName + " vs. "));
				}
			}
		}
	}
	
	@Test
	void TestGetTeamSeasonsPlayedInfo() {
		String[] teamList = DataGenerator.teamList;
		String[] yearList = DataGenerator.yearList;
		for(String teamName : teamList) {
			List<String> resultList = instance.getTeamInformation(teamName, false, true, false, "", 0);
			if(resultList == null) {
				Assertions.fail("An Error occured and no data was received");
				continue;
			}
			for(int i = 0; i < yearList.length; i++) {
				assertTrue(resultList.get(i).startsWith("Season year: "));
				assertTrue(resultList.get(i).endsWith(yearList[i]));
			}
		}
	}
	
	@Test
	void TestGetTeamFranchiseInfo() {
		String[] teamList = DataGenerator.teamList;
		for(String teamName : teamList) {
			List<String> resultList = instance.getTeamInformation(teamName, false, false, true, "", 0);
			if(resultList == null) {
				Assertions.fail("An Error occured and no data was received");
				continue;
			}
			assertTrue(resultList.get(0).contains("Franchise Points For:"));
			assertTrue(resultList.get(0).contains("Franchise Points Against:"));
			assertTrue(resultList.get(0).contains("Win Percentage:"));
		}
	}
	
	@Test
	void TestGetTeamGameInfo() {
		String[] teamList = DataGenerator.teamList;
		String[] yearList = DataGenerator.yearList;
		for(String teamName : teamList) {
			for(String year : yearList) {
				try {
					List<String> resultListA = instance.getTeamInformation(teamName, true, false, false, year, 0);
					if(resultListA == null) {
						Assertions.fail("An Error occured and no data was received");
						continue;
					}
					for(int i = 0; i < resultListA.size(); i++ ) {
						List<String> resultListB = instance.getTeamGameInfo(teamName, year, i);
						assertTrue(resultListB.get(0).contains("Game Points For:"));
						assertTrue(resultListB.get(0).contains("Game Points Against:"));
					}
					
				} catch (SQLException e) {
				}			
			}
		}
	}
	
	@Test
	void TestGetTeamSeasonInfo() {
		String[] teamList = DataGenerator.teamList;
		for(String teamName : teamList) {
			try {
				List<String> resultListA = instance.getTeamInformation(teamName, false, true, false, "", 0);
				if(resultListA == null) {
					Assertions.fail("An Error occured and no data was received");
					continue;
				}
				for(int i = 0; i < resultListA.size(); i++ ) {
					List<String> resultListB = instance.getTeamSeasonInfo(teamName, 0);					
					assertTrue(resultListB.get(0).contains("Season Points For:"));
					assertTrue(resultListB.get(0).contains("Season Points Against:"));
					assertTrue(resultListB.get(0).contains("Win Percentage:"));
				}
			} catch (SQLException e) {
			}
		}
	}
}
