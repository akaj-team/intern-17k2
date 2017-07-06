package vn.asiantech.internship.helpers;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Http Handler.
 * Created by huypham on 03-Jul-17.
 */
class HttpHandler {
    private static final String TAG = "HttpHandler";

    HttpHandler() {
    }

    String makeServiceCall(String requestUrl) {
        String response = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream stream = new BufferedInputStream(connection.getInputStream());
            response = convertStreamToString(stream);
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

    private String convertStreamToString(InputStream inputStream) throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder builder = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e(TAG, "convertStreamToString: " + e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "convertStreamToString: " + e.getMessage());
            }
        }
        return builder.toString();
    }
}
