package jsonProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidateTeamTest {

	// File path of the JSON data file
	private static final String JSON_FILE_PATH = System.getProperty("user.dir") + "//jsonFiles//response.json";

	// Step 2. Write a test to validates that the team has only 4 foreign players
	@Test
	public void foreignPlayers() throws IOException, JSONException {
		String jsonDataString = readFileAsString(JSON_FILE_PATH);
		// System.out.println("String" + jsonDataString); --->I just use it to check if it's reading the data
		JSONObject jsonObject = new JSONObject(jsonDataString);
		JSONArray jsonArray = jsonObject.getJSONArray("player");
		List<String> foreignPlayers = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			String country = jsonArray.getJSONObject(i).getString("country");
			if (!country.equalsIgnoreCase("India")) {
				String name = jsonArray.getJSONObject(i).getString("name");
				foreignPlayers.add(name);
			}
		}

		Assert.assertEquals(4, foreignPlayers.size());
		System.out.println("Foreign Players: " + foreignPlayers);
	}

//Step 3. Write a test to validates that there is at least one wicket keeper
	@Test
	public void wicketKeeper() throws IOException, JSONException {
		String jsonDataString = readFileAsString(JSON_FILE_PATH);
		JSONObject jsonObject = new JSONObject(jsonDataString);
		JSONArray jsonArray = jsonObject.getJSONArray("player");
		boolean hasWicketKeeper = false;
		String wicketKeeperName = null;

		for (int i = 0; i < jsonArray.length(); i++) {
			String role = jsonArray.getJSONObject(i).getString("role");
			if (role.equalsIgnoreCase("Wicket-keeper")) {
				hasWicketKeeper = true;
				wicketKeeperName = jsonArray.getJSONObject(i).getString("name");
				break;
			}
		}

		Assert.assertTrue(hasWicketKeeper);
		System.out.println("The team has at least one wicket keeper.");
		if (wicketKeeperName != null) {
			System.out.println("Wicket Keeper: " + wicketKeeperName);
		} else {
			System.out.println("No wicket keeper found in the team.");
		}
	}

	private String readFileAsString(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}
}
