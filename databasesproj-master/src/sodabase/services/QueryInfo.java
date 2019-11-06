package sodabase.services;

public class QueryInfo {

	private String firstName;
	private String lastName;
	private boolean game;
	private boolean season;
	private boolean career;
	
	public QueryInfo(String fname, String lname, boolean g, boolean s, boolean c) {
		this.firstName = fname;
		this.lastName = lname;
		this.game = g;
		this.season = s;
		this.career = c;
	}
	
	public String getFName() {
		return this.firstName;
	}
	
	public String getLName() {
		return this.lastName;
	}
	
	public boolean getGame() {
		return this.game;
	}
	
	public boolean getSeason() {
		return this.season;
	}
	
	public boolean getCareer() {
		return this.career;
	}
}
