package com.example.project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvTitle;
    Button btnGoToLogin, btnGoToRegister;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {

        btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnGoToRegister = findViewById(R.id.btnCreateAccount);
        btnGoToLogin.setOnClickListener(this);
        btnGoToRegister.setOnClickListener(this);
        tvTitle = findViewById(R.id.tvTitanTitle);
    }

    public void userLogin()
    {
        intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void goToCreateAccount()
    {
        intent = new Intent(this, RegisterAccount.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnGoToLogin:
                userLogin();
                break;
            case R.id.btnCreateAccount:
                goToCreateAccount();
                break;

        }

    }
}