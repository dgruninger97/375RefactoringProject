package DataParsing;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DataGenerator {
	static HashMap<String, String> playerTeamMap = new HashMap<String, String>();
	static String[] teamList = {"Boston Celtics", "Brooklyn Nets", "New York Knicks", "Philadelphia 76ers",
			"Toronto Raptors", "Golden State Warriors", "Los Angeles Clippers", "Los Angeles Lakers", "Phoenix Suns",
			"Sacramento Kings", "Chicago Bulls", "Cleveland Cavaliers", "Detroit Pistons", "Indiana Pacers", 
			"Milwaukee Bucks", "Dallas Mavericks", "Houston Rockets", "Memphis Grizzlies", "New Orleans Hornets",
			"San Antonio Spurs", "Atlanta Hawks", "Charlotte Bobcats", "Miami Heat", "Orlando Magic", 
			"Washington Wizards", "Denver Nuggets", "Minnesota Timberwolves", "Oklahoma City Thunder",
			"Portland Trail Blazers", "Utah Jazz"};
	
	static String[] yearList = {"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
			"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019"};
	
	public static void main(String[] args) throws IOException {
		loadHashMap();
		FileWriter csvWriter = new FileWriter("DataSheet4.csv");
		csvWriter.append("FName");
		csvWriter.append(",");
		csvWriter.append("LName");
		csvWriter.append(",");
		csvWriter.append("Team");
		csvWriter.append(",");
		csvWriter.append("OppTeam");
		csvWriter.append(",");
		csvWriter.append("GamePoints");
		csvWriter.append(",");
		csvWriter.append("GameAssists");
		csvWriter.append(",");
		csvWriter.append("GameRebounds");
		csvWriter.append(",");
		csvWriter.append("GmPointsFor");
		csvWriter.append(",");
		csvWriter.append("GmPointsAgainst");
		csvWriter.append(",");
		csvWriter.append("Year");
		csvWriter.append("\n");
		for (String playerName : playerTeamMap.keySet()) {
			for(int i = 0; i < 5; i++) {
				csvWriter.append(playerName.substring(0, playerName.indexOf(" ")));
				csvWriter.append(",");
				csvWriter.append(playerName.substring(playerName.indexOf(" ")).trim());
				csvWriter.append(",");
				csvWriter.append(playerTeamMap.get(playerName));
				csvWriter.append(",");
				String opposingTeam;
				while(true) {
					int n = (int) (Math.random() * 30);
					opposingTeam = teamList[n];
					if(!opposingTeam.equals(playerTeamMap.get(playerName))) {
						break;
					}
				}
				csvWriter.append(opposingTeam);
				csvWriter.append(",");
				int gamePoints = (int) (Math.random() * 20);
				csvWriter.append(gamePoints + "");
				csvWriter.append(",");
				int gameAssists = (int) (Math.random() * 15);
				csvWriter.append(gameAssists + "");
				csvWriter.append(",");
				int gameRebounds = (int) (Math.random() * 15);
				csvWriter.append(gameRebounds + "");
				csvWriter.append(",");
				int gmPointsFor = (int) (Math.random() * 60) + 50;
				csvWriter.append(gmPointsFor + "");
				csvWriter.append(",");
				int gmPointsAgainst = (int) (Math.random() * 60) + 50;
				csvWriter.append(gmPointsAgainst + "");
				csvWriter.append(",");
				int n = (int) (Math.random() * 19);
				String year = yearList[n];
				csvWriter.append(year);
				csvWriter.append(",");
				csvWriter.append("\n");
			}
		}
		csvWriter.flush();
		csvWriter.close();
	}

	private static void loadHashMap() {
		playerTeamMap.put("Kobe Bryant", "Los Angeles Lakers");
		playerTeamMap.put("Paul George", "Indiana Pacers");
		playerTeamMap.put("Steph Curry", "Golden State Warriors");
		playerTeamMap.put("Dwyane Wade", "Miami Heat");
		playerTeamMap.put("LeBron James", "Cleveland Cavaliers");
		playerTeamMap.put("Scottie Pippen", "Portland Trail Blazers");
		playerTeamMap.put("Anthony Davis", "Los Angeles Lakers");
		playerTeamMap.put("Kevin Durant", "New York Knicks");
		playerTeamMap.put("Giannis Antetokounmpo", "Milwaukee Bucks");
		playerTeamMap.put("Kawhi Leonard", "Toronto Raptors");
		playerTeamMap.put("James Harden", "Brooklyn Nets");
		playerTeamMap.put("Joel Embiid", "Philadelphia 76ers");
		playerTeamMap.put("Damian Lillard", "Portland Trail Blazers");
		playerTeamMap.put("Nikola Jokic", "Denver Nuggets");
		playerTeamMap.put("Kyrie Irving", "Brooklyn Nets");
		playerTeamMap.put("Jayson Tatum", "Boston Celtics");
		playerTeamMap.put("Donovan Mitchell", "Utah Jazz");
		playerTeamMap.put("Ben Simmons", "Philadelphia 76ers");
		playerTeamMap.put("Larry Bird", "Boston Celtics");
		playerTeamMap.put("Magic Johnson", "Los Angeles Lakers");
	}

}
