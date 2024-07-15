package com.example.tic_tac_toe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Game {
    private Date datePlayed;
    private String player1Name,player2Name;
    private String status;

    public Game(String player1Name,String player2Name){
        datePlayed=new Date();
        this.player1Name=player1Name;
        this.player2Name=player2Name;

    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatePlayed(){
        return datePlayed;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public String getStatus() {
        return status;
    }

    public static String gameToJson(Game game) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("datePlayed", game.getDatePlayed().toString());
            jsonObject.put("player1Name", game.getPlayer1Name());
            jsonObject.put("player2Name", game.getPlayer2Name());
            jsonObject.put("status", game.getStatus());
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatGameSummary(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String player1=jsonObject.getString("player1Name");
        String player2=jsonObject.getString("player2Name");
        String status=jsonObject.getString("status");
        String datePlayed=jsonObject.getString("datePlayed");

        return "The game between:"+player1+" and "+player2+"\nplayed on:"+datePlayed+"\n"+"ended in "+status+"\n";
    }




}
