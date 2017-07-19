package vn.asiantech.internship.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import recyclerview.FriendActivity;
import vn.asiantech.internship.R;
import vn.asiantech.internship.canvas.CanvasActivity;
import vn.asiantech.internship.demo.RecyclerViewActivity;
import vn.asiantech.internship.feed.FeedActivity;
import vn.asiantech.internship.fragment.SendActivity;
import vn.asiantech.internship.music.MusicActivity;
import vn.asiantech.internship.ninepath.ChatActivity;
import vn.asiantech.internship.notesqlite.NoteActivity;
import vn.asiantech.internship.tablayout.ImageActivity;
import vn.asiantech.internship.test.TestActivity;
import vn.asiantech.internship.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements SplashAdapter.OnClickListener {
    private final List<String> mDays = Arrays.asList("Day 1", "Day 3", "Day 5", "Day 7", "Day 11", "Day 11", "Day 12", "Day 15", "Day 16", "Day 20", "Day 24");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        RecyclerView splashRecyclerView = (RecyclerView) findViewById(R.id.splashRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        splashRecyclerView.setLayoutManager(linearLayoutManager);
        SplashAdapter adapter = new SplashAdapter(mDays, this);
        splashRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                break;
            case 1:
                startActivity(new Intent(SplashActivity.this, SendActivity.class));
                break;
            case 2:
                startActivity(new Intent(SplashActivity.this, RecyclerViewActivity.class));
                break;
            case 3:
                startActivity(new Intent(SplashActivity.this, FriendActivity.class));
                break;
            case 4:
                startActivity(new Intent(SplashActivity.this, NoteActivity.class));
                break;
            case 5:
                startActivity(new Intent(SplashActivity.this, FeedActivity.class));
                break;
            case 6:
                startActivity(new Intent(SplashActivity.this, ChatActivity.class));
                break;
            case 7:
                startActivity(new Intent(SplashActivity.this, TestActivity.class));
                break;
            case 8:
                startActivity(new Intent(SplashActivity.this, ImageActivity.class));
                break;
            case 9:
                startActivity(new Intent(SplashActivity.this, MusicActivity.class));
                break;
            case 10:
                startActivity(new Intent(SplashActivity.this, CanvasActivity.class));
                break;
        }
    }
}
