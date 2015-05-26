package com.nandha.meteorLeaderBoard.android;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.nandha.meteorLeaderBoard.R;
import com.nandha.meteorLeaderBoard.adapter.PlayerListAdapter;
import com.nandha.meteorLeaderBoard.models.Player;

public class MainActivity extends Activity implements MeteorCallback {

	// Constants
	private final String URL = "ws://nandha-leaderboard.meteor.com/websocket";
	private final String COLLECTION_NAME = "players";
	private final String PUBLICATION_NAME = "players";

	// meteor object
	private static Meteor meteor;

	// player list
	private List<Player> players;
	private PlayerListAdapter listViewAdapter;

	// players List View
	private ListView playersListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// player list view
		playersListView = (ListView) findViewById(R.id.players_listView);

		// meteor initialization
		meteor = new Meteor(URL);
		meteor.setCallback(this);

		// player list
		players = new ArrayList<Player>();

		Log.d("subscribe", meteor.subscribe(PUBLICATION_NAME));

		listViewAdapter = new PlayerListAdapter(players, MainActivity.this);

		playersListView.setAdapter(listViewAdapter);

	}

	@Override
	public void onConnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDataAdded(String collectionName, String documentID,
			String newValuesJson) {

		try {
			// creating a player object and adding to players list
			Player player = new Player();
			player.jsonParser(new JSONObject(newValuesJson), documentID);
			players.add(player);
			// sorting players
			sortPlayers();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDataChanged(String collectionName, String documentID,
			String updatedValuesJson, String removedValuesJson) {

		// finding position by documentID
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getId().equals(documentID)) {
				try {
					// players values updates
					players.get(i).jsonParser(
							new JSONObject(updatedValuesJson), documentID);
					// sorting player list
					sortPlayers();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onDataRemoved(String collectionName, String documentID) {
	}

	@Override
	public void onDisconnect(int code, String reason) {
	}

	@Override
	public void onException(Exception exception) {
		// TODO Auto-generated method stub
		Log.d("exception", exception.toString());
	}

	private void sortPlayers() {
		for (int i = 0; i < players.size(); i++) {
			for (int j = (i + 1); j < players.size(); j++) {
				if (players.get(i).getScore() < players.get(j).getScore()) {
					Player player = players.get(i);
					players.set(i, players.get(j));
					players.set(j, player);
				}
			}
		}

		// updating list view
		listViewAdapter = new PlayerListAdapter(players, MainActivity.this);
		playersListView.setAdapter(listViewAdapter);
		listViewAdapter.notifyDataSetChanged();
	}

	// Update five points on button click
	public void addFivePoints(View view) {

		View parentRow = (View) view.getParent().getParent(); // button inside
																// linear layout
		ListView listView = (ListView) parentRow.getParent();
		final int position = listView.getPositionForView(parentRow);
		players.get(position).setScore(players.get(position).getScore() + 5);

		Map<String, Object> query = new HashMap<String, Object>();
		query.put("_id", players.get(position).getId());

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("name", players.get(position).getName());
		values.put("score", players.get(position).getScore());

		meteor.update(COLLECTION_NAME, query, values);

	}

}
