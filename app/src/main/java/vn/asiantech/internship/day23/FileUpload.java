package vn.asiantech.internship.day23;

/**
 * Created by at-dinhvo on 05/07/2017.
 */

public class FileUpload{

    /*private String webAddressToPost = "http://2.pik.vn";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(webAddressToPost);

            HandlerUploadFile entity = new HandlerUploadFile(HttpMultipartMode.BROWSER_COMPATIBLE);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();
            String file = Base64.encodeBytes(data);

            entity.addPart("uploaded", new StringBody(file));
            entity.addPart("someOtherStringToSend", new StringBody("your string here"));

            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost,localContext);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));

            String sResponse = reader.readLine();
            return sResponse;
        } catch (Exception e) {
            // something went wrong. connection with the server error
        }
        return null;
    }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }*/
}