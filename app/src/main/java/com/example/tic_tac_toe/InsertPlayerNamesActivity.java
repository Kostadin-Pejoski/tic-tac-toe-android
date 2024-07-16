package com.example.tic_tac_toe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class InsertPlayerNamesActivity extends AppCompatActivity {
    private int noPlayers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_player_names_acitivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i = getIntent();
        noPlayers = Integer.parseInt(Objects.requireNonNull(i.getStringExtra("noPlayers")));
        Log.d("NUMBEROFPLAYERS",String.valueOf(noPlayers));
        if (noPlayers==1){
            ((EditText)findViewById(R.id.player2Input)).setVisibility(View.INVISIBLE);
            ((TextView)findViewById(R.id.player2Label)).setVisibility(View.INVISIBLE);
        }
        else{
            ((EditText)findViewById(R.id.player2Input)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.player2Label)).setVisibility(View.VISIBLE);
        }
    }


    public void startBtnFunc(View v){
        EditText player1Input = findViewById(R.id.player1Input);
        EditText player2Input = findViewById(R.id.player2Input);
        if ( player1Input.getText().toString().isEmpty() || player2Input.getText().toString().isEmpty()){
            Toast.makeText(this,"player enter valid names",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i = new Intent(this,MainGameActivity.class);
            i.putExtra("player1Name",player1Input.getText().toString());
            if (noPlayers==1){
                i.putExtra("player2Name","computer");

            }
            else{
                i.putExtra("player2Name",player2Input.getText().toString());

            }
            i.putExtra("noPlayers",String.valueOf(noPlayers));
            startActivity(i);
        }
    }
}