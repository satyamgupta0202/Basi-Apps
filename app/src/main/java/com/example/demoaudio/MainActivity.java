package com.example.demoaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    public void play(View view) {
        //mediaPlayer =  MediaPlayer.create(this, R.raw.sound);
        mediaPlayer.start();
    }
    public void pause(View view) {
       // mediaPlayer =  MediaPlayer.create(this, R.raw.sound);
        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mediaPlayer =  MediaPlayer.create(this, R.raw.sound);
       SeekBar volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);

       volumeControl.setMax(maxVolume);
       volumeControl.setProgress(currentVolume);
       volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
               Log.i("on seek",Integer.toString(progress));
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });

        SeekBar scrubControl = (SeekBar) findViewById(R.id.scrubSeekBar);
        scrubControl.setMax(mediaPlayer.getDuration());
        scrubControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
                Log.i("scrub bar seek",Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrubControl.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,9000);
    }
}