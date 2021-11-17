package com.example.project.ui.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.WorkoutActivity;
import com.example.project.databinding.FragmentStartworkoutBinding;

import java.util.Random;

public class StartWorkoutFragment extends Fragment implements View.OnClickListener {

    TextView tvPremadeHeader;
    Button btnStartWorkout;
    Intent intent;
    String[] quotes = {"\"Just do it.\"", "\"The body achieves what the mind believes.\"",
            "\"Most people fail, not because of lack of desire, but, because of lack of commitment.\""};

    TextView tvRandomQuote;
    private StartWorkoutViewModel startWorkoutViewModel;
    private FragmentStartworkoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_startworkout, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvRandomQuote = view.findViewById(R.id.tvRandomQuote);
        tvRandomQuote.setText(chooseQuote());
        
        initialize();

    }

    private void initialize() {
        tvPremadeHeader = getView().findViewById(R.id.tvPremadeHeader);


        btnStartWorkout = getView().findViewById(R.id.btnStartWorkout);
        btnStartWorkout.setOnClickListener(this);
    }

    private String chooseQuote()
    {
        int random = new Random().nextInt(quotes.length);

        return quotes[random];
    }

    private void startNewWorkout(View view)
    {
        intent = new Intent(view.getContext(), WorkoutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.btnStartWorkout:
                startNewWorkout(view);
                break;
        }

    }

}
