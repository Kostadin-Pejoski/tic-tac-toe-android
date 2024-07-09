package com.example.tic_tac_toe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MainGameActivity extends AppCompatActivity {

    private String currentTurn="x";
    private String[][] board = new String[3][3];
    private String player1Name,player2Name;
    private TextView displayPlayer1,displayName2;
    private HashMap<Integer,Integer> realIdToBoardIdMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                board[i][j]= "";
            }
        }
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
        displayPlayer1.setText(player1Name);
        displayName2.setText(player2Name);
        Log.d("idbtntest",String.valueOf(R.id.btn0));
        realIdToBoardIdMap.put(R.id.btn1,1);
        realIdToBoardIdMap.put(R.id.btn2,2);
        realIdToBoardIdMap.put(R.id.btn3,3);
        realIdToBoardIdMap.put(R.id.btn4,4);
        realIdToBoardIdMap.put(R.id.btn5,5);
        realIdToBoardIdMap.put(R.id.btn6,6);
        realIdToBoardIdMap.put(R.id.btn7,7);
        realIdToBoardIdMap.put(R.id.btn8,8);



    }

    private String getBoardAsString(){
        String res="";
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                res+=board[i][j]+",";
            }
            res+="\n";
        }
        return res;
    }

    public void boxClick(View v){
        Button viewAsBtn = (Button)v;
        viewAsBtn.setText(currentTurn);
        int realId = realIdToBoardIdMap.get(v.getId());
        for(int i=0;i<9;i++){
            if (realId==i){

                break;
            }
        }
        if (currentTurn.equals("x")){
            currentTurn="o";
        }
        else{
            currentTurn="x";
        }

    }
}