package com.example.fithub_mobile.routine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.CycleDisplay;
import com.example.fithub_mobile.ExecutionActivity;
import com.example.fithub_mobile.ExerciseQueueRealState;
import com.example.fithub_mobile.ui.login.Login;
import com.example.fithub_mobile.MainActivity;
import com.example.fithub_mobile.NotificationActivity;
import com.example.fithub_mobile.QrGenActivity;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullCycle;
import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.Review;
import com.example.fithub_mobile.excercise.LastlyExecutedCardData;
import com.example.fithub_mobile.excercise.LastlyExecutedCardDataManager;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RoutineActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.fithub_mobile.EXTRA_ID";
    static final private String ID_PARENT_EXTRA = "com.example.fithub_mobile.ID_PARENT";
    static final public String TITLE_QR_ID = "com.example.fithub_mobile.TITLE_QR_ID";
    public static final String COMEBACK_URL = "com.example.fithub_mobile.COMEBACK_URL";
    private FullRoutine routine;
    private List<FullCycle> cycles;
    private int id;
    private Context context;
    private SharedPreferences sp;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getData().getQueryParameter("id"));
        uri = intent.getData();

        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(!sp.getBoolean("logged",false)){
            goToLogin();
        }

        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        RatingBar ratingBar = findViewById(R.id.rating_bar_routine_view);
        TextView descView = findViewById(R.id.desc_routine);
        ImageButton qrBtn = findViewById(R.id.qr_btn);

        App app = (App)getApplication();
        app.getRoutineRepository().getRoutine(id).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                routine = r.getData();
                ratingBar.setRating(routine.getAverageRating());
                descView.setText(routine.getDetail());
                toolBarLayout.setTitle(routine.getName());
                if (!routine.isIsPublic()){
                    qrBtn.setVisibility(View.GONE);
                }
            } else {
                Resource.defaultResourceHandler(r);
            }
        });

        LinearLayout cycleContainer = findViewById(R.id.cycle_container);

        app.getCycleRepository().getCycles(id).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                assert r.getData() != null;
                cycles = r.getData().getContent();
                for (FullCycle cycle : cycles) {
                    if (!cycle.getType().equals("cooldown"))
                        cycleContainer.addView(new CycleDisplay(this,cycle));
                }
                if (cycles.size() > 1)
                    cycleContainer.addView(new CycleDisplay(this,cycles.get(1)));
            } else {
                Resource.defaultResourceHandler(r);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startExecution());

        qrBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, QrGenActivity.class);
            i.putExtra(EXTRA_ID,id);
            i.putExtra(TITLE_QR_ID, routine.getName());
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
                            }
                            for (int i = 0; i < cycle.getRepetitions(); i ++) {
                                List<FullCycleExercise> setExercises = new ArrayList<>();
                                for (FullCycleExercise ex : cycleExercises) {
                                    FullCycleExercise newEx = new FullCycleExercise(
                                            ex.getExercise(),ex.getOrder(),ex.getDuration(),ex.getRepetitions(),null
                                    );
                                    newEx.setCycle(ex.getCycle());
                                    newEx.setSet(i);
                                    app.getExerciseImageRepository().getExerciseImages(ex.getExercise().getId()).observe(this, rImg -> {
                                        if (rImg.getStatus() == Status.SUCCESS) {
                                            assert rImg.getData() != null;
                                            newEx.setImg(rImg.getData().getContent().get(0).getUrl());
                                        } else {
                                            Resource.defaultResourceHandler(rImg);
                                        }
                                    });
                                    setExercises.add(newEx);
                                }
                                exerciseQueueRealState.getExercises().addAll(setExercises);
                            }

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToLogin(){
        Intent i = new Intent(this, Login.class);
        i.putExtra(COMEBACK_URL,uri.toString());
        startActivity(i);
        finish();
    }
}