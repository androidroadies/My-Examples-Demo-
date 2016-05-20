package com.examples.ffmpeg4android_demo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.netcompss.ffmpeg4android_client.BaseWizard;
import com.netcompss.ffmpeg4android_client.Prefs;

public class DemoClient extends BaseWizard {

    // working
    String[] complexCommand = {"ffmpeg",
            "-y",
            "-i",
            "/sdcard/videokit/in.mp4",
            "-i",
            "/sdcard/videokit/in.mp4",
            "-strict",
            "experimental",
            "-filter_complex",
            "[0:v]scale=640x480,setsar=1:1[v0];[1:v]scale=640x480,setsar=1:1[v1];[v0][0:a][v1][1:a] concat=n=2:v=1:a=1",
            "-ab",
            "48000",
            "-ac",
            "2",
            "-ar",
            "22050",
            "-s",
            "640x480",
            "-r", "30",
            "-vcodec",
            "mpeg4",
            "-b",
            "2097k",
            "/sdcard/videokit/result.mp4"};
    // working
    String[] overlayCommand = {"ffmpeg",
            "-y",
            "-i",
            "/sdcard/videokit/in.mp4",
            "-i",
            "/sdcard/videokit/12.png",
            "-filter_complex",
            "[0:v][1:v]overlay=main_w-overlay_w-100:main_h-overlay_h-60[out]", // position of watermark
            "-map",
            "[out]",
            "-map",
            "0:a",
            "-codec:a",
            "copy",
            "/sdcard/videokit/outBig123.mp4"};

    String [] playGIF ={"ffmpeg","-i", "/sdcard/videokit/in.mp4","-i", "/sdcard/videokit/gif.gif","-filter_complex","[0:v][1:v] overlay=10:10 [v]","-map","[v]","-map","0:a","-c:v","libx264","-c:a","copy","/sdcard/videokit/outGIF.mp4"};
//    String playGIF ="ffmpeg -i /sdcard/videokit/in.mp4 -ignore_loop 0 -i /sdcard/videokit/gif.gif -filter_complex [0:v][1:v]overlay=10:10:shortest=1 -vcodec mpeg2video /sdcard/videokit/outGIF.mp4";
    String effectBlue = "ffmpeg -y -i /sdcard/videokit/in.mp4 -strict experimental -vf hue=s=10 -vcodec mpeg4 -b 2097152 -s 320x240 -r 30 /sdcard/videokit/Blue.mp4";
    String effectRed = "ffmpeg -y -i /sdcard/videokit/in.mp4 -strict experimental -vf hue=s=-10 -vcodec mpeg4 -b 2097152 -s 320x240 -r 30 /sdcard/videokit/Red.mp4";
    String effectBW = "ffmpeg -y -i /sdcard/videokit/in.mp4 -strict experimental -vf hue=s=0 -vcodec mpeg4 -b 2097152 -s 320x240 -r 30 /sdcard/videokit/BW.mp4";

    // str1 + effect name + str2 + output file name.mp4
    String effStr2 = " -s 640x480 -r 30 -g 30 -b 30 -aspect 4:3 -ab 48000 -ac 2 -ar 22050 -b 2097k -vcodec mpeg4 /sdcard/videokit/";
    String effStr1 = "ffmpeg -y -i /sdcard/videokit/in.mp4 -strict experimental -vf curves=";

    String eff1 = "color_negative";
    String eff2 = "cross_process";
    String eff3 = "darker";
    String eff4 = "increase_contrast";
    String eff5 = "lighter";
    String eff6 = "linear_contrast";
    String eff7 = "medium_contrast";
    String eff8 = "negative";
    String eff9 = "strong_contrast";
    String eff10 = "vintage";

    String compress = "ffmpeg -i /sdcard/videokit/test.mp4 -s 640x480 -b 512k -vcodec mpeg1video -acodec copy /sdcard/videokit/textCompress.mp4";
    String compress1 = "ffmpeg -y -i /sdcard/videokit/in.mp4 -strict experimental -s 160x120 -r 25 -vcodec mpeg4 -b 150k -ab 48000 -ac 2 -ar 22050 /sdcard/videokit/out.mp4";

    // Not working
//    public static String effectAudio = "ffmpeg -i /sdcard/videokit/in.mp4 -i /sdcard/videokit/abcd.mp3 -c:v copy -c:a copy /sdcard/videokit/output.mp4";

    // working Add your audio and remove recorded sound.
//    public static String effectAudio = "ffmpeg -i /sdcard/videokit/in.mp4 -i /sdcard/videokit/abcd.mp3 -c:v copy -c:a aac -strict experimental -map 0:v:0 -map 1:a:0 /sdcard/videokit/output1.mp4";

