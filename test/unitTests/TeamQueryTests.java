package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DatabaseQueries.DatabaseQuery;
import TeamQueries.*;
import services.DatabaseConnectionService;

class TeamQueryTests extends EasyMockSupport {
	
	@Mock
	DatabaseConnectionService fakeDatabase;
	@Mock
	Connection fakeConnection;
	@Mock
	CallableStatement fakeStatement;
	@Mock
	ResultSet fakeResults;
	
	String teamName;
	String seasonYear;
	String gameID;
	String result;

	
	@BeforeEach
	void Setup() {
		teamName = "The Tune Squad";
		seasonYear = "550";
		gameID = "42";
		result = "This is a test Result";

		
		fakeDatabase = createNiceMock(DatabaseConnectionService.class);
		fakeConnection = createNiceMock(Connection.class);
		fakeStatement = createNiceMock(CallableStatement.class);
		fakeResults = createNiceMock(ResultSet.class);
	}
	
	@AfterEach
	void TearDown() {
		verifyAll();
	}
	
	@Test
	void testTeamFranchiseDataQuery() {
		/*
		 * Tests : TeamFranchiseDataQuery.runQuery()
		 * 
		 * Expects that TeamFranchiseDataQuery
		 *  - Gets a connection from the database service
		 *  - Gets a callable statement from the connection
		 *  - Gives the statement its parameters
		 *  - Executes the query and saves the results
		 */
		
		TeamFranchiseDataQuery instance = new TeamFranchiseDataQuery(fakeDatabase, teamName);
		
		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			fakeStatement.setString(EasyMock.anyInt(), EasyMock.same(teamName));
			EasyMock.expectLastCall();
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		runQueryTest(instance);
	}
	
	@Test
	void testTeamGameDataQuery() {
		/* Tests : TeamGameDataQuery.runQuery()
		 * 
		 * Expects that TeamGameDataQuery
		 *  - Gets a connection from the database service
		 *  - Gets a callable statement from the connection
		 *  - Gives the statement its parameters
		 *  - Executes the query and saves the results
		 */
		
		TeamGameDataQuery instance = new TeamGameDataQuery(fakeDatabase, teamName, seasonYear, gameID);

		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			fakeStatement.setString(EasyMock.anyInt(), EasyMock.same(teamName));
			EasyMock.expectLastCall();
			fakeStatement.setInt(EasyMock.anyInt(), EasyMock.eq(Integer.valueOf(seasonYear)));
			EasyMock.expectLastCall();
			fakeStatement.setInt(EasyMock.anyInt(), EasyMock.eq(Integer.valueOf(gameID)));
			EasyMock.expectLastCall();
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		runQueryTest(instance);
	}
	
	@Test
	void testTeamGamesPlayedDataQuery() {
		/* Tests : TeamGamesPlayedDataQuery.runQuery()
		 * 
		 * Expects that TeamGamesPlayedDataQuery
		 *  - Gets a connection from the database service
		 *  - Gets a callable statement from the connection
		 *  - Gives the statement its parameters
		 *  - Executes the query and saves the results
		 */
		
		TeamGamesPlayedDataQuery instance = new TeamGamesPlayedDataQuery(fakeDatabase, teamName, seasonYear);
		
		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			fakeStatement.setString(EasyMock.anyInt(), EasyMock.same(teamName));
			EasyMock.expectLastCall();
			fakeStatement.setInt(EasyMock.anyInt(), EasyMock.eq(Integer.valueOf(seasonYear)));
			EasyMock.expectLastCall();
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		runQueryTest(instance);
	}
	
	@Test
	void testTeamSeasonDataQuery() {
		/* Tests : TeamSeasonDataQuery.runQuery()
		 * 
		 * Expects that TeamSeasonDataQuery
		 *  - Gets a connection from the database service
		 *  - Gets a callable statement from the connection
		 *  - Gives the statement its parameters
		 *  - Executes the query and saves the results
		 */
		
		TeamSeasonDataQuery instance = new TeamSeasonDataQuery(fakeDatabase, teamName, seasonYear);
		
		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			fakeStatement.setString(EasyMock.anyInt(), EasyMock.same(teamName));
			EasyMock.expectLastCall();
			fakeStatement.setString(EasyMock.anyInt(), EasyMock.same(seasonYear));
			EasyMock.expectLastCall();
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		runQueryTest(instance);
	}
	
	
	
	@Test
	void testTeamSeasonsPlayedDataQuery() {
		/* Tests : TeamSeasonsPlayedDataQuery.runQuery()
		 * 
		 * Expects that TeamSeasonsPlayedDataQuery
		 *  - Gets a connection from the database service
		 *  - Gets a callable statement from the connection
		 *  - Gives the statement its parameters
		 *  - Executes the query and saves the results
		 */
		
		TeamSeasonsPlayedDataQuery instance = new TeamSeasonsPlayedDataQuery(fakeDatabase, teamName);
		
		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			fakeStatement.setString(EasyMock.anyInt(), EasyMock.same(teamName));
			EasyMock.expectLastCall();
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		runQueryTest(instance);
	}
	
	@Test
	void testFranchiseDataGetResults() {
		DatabaseQuery instance = new TeamFranchiseDataQuery(fakeDatabase, teamName);
		setExpectationsForCheckResults(instance, result);
		checkResults(instance, result);
	}
	
	@Test
	void testGamesDataGetResults() {
		DatabaseQuery instance = new TeamGameDataQuery(fakeDatabase, teamName, seasonYear, gameID);
		setExpectationsForCheckResults(instance, result);
		checkResults(instance, result);	
	}
	
	@Test
	void testGamesPlayedDataGetResults() {
		DatabaseQuery instance = new TeamGamesPlayedDataQuery(fakeDatabase, teamName, seasonYear);
		setExpectationsForCheckResults(instance, result);
		checkResults(instance, result);	
	}
	
	@Test
	void testSeasonDataGetResults() {
		DatabaseQuery instance = new TeamSeasonDataQuery(fakeDatabase, teamName, seasonYear);
		setExpectationsForCheckResults(instance, result);
		checkResults(instance, result);	
	}
	
	@Test
	void testSeasonsPlayedDataGetResults() {
		DatabaseQuery instance = new TeamSeasonsPlayedDataQuery(fakeDatabase, teamName);
		setExpectationsForCheckResults(instance, result);
		checkResults(instance, result);
	}
	
	private void setExpectationsForCheckResults(DatabaseQuery query, String inputString) {
		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			EasyMock.expect(fakeResults.next()).andReturn(true).times(2);
			EasyMock.expect(fakeResults.getString(EasyMock.anyInt())).andReturn(result).atLeastOnce();
			EasyMock.expect(fakeResults.next()).andReturn(false).times(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void checkResults(DatabaseQuery query, String expectedValue) {
		replayAll();
		
		List<String> results = null;
		
		try {
			results = query.getResults();
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL Exception");
		}
		
		for(String s : results) {
			assertTrue(s.contains(expectedValue));
		}
	}

	private void runQueryTest(DatabaseQuery query) {
		replayAll();
		
		try {
			query.runQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
