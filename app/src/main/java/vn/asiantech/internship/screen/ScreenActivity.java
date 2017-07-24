package vn.asiantech.internship.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.ui.activity.NoteActivity;
import vn.asiantech.internship.day13.ui.NinePathActivity;
import vn.asiantech.internship.day15.ui.activity.QuizActivity;
import vn.asiantech.internship.day16.ui.activity.TabActivity;
import vn.asiantech.internship.day24.ChartActivity;
import vn.asiantech.internship.day5.FriendActivity;
import vn.asiantech.internship.day7.ui.MainActivity;
import vn.asiantech.internship.day9.ui.FeedActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 10/07/2017.
 */
public class ScreenActivity extends AppCompatActivity implements OnDayChooseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewScreen);
        List<String> days = new ArrayList<>();
        days.add(getString(R.string.day5));
        days.add(getString(R.string.day7));
        days.add(getString(R.string.day9));
        days.add(getString(R.string.day11));
        days.add(getString(R.string.day13));
        days.add(getString(R.string.day15));
        days.add(getString(R.string.day16));
        days.add(getString(R.string.day24));

        ScreenAdapter screenAdapter = new ScreenAdapter(days, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(screenAdapter);
    }

    @Override
    public void chooseDay(int position) {
        switch (position) {
            case 0:
                Intent friendIntent = new Intent(ScreenActivity.this, FriendActivity.class);
                startActivity(friendIntent);
                break;
            case 1:
                Intent mainIntent = new Intent(ScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case 2:
                Intent feedIntent = new Intent(ScreenActivity.this, FeedActivity.class);
                startActivity(feedIntent);
                break;
            case 3:
                Intent noteIntent = new Intent(ScreenActivity.this, NoteActivity.class);
                startActivity(noteIntent);
                break;
            case 4:
                Intent ninePatchIntent = new Intent(ScreenActivity.this, NinePathActivity.class);
                startActivity(ninePatchIntent);
                break;
            case 5:
                Intent quizIntent = new Intent(ScreenActivity.this, QuizActivity.class);
                startActivity(quizIntent);
                break;
            case 6:
                Intent tabIntent = new Intent(ScreenActivity.this, TabActivity.class);
                startActivity(tabIntent);
                break;
            case 7:
                Intent chartIntent = new Intent(ScreenActivity.this, ChartActivity.class);
                startActivity(chartIntent);
                break;
        }
    }
}
