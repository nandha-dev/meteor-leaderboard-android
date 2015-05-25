package com.nandha.meteorLeaderBoard.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nandha.meteorLeaderBoard.R;
import com.nandha.meteorLeaderBoard.models.Player;

public class PlayerListAdapter extends BaseAdapter {

	private List<Player> players;
	private LayoutInflater inflater;

	public PlayerListAdapter(List<Player> players, Activity activity) {
		this.players = players;
		this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return players.size();
	}

	@Override
	public Object getItem(int postion) {
		// TODO Auto-generated method stub
		return players.get(postion);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		if (view == null) {
			view = inflater
					.inflate(R.layout.player_list_item, viewGroup, false);

			// find view by id
			TextView nameTextView = (TextView) view
					.findViewById(R.id.player_item_tv_name);
			TextView scoreTextView = (TextView) view
					.findViewById(R.id.player_item_tv_score);

			// set values
			Player player = (Player) getItem(position);

			nameTextView.setText(player.getName());
			scoreTextView.setText(Integer.toString(player.getScore()));
		}
		return view;
	}

}
