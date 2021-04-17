import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.List;

import org.json.JSONObject;

@RestController
public class PlayerController {
	
	@GetMapping("/playerCareer")
	public String playerCareer(@RequestParam(value = "firstName", defaultValue = "") String firstName, @RequestParam(value = "lastName", defaultValue = "") String lastName) {
		JSONObject obj = new JSONObject();
		List<String> playerCareerStats = new ArrayList<String>();
		playerCareerStats.add("Lebron James == GOAT");
		obj.append("playerStats", playerCareerStats);
		return obj.toString();
	}
	
}
