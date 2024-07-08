package com.example.tic_tac_toe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button onePlayerBtn,twoPlayerBtn,historyBtn;
    private TextView counterView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        onePlayerBtn=findViewById(R.id.startOnePlayer);
        twoPlayerBtn=findViewById(R.id.startTwoPlayer);
        historyBtn=findViewById(R.id.historyBtn);
    }

    public void startGame(View v){
        if(v.getId()==onePlayerBtn.getId()){
            Toast.makeText(this,"1 player",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"2 player",Toast.LENGTH_SHORT).show();
        }
    }

    public void showHistory(View v){
        Toast.makeText(this,"history",Toast.LENGTH_SHORT).show();
    }

}