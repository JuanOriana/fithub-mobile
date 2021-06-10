package com.example.fithub_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.fithub_mobile.routine.RoutineActivity;

import net.glxn.qrgen.android.QRCode;

public class QrGenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_gen);
        int id = getIntent().getIntExtra(RoutineActivity.EXTRA_ID,0);

        Bitmap myBitmap = QRCode.from("http://fithub.com/routine?id="+ id).bitmap();
        ImageView myImage = findViewById(R.id.qr_code);
        myImage.setImageBitmap(myBitmap);
    }
}