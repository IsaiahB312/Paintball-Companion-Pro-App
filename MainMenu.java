package com.example.paintballcompanionpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    TextView welcome;
    DB_Stats DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        SharedPreferences sp1 = this.getSharedPreferences("Login", MODE_PRIVATE);
        String User = sp1.getString("Username", null);

        welcome = (TextView) findViewById(R.id.Welcome);

        welcome.setText(welcome.getText().toString() + " " + User + "!");

        DB = new DB_Stats(this);

        findViewById(R.id.Team_Setup).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), setup_teams.class);
            startActivity(intent);
        });

//        findViewById(R.id.Elimination).setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), EliminationGameSetup.class);
//            startActivity(intent);
//        });

        findViewById(R.id.Stats).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), StatTracker.class);
            startActivity(intent);
        });
    }
}