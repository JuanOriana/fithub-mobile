package com.example.fithub_mobile.routine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.fithub_mobile.CycleData;
import com.example.fithub_mobile.CycleDisplay;
import com.example.fithub_mobile.ExecutionActivity;
import com.example.fithub_mobile.ExerciseQueueRealState;
import com.example.fithub_mobile.NotificationActivity;
import com.example.fithub_mobile.QrGenActivity;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.excercise.ExerciseData;
import com.example.fithub_mobile.excercise.LastlyExecutedCard;
import com.example.fithub_mobile.excercise.LastlyExecutedCardData;
import com.example.fithub_mobile.excercise.LastlyExecutedCardDataManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoutineActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.fithub_mobile.EXTRA_ID";
    static final private String ID_PARENT_EXTRA = "com.example.fithub_mobile.ID_PARENT";
    private int id;
    private String title;
    private String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        Intent intent = getIntent();

        id = Integer.parseInt(intent.getData().getQueryParameter("id"));
        title = intent.getStringExtra(RoutineCard.TITLE_MESSAGE);
        int rating = intent.getIntExtra(RoutineCard.RATING_MESSAGE,0);
        desc = intent.getStringExtra(RoutineCard.DESC_MESSAGE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        if (title!=null)
            toolBarLayout.setTitle(title);

        RatingBar ratingBar = findViewById(R.id.rating_bar_routine_view);
        ratingBar.setRating(rating);

        TextView descView = findViewById(R.id.desc_routine);
        if (desc != null)
            descView.setText(desc);

        LinearLayout cycleContainer = findViewById(R.id.cycle_container);
        cycleContainer.addView(new CycleDisplay(this,new CycleData(1,"Uno",4)));
        cycleContainer.addView(new CycleDisplay(this,new CycleData(1,"Uno",4)));
        cycleContainer.addView(new CycleDisplay(this,new CycleData(1,"Uno",4)));

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
    }

    public void startExecution(){
        ArrayList<ExerciseData> exercises = new ArrayList<>();
        exercises.add(new ExerciseData(1,"Diácono1","Prueba",4,4,"http://i.imgur.com/DvpvklR.png"));
        exercises.add(new ExerciseData(1,"Diácono2","Prueba",4,4,"http://i.imgur.com/DvpvklR.png"));
        exercises.add(new ExerciseData(1,"Diácono3","Prueba",4,4,"http://i.imgur.com/DvpvklR.png"));
        exercises.add(new ExerciseData(1,"Diácono4","Prueba",4,4,"http://i.imgur.com/DvpvklR.png"));
        exercises.add(new ExerciseData(1,"Diácono5","Prueba",4,4,"http://i.imgur.com/DvpvklR.png"));

        ExerciseQueueRealState exerciseQueueRealState = ExerciseQueueRealState.getInstance();
        exerciseQueueRealState.setNewRoutine(exercises);

        //Adding to lastlyExecData

        SharedPreferences sp = getSharedPreferences("lastly_exec",MODE_PRIVATE);
        String stringedData = sp.getString("lastly_exec_ex","");
        Gson gson = new Gson();
        Type type = new TypeToken<LastlyExecutedCardDataManager>() {}.getType();
        LastlyExecutedCardDataManager lastlyExecManager = gson.fromJson(stringedData,type);
        if (lastlyExecManager == null){
            lastlyExecManager = new LastlyExecutedCardDataManager();
        }

        lastlyExecManager.add(new LastlyExecutedCardData((title != null)?title:"titulo",
                (desc!=null)?desc:"desc",id));

        stringedData = gson.toJson(lastlyExecManager);
        sp.edit().putString("lastly_exec_ex",stringedData).apply();

        Intent i = new Intent(this, ExecutionActivity.class);
        startActivity(i);
    }
}