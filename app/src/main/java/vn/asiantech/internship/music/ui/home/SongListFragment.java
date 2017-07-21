package vn.asiantech.internship.music.ui.home;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import vn.asiantech.internship.music.models.Action;
import vn.asiantech.internship.music.models.Song;
import vn.asiantech.internship.music.services.MusicService;

public class SongListFragment extends Fragment {
    private static final String TAG = SongListFragment.class.getSimpleName();
    private List<Song> mSongs;
    private String[] mSongIds;
    private RecyclerView mRecyclerViewSong;
    private SongAdapter mSongAdapter;
    private RequestQueue mRequestQueue;
    private ProgressDialog mProgressDialog;
    private LoadSongList mLoadSongList;
    public boolean mDataLoaded;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSongs = new ArrayList<>();
        mSongIds = new String[]{"ZWZCDU6E", "IWZDZ08I", "ZW7FE0FC", "ZWZ9Z0A8"};
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mLoadSongList = new LoadSongList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_song, container, false);
        initRecyclerView(view);
        if (!mDataLoaded) {
            mLoadSongList.execute(mSongIds);
            mDataLoaded = true;
        }
        return view;
    }

    private void initRecyclerView(View view) {
        mRecyclerViewSong = (RecyclerView) view.findViewById(R.id.recyclerViewSong);
        mSongAdapter = new SongAdapter(mSongs);
        mRecyclerViewSong.setAdapter(mSongAdapter);
        mRecyclerViewSong.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class LoadSongList extends AsyncTask<String, Void, ArrayList<Song>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.wait));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<Song> doInBackground(String... strings) {
            ArrayList<Song> songs = new ArrayList<>();
            for (String string : strings) {
                String url = "http://api.mp3.zing.vn/api/mobile/song/getsonginfo?requestdata={%22id%22:%22" + string + "%22}";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String title = response.getString("title");
                            String artist = response.getString("artist");
                            JSONObject jsonSource = response.getJSONObject("source");
                            String source = jsonSource.getString("128");
                            Song song = new Song(title, artist, source);
                            sendData(song);
                            mSongs.add(song);
                            mSongAdapter.notifyItemInserted(mSongs.size() - 1);

                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: ");
                    }
                });
                mRequestQueue.add(jsonObjectRequest);
            }
            return songs;
        }

        @Override
        protected void onPostExecute(ArrayList<Song> songs) {
            super.onPostExecute(songs);
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    private void sendData(Song song) {
        Intent intent = new Intent(getActivity(), MusicService.class);
        Bundle bundle = new Bundle();
        Log.d(TAG, "sendData: " + song.getTitle());
        bundle.putParcelable(SongActivity.KEY_SONG, song);
        intent.setAction(Action.INTENT.getValue());
        intent.putExtras(bundle);
        getActivity().startService(intent);
    }
}
