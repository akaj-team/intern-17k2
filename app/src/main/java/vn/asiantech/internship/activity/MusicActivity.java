package vn.asiantech.internship.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.fragment.SongFragment;
import vn.asiantech.internship.interfaces.OnUpdateDataListerner;
import vn.asiantech.internship.models.Song;

/**
 * Created by ducle on 03/07/2017.
 * MusicActivity show media to play music
 */
public class MusicActivity extends AppCompatActivity implements OnUpdateDataListerner{
    private static final String TAG = MusicActivity.class.getSimpleName();
    private List<String> mSongId;
    private ArrayList<Song> mSongs;
    private RequestQueue mRequestQueue;
    private SongFragment mSongFragment;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_music);


        addFragment();
        init();
    }

    private void addFragment() {
        mSongFragment=SongFragment.newInstance(mSongs);
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContain,mSongFragment);
        fragmentTransaction.commit();
    }

    private void init() {
        initData();
    }

    private void initData() {
        mSongId = new ArrayList<>();
        mSongs = new ArrayList<>();
        mSongId.add("IW9AAAEA");
        mSongId.add("IW89O70O");
        mSongId.add("IWB6OEFZ");
        getSongs();
    }

    public void getJSONObject(JSONObject json) {
        try {
            String title = json.getString("title");
            String artist = json.getString("artist");
            JSONObject jsonSource = json.getJSONObject("source");
            String source = jsonSource.getString("128");
            int duration = json.getInt("duration");
            Song song = new Song(title, artist, source, duration);
            mSongs.add(song);
        } catch (JSONException e) {
            Log.d(TAG, "onResponse: " + e.getMessage());
        }
    }

    public void showToast() {
        Toast.makeText(this, mSongs.size() + "", Toast.LENGTH_LONG).show();
    }


    public void getSongs() {
        mRequestQueue = Volley.newRequestQueue(this);
        for (int i = 0; i < mSongId.size(); i++) {
            String url = "http://api.mp3.zing.vn/api/mobile/song/getsonginfo?requestdata={%22id%22:%22" + mSongId.get(i) + "%22}";
            final int finalI = i;
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    getJSONObject(response);
                    if (finalI == (mSongId.size() - 1)) {
                        showToast();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error.getMessage());
                }
            });
            mRequestQueue.add(jsonRequest);
        }
    }

    @Override
    public void onShowProgressDialog() {
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void onAddSong(Song song) {
        mSongs.add(song);
    }

    @Override
    public void onShowFragment() {
        addFragment();
    }

    @Override
    public void onCloseProgressDialog() {
        if (mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
