package com.example.fithub_mobile.routine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.CycleData;
import com.example.fithub_mobile.CycleDisplay;
import com.example.fithub_mobile.ExecutionActivity;
import com.example.fithub_mobile.ExerciseQueueRealState;
import com.example.fithub_mobile.MainActivity;
import com.example.fithub_mobile.NotificationActivity;
import com.example.fithub_mobile.QrGenActivity;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullCycle;
import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.Review;
import com.example.fithub_mobile.excercise.ExerciseData;
import com.example.fithub_mobile.excercise.LastlyExecutedCard;
import com.example.fithub_mobile.excercise.LastlyExecutedCardData;
import com.example.fithub_mobile.excercise.LastlyExecutedCardDataManager;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoutineActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.fithub_mobile.EXTRA_ID";
    static final private String ID_PARENT_EXTRA = "com.example.fithub_mobile.ID_PARENT";
    private FullRoutine routine;
    private List<FullCycle> cycles;
    private int id;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        context = this;


        Intent intent = getIntent();
        id = Integer.parseInt(intent.getData().getQueryParameter("id"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        RatingBar ratingBar = findViewById(R.id.rating_bar_routine_view);
        TextView descView = findViewById(R.id.desc_routine);
        TextView titleView = findViewById(R.id.title_routine);

        App app = (App)getApplication();
        app.getRoutineRepository().getRoutine(id).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                routine = r.getData();
                ratingBar.setRating(routine.getAverageRating());
                descView.setText(routine.getDetail());
                if(toolBarLayout != null)
                    toolBarLayout.setTitle(routine.getName());
                else
                    titleView.setText(routine.getName());
            } else {
                Resource.defaultResourceHandler(r);
            }
        });

        LinearLayout cycleContainer = findViewById(R.id.cycle_container);

        app.getCycleRepository().getCycles(id).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                cycles = r.getData().getContent();
                for (FullCycle cycle : cycles) {
                    cycleContainer.addView(new CycleDisplay(this,cycle));
                }
            } else {
                Resource.defaultResourceHandler(r);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startExecution());

        ImageButton qrBtn = findViewById(R.id.qr_btn);
        qrBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, QrGenActivity.class);
            i.putExtra(EXTRA_ID,id);
            startActivity(i);
        });

        ImageButton calendarBtn = findViewById(R.id.calendar_btn);
        calendarBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, NotificationActivity.class);
            i.putExtra(ID_PARENT_EXTRA,id);
            startActivity(i);

        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setIsIndicator(false);
                if (fromUser) {
                    app.getReviewRepository().addReview(id, new Review((int)rating,"")).observe((LifecycleOwner) context, r -> {
                        if (r.getStatus() == Status.SUCCESS) {
                            Toast.makeText(context,getText(R.string.rated),Toast.LENGTH_SHORT).show();
                            ratingBar.setIsIndicator(true);

                        }else {
                            Resource.defaultResourceHandler(r);
                        }
                    });
                }
            }
        });
    }

    public void startExecution(){

        App app = (App)getApplication();
        app.getCycleRepository().getCycles(id).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                assert r.getData() != null;
                cycles = r.getData().getContent();

                ExerciseQueueRealState exerciseQueueRealState = ExerciseQueueRealState.getInstance();
                exerciseQueueRealState.setNewRoutine(new ArrayList<>());

                for (FullCycle cycle : cycles) {
                    app.getCycleRepository().getCycleExercises(cycle.getId()).observe(this, rEx -> {
                        if (rEx.getStatus() == Status.SUCCESS) {
                            assert rEx.getData() != null;
                            exerciseQueueRealState.addCycle();
                            List<FullCycleExercise> cycleExercises = rEx.getData().getContent();
                            for (FullCycleExercise ex : cycleExercises) {
                                ex.setCycle(cycle);
                                app.getExerciseImageRepository().getExerciseImages(ex.getExercise().getId()).observe(this, rImg -> {
                                    if (rImg.getStatus() == Status.SUCCESS) {
                                        assert rImg.getData() != null;
                                        ex.setImg(rImg.getData().getContent().get(0).getUrl());
                                    } else {
                                        Resource.defaultResourceHandler(rImg);
                                    }
                                });
                            }
                            for (int i = 0; i < cycle.getRepetitions(); i ++)
                                exerciseQueueRealState.getExercises().addAll(cycleExercises);

                            if (exerciseQueueRealState.getCycleCount() == cycles.size()){
                                Collections.sort(exerciseQueueRealState.getExercises());
                                Intent i = new Intent(this, ExecutionActivity.class);
                                startActivity(i);
                            }

                        } else {
                            Resource.defaultResourceHandler(rEx);
                        }
                    });
                }
            } else {
                Resource.defaultResourceHandler(r);
            }
        });


        //Adding to lastlyExecData

        LastlyExecutedCardDataManager lastlyExecManager = LastlyExecutedCardDataManager.getInstance();

        lastlyExecManager.add(this,new LastlyExecutedCardData(routine.getName(),
                routine.getDetail(),id));

    }

    public void onBackPressed(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}