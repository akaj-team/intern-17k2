package vn.asiantech.internship.backgrounds;

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
 * @author at-cuongcao
 * @version 1.0
 * @since 7/6/2017.
 */
public class UpImageAsyncTask extends AsyncTask<String, Void, String> {
    public static final String URL = "http://2.pik.vn/";
    private static final String TAG = "tag11";

    private TaskStatusListener mListener;

    public UpImageAsyncTask(TaskStatusListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onPrepareExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), params[0]);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(22, TimeUnit.SECONDS)
                .writeTimeout(22, TimeUnit.SECONDS)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null) {
                String jsonString = response.body().string();
                Log.i(TAG, jsonString);
                if (jsonString != null) {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if (jsonObject.has("saved") && jsonObject.optString("saved") != null) {
                        result = jsonObject.getString("saved");
                        Log.i("tag111", result + "---");
                    }
                }
            }
        } catch (JSONException | IOException e) {
            Log.d(TAG, e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mListener.onComplete(s);
        Log.i(TAG, s);
    }

    /**
     * This interface used to handle task status
     */
    public interface TaskStatusListener {
        void onPrepareExecute();

        void onComplete(String result);
    }
}
