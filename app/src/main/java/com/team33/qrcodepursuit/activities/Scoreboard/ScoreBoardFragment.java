package com.team33.qrcodepursuit.activities.Scoreboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team33.qrcodepursuit.R;
import com.team33.qrcodepursuit.models.Account;
import com.team33.qrcodepursuit.models.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

class ScoreBoardAccount {
    public ScoreBoardAccount() {

    }
}

public class ScoreBoardFragment extends Fragment {

    private final String TAG = "ScoreBoardFragment";

    // Declare the variables so that you will be able to reference it later.
    Button sortByButton;
    Button regionButton;
    ListView playerList;

    FirebaseFirestore db;

    ArrayAdapter<Account> playerAdapter;
    ArrayList<Account> playerDataList;

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

        playerDataList = new ArrayList<Account>();

        //setContentView(R.layout.activity_main);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // Create a reference to the cities collection
        CollectionReference accounts = db.collection("Accounts");

        // Create a query against the collection.
        Query query = accounts.whereNotEqualTo("username", "poo");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Account acc = new Account(document.getId(), true);
                        playerDataList.add(acc);
                    }

                    Collections.sort(playerDataList, Comparator.comparing(Account::getTotalScore));
                    playerAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Error while querying for accounts");
                }
            }
        });
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