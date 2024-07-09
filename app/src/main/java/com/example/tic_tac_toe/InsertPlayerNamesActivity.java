package com.example.tic_tac_toe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InsertPlayerNamesActivity extends AppCompatActivity {
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
    }


    public void startBtnFunc(View v){
        EditText player1Input = findViewById(R.id.player1Input);
        EditText player2Input = findViewById(R.id.player2Input);
        if (player1Input.getText().toString().contains("example") || player2Input.getText().toString().contains("example") || player1Input.getText().toString().isEmpty() || player2Input.getText().toString().isEmpty()){
            Toast.makeText(this,"player enter valid names",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i = new Intent(this,MainGameActivity.class);
//            Log.d("player 1",player1Input.getText().toString());
            i.putExtra("player1Name",player1Input.getText().toString());
            i.putExtra("player2Name",player2Input.getText().toString());
            startActivity(i);
        }
    }
}