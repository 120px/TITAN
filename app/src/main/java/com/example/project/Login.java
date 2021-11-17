package com.example.project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btnValidateLogin, btnForgotPassword;
    EditText edEmailLogin, etPassword;
    Intent intent;
    TextView tvCreateAccount;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
    }

    private void initialize() {

        btnValidateLogin = findViewById(R.id.btnValidateLogin);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);

        btnForgotPassword.setOnClickListener(this);
        btnValidateLogin.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);


        edEmailLogin = findViewById(R.id.edEmailLogin);
        etPassword = findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();

    }

    private void validateLogin() {

        String email = edEmailLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    intent = new Intent(Login.this, SelectionScreen.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Login.this, "Failed to log in. Check your inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToRegister() {
        intent = new Intent(this, RegisterAccount.class);
        startActivity(intent);
    }

    private void goToForgotPassword()
    {
        intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnValidateLogin:
                validateLogin();
                break;
            case R.id.tvCreateAccount:
                goToRegister();
                break;
            case R.id.btnForgotPassword:
                goToForgotPassword();
                break;

        }
    }
}