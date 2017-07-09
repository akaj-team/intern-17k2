package vn.asiantech.internship.day23;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by at-dinhvo on 09/07/2017.
 */

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    String makeServiceCall(String reqUrl, String base64) {
        String response = " ";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("image", base64);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                response = convertStreamToString(conn.getInputStream());
            } else {
                response = "";
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "UnsupportedEncodingException ");
        }
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            }
        } catch (IOException e) {
            Log.d(TAG, "IOException when convert Stream to String: ");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.d(TAG, "IOException when convert Stream to String: ");
            }
        }
        return sb.toString();
    }
}
