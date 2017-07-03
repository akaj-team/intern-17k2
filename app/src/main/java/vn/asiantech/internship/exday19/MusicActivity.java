package vn.asiantech.internship.exday19;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 02-07-2017.
 */
public class MusicActivity extends AppCompatActivity implements MusicListAdapter.OnClickMusicItemListener {
    public static final String KEY_MUSIC = "music";
    private FragmentTransaction mFragmentTransaction;
    private PlayMusicFragment mPlayMusicFragment;
    private MusicListFragment mMusicListFragment;
    private ArrayList<MusicItem> mMusicItems = new ArrayList<>();
    private int mPosition;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mMusicListFragment = new MusicListFragment();
        mPlayMusicFragment = new PlayMusicFragment();
        replaceFragment(mMusicListFragment, false);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(R.id.flMusicList, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        updateToolbar(getCurrentFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.list_item:
                replaceFragment(mMusicListFragment, true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.flMusicList);
    }

    @Override
    public void onItemClick(ArrayList<MusicItem> musicItems, int position) {
        mPosition = position;
        mMusicItems = musicItems;
        Log.d("tag", "onItemClick: "+mPosition);
        Log.d("tag", "onItemClick: "+mMusicItems);
        Bundle bundle = new Bundle();
        bundle.putSerializable("position", position);
        bundle.putParcelableArrayList(KEY_MUSIC, musicItems);
        mPlayMusicFragment.setArguments(bundle);
        replaceFragment(mPlayMusicFragment, true);
    }
}
