package com.team33.qrcodepursuit;


// User issues
// View total number of QR codes scanned
// View sum of scores scanned
// View highest and lowest scoring QR codes
//
// Classes
// Scoreboard
// GameStats
//

// Game Stats responsibilities
// Summarize stats of a Game History
// Can be viewed by other players
// Stores ranking for account


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GameStats extends GameHistory {

}
