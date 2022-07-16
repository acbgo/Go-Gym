package com.example.go_gym.part2;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
import com.example.go_gym.R;

public class PlayActivity extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        //将状态栏设置为透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AudioMngHelper audioMngHelper = new AudioMngHelper(this);
        audioMngHelper.AddMusicVoiceVolumn(10);

        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(getBaseContext());
        String path = "android.resource://"+getPackageName() + "/"+R.raw.video;
        videoView.setVideoURI(Uri.parse(path));
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.requestFocus();
        videoView.start();
    }


}