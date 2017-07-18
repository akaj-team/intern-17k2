package vn.asiantech.internship.backgrounds;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/4/2017.
 */

public class GetSongAsyncTask extends AsyncTask<String, Void, ArrayList<Song>> {

    private Context mContext;
    private CallBackListener mListener;

    public GetSongAsyncTask(Context context, CallBackListener listener) {
        mListener = listener;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onTaskPreExecute();
    }

    @Override
    protected ArrayList<Song> doInBackground(String... params) {
        ArrayList<Song> songs = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        String url;
        String imageUrl = mContext.getString(R.string.image_url);

        for (int i = 0; i < params.length; i++) {
            url = mContext.getString(R.string.song_url, params[i]);
            try {
                JSONObject jsonObject = new JSONObject(httpHandler.makeServiceCall(url));
                Song song = new Song(jsonObject.getString("title"), jsonObject.getString("artist"), jsonObject.getJSONObject("source").getString("128"), imageUrl);
                songs.add(song);
            } catch (JSONException e) {
                Log.i("tag11", e.getMessage());
            }
        }
        return songs;
    }

    @Override
    protected void onPostExecute(ArrayList<Song> songs) {
        super.onPostExecute(songs);
        mListener.onTaskCompleted(songs);
    }

    /**
     * interface for task event
     */
    public interface CallBackListener {
        void onTaskPreExecute();

        void onTaskCompleted(ArrayList<Song> songs);
    }
}
