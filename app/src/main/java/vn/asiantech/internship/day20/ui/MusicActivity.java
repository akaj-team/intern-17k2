package vn.asiantech.internship.day20.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day20.service.MusicService;

/**
 * MusicActivity contain fragment music
 */
public class MusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        startService(new Intent(MusicActivity.this, MusicService.class));
        getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new MusicFragment()).commit();
    }
}
