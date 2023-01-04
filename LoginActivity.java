package com.example.paintballcompanionpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText Username, Password;
    Button btn_signin;
    DB_Login DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = (EditText) findViewById(R.id.login_username);
        Password = (EditText) findViewById(R.id.login_password);

        btn_signin = (Button) findViewById(R.id.btn_sign_inL);

        DB = new DB_Login(this);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Username.getText().toString();
                String pass = Password.getText().toString();

                if(user.equals("") || pass.equals(""))
                    Toast.makeText(LoginActivity.this, "PLease fill in all of the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkUsernameAndPassword(user, pass);
                    if (checkuserpass == true) {
                        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putString("Username", user);
                        Ed.putString("Password", pass);
                        Ed.commit();

                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}