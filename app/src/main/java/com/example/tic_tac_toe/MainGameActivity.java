package com.example.tic_tac_toe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MainGameActivity extends AppCompatActivity {

    private String currentTurn="x";
    private String[][] board = new String[3][3];
    private String player1Name,player2Name;
    private TextView displayPlayer1,displayName2;
    private HashMap<Integer,Integer> realIdToBoardIdMap = new HashMap<>();
    private int noMoves=0;
    private Button gotoMainBtn,restartGameBtn;
    private Game currentGame;
    private int noPlayers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        resetBoardWithoutView();
        setContentView(R.layout.activity_main_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i = getIntent();
        player1Name=i.getStringExtra("player1Name");
        player2Name=i.getStringExtra("player2Name");
        noPlayers=Integer.parseInt(Objects.requireNonNull(i.getStringExtra("noPlayers")));
        currentGame=new Game(player1Name,player2Name);
        displayPlayer1=findViewById(R.id.buttonsAndMisc).findViewById(R.id.playerDisplay1);
        displayName2=findViewById(R.id.buttonsAndMisc).findViewById(R.id.playerDisplay2);
        gotoMainBtn = findViewById(R.id.buttonsAndMisc).findViewById(R.id.gotoMainBtn);
        restartGameBtn = findViewById(R.id.buttonsAndMisc).findViewById(R.id.restartBoardBtn);
        displayPlayer1.setText(player1Name);
        displayName2.setText(player2Name);
        realIdToBoardIdMap.put(R.id.btn0,0);
        realIdToBoardIdMap.put(R.id.btn1,1);
        realIdToBoardIdMap.put(R.id.btn2,2);
        realIdToBoardIdMap.put(R.id.btn3,3);
        realIdToBoardIdMap.put(R.id.btn4,4);
        realIdToBoardIdMap.put(R.id.btn5,5);
        realIdToBoardIdMap.put(R.id.btn6,6);
        realIdToBoardIdMap.put(R.id.btn7,7);
        realIdToBoardIdMap.put(R.id.btn8,8);



    }
    public void boxClick(View v){


        Button viewAsBtn = (Button)v;

        if (!viewAsBtn.getText().equals("")) return;
        viewAsBtn.setText(currentTurn);
        int realId = realIdToBoardIdMap.get(v.getId());
        if (realId<3){
            board[0][realId%3]=currentTurn;
        }
        else if (realId<6){
            board[1][realId%3]=currentTurn;
        }
        else{
            board[2][realId%3]=currentTurn;
        }
        if (currentTurn.equals("x")){
            currentTurn="o";
            if (noPlayers==1){
                makeBestMove();
            }
        }
        else{
            currentTurn="x";
        }
        noMoves++;
        if (checkWin("x") || checkWin("o")){
            stopGame("win");
        }
        else if (!checkWin("x") && !checkWin("o") && noMoves==9){
            stopGame("draw");
        }
    }

    private void stopGame(String stopCondition) {
        realIdToBoardIdMap.keySet().forEach(realId->{
            Button b=findViewById(realId);
            b.setEnabled(false);
        });


        gotoMainBtn.setVisibility(View.VISIBLE);
        restartGameBtn.setVisibility(View.VISIBLE);
        if (stopCondition.equals("draw")){
            Toast.makeText(this, "its a draw", Toast.LENGTH_SHORT).show();
            currentGame.setStatus("draw");
        }
        else{
            String winingPlayer;
            if (checkWin("x")){
                winingPlayer=player1Name;
            }
            else{
                winingPlayer=player2Name;
            }
            Toast.makeText(this,winingPlayer+" won",Toast.LENGTH_LONG).show();
            currentGame.setStatus("winner:"+winingPlayer);
        }

        saveGame();

    }

    private boolean checkWin(String player) {
//        check column
        for (int i=0;i<3;i++){
            boolean flag=true;
            for(int j=0;j<3;j++){
                if (board[i][j].equals(player)){
                    continue;
                }
                else{
                    flag=false;
                    break;
                }
            }
            if (flag){
                return true;
            }
        }
        for (int i=0;i<3;i++){
            boolean flag=true;
            for(int j=0;j<3;j++){
                if (board[j][i].equals(player)){
                    continue;
                }
                else{
                    flag=false;
                    break;
                }
            }
            if (flag){
                return true;
            }
        }
//        check diagonals
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].isEmpty()){
            return true;
        }
        else if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].isEmpty()){
            return true;
        }
        return false;
    }

    private void resetBoardWithoutView(){
        currentTurn="x";
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                board[i][j]= "";
            }
        }
        noMoves=0;
        realIdToBoardIdMap.keySet().stream().map(id->(Button)findViewById(R.id.box).findViewById(id)).forEach(button -> {
            button.setText("");
            button.setEnabled(true);
        });

    }

    public void restartBoardFunc(View v){
        restartGameBtn.setVisibility(View.INVISIBLE);
        gotoMainBtn.setVisibility(View.INVISIBLE);
        resetBoardWithoutView();
    }

    public void gotoMain(View v){
        Intent i = new Intent(this, MainActivity.class);
        resetBoardWithoutView();
        startActivity(i);
    }
    private void saveGame() {
        String gameJson = Game.gameToJson(currentGame);
        if (gameJson == null) {
            return;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("GameHistory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        long uniqueKey = System.currentTimeMillis(); // Using current time as unique key
        editor.putString("game_" + uniqueKey, gameJson);
        editor.apply();
        Toast.makeText(this, "Sucessfull save of game", Toast.LENGTH_SHORT).show();
    }


    public void makeBestMove() {
        Move bestMove = findBestMove();
        int realId;
        if (bestMove.row==0){
            realId=bestMove.col;
        }
        else if (bestMove.row==1){
            realId= bestMove.col%3+3;
        }
        else{
            realId=bestMove.col%3+6;
        }
        Log.d("realid",String.valueOf(realId));
        for (Map.Entry<Integer,Integer> entry:realIdToBoardIdMap.entrySet()){
            if (entry.getValue()==realId){
                boxClick(findViewById(entry.getKey()));
                break;
            }
        }
//        board[bestMove.row][bestMove.col] = "o";
    }

    private Move findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        Move bestMove = new Move(-1, -1);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) {
                    board[i][j] = "O"; // Try this move for the AI
                    int moveVal = minimax(board, false); // Compute evaluation function for this move
                    board[i][j] = ""; // Undo the move

                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(String[][] board, boolean isMaximizing) {
        if (checkWin("o")) {
            return 10; // If AI wins
        } else if (checkWin( "x")) {
            return -10; // If player wins
        } else if (!isMovesLeft(board)) {
            return 0; // Draw
        }

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals("")) {
                        board[i][j] = "o";
                        best = Math.max(best, minimax(board, false));
                        board[i][j] = "";
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals("")) {
                        board[i][j] = "x";
                        best = Math.min(best, minimax(board, true));
                        board[i][j] = "";
                    }
                }
            }
            return best;
        }
    }

    private int evaluate(String[][] board, String player, String opponent) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(board[row][1]) && board[row][1].equals(board[row][2])) {
                if (board[row][0].equals(player)) return 10;
                if (board[row][0].equals(opponent)) return -10;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(board[1][col]) && board[1][col].equals(board[2][col])) {
                if (board[0][col].equals(player)) return 10;
                if (board[0][col].equals(opponent)) return -10;
            }
        }

        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            if (board[0][0].equals(player)) return 10;
            if (board[0][0].equals(opponent)) return -10;
        }

        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            if (board[0][2].equals(player)) return 10;
            if (board[0][2].equals(opponent)) return -10;
        }

        return 0;
    }

    private boolean isMovesLeft(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) return true;
            }
        }
        return false;
    }

    private class Move {
        int row, col;
        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
