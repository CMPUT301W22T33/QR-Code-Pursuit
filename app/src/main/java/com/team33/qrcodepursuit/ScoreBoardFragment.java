package com.team33.qrcodepursuit;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreBoardFragment extends Fragment {

    // Declare the variables so that you will be able to reference it later.
    Button sortByButton;
    Button regionButton;
    ListView playerList;

    FirebaseFirestore db;

    ArrayAdapter<Player> playerAdapter;
    ArrayList<Player> playerDataList;

    //ScoreBoardList scoreBoardList;

    public ScoreBoardFragment() {
        // Required empty public constructor
    }

    public static ScoreBoardFragment newInstance() {
        ScoreBoardFragment fragment = new ScoreBoardFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // Get a top level reference to the collection
        final CollectionReference collectionReference = db.collection("Accounts");

        playerDataList = new ArrayList<>();

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                // Clear the old list
                playerDataList.clear();

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    Map<String, Object> data = doc.getData();
                    String username = (String) data.get("username");
                    String bio = (String) data.get("bio");
                    String region = (String) data.get("region");
                    playerDataList.add(new Player(username, bio, region)); // Adding the players from FireStore
                }

                playerAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }

        });

        //this should sort them by score. Again, we need a player class or know how one works
        //Collections.sort(playerDataList, Comparator.comparing(Player::getScore));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_score_board, container, false);

        playerList = rootView.findViewById(R.id.player_list);
        sortByButton = rootView.findViewById(R.id.sort_by_button);
        regionButton = rootView.findViewById(R.id.region_button);

        // display the scoreboard
        playerAdapter = new ScoreBoardList(this.getActivity(), playerDataList);
        playerList.setAdapter(playerAdapter);

        return rootView;
    }
}