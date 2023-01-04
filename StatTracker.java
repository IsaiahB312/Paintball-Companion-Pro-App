package com.example.paintballcompanionpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class StatTracker extends AppCompatActivity {

    TextView Eliminations, Headshots, Flag_Captures, Deaths, Ratio;
    DecimalFormat df = new DecimalFormat("0.00");
    Button goUpdate, mainMenu;
    DB_Stats DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_tracker);
        // Retrieve the Username from shared preferences
        SharedPreferences sp1 = this.getSharedPreferences("Login", MODE_PRIVATE);
        String User = sp1.getString("Username", null);

        Eliminations = (TextView) findViewById(R.id.elimsNum);
        Headshots = (TextView) findViewById(R.id.headshotCount);
        Flag_Captures = (TextView) findViewById(R.id.timesCFcount);
        Deaths = (TextView) findViewById(R.id.timesEliminatedCount);
        Ratio = (TextView) findViewById(R.id.ratioNumber);

        goUpdate = (Button) findViewById(R.id.goUpdate);
        mainMenu = (Button) findViewById(R.id.mainMenu);

        DB = new DB_Stats(this);

        Cursor result = DB.getData(User);


        // Insert 0 in each stat if the user is new
        if(result.getCount() == 0) {
            Boolean insert = DB.insertStats(User, 0, 0, 0, 0);
        }

        else {
            // Set each stat in the activity to their respective value in the database
            while (result.moveToNext()) {

                Eliminations.setText(result.getString(1));


                Headshots.setText(result.getString(2));


                Flag_Captures.setText(result.getString(3));


                Deaths.setText(result.getString(4));
            }
        }

        // Getting both of the numbers used to calculate the E/D ratio
        int elims = Integer.parseInt(Eliminations.getText().toString());
        int deaths = Integer.parseInt(Deaths.getText().toString());


        // Set deaths to 1 if its value is zero as we can't divide by zero
        if (deaths == 0) {
            deaths = 1;
        }

        // Calculate the ratio and then set it to the edit text
        double ED = (double) elims / deaths;
        Ratio.setText(df.format(ED));

        goUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateStats.class);
                startActivity(intent);
            }
        });

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
            }
        });
    }
}