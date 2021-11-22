package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.project.data.Exercise;
import com.example.project.data.ExerciseInfo;
import com.example.project.data.User;
import com.example.project.data.UserWorkout;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Text;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class WorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ExerciseInfo> exerciseInfos;
    private ArrayList<Exercise> exercises;
    private ArrayList<UserWorkout> userWorkouts;

    String date;
    TextView tvWorkoutDate;
    TableRow rowButtons;
    Button btnAddExercise, btnFinishWorkout;
    private FirebaseUser user;
    private DatabaseReference dbReference, rootNode;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        initialize();
    }

    private void initialize() {

        //createNewExerciseTable();
        rowButtons = findViewById(R.id.rowButtons);

        tvWorkoutDate = findViewById(R.id.tvWorkoutDate);
        date = new SimpleDateFormat("MM dd, yyyy", Locale.getDefault()).format(new Date());

        tvWorkoutDate.setText(date);

        btnFinishWorkout = findViewById(R.id.btnFinishWorkout);
        btnFinishWorkout.setOnClickListener(this);

        btnAddExercise = findViewById(R.id.btnAddExercise);
        btnAddExercise.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

    }

    private void getExercises(TextView tv)
    {
        dbReference = FirebaseDatabase.getInstance().getReference().child("exercises");
        TextView tvExerciseName = tv;
        dbReference.addValueEventListener(new ValueEventListener() {
        ArrayList<String> listExercises = new ArrayList<String>();
        ArrayAdapter<String> adapter;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinearLayout layout = (LinearLayout)findViewById(R.id.layout);
                AlertDialog.Builder alertExerciseSelector = new AlertDialog.Builder(WorkoutActivity.this);
                AlertDialog alert = alertExerciseSelector.create();
                alert.setTitle("Selection");
                alert.setMessage("Please select an exercise");
                alert.setCancelable(true);

                ListView listViewExercises = new ListView(WorkoutActivity.this);
                adapter = new ArrayAdapter<String>(WorkoutActivity.this, android.R.layout.simple_list_item_1, listExercises);
                listViewExercises.setAdapter(adapter);
                listViewExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Object exrName = listViewExercises.getItemAtPosition(position);
                        tvExerciseName.setText(exrName.toString());
                        alert.cancel();
                    }
                });

                for (DataSnapshot dataSnapshots: snapshot.getChildren())
                {
                    Exercise exercise = dataSnapshots.getValue(Exercise.class);

                    int id = exercise.getId();
                    String name = exercise.getName();

                    listExercises.add(name);
                }
                alert.setView(listViewExercises);
                alert.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private TableRow createInputNewTableRow(int index)
    {
        TableRow trInputs = new TableRow(this);

        ImageButton btnDeleteSet = new ImageButton(this);
        EditText etWeight = new EditText(this);
        EditText etReps = new EditText(this);
        EditText etSets = new EditText(this);

        etWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        etWeight.setEms(3);
        etReps.setInputType(InputType.TYPE_CLASS_NUMBER);
        etReps.setEms(3);
        etSets.setInputType(InputType.TYPE_CLASS_NUMBER);
        etSets.setText(String.valueOf(index));
        btnDeleteSet.setBackgroundResource(R.drawable.ic_baseline_delete_24);
        btnDeleteSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow row = (TableRow)v.getParent();
                TableLayout tableLayout = (TableLayout) row.getParent();

                tableLayout.removeView(row);

                if (tableLayout.getChildCount() == 3)
                {
                    LinearLayout linearLayout = (LinearLayout)tableLayout.getParent();

                    linearLayout.removeView(tableLayout);
                }
            }
        });

        trInputs.addView(etWeight);
        trInputs.addView(etReps);
        trInputs.addView(etSets);
        trInputs.addView(btnDeleteSet);

        return trInputs;
    }

    private TextView createNewExerciseTable() {
        RelativeLayout.LayoutParams layoutBtn = new RelativeLayout.LayoutParams(80, 50);

        //get the layout
        LinearLayout layout = (LinearLayout)findViewById(R.id.layout);

        //create new Table
        TableLayout newExerciseTable = new TableLayout(this);
        newExerciseTable.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));
        newExerciseTable.setColumnStretchable(0, false);
