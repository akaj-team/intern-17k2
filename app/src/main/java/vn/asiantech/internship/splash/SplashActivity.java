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
import vn.asiantech.internship.contact.ContactActivity;
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
    private final List<String> mDays = Arrays.asList("Day 1", "Day 3", "Day 5", "Day 7", "Day 11", "Day 11", "Day 12", "Day 15", "Day 16", "Day 20", "Day 21");

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
        Class classDestination = MainActivity.class;
        switch (position) {
            case 0:
                classDestination = MainActivity.class;
                break;
            case 1:
                classDestination = SendActivity.class;
                break;
            case 2:
                classDestination = RecyclerViewActivity.class;
                break;
            case 3:
                classDestination = FriendActivity.class;
                break;
            case 4:
                classDestination = NoteActivity.class;
                break;
            case 5:
                classDestination = FeedActivity.class;
                break;
            case 6:
                classDestination = ChatActivity.class;
                break;
            case 7:
                classDestination = TestActivity.class;
                break;
            case 8:
                classDestination = ImageActivity.class;
                break;
            case 9:
                classDestination = MusicActivity.class;
                break;
            case 10:
                classDestination = ContactActivity.class;
                break;
        }
        startActivity(new Intent(SplashActivity.this, classDestination));
    }
}
