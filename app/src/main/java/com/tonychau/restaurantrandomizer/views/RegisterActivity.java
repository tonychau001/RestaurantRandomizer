package com.tonychau.restaurantrandomizer.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tonychau.restaurantrandomizer.R;
import com.tonychau.restaurantrandomizer.repositories.BackgroundWorker;

public class RegisterActivity extends AppCompatActivity {

    EditText edtFirstName, edtSurname, edtAge, edtRegistarUsername, edtRegistarPassword;
    Button btnRegistar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtFirstName = findViewById(R.id.edtFirstName);
        edtSurname = findViewById(R.id.edtSurname);
        edtAge = findViewById(R.id.edtAge);
        edtRegistarUsername = findViewById(R.id.edtRegistarUsername);
        edtRegistarPassword = findViewById(R.id.edtRegistarPassword);
        btnRegistar = findViewById(R.id.btnRegistar);

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = edtFirstName.getText().toString();
                String surame = edtSurname.getText().toString();
                String age = edtAge.getText().toString();
                String username = edtRegistarUsername.getText().toString();
                String password = edtRegistarPassword.getText().toString();
                String type = "Register";

                BackgroundWorker backgroundWorker = new BackgroundWorker(RegisterActivity.this);
                backgroundWorker.execute(type, firstName, surame, age, username, password);
            }
        });

    }
}