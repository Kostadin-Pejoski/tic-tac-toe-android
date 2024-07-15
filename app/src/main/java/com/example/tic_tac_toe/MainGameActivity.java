package com.example.tic_tac_toe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainGameActivity extends AppCompatActivity {

    private String currentTurn="x";
    private String[][] board = new String[3][3];
    private String player1Name,player2Name;
    private TextView displayPlayer1,displayName2;
    private HashMap<Integer,Integer> realIdToBoardIdMap = new HashMap<>();
    private int noMoves=0;
    private Button gotoMainBtn,restartGameBtn;
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
        if(i.getStringExtra("player2Name")!=null){
            player2Name= i.getStringExtra("player2Name");
        }
        else{
            player2Name="Computer";
        }
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
            Log.d("realId",String.valueOf(realId));
            board[1][realId%3]=currentTurn;
        }
        else{
            board[2][realId%3]=currentTurn;
        }
        if (currentTurn.equals("x")){
            currentTurn="o";
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
        }


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
}