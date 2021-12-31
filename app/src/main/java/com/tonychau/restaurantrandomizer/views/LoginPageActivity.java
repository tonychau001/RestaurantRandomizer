package com.tonychau.restaurantrandomizer.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tonychau.restaurantrandomizer.R;
import com.tonychau.restaurantrandomizer.repositories.BackgroundWorker;

public class LoginPageActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        edtUsername = findViewById(R.id.edtRegistarUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.edtLoginButton);
        btnLoginRegister = findViewById(R.id.btnLoginRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String type = "Login";

                BackgroundWorker backgroundWorker = new BackgroundWorker(LoginPageActivity.this);
                backgroundWorker.execute(type, username, password);
                // TODO: If signed in, go to UserActivity.
            }
        });
        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPageActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}