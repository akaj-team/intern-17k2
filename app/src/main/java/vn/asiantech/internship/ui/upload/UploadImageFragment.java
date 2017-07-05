package vn.asiantech.internship.ui.upload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vn.asiantech.internship.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageFragment extends Fragment {

    private static final String URL = "http://2.pik.vn/";
    private static final String TAG = "UploadImageFragment";
    private static final int KEY_LIBRARY = 2;

    private ImageView mImgShow;
    private String mBase64 = "";
    private MediaType mMediaType = MediaType.parse("application/x-www-form-urlencoded");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_image, container, false);
        mImgShow = (ImageView) v.findViewById(R.id.imgShow);
        Button btnUpload = (Button) v.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(uploadImage());
        mImgShow.setOnClickListener(choiceImage());
        return v;
    }

    private View.OnClickListener uploadImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (TextUtils.equals(mBase64, "")) {
                        String json = post(URL, mBase64);
                        getData(json);
                    }
                } catch (IOException e) {
                    Log.d(TAG, "onClick: " + e.toString());
                } catch (JSONException e) {
                    Log.d(TAG, "JSONException: " + e.toString());
                }
            }
        };
    }

    private void getData(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
    }

    private View.OnClickListener choiceImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLibrary();
            }
        };
    }

    private void openLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, KEY_LIBRARY);
    }

    String post(String url, String base64) throws IOException {
        OkHttpClient client = new OkHttpClient();
        mMediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mMediaType, base64);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("content-length", "36378")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "8d3f2152-b86e-af83-c5af-0b850bce9cc3")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case KEY_LIBRARY:
                    Uri uriSelectedImage = data.getData();

                    Display display = getActivity().getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int dw = size.x;
                    int dh = size.y;
                    try {
                        // Load up the image's dimensions not the image itself
                        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                        bmpFactoryOptions.inJustDecodeBounds = true;
                        int heightRatio = (int) Math
                                .ceil(bmpFactoryOptions.outHeight / (float) dh);
                        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                                / (float) dw);
                        if (heightRatio > 1 && widthRatio > 1) {
                            if (heightRatio > widthRatio) {
                                bmpFactoryOptions.inSampleSize = heightRatio;
                            } else {
                                bmpFactoryOptions.inSampleSize = widthRatio;
                            }
                        }
                        bmpFactoryOptions.inJustDecodeBounds = false;
                        Bitmap bmp = BitmapFactory.decodeStream(getActivity().getContentResolver()
                                        .openInputStream(uriSelectedImage), null,
                                bmpFactoryOptions);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        mBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    } catch (FileNotFoundException e) {
                        Log.v("ERROR", e.toString());
                    }
                    break;
            }
        }
    }
}
