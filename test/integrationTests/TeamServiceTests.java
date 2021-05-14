package integrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domain.DatabaseConnectionService;
import Domain.TeamService;

class TeamServiceTests {

	DatabaseConnectionService mockDatabase;
	TeamService instance;
	String teamName = "TestTeamName";
	String year = "TestYear";
	
	@BeforeEach
	void setUp() {
		mockDatabase = EasyMock.createNiceMock(DatabaseConnectionService.class);
	}
	
	@Test
	void testGetPlayerInformationGame() {
		instance = EasyMock.partialMockBuilder(TeamService.class)
				.addMockedMethod("getTeamGamesPlayedInfo")
				.createMock();
		
		List<String> gameList = new ArrayList<String>();
		gameList.add("Game1");
		
		int choiceIndex = 0;
		try {
			EasyMock.expect(instance.getTeamGamesPlayedInfo(teamName, year))
			.andReturn(gameList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EasyMock.replay(instance);
		
		List<String> returnedList = instance.getTeamInformation(teamName, true, false, false, year, choiceIndex);
		
		EasyMock.verify(instance);
		assertEquals(gameList, returnedList);
	}
	
	@Test
	void testGetPlayerInformationSeason() {
		instance = EasyMock.partialMockBuilder(TeamService.class)
				.addMockedMethod("getTeamSeasonsPlayedInfo")
				.createMock();
		
		List<String> seasonList = new ArrayList<String>();
		seasonList.add("Season1");
		
		int choiceIndex = 0;
		try {
			EasyMock.expect(instance.getTeamSeasonsPlayedInfo(teamName))
			.andReturn(seasonList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EasyMock.replay(instance);
		
		List<String> returnedList = instance.getTeamInformation(teamName, false, true, false, year, choiceIndex);
		
		EasyMock.verify(instance);
		assertEquals(seasonList, returnedList);
	}
	
	@Test
	void testGetPlayerInformationCareer() {
		instance = EasyMock.partialMockBuilder(TeamService.class)
				.addMockedMethod("getTeamFranchiseInfo")
				.createMock();
		
		List<String> careerList = new ArrayList<String>();
		careerList.add("Career1");
		
		int choiceIndex = 0;
		try {
			EasyMock.expect(instance.getTeamFranchiseInfo(teamName))
			.andReturn(careerList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EasyMock.replay(instance);
		
		List<String> returnedList = instance.getTeamInformation(teamName, false, false, true, year, choiceIndex);
		
		EasyMock.verify(instance);
		assertEquals(careerList, returnedList);
	}
}
