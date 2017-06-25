package vn.asiantech.internship.drawer.day15.ui;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.drawer.day15.models.Question;

/**
 * Created by at-dinhvo on 25/06/2017.
 */

class JSONHandler {
    /*
    * "question": "Sông Thạch Hãn ở tỉnh Quảng Trị đổ ra biển bởi cửa sông nào?",
      "answer_a": "Cửa Việt",
      "answer_b": "Cửa Tùng",
      "answer_c": "Cửa Đại",
      "answer_d": "Cửa Hội",
      "answer_right": "Cửa Việt"
    *
    * */
    private static final String QUESTION = "question";
    private static final String ANSWER_A = "answer_a";
    private static final String ANSWER_B = "answer_b";
    private static final String ANSWER_C = "answer_c";
    private static final String ANSWER_D = "answer_d";
    private static final String ANSWER_RIGHT = "answer_right";

    private Context mContext;

    JSONHandler(Context context){
        mContext = context;
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(mContext));
            JSONArray jsonArray = jsonObject.getJSONArray("listQuestion");
            String[] answers;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jb = (JSONObject) jsonArray.get(i);
                answers = new String[4];
                answers[0] = jb.getString(ANSWER_A);
                answers[1] = jb.getString(ANSWER_B);
                answers[2] = jb.getString(ANSWER_C);
                answers[3] = jb.getString(ANSWER_D);
                questions.add(new Question(jb.getString(QUESTION), answers, jb.getString(ANSWER_RIGHT)));
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
        }
        return questions;
    }

    private String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream inputStream = context.getAssets().open("question.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e("IOException", e.toString());
            return null;
        }
        return json;
    }
}
