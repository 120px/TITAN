package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddExercise, btnAddSet;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        initialize();
    }

    private void initialize() {

        createNewExerciseTable();

        btnAddExercise = findViewById(R.id.btnAddExercise);
        btnAddExercise.setOnClickListener(this);

    }

    private void getExercises()
    {

    }

    private void createNewExerciseTable() {
        //get the layout
        LinearLayout layout = (LinearLayout)findViewById(R.id.layout);

        //create new Table
        TableLayout newExerciseTable = new TableLayout(this);
        newExerciseTable.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));
        newExerciseTable.setStretchAllColumns(true);

        //create rows
        TableRow trExerciseHeader = new TableRow(this);

        TableLayout.MarginLayoutParams params = (TableLayout.MarginLayoutParams)newExerciseTable.getLayoutParams();
        params.topMargin = 50;
        TableRow trInputHeaders = new TableRow(this);
        TableRow trInputs = new TableRow(this);
        TableRow trButtons = new TableRow(this);
        trInputHeaders.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        //instantiate
        TextView exerciseTitle = new TextView(this);
        EditText etWeight = new EditText(this);
        EditText etReps = new EditText(this);
        EditText etSets = new EditText(this);
        TextView tvReps = new TextView(this);
        TextView tvWeight = new TextView(this);
        TextView tvSets = new TextView(this);
        Button btnAddSet = new Button(this);

        //params
        exerciseTitle.setGravity(Gravity.CENTER);
        tvReps.setGravity(Gravity.CENTER);
        tvWeight.setGravity(Gravity.CENTER);
        tvSets.setGravity(Gravity.CENTER);
        etWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        etReps.setInputType(InputType.TYPE_CLASS_NUMBER);
        etSets.setInputType(InputType.TYPE_CLASS_NUMBER);
        btnAddSet.setText("Add Set");
        btnAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        trInputHeaders.addView(tvWeight);
        trInputHeaders.addView(tvReps);
        trInputHeaders.addView(tvSets);
        trButtons.addView(btnAddSet);
        trInputs.addView(etReps);
        trInputs.addView(etSets);
        trInputs.addView(etWeight);
        exerciseTitle.setText("EXERCISE NAME");
        tvReps.setText("Reps");
        tvWeight.setText("Weight");
        tvSets.setText("Set #");
        trExerciseHeader.addView(exerciseTitle);
        newExerciseTable.addView(trExerciseHeader);
        newExerciseTable.addView(trInputHeaders);
        newExerciseTable.addView(trInputs);
        newExerciseTable.addView(trButtons);

        layout.addView(newExerciseTable);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.btnAddExercise:
                createNewExerciseTable();
                break;
        }

    }
}
//api
//https://wger.de/en/software/api