    // Not working
//    public static String effectAudio = "ffmpeg.exe -ss 00:00:4  -t 5 -i /sdcard/videokit/in.mp4 -ss 0:00:01 -t 4 -i /sdcard/videokit/abcd.mp3 -map 0:v:0 -map 1:a:0 -y /sdcard/videokit/output1.mp4";

//    Working
//    public static String effectAudio = "ffmpeg -y -i /sdcard/vid.mp4 -itsoffset 00:00:30 /sdcard/videokit/abcd.mp3 -map 0:0 -map 1:0 -c:v copy -preset ultrafast -async 1 /sdcard/videokit/out1.mp4";

    // Audio cur -ss start from 10(start value in sec) and -t upto 6 sec. working
    public static String effectAudio = "ffmpeg -i /sdcard/videokit/abcd.mp3 -ss 10 -t 6 -acodec copy /sdcard/videokit/abcd1.mp3";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _prefs = new Prefs();
        _prefs.setContext(this);
        // this will copy the license file and the demo video file.
        // to the videokit work folder location.
        // without the license file the library will not work.
        copyLicenseAndDemoFilesFromAssetsToSDIfNeeded();
        setContentView(R.layout.ffmpeg_demo_client);
        Button invoke = (Button) findViewById(R.id.invokeButton);
        invoke.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditText commandText = (EditText) findViewById(R.id.CommandText);
//                commandText.setText(overlayCommand.toString());
                String commandStr = commandText.getText().toString();
                String CommandTrimmingVideo = getString(R.string.command_video_trimming);
//                setCommand(CommandTrimmingVideo);


                //Log.i(Prefs.TAG, "Overriding the command with hard coded command");
                //commandStr = "ffmpeg -y -i /sdcard/videokit/in.mp4 -strict experimental -vf transpose=1 -s 160x120 -r 30 -aspect 4:3 -ab 48000 -ac 2 -ar 22050 -b 2097k /sdcard/videokit/vid_trans.mp4";

                // complex command should be used in cases sub-commands and embedded commands (for example quotations inside a command).
                //String[] complexCommand = {"ffmpeg","-y" ,"-i", "/sdcard/videokit/in.mp4","-strict","experimental", "-vf", "crop=iw/2:ih:0:0,split[tmp],pad=2*iw[left]; [tmp]hflip[right]; [left][right] overlay=W/2", "-vb", "20M", "-r", "23.956", "/sdcard/videokit/out_complex.mp4"};
                ////////////////////////////////////////////////////////////////////////////////
                ////// commands to needed to run the transcoding, only
                ////// the setCommand and runTranscoding are mandatory.
                ////// All the other commands are optional
//                setCommand(commandStr);
//
                setCommandComplex(playGIF);
//                setCommand(playGIF);
//                setCommand(effectAudio);

//                setCommand(effectBlue);
//                setCommand(effectBW);
//                setCommand(effectRed);
//                setCommand(effStr1 + eff1 + effStr2 + eff1 + ".mp4");
//                setCommand(effStr1 + eff2 + effStr2 + eff2 + ".mp4");
//                setCommand(effStr1 + eff3 + effStr2 + eff3 + ".mp4");
//                setCommand(effStr1 + eff4 + effStr2 + eff4 + ".mp4");
//                setCommand(effStr1 + eff5 + effStr2 + eff5 + ".mp4");
//                setCommand(effStr1 + eff6 + effStr2 + eff6 + ".mp4");
//                setCommand(effStr1 + eff7 + effStr2 + eff7 + ".mp4");
//                setCommand(effStr1 + eff8 + effStr2 + eff8 + ".mp4");
//                setCommand(effStr1 + eff9 + effStr2 + eff9 + ".mp4");
//                setCommand(effStr1 + eff10 + effStr2 + eff10 + ".mp4");

                ///optional////
                setProgressDialogTitle("Exporting As MP4 Video");
                setProgressDialogMessage("Depends on your video size, it can take a few minutes");
                setNotificationIcon(R.drawable.icon2);
                setNotificationMessage("Demo is running...");
                setNotificationTitle("Demo Client");
                setNotificationfinishedMessage("Demo Transcoding finished");
                setNotificationStoppedMessage("Demo Transcoding stopped");
                ///////////////
                runTranscoing();
            }
        });
        Button showLog = (Button) findViewById(R.id.showLastRunLogButton);
        showLog.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startAct(com.netcompss.ffmpeg4android_client.ShowFileAct.class);
            }
        });

        Button cutVideo = (Button) findViewById(R.id.cutVideoButton);
        cutVideo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startAct(com.examples.ffmpeg4android_demo.videocut.VideoCustomSeekBarActivity.class);
            }
        });
    }
}