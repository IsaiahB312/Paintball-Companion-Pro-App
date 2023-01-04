package com.example.paintballcompanionpro;

import android.database.Cursor;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class GameSetup extends AppCompatActivity {

    private EditText TimeInput;
    private TextView Countdown;
    private Button setTime;
    private Button StartAndPause;
    private Button Reset;

    private CountDownTimer countdownTimer;

    private boolean isTimerRunning;

    private long startTime;
    private long timeLeft;
    private long endTime;

   DB_Team1 DB_t1;
   DB_Team2 DB_t2;

   RecyclerView team1;
   RecyclerView team2;

  ArrayList<String> players1;
  ArrayList<String> players2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);

        // Retrieve life choices from shared preferences
        SharedPreferences sp1 = this.getSharedPreferences("Choices", MODE_PRIVATE);
        String Choice1 = sp1.getString("Choice1", null);
        String Choice2 = sp1.getString("Choice2", null);

        // Team 1 Recycler View Initialization
        switch (Choice1) {
            // This will populate the recycler view with player cards containing one life
            case "1 Life":
                DB_t1 = new DB_Team1(this);
                players1 = new ArrayList<>();
                team1 = findViewById(R.id.team1);
                PlayersAdapter1 adapter1 = new PlayersAdapter1(this, players1);
                team1.setAdapter(adapter1);
                team1.setLayoutManager(new LinearLayoutManager(this));
                break;

            case "2 Lives":
                // This will populate the recycler view with player cards containing two lives
                DB_t1 = new DB_Team1(this);
                players1 = new ArrayList<>();
                team1 = findViewById(R.id.team1);
                PlayersAdapter2 adapter2 = new PlayersAdapter2(this, players1);
                team1.setAdapter(adapter2);
                team1.setLayoutManager(new LinearLayoutManager(this));
                break;

        // 3 lives doesn't work properly. It it is not being used right now
            case "3 Lives":
                DB_t1 = new DB_Team1(this);
                players1 = new ArrayList<>();
                team1 = findViewById(R.id.team1);
                PlayersAdapter3 adapter3 = new PlayersAdapter3(this, players1);
                team1.setAdapter(adapter3);
                team1.setLayoutManager(new LinearLayoutManager(this));
                break;

            case "Unlimited Lives":
                // This will populate the recycler view with player cards containing unlimited lives
                DB_t1 = new DB_Team1(this);
                players1 = new ArrayList<>();
                team1 = findViewById(R.id.team1);
                PlayersAdapter4 adapter4 = new PlayersAdapter4(this, players1);
                team1.setAdapter(adapter4);
                team1.setLayoutManager(new LinearLayoutManager(this));
                break;
        }

        // Team 2 Recycler View Initialization
        switch (Choice2) {
            // This will populate the recycler view with player cards containing one life
            case "1 Life":
                DB_t2 = new DB_Team2(this);
                players2 = new ArrayList<>();
                team2 = findViewById(R.id.team2);
                PlayersAdapter1 adapter1 = new PlayersAdapter1(this, players2);
                team2.setAdapter(adapter1);
                team2.setLayoutManager(new LinearLayoutManager(this));
                break;

            case "2 Lives":
                // This will populate the recycler view with player cards containing two lives
                DB_t2 = new DB_Team2(this);
                players2 = new ArrayList<>();
                team2 = findViewById(R.id.team2);
                PlayersAdapter2 adapter2 = new PlayersAdapter2(this, players2);
                team2.setAdapter(adapter2);
                team2.setLayoutManager(new LinearLayoutManager(this));
                break;

            // 3 lives doesn't work properly. It it is not being used right now
            case "3 Lives":
                DB_t2 = new DB_Team2(this);
                players2 = new ArrayList<>();
                team2 = findViewById(R.id.team2);
                PlayersAdapter3 adapter3 = new PlayersAdapter3(this, players2);
                team2.setAdapter(adapter3);
                team2.setLayoutManager(new LinearLayoutManager(this));
                break;

            case "Unlimited Lives":
                // This will populate the recycler view with player cards containing unlimited lives
                DB_t2 = new DB_Team2(this);
                players2 = new ArrayList<>();
                team2 = findViewById(R.id.team2);
                PlayersAdapter4 adapter4 = new PlayersAdapter4(this, players2);
                team2.setAdapter(adapter4);
                team2.setLayoutManager(new LinearLayoutManager(this));
                break;
        }

        // Display the players in the recycler views
        displayPLayers();

        TimeInput = findViewById(R.id.edit_text_input);
        Countdown = findViewById(R.id.text_view_countdown);

        setTime = findViewById(R.id.button_set);
        StartAndPause = findViewById(R.id.button_start_pause);
        Reset = findViewById(R.id.button_reset);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = TimeInput.getText().toString();
                // Will display if user enters nothing for the time
                if (input.length() == 0) {
                    Toast.makeText(GameSetup.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                // Will display if user attempts to enter a negative number
                if (millisInput == 0) {
                    Toast.makeText(GameSetup.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                TimeInput.setText("");
            }
        });

        StartAndPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pause if the timer is running
                if (isTimerRunning) {
                    pauseTimer();
                }
                // Resume if the timer isn't running
                else {
                    startTimer();
                }
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            // Reset the timer
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void displayPLayers() {
        Cursor cursor1 = DB_t1.getData();
        Cursor cursor2 = DB_t2.getData();
        if (cursor1.getCount() == 0 && cursor2.getCount() == 0) {
            // Will display if no players have been added to the team
            Toast.makeText(GameSetup.this, "No players have been added to either team", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (cursor1.getCount() > 0 && cursor2.getCount() == 0) {
            while (cursor1.moveToNext()) {
                players1.add(cursor1.getString(0));
            }
            // Will display if no players have been added to team 2
            Toast.makeText(GameSetup.this, "No players have been added to team 2", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (cursor1.getCount() == 0 && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                players2.add(cursor2.getString(0));
            }
            // Will display if no players have been added to team 1
            Toast.makeText(GameSetup.this, "No players have been added to team 1", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            while (cursor1.moveToNext()) {
                players1.add(cursor1.getString(0));
            }

            while (cursor2.moveToNext()) {
                players2.add(cursor2.getString(0));
            }
        }
    }

    // Countdown Timer methods
    private void setTime(long milliseconds) {
        startTime = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + timeLeft;

        countdownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                updateWatchInterface();
            }
        }.start();

        isTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        countdownTimer.cancel();
        isTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        timeLeft = startTime;
        updateCountDownText();
        updateWatchInterface();
    }

    // Method for formatting the clock properly given how many hours there are
    private void updateCountDownText() {
        int hours = (int) (timeLeft / 1000) / 3600;
        int minutes = (int) ((timeLeft / 1000) % 3600) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        Countdown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        // Hide the time input edit text, Reset button, and Set Time button if the timer is running
        if (isTimerRunning) {
            TimeInput.setVisibility(View.INVISIBLE);
            setTime.setVisibility(View.INVISIBLE);
            Reset.setVisibility(View.INVISIBLE);
            StartAndPause.setText("Pause");
        }
        // Ensure that the buttons are visible if the timer isn't running
        else {
            TimeInput.setVisibility(View.VISIBLE);
            setTime.setVisibility(View.VISIBLE);
            StartAndPause.setText("Start");

            if (timeLeft < 1000) {
                StartAndPause.setVisibility(View.INVISIBLE);
            }

            else {
                StartAndPause.setVisibility(View.VISIBLE);
            }

            if (timeLeft < startTime) {
                Reset.setVisibility(View.VISIBLE);
            } else {
                Reset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save the time if the activity closes
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", startTime);
        editor.putLong("millisLeft", timeLeft);
        editor.putBoolean("timerRunning", isTimerRunning);
        editor.putLong("endTime", endTime);

        editor.apply();

        if (countdownTimer != null) {
            countdownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Restore the saved time
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        startTime = prefs.getLong("startTimeInMillis", 600000);
        timeLeft = prefs.getLong("millisLeft", startTime);
        isTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (isTimerRunning) {
            endTime = prefs.getLong("endTime", 0);
            timeLeft = endTime - System.currentTimeMillis();

            if (timeLeft < 0) {
                timeLeft = 0;
                isTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }
}