package com.team33.qrcodepursuit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

//alot of the imports will have to go I think, but they work for now


//I dont know how this handles super big player sizes.
//we need to limit the scoreboard to top 50 only displayed probaby
public class ScoreBoardActivity extends AppCompatActivity {

    // Declare the variables so that you will be able to reference it later.
    Button sortByButton;
    Button regionButton;
    ListView playerList;


    //none of the firebase stuff works yet, need to enable it (lab5 or something has instructions)
    FirebaseFirestore db;

    ArrayAdapter<Player> playerAdapter;
    ArrayList<Player> playerDataList;

    scoreBoardList scoreBoardList;


    //this
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // Get a top level reference to the collection
        final CollectionReference collectionReference = db.collection("Accounts"); //pretty sure this is the right destination

        playerList = findViewById(R.id.player_list);
        sortByButton = findViewById(R.id.sort_by_button);
        regionButton = findViewById(R.id.region_button);

        playerDataList = new ArrayList<>();


        //clear the datalist each time
        playerDataList.clear();

        //if there are users in the database, collect them
        if (dataSnapshot.exists()){
            for (DataSnapshot Accounts:dataSnapShot.getChildren()){
                String playerName=Accounts.getValue(String.username);
                Integer playerScore= Accounts.getValue(Integer.totalScore);
                String playerRegion= Accounts.getValue(String.region);
                //the player class in the below code is a placeholder
                playerDataList.add((new Player(playerName,playerRegion,playerScore)));
            }
        }

        //this SHOULD sort them by score. Again, we need a player class or know how one works
        Collections.sort(playerDataList, Comparator.comparing(Player::getScore));

        //prints the scorboard, probably.
        playerAdapter = new scoreBoardList(this, playerDataList);
        playerList.setAdapter(playerAdapter);


    }





}
