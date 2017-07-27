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
    private final List<String> mDays = Arrays.asList("Day 1", "Day 3", "Day 5", "Day 7", "Day 11", "Day 11", "Day 12", "Day 15", "Day 16", "Day 20", "Day 27");

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
        Class clazz = MainActivity.class;
        switch (position) {
            case 0:
                clazz = MainActivity.class;
                break;
            case 1:
                clazz = SendActivity.class;
                break;
            case 2:
                clazz = RecyclerViewActivity.class;
                break;
            case 3:
                clazz = FriendActivity.class;
                break;
            case 4:
                clazz = NoteActivity.class;
                break;
            case 5:
                clazz = FeedActivity.class;
                break;
            case 6:
                clazz = ChatActivity.class;
                break;
            case 7:
                clazz = TestActivity.class;
                break;
            case 8:
                clazz = ImageActivity.class;
                break;
            case 9:
                clazz = MusicActivity.class;
                break;
            case 10:
                clazz = NoteActivity.class;
                break;
        }
        startActivity(new Intent(SplashActivity.this, clazz));
    }
}
