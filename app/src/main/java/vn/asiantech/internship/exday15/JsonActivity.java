package vn.asiantech.internship.exday15;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 23-06-2017.
 */
public class JsonActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private Button mBtnNext;
    private Button mBtnPrev;
    private TextView mTvTitle;

    private JsonViewpagerAdapter mJsonViewpagerAdapter;
    private ArrayList<ItemQuestion> mItemQuestion;
    private ItemQuestion mQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mBtnNext = (Button) findViewById(R.id.btnNext);
        mBtnPrev = (Button) findViewById(R.id.btnPrev);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);

        mBtnPrev.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnNext.setText("Next");
        mBtnPrev.setText("Previous");
        mBtnPrev.setVisibility(View.GONE);

        mItemQuestion = new ArrayList<>();
        try {
            mItemQuestion.addAll(getQuestion(loadJSONFromAsset(),10));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        JsonFragment jsonFragment = new JsonFragment();
        transaction.replace(R.id.flJson, jsonFragment).commit();
    }

    public JSONArray loadJSONFromAsset() {
        JSONArray jsonArray = null;
        String json = null;
        try {
            InputStream is = getAssets().open("question.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
//    public ArrayList<Integer> getRandomObject(JSONArray jsonArray, int indexesWeeNeed) throws JSONException {
//        Random rn = new Random();
//        Set<Integer> generated = new LinkedHashSet<>();
//        while (generated.size() < indexesWeeNeed) {
//            int index = rn.nextInt(10);
//            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
//            int age = jsonObject.getInt("age");
//            if (age < 961) {
//                generated.add(index);
//            }
//        }
//        ArrayList<Integer> arrayList = new ArrayList<>();
//        arrayList.addAll(generated);
//        return arrayList;
//    }

    @Override
    public void onClick(View v) {

    }

//    private List<ItemQuestion> getObject() {
//        List<ItemQuestion> itemQuestion = new ArrayList<>();
//        try {
//            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
//            JSONArray jsonArray = jsonObject.getJSONArray("test");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jb = (JSONObject) jsonArray.get(i);
//                itemQuestion.add(new ItemQuestion(jb.getString("question"), jb.getString("answer_a"), jb.getString("answer_b"),
//                        jb.getString("answer_c"), jb.getString("answer_d"), jb.getString("answer_right")));
//            }
//        } catch (JSONException e) {
//            Log.e("tag", e.toString());
//        }
//        return itemQuestion;
//    }

    public static ArrayList<ItemQuestion> getQuestion(JSONArray jsonArray, int length) throws JSONException {
        ArrayList<ItemQuestion> itemQuestions = new ArrayList<>();
        List<JSONObject> jsonObjects = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObjects.add(jsonArray.getJSONObject(i));
        }
        for (int i = 0; i < length; i++) {
            int position = random.nextInt(jsonObjects.size());
            itemQuestions.add(new ItemQuestion(jsonObjects.get(position)));
            jsonObjects.remove(position);
        }
        return itemQuestions;
    }
}
