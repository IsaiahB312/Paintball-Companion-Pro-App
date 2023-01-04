package com.example.paintballcompanionpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class setup_teams extends AppCompatActivity {

    EditText t1_player_add, t2_player_add, t1_player_del, t2_player_del;
    Button addP_t1, addP_t2, delP_t1, delP_t2, start_game;
    DB_Team1 DB1;
    DB_Team2 DB2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_teams);

        t1_player_add = findViewById(R.id.t1pa);
        t2_player_add = findViewById(R.id.t2pa);
        t1_player_del = findViewById(R.id.t1pd);
        t2_player_del = findViewById(R.id.t2pd);

        addP_t1 = findViewById(R.id.addP_t1);
        addP_t2 = findViewById(R.id.addP_t2);
        delP_t1 = findViewById(R.id.delP_t1);
        delP_t2 = findViewById(R.id.delP_t2);

        start_game = findViewById(R.id.startGame);

        DB1 = new DB_Team1(this);
        DB2 = new DB_Team2(this);

        Spinner spinner1 = (Spinner) findViewById(R.id.lives_options1);
        Spinner spinner2 = (Spinner) findViewById(R.id.lives_options2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.life_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        addP_t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = t1_player_add.getText().toString();

                if(player.equals("")) {
                    Toast.makeText(setup_teams.this, "Player name can't be blank", Toast.LENGTH_SHORT).show();
                }

                else {
                    Boolean checkPlayerInsert = DB1.insertPlayer(player);
                    if(checkPlayerInsert == true) {
                        Toast.makeText(setup_teams.this, "Player Added", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(setup_teams.this, "Can't have two players with the same name", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        addP_t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player = t2_player_add.getText().toString();

                if(player.equals("")) {
                    Toast.makeText(setup_teams.this, "Player name can't be blank", Toast.LENGTH_SHORT).show();
                }

                else {
                    Boolean checkPlayerInsert = DB2.insertPlayer(player);
                    if(checkPlayerInsert == true) {
                        Toast.makeText(setup_teams.this, "Player Added", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(setup_teams.this, "Player couldn't be added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        delP_t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = t1_player_del.getText().toString();

                Boolean delete = DB1.deleteData(name);
                if (delete == true)
                    Toast.makeText(setup_teams.this, "Player has been deleted.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(setup_teams.this, "Player does not exist.", Toast.LENGTH_SHORT).show();
            }
        });

        delP_t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String name = t2_player_del.getText().toString();

            Boolean delete = DB2.deleteData(name);
            if (delete == true)
                Toast.makeText(setup_teams.this, "Player has been deleted.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(setup_teams.this, "Player does not exist", Toast.LENGTH_SHORT).show();
            }
        });

        start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp1 = getSharedPreferences("Choices", MODE_PRIVATE);
                SharedPreferences.Editor Ed = sp1.edit();

                Ed.putString("Choice1", spinner1.getSelectedItem().toString());
                Ed.putString("Choice2", spinner2.getSelectedItem().toString());
                Ed.commit();

                Intent intent = new Intent(getApplicationContext(), GameSetup.class);
                startActivity(intent);
            }
        });
    }
}