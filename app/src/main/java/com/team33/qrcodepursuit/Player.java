package com.team33.qrcodepursuit;


//THIS IS JUST A PLACEHOLDER
public class Player {
    private String username;
    private String bio;
    private String region;

    Player(String username, String bio, String region){
        this.username = username;
        this.bio = bio;
        this.region = region;
    }

    String getUsername(){
        return this.username;
    }
    String getBio(){
        return this.bio;
    }
    String getRegion(){
        return this.region;
    }
}
