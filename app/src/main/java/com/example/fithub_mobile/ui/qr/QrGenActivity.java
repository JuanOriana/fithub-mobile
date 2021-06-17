package com.example.fithub_mobile.ui.qr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fithub_mobile.BuildConfig;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.ui.routine.RoutineActivity;

import net.glxn.qrgen.android.QRCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class QrGenActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_gen);


        context = this;

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        int id = getIntent().getIntExtra(RoutineActivity.EXTRA_ID,0);
        String title = getIntent().getStringExtra(RoutineActivity.TITLE_QR_ID);
        ((TextView)findViewById(R.id.title_qr_view)).setText(title);

        Bitmap myBitmap = QRCode.from("http://fithub.com/routine?id="+ id).bitmap();
        ImageView myImage = findViewById(R.id.qr_code);
        myImage.setImageBitmap(myBitmap);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.screenshot_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.take_screen){
            verifyStoragePermission(QrGenActivity.this);
            takeScreenShot(getWindow().getDecorView());
        }
        return super.onOptionsItemSelected(item);
    }


    private void takeScreenShot(View view) {

        //This is used to provide file name with Date a format
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        //It will make sure to store file to given below Directory and If the file Directory dosen't exist then it will create it.
        try {
            File mainDir = new File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }

            //Providing file name along with Bitmap to capture screenview
            String path = mainDir + "/" + "Fithub" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            //This logic is used to save file at given location with the given filename and compress the Image Quality.
            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        //Create New Method to take ScreenShot with the imageFile.
            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Share ScreenShot
    private void shareScreenShot(File imageFile) {
        //Using sub-class of Content provider
        Uri uri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + "." + getLocalClassName() + ".provider",
                imageFile);

        //Explicit intent
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        //It will show the application which are available to share Image; else Toast message will throw.
        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    public static void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

}