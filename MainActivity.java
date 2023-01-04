package com.example.paintballcompanionpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password, re_password;
    Button sign_up, sign_in;
    DB_Login DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        re_password = (EditText) findViewById(R.id.repassword);

        sign_up = (Button) findViewById(R.id.sign_up);
        sign_in = (Button) findViewById(R.id.sign_in);

        DB = new DB_Login(this);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = re_password.getText().toString();

                if(user.equals("") || pass.equals("") || re_password.equals(""))
                    Toast.makeText(MainActivity.this, "Please fill in all of the fields", Toast.LENGTH_SHORT).show();
                else {
                    if(pass.equals(repass)) {
                        Boolean check_user = DB.checkUsername(user);
                        if(check_user == false) {
                            Boolean insert = DB.insertData(user, pass);
                            if(insert == true) {
                                Toast.makeText(MainActivity.this, "Registration was successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "User already exists. Please sign in.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Passwords do not match. ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}