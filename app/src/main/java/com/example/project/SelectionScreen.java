package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.data.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.project.databinding.ActivitySelectionScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SelectionScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener{

    private ActivitySelectionScreenBinding binding;
    Intent intent;
    Button logout;

    private FirebaseUser user;
    private DatabaseReference dbReference;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding = ActivitySelectionScreenBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_profile,
                R.id.navigation_startWorkout, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_selection_screen);
        NavigationUI.setupWithNavController(binding.navView, navController);

        initialize();
    }

    private void initialize() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        //data = getIntent().getStringExtra("username");
        //tvDisplayUsername.setText(data);

    }

    private void sendData() {
        //https://camposha.info/android-examples/android-data-passing/

    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {

    }
}