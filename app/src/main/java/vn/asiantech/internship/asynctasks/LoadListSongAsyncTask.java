package vn.asiantech.internship.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import vn.asiantech.internship.interfaces.OnUpdateDataListener;
import vn.asiantech.internship.models.Song;

/**
 * Created by ducle on 05/07/2017.
 */

public class LoadListSongAsyncTask extends AsyncTask<String[], Void, Void> {
    private static final String TAG = LoadListSongAsyncTask.class.getSimpleName();
    private Context mContext;
    private OnUpdateDataListener mOnUpdateDataListener;

    public LoadListSongAsyncTask(Context context) {
        mContext = context;
        mOnUpdateDataListener = (OnUpdateDataListener) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //mOnUpdateDataListener.onShowProgressDialog();
    }

    @Override
    protected Void doInBackground(String[]... params) {
        addSong(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //mOnUpdateDataListener.onCloseProgressDialog();
    }

    private void addSong(String[] songIDs) {
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        for (int i = 0; i < songIDs.length; i++) {
            String url = "http://api.mp3.zing.vn/api/mobile/song/getsonginfo?requestdata={%22id%22:%22" + songIDs[i] + "%22}";
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String title;
                        String artist;
                        String source;
                        int duration;
                        if (response.has("title") && response.optString("title") != null) {
                            title = response.getString("title");
                        } else {
                            title = null;
                        }
                        if (response.has("artist") && response.optString("artist") != null) {
                            artist = response.getString("artist");
                        } else {
                            artist = null;
                        }
                        if (response.has("source") && response.optJSONObject("source") != null) {
                            JSONObject jsonSource = response.getJSONObject("source");
                            if (jsonSource.has("128") && jsonSource.optString("128") != null) {
                                source = jsonSource.getString("128");
                            } else {
                                source = null;
                            }
                        } else {
                            source = null;
                        }
                        if (response.has("duration") && response.optString("duration") != null) {
                            duration = response.getInt("duration");
                        } else {
                            duration = 0;
                        }
                        Song song = new Song(title, artist, source, duration);
                        mOnUpdateDataListener.onAddSong(song);
                    } catch (JSONException e) {
                        Log.d(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error.getMessage());
                }
            });
            requestQueue.add(jsonRequest);
        }
    }
}
