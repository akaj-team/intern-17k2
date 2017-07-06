package vn.asiantech.internship.day23;

import android.os.AsyncTask;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 06/07/2017.
 */

public class UploadImageToServer extends AsyncTask<Void,Void,String> {
    private UploadActivity mUploadActivity;

    public UploadImageToServer(UploadActivity uploadActivity){
        mUploadActivity=uploadActivity;
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpHandler httpHandler =new HttpHandler();

        return null;

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
    }
}
