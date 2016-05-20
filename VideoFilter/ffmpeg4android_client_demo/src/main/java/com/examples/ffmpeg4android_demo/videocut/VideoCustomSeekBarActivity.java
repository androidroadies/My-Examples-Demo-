package com.examples.ffmpeg4android_demo.videocut;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.audio_trimmer.CheapSoundFile;
import com.example.audio_trimmer.Util;
import com.examples.ffmpeg4android_demo.R;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class VideoCustomSeekBarActivity extends Activity implements View.OnClickListener {

    private TextView tvMinMax;
    private Button btnDone;
    private LinearLayout layout;
    private Button btnPlay;
    private DiscreteRangeSeekBar<Double> rangeSeekBar;
    //    private MediaPlayer mediaPlayer;
    VideoView mVideoView;
    private int max;
    private MediaObserver observer;
    private boolean prepared = false;
    private boolean audioSet;
    private int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_seek_bar);

        btnDone = (Button) findViewById(R.id.btn_done);
        btnPlay = (Button) findViewById(R.id.btn_play);
        tvMinMax = (TextView) findViewById(R.id.tv_minmax);
        layout = (LinearLayout) findViewById(R.id.seekbar_placeholder);

        btnDone.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        setRangeBar(Uri.parse("/sdcard/song.mp4"));
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 101) {
//                Uri audioFileUri = data.getData();
//                setRangeBar(audioFileUri);
//            }
//        }
//    }

    private void setRangeBar(Uri videoFileUri) {
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//        try {
//            mediaPlayer.setDataSource(this, audioFileUri);
//            mediaPlayer.prepare();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                prepared = true;
//                startPlaying();
//            }
//        });
//        if (mediaPlayer.getDuration() < 90) {
//            Toast.makeText(this, "Song length is less than 90 seconds. Please select another song", Toast.LENGTH_LONG);
//            return;
//        }

        mVideoView = (VideoView) findViewById(R.id.mVideoView);
        try {
            mVideoView.setVideoURI(videoFileUri);
//            mVideoView.start();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mPlayer) {

            }
        });

        System.out.println("max values: " + mVideoView.getDuration());
        rangeSeekBar = new DiscreteRangeSeekBar<Double>(0d, mVideoView.getDuration() / 1000d, 0.1, 5d, 60d, this);
//        // Set the range
//        rangeSeekBar.setRangeValues(15, 90);
        rangeSeekBar.setSelectedMinValue(5d);
        rangeSeekBar.setSelectedMaxValue(60d);

        layout.addView(rangeSeekBar);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Double>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Double minValue, Double maxValue) {
                tvMinMax.setText("Start point: \t" + minValue.intValue() + "\nEnd point: \t" + maxValue.intValue());
            }
        });
    }

    private void startPlaying() {
        if (prepared && audioSet) {
            min = rangeSeekBar.getSelectedMinValue().intValue();
            max = rangeSeekBar.getSelectedMaxValue().intValue();
            if (mVideoView != null) {
                mVideoView.start();
                mVideoView.seekTo(min * 1000);
                Log.wtf("TAG", "startPlaying " + mVideoView.getCurrentPosition());
                observer = new MediaObserver();
                new Thread(observer).start();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnDone) {
            if (saveAudioFile(String.valueOf(Uri.parse(getIntent().getExtras().getString("audioURI")))).toString().length() > 0) {

                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong, Please try again.", Toast.LENGTH_LONG).show();
            }
        } else if (v == btnPlay) {
            if (btnPlay.getText().toString().equalsIgnoreCase("Play")) {
                if (rangeSeekBar != null && prepared) {
                    audioSet = true;
                    startPlaying();
                }
            } else {
                if (mVideoView != null) {
                    mVideoView.stopPlayback();
                }
            }
        }
    }

    private class MediaObserver implements Runnable {
        private AtomicBoolean stop = new AtomicBoolean(false);

        public void stop() {
            stop.set(true);
        }

        @Override
        public void run() {
            while (!stop.get()) {
                Log.wtf("TAG", "run " + mVideoView.getCurrentPosition() / 1000);
                if (mVideoView.getCurrentPosition() / 1000 >= 80) {
                    mVideoView.stopPlayback();
                    this.stop();
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String saveAudioFile(String filePath) {
        CheapSoundFile cheapSoundFile = null;
        String outputPath = "";
        try {
            cheapSoundFile = CheapSoundFile.create(filePath, listener);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int mSampleRate = cheapSoundFile.getSampleRate();
        int mSamplesPerFrame = cheapSoundFile.getSamplesPerFrame();
        int startFrame = Util.secondsToFrames(min, mSampleRate, mSamplesPerFrame);
        int endFrame = Util.secondsToFrames(max, mSampleRate, mSamplesPerFrame);

//        try {
//            cheapSoundFile.WriteFile(Utils.getOutputMediaFile(Utils.MEDIA_TYPE_IMAGE), startFrame, endFrame - startFrame);
//            outputPath = Utils.getOutputMediaFile(Utils.MEDIA_TYPE_IMAGE).getPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return outputPath;
    }

    final CheapSoundFile.ProgressListener listener = new CheapSoundFile.ProgressListener() {
        public boolean reportProgress(double frac) {
            return true;
        }
    };
}