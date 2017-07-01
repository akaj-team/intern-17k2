package vn.asiantech.internship.services;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.ui.music.MusicActivity;

/**
 *
 * Created by quanghai on 30/06/2017.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener {
    private MediaPlayer mMediaPLayer;
    private List<Song> mSongs;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSongs = new ArrayList<>();
        mMediaPLayer = new MediaPlayer();
        mMediaPLayer.setOnPreparedListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(MusicActivity.KEY_INTENT);
            mSongs = bundle.getParcelableArrayList(MusicActivity.KEY_BUNDLE_ARRAYLIST);
            int position = bundle.getInt(MusicActivity.KEY_BUNDLE_POSITION);
            playSong(position);
        }
        return START_STICKY;
    }

    private void playSong(int position) {
        mMediaPLayer.reset();
        Song song = mSongs.get(position);
        int id = song.getId();
        String name = song.getTitle();
        Log.d("xxx", "id: " + id + " " + name);
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        Log.d("xxx", "playSong: " + uri);
        try {
            mMediaPLayer.setDataSource(this, uri);
            Log.d("xxx", "playSong: true");
        } catch (IOException e) {
            Log.d("xxx", "playSong: false");
            e.printStackTrace();
        }
        mMediaPLayer.prepareAsync();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPLayer.stop();
        Log.d("xxx", "onDestroy: ");
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}