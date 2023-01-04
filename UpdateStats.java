package com.example.paintballcompanionpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateStats extends AppCompatActivity {

    EditText Eliminations, Headshots, Flag_Captures, Deaths;
    Button update_btn;
    DB_Stats DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_stats);
        // Get the Username from the shared preferences
        SharedPreferences sp1 = this.getSharedPreferences("Login", MODE_PRIVATE);
        String User = sp1.getString("Username", null);

        Eliminations = (EditText) findViewById(R.id.inputElims);
        Headshots = (EditText) findViewById(R.id.inputHeadshots);
        Flag_Captures = (EditText) findViewById(R.id.inputFC);
        Deaths = (EditText) findViewById(R.id.inputDeaths);

        update_btn = (Button) findViewById(R.id.updateStats);

        DB = new DB_Stats(this);

        Cursor result = DB.getData(User);
        if (result.getCount() == 0) {
            Toast.makeText(UpdateStats.this, "No data is in the table.", Toast.LENGTH_SHORT).show();
            return;
        }

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = User;

                // Update each stat individually in case users don't update every single one

                if (!(Eliminations.getText().toString().equals(""))) {
                    String elims = Eliminations.getText().toString();
                    int Eliminations = Integer.parseInt(elims);
                    DB.updateEliminations(user, Eliminations);
                }

                if(!(Headshots.getText().toString().equals(""))) {
                    String headsh = Headshots.getText().toString();
                    int Headshots = Integer.parseInt(headsh);
                    DB.updateHeadshots(user, Headshots);
                }

                if(!(Flag_Captures.getText().toString().equals(""))) {
                    String fc = Flag_Captures.getText().toString();
                    int Flag_Captures = Integer.parseInt(fc);
                    DB.updateFlag_Captures(user, Flag_Captures);
                }

                if(!(Deaths.getText().toString().equals(""))) {
                    String deaths = Deaths.getText().toString();
                    int Deaths = Integer.parseInt(deaths);
                    DB.updateDeaths(user, Deaths);
                }

                // Go to the Stat tracker activity after pressing the button to update
                Intent intent = new Intent(getApplicationContext(), StatTracker.class);
                startActivity(intent);
            }
        });
    }
}