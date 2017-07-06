package vn.asiantech.internship.day23;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

public class UploadActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

    }

    public void initDialogProgress(){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.message_progress_dialog));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void dismissDialog(){
        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
