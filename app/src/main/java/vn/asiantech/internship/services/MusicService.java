package vn.asiantech.internship.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import vn.asiantech.internship.models.Action;

/**
 * Created by ducle on 03/07/2017.
 *
 */
public class MusicService extends Service {
    private MediaPlayer mMediaPlayer;
    private String mUrl;
    private int mLength;
    private CountDownTimer mCountDownTimer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getStringExtra("url") != null) {
            mUrl = intent.getStringExtra("url");
        }
        if (intent!=null&& intent.getAction()!=null){
            if (intent.getAction().equals(Action.PAUSE.getValue())){
                mMediaPlayer.pause();
                mLength=mMediaPlayer.getCurrentPosition();
            }else if (intent.getAction().equals(Action.START.getValue())){
                mMediaPlayer=new MediaPlayer();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mMediaPlayer.setDataSource(mUrl);
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        showNotification();
                        mMediaPlayer.start();
                    }
                });
                final  Intent timeIntent=new Intent(Action.SEEK.getValue());
                mCountDownTimer=new CountDownTimer(mMediaPlayer.getDuration(),1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeIntent.putExtra("time",mMediaPlayer.getDuration()+"");
                        timeIntent.putExtra("second",mMediaPlayer.getCurrentPosition()+"");
                        sendBroadcast(timeIntent);
                    }

                    @Override
                    public void onFinish() {

                    }
                };
                mCountDownTimer.start();
            }else if (intent.getAction().equals(Action.RESUME.getValue())){
                mMediaPlayer.seekTo(mLength);
                mMediaPlayer.start();
            }else if (intent.getAction().equals(Action.SEEK_TO.getValue())){
                int time=intent.getIntExtra("chooseTime",0);
                mMediaPlayer.seekTo(time);
                mMediaPlayer.start();
            }
        }
        return START_STICKY;
    }

    private void showNotification() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }
        if (mMediaPlayer!=null){
            mMediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
