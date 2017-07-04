package vn.asiantech.internship.day22.handlerjson;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by at-dinhvo on 03/07/2017.
 */
class HttpHandler {

    private static final String[] METHOD = {"GET", "POST"};

    HttpHandler() {
    }

    String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(METHOD[0]);
            InputStream inputStream = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(inputStream);
        } catch (MalformedURLException e) {
            Log.e("MalformedURLException", "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e("ProtocolException", "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e("Exception", "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e("IOException", "IOException: " + e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e("IOException", "IOException: " + e.getMessage());
            }
        }
        return sb.toString();
    }
}
