package vn.asiantech.internship.day23;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by at-dinhvo on 05/07/2017.
 */

class FileUpload extends AsyncTask<String, Integer, String> {

    private static final String URL = "http://2.pik.vn/";
    private static final String TAG = "at-dinhvo";

    private MediaType mMediaType = MediaType.parse("application/x-www-form-urlencoded");
    private OnResponseImageListener mOnResponseImageListener;

    /**
     * Call back return link
     */
    interface OnResponseImageListener {
        void onResponseImage(String link);
    }

    FileUpload(OnResponseImageListener onResponseImageListener) {
        mOnResponseImageListener = onResponseImageListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String link = null;
        RequestBody body = RequestBody.create(mMediaType, strings[0]);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null) {
                String jsonString = response.body().string();
                if (jsonString != null) {
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String image = jsonObj.getString("saved");
                    link = URL + image;
                }
            }
        } catch (JSONException | IOException e) {
            Log.d(TAG, "JSONException | IOException" + e.toString());
        }
        return link;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mOnResponseImageListener.onResponseImage(result);
    }
}
