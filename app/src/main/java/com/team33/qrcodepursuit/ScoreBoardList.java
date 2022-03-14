package com.team33.qrcodepursuit;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ScoreBoardList extends ArrayAdapter<Player> {

    private ArrayList<Player> players;
    private Context context;

    public ScoreBoardList(Context context, ArrayList<Player> players){
        super(context,0, players);
        this.players = players;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_main, parent,false); //content is a mystery for tomorrow
        }

        Player player = players.get(position);


       //these bottom 4 lines were in the lab, but idk if they are needed?

       // TextView playerName = view.findViewById(R.id.city_text);
        //TextView playerRegion = view.findViewById(R.id.province_text);

        //playerName.setText(player.getPlayerName());
        //playerRegion.setText(player.getPlayerRegion());

        return view;

    }
}
