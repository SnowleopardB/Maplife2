package com.example.maplife2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendsAdapter extends ArrayAdapter<Friend> {
    private ArrayList<Friend> friends;

    public FriendsAdapter(Context context, int resource, ArrayList<Friend> objects) {
        super(context, resource, objects);
        this.friends = objects;
    }

    // set the covertView of the adapter to friend_grid_item and return convertView.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_grid_item, parent, false);
        }

        Friend current_friend = friends.get(position);

        TextView name = convertView.findViewById(R.id.friendName);
        name.setText(current_friend.getName());

        return convertView;
    }
}
