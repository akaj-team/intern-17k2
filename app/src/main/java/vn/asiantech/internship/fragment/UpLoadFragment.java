package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vn.asiantech.internship.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ducle on 06/07/2017.
 * UpLoadFragment is fragment to up load
 */
public class UpLoadFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = UpLoadFragment.class.getSimpleName();
    public static final String SERVER_URL = "http://2.pik.vn/";
    private static final int REQUEST_CODE = 1;
    private Button mBtnUpLoad;
    private ImageView mImgUpLoad;
    private Thread mThread;
    private String mImgString;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        initViews(view);
        mBtnUpLoad.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        mBtnUpLoad = (Button) view.findViewById(R.id.btnUpLoad);
        mImgUpLoad = (ImageView) view.findViewById(R.id.imgUpload);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUpLoad) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                String path = data.getData().toString();
                Log.d(TAG, "onActivityResult: path " + path);
                byte[] byteArr = convertImageToByte(data.getData());
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("data:image/png;base64,");
                stringBuffer.append(Base64.encodeToString(byteArr, Base64.NO_WRAP));
                mImgString = stringBuffer.toString();
                Log.d(TAG, "onActivityResult: " + mImgString);
                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        upLoadFile(mImgString);
                    }
                });
                mThread.start();
            }
        }
    }

    private void upLoadFile(String imgString) {
        final String[] responseString = {null};
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("image", imgString).build();
        Request request = new Request.Builder().url(SERVER_URL).post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                responseString[0] = response.body().string();
                Log.d(TAG, "onResponse: " + responseString[0]);
                url = getUrl(responseString[0]);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageLoader imageLoader = ImageLoader.getInstance();
                        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
                        imageLoader.displayImage(SERVER_URL + url, mImgUpLoad);
                        mThread.interrupt();
                    }
                });
            }
        });
    }

    private String getUrl(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("saved") && jsonObject.optString("saved") != null) {
                return jsonObject.getString("saved");
            } else return null;
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
            return null;
        }
    }

    public byte[] convertImageToByte(Uri uri) {
        InputStream iStream = null;
        try {
            iStream = getActivity().getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = iStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "FileNotFoundException: ");
            return null;
        } catch (IOException e) {
            Log.d(TAG, "IOException: ");
            return null;
        }
    }
}
