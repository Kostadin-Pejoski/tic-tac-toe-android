package com.example.tic_tac_toe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        View main = findViewById(R.id.main);
        LinearLayout layout=main.findViewById(R.id.mainLayout);
        SharedPreferences sharedPreferences = getSharedPreferences("GameHistory", Context.MODE_PRIVATE);
        sharedPreferences.getAll().values().stream().map(val->(String)val).filter(str->str.contains("status")).forEach(filtered->{
            TextView tv = new TextView(this);
            try {
                String text= Game.formatGameSummary(filtered);
                tv.setText(text);
                layout.addView(tv);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
}