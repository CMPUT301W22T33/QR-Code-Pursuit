package com.team33.qrcodepursuit;


//THIS IS JUST A PLACEHOLDER
public class Player {
    private String name;
    private String region;
    private Integer score;

    Player(String city, String province, Integer score){
        this.name = city;
        this.region = province;
        this.score =  score;
    }

    String getPlayerName(){
        return this.name;
    }

    String getRegion(){
        return this.region;
    }
    Integer getScore(){
        return this.score;
    }
}
