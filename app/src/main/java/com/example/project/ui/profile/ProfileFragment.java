package com.example.project.ui.profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.MainActivity;
import com.example.project.R;
//import com.example.project.databinding.FragmentProfileBinding;
import com.example.project.data.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    TextView tvDisplayUsername;
    Button btnLogout, btnEditProfile;
    Intent intent;
    String data;

    private FirebaseUser user;
    private DatabaseReference dbReference;
    private String userId;

        @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("username", intent.getStringExtra("username"));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();

        createCompletedWorkouts();
        createKMWalked();
        displayProfileInfo();

        
    }

    private void initialize() {
        tvDisplayUsername = getView().findViewById(R.id.tvDisplayUsername);

        btnEditProfile = getView().findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(this);
        btnLogout = getView().findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

    }

    private void displayProfileInfo()
    {
        dbReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfileInfo = snapshot.getValue(User.class);
                if (userProfileInfo != null)
                {
                    String username = userProfileInfo.username;

                    tvDisplayUsername.setText(username);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createCompletedWorkouts()
    {
        BarChart barChart = (BarChart)getView().findViewById(R.id.completedWorkoutsBarchart);

        ArrayList<BarEntry> daysCompleted = new ArrayList<BarEntry>();
        daysCompleted.add(new BarEntry(0, 4));
        daysCompleted.add(new BarEntry(1, 7));
        daysCompleted.add(new BarEntry(2, 3));
        daysCompleted.add(new BarEntry(3, 4));
        daysCompleted.add(new BarEntry(4, 4));

        BarDataSet barDataSet = new BarDataSet(daysCompleted, "Days Completed");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
    }

    public void createKMWalked()
    {
        LineChart lineChart = (LineChart) getView().findViewById(R.id.KMWalkedLineChart);

        ArrayList<Entry> distanceWalked = new ArrayList<Entry>();
        distanceWalked.add(new Entry(0, 2));
        distanceWalked.add(new Entry(1, 3));
        distanceWalked.add(new Entry(2, 6));
        distanceWalked.add(new Entry(3, 4));

        LineDataSet lineDataSet = new LineDataSet(distanceWalked, "Distance Walked");
        lineDataSet.setCircleColors((ColorTemplate.rgb("#00000")));
        lineDataSet.setLineWidth(1);



        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

    }

    public void userLogout(View view)
    {
        intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);

    }

    private void goToEditProfile(View view) {

        intent = new Intent(view.getContext(), ProfileEditInfo.class);
        startActivity(intent);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

        private ProfileViewModel profileViewModel;

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.btnLogout:
                userLogout(view);
                break;
            case R.id.btnEditProfile:
                goToEditProfile(view);
                break;
        }
    }
}