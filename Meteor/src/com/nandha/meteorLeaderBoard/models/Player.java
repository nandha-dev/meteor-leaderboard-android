package com.nandha.meteorLeaderBoard.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Player {

	private String id;
	private String name;
	private int score;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	// json parser
	public void jsonParser(JSONObject playerJsonObject, String id) {
		try {
			this.setId(id);
			if (playerJsonObject.has("name")
					&& !playerJsonObject.isNull("name")) {
				this.setName(playerJsonObject.getString("name"));
			}

			if (playerJsonObject.has("score")
					&& !playerJsonObject.isNull("score")) {
				this.setScore(playerJsonObject.getInt("score"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
