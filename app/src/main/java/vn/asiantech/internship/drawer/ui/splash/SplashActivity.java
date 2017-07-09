package vn.asiantech.internship.drawer.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import vn.asiantech.internship.R;
import vn.asiantech.internship.bai24.CanvasActivity;
import vn.asiantech.internship.day13.ChatActivity;
import vn.asiantech.internship.day15.ui.QuestionActivity;
import vn.asiantech.internship.day16.ui.TabActivity;
import vn.asiantech.internship.day7.ui.CommunicateActivity;
import vn.asiantech.internship.drawer.ui.main.MainActivity;
import vn.asiantech.internship.friend.FriendActivity;
import vn.asiantech.internship.note.ui.NoteActivity;

/**
 * SplashActivity
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.btnBai7ex2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, CommunicateActivity.class));
            }
        });
        findViewById(R.id.btnFriend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, FriendActivity.class));
            }
        });
        findViewById(R.id.btnNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, NoteActivity.class));
            }
        });
        findViewById(R.id.btnBai13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, ChatActivity.class));
            }
        });
        findViewById(R.id.btnBai15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });
        findViewById(R.id.btnQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, QuestionActivity.class));
            }
        });
        findViewById(R.id.btnBai16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, TabActivity.class));
            }
        });
        findViewById(R.id.btnBai24).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, CanvasActivity.class));
            }
        });
    }
}
