package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener{

    Button btnReturn, btnReset;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initialize();
    }

    public void initialize()
    {
        btnReset = findViewById(R.id.btnReset);
        btnReturn = findViewById(R.id.btnReturn);
        btnReset.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

    }

    public void goToMain()
    {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void resetPassword()
    {
        Toast.makeText(this, "Your email has been sent!", Toast.LENGTH_SHORT).show();

        intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btnReset:
                resetPassword();
                break;
            case R.id.btnReturn:
                goToMain();
                break;
        }

    }
}