//        newExerciseTable.setStretchAllColumns(true);
//        newExerciseTable.setShrinkAllColumns(true);

        //create rows
        TableRow trExerciseHeader = new TableRow(this);
        TableLayout.MarginLayoutParams params = (TableLayout.MarginLayoutParams)newExerciseTable.getLayoutParams();
        params.topMargin = 50;
        TableRow trInputHeaders = new TableRow(this);

        TableRow trButtons = new TableRow(this);
        trInputHeaders.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        //instantiate
        TextView exerciseTitle = new TextView(this);
        TextView tvReps = new TextView(this);
        TextView tvWeight = new TextView(this);
        TextView tvSets = new TextView(this);
        Button btnAddSet = new Button(this);


        //params
        exerciseTitle.setGravity(Gravity.CENTER);
        exerciseTitle.setTextSize(16);
        exerciseTitle.setTextColor(Color.BLACK);
        tvReps.setGravity(Gravity.CENTER);
        tvWeight.setGravity(Gravity.CENTER);
        tvSets.setGravity(Gravity.CENTER);
        btnAddSet.setText("Add Set");
        btnAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableLayout p = (TableLayout)v.getParent().getParent();

                p.addView(createInputNewTableRow(p.getChildCount()-2), p.getChildCount()-1);

            }
        });

        trInputHeaders.addView(tvWeight);
        trInputHeaders.addView(tvReps);
        trInputHeaders.addView(tvSets);
        trButtons.addView(btnAddSet);
        exerciseTitle.setText("EXERCISE NAME");
        tvReps.setText("Reps");
        tvWeight.setText("Weight");
        tvSets.setText("Set #");
        trExerciseHeader.addView(exerciseTitle);
        newExerciseTable.addView(trExerciseHeader);
        newExerciseTable.addView(trInputHeaders);
        newExerciseTable.addView(createInputNewTableRow(1));
        newExerciseTable.addView(trButtons);

        layout.addView(newExerciseTable);

        return exerciseTitle;
    }

    private void getLoggedInUserInfo()
    {
        dbReference = FirebaseDatabase.getInstance().getReference("User");

    }

    private void saveWorkoutToDb() {


        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        ArrayList<Exercise> exercises = new ArrayList<>();
        if (layout.getChildCount() > 3) {
            btnFinishWorkout.setText(String.valueOf(layout.getChildCount()));
            for (int i = 3; i < layout.getChildCount(); i++) {
                TableLayout tableLayout = (TableLayout) layout.getChildAt(i);


                ArrayList<ExerciseInfo> exerciseInfos = new ArrayList<>();
                String inputWeight, inputReps, inputSets;
                for (int rowData = 2; rowData < tableLayout.getChildCount() - 1; rowData++) {
                    TableRow row = (TableRow) tableLayout.getChildAt(rowData);

                    inputWeight = ((TextView)row.getChildAt(0)).getText().toString();
                    inputReps = ((TextView)row.getChildAt(1)).getText().toString();
                    inputSets = ((TextView)row.getChildAt(2)).getText().toString();

                    ExerciseInfo exerciseInfo = new ExerciseInfo(inputWeight,
                            inputReps,
                            inputSets);
                    exerciseInfos.add(exerciseInfo);

                    Exercise exercise = new Exercise(exerciseInfos, "wtv");
                    exercises.add(exercise);
                }
            }

            dbReference = FirebaseDatabase.getInstance().getReference("UserWorkout");
            DatabaseReference postsRef = dbReference.child(userId);
            DatabaseReference newPostsRef = postsRef.push();

            UserWorkout userWorkout = new UserWorkout(exercises);
            newPostsRef.setValue(userWorkout);

        }
    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.btnAddExercise:
                TextView createTables = createNewExerciseTable();
                getExercises(createTables);
                break;
            case R.id.btnFinishWorkout:
                saveWorkoutToDb();
                break;

        }

    }


}
