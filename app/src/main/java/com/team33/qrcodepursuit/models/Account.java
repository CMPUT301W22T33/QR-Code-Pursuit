package com.team33.qrcodepursuit.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Account {
    // DB fields
    protected String username;
    protected String contactinfo;
    protected String bio;
    protected ArrayList<String> OwnedQRs;
    protected ArrayList<String> ScannedQRs;
    // non-DB fields
    @Exclude protected String uid;
    @Exclude protected FirebaseFirestore db;
    @Exclude protected DocumentReference dbAccount;

    /**
     * default constructor, initialize all super fields with empty values
     * i.e. username, contactinfo, bio = ""
     * OwnedQRs, ScannedQRs = []
     */
    public Account() {
        username = "";
        contactinfo = "";
        bio = "";
        OwnedQRs = new ArrayList<>();
        ScannedQRs = new ArrayList<>();
    }
    /**
     * main constructor
     * construct instance and create ref to Firestore document
     * optionally perform sync from Firestore
     * @param uid FirebaseAuth uid (= document id in Firestore/Accounts)
     * @param sync_now update this object with Firestore data
     */
    public Account(String uid, boolean sync_now) {
        this();
        db = FirebaseFirestore.getInstance();
        dbAccount = db.collection("/Accounts").document(uid);
        if (sync_now) fromDB();
    }

    // basic getters
    public String getUsername() { return username; }
    public String getContactinfo() { return contactinfo; }
    public String getBio() { return bio; }
    public ArrayList<String> getOwnedQRs() { return OwnedQRs; }
    public ArrayList<String> getScannedQRs() { return ScannedQRs; }
    // basic setters
    public void setUsername(String username) { this.username = username; }
    public void setContactinfo(String contactinfo) { this.contactinfo = contactinfo; }
    public void setBio(String bio) { this.bio = bio; }
    public void setOwnedQRs(ArrayList<String> ownedQRs) { OwnedQRs = ownedQRs; }
    public void setScannedQRs(ArrayList<String> scannedQRs) { ScannedQRs = scannedQRs; }

    /**
     * sync DB to local
     * @return task set(super AccountPOJO)
     */
    @Exclude
    public Task<Void> toDB() {
        return dbAccount.set(this);
    }
    /**
     * sync local to DB
     */
    @Exclude
    public void fromDB() {
        // todo
        // this = (Account) dbAccount.get().getResult().toObject(AccountPOJO.class);
    }

    /**
     * get uid associated with Account
     * @return uid of FirebaseAuth user
     */
    @Exclude
    public String getUid() { return uid; }
    /**
     * set uid associated with this Account
     * @param uid uid of FirebaseAuth user
     */
    @Exclude
    public void setUid(String uid) { this.uid = uid; }

    /**
     * validate current uid
     * @return true if current uid is valid FirebaseAuth
     */
    @Exclude
    public boolean isValid() {
        // todo : validate current uid
        return true;
    }
    /**
     * get highest score...?
     */
    @Exclude
    public int getHiScore() {
        // todo : get high score
        return 0;
    }
}
