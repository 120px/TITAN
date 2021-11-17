package com.example.project.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.RegisterAccount;
import com.example.project.SelectionScreen;
import com.example.project.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileEditInfo extends AppCompatActivity implements View.OnClickListener {

    EditText etEditEmail, etEditFirstName, etEditLastName, etEditPassword;
    Button btnSaveProfileChanges, btnCancelProfileChanges;

    private FirebaseUser user;
    private DatabaseReference dbReference;
    private String userId;

    Intent intent;
    String dbEmail, dbFirstName, dbLastName, dbUsername, dbPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_info);

        initialize();
        populateEditData();
    }

    private void initialize() {

        etEditEmail = findViewById(R.id.etEditEmail);
        etEditFirstName = findViewById(R.id.etEditFirstName);
        etEditLastName = findViewById(R.id.etEditLastName);
        etEditPassword = findViewById(R.id.etEditPassword);

        btnSaveProfileChanges = findViewById(R.id.btnSaveProfileChanges);
        btnSaveProfileChanges.setOnClickListener(this);
        btnCancelProfileChanges = findViewById(R.id.btnCancelProfileChanges);
        btnCancelProfileChanges.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
    }

    private void populateEditData() {

        dbReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfileInfo = snapshot.getValue(User.class);
                if (userProfileInfo != null)
                {
                    String email = userProfileInfo.email;
                    String firstname = userProfileInfo.firstName;
                    String lastname = userProfileInfo.lastName;

                    etEditEmail.setText(email);
                    etEditFirstName.setText(firstname);
                    etEditLastName.setText(lastname);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void saveChanges()
    {
//        dbReference.child(userId).child("email").setValue(etEditEmail.getText().toString().trim());
//        dbReference.child(userId).child("firstName").setValue(etEditFirstName.getText().toString().trim());
//        dbReference.child(userId).child("lastName").setValue(etEditLastName.getText().toString().trim());
//        dbReference.child(userId).child("password").setValue(etEditPassword.getText().toString().trim());

       AlertDialog.Builder alert =  new AlertDialog.Builder(this);
       alert.setTitle("Confirmation");
       alert.setMessage("Please enter your current password to confirm the changes");
       alert.setCancelable(true);

       EditText input = new EditText(this);
       alert.setView(input);

       alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               if(input.equals(dbReference.child(userId).child("password").toString()))
               {
                   dialog.cancel();
                   Toast.makeText(ProfileEditInfo.this, "Information changed", Toast.LENGTH_LONG).show();
                   intent = new Intent(ProfileEditInfo.this, SelectionScreen.class);
                   startActivity(intent);
               }
               else
               {
                   Toast.makeText(ProfileEditInfo.this, "Incorrect password", Toast.LENGTH_LONG).show();
               }
           }
       });

       alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
               Toast.makeText(ProfileEditInfo.this, "Information not changed", Toast.LENGTH_SHORT).show();
           }
       });

       AlertDialog alertShow = alert.create();
        alertShow.show();

    }

    private void saveProfileChangesToDb() {

        //first check if info was changed
        saveChanges();
        isEmailChanged();
//        isFirstNameChanged();
//        isLastNameChanged();
//        isPasswordChanged();

    }

    private void isEmailChanged() {

        //if(!dbReference.child(userId).child("email").equals())

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSaveProfileChanges:
                saveChanges();
                break;
        }
    }
}