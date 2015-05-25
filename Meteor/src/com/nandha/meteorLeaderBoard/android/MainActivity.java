package com.nandha.meteorLeaderBoard.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.nandha.meteorLeaderBoard.R;
import com.nandha.meteorLeaderBoard.adapter.PlayerListAdapter;
import com.nandha.meteorLeaderBoard.models.Player;

public class MainActivity extends Activity implements MeteorCallback {

	private final String URL = "ws://10.0.2.2:3000/websocket";
	private Meteor meteor;

	// player list
	private List<Player> players;

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

		meteor.subscribe("players");

		playersListView.setAdapter(new PlayerListAdapter(players,
				MainActivity.this));

	}

	@Override
	public void onConnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDataAdded(String collectionName, String documentID,
			String newValuesJson) {

		try {
			Player player = new Player();
			player.jsonParser(new JSONObject(newValuesJson), documentID);
			players.add(player);
			playersListView.setAdapter(new PlayerListAdapter(players,
					MainActivity.this));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDataChanged(String collectionName, String documentID,
			String updatedValuesJson, String removedValuesJson) {
		Log.d("Collection Name", collectionName);
		Log.d("documentID", documentID);
		Log.d("updatedValuesJson", updatedValuesJson.toString());
		// Log.d("removedValuesJson", removedValuesJson.toString());
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

}
