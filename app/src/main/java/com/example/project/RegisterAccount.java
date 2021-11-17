package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.project.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterAccount extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    ImageButton imgBtnBack;
    Button btnSubmit;
    Intent intent;
    EditText edFirstName, edLastName, edEmailAddress, edUsername, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        mAuth = FirebaseAuth.getInstance();
        initialize();

    }

    private void initialize() {

        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edEmailAddress = findViewById(R.id.edEmailAddress);
        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnBack.setOnClickListener(this);



    }

    public void goToLogin()
    {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imgBtnBack:
                intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
            case R.id.btnSubmit:
                registerUser();
                break;

        }
    }

    private void registerUser() {

        String firstName = edFirstName.getText().toString().trim();
        String lastName = edLastName.getText().toString().trim();
        String email = edEmailAddress.getText().toString().trim();
        String username = edUsername.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if (firstName.isEmpty())
        {
            edFirstName.setError("First name is required!");
            edFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty())
        {
            edLastName.setError("Last name is required!");
            edLastName.requestFocus();
            return;
        }

        if (username.isEmpty())
        {
            edUsername.setError("Last name is required!");
            edUsername.requestFocus();
            return;
        }

        if (email.isEmpty())
        {
            edEmailAddress.setError("Email is required!");
            edEmailAddress.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edEmailAddress.setError("Provide a valid email!");
            edEmailAddress.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            edPassword.setError("A password is required!");
            edPassword.requestFocus();
            return;
        }

        if (password.length() < 6)
        {
            edPassword.setError("Your password must be longer than 6 characters!");
            edPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            User user = new User(firstName, lastName, email, password, username);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterAccount.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(RegisterAccount.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(RegisterAccount.this, "Registration ds!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

}