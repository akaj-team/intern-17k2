package vn.asiantech.internship.day23;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import vn.asiantech.internship.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 06/07/2017.
 */
class UploadImageToServer extends AsyncTask<String, Void, String> {
    private static final String TYPE_SAVE = "saved";
    private UploadActivity mUploadActivity;
    private String mUrl = "http://2.pik.vn/";

    UploadImageToServer(UploadActivity uploadActivity) {
        mUploadActivity = uploadActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = "";
        HttpHandler httpHandler = new HttpHandler();
        String jsonStr = httpHandler.makeServiceCall(mUrl, params[0]);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                if (jsonObj.has(TYPE_SAVE) && jsonObj.optString(TYPE_SAVE) != null) {
                    response = jsonObj.getString(TYPE_SAVE);
                    return response;
                }

            } catch (final JSONException e) {
                e.printStackTrace();
                mUploadActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mUploadActivity.getApplicationContext(),
                                mUploadActivity.getString(R.string.json_passing_error, e.getMessage()),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            mUploadActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mUploadActivity.getApplicationContext(),
                            mUploadActivity.getString(R.string.error_http_json),
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
        return response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mUploadActivity.initDialogProgress();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mUploadActivity.dismissDialog();
        mUploadActivity.showImage(mUrl + s);
    }
}
