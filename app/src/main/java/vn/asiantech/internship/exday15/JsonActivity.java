package vn.asiantech.internship.exday15;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.Objects;
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
    private ArrayList<ItemQuestion> mItemQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        initView();

        mBtnPrev.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnNext.setText(R.string.tv_next);
        mBtnPrev.setText(R.string.tv_prev);
        mBtnPrev.setVisibility(View.GONE);

        mItemQuestion = getQuestion(loadJSONFromAsset(), 10);
        JsonViewpagerAdapter jsonViewpagerAdapter = new JsonViewpagerAdapter(getSupportFragmentManager(), mItemQuestion);
        mViewPager.setAdapter(jsonViewpagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position > 0) {
                    mBtnPrev.setVisibility(View.VISIBLE);
                } else {
                    mBtnPrev.setVisibility(View.GONE);
                }
                if (position < mItemQuestion.size() - 1) {
                    mBtnNext.setVisibility(View.VISIBLE);
                    mBtnNext.setText(R.string.tv_next);
                } else {
                    mBtnNext.setVisibility(View.VISIBLE);
                    mBtnNext.setText(R.string.tv_result);
                }
                mTvTitle.setText(getString(R.string.title_question, position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mBtnNext = (Button) findViewById(R.id.btnNext);
        mBtnPrev = (Button) findViewById(R.id.btnPrev);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                if (Objects.equals(mBtnNext.getText().toString(), getString(R.string.tv_result))) {
                    showDialog();
                } else {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
                break;
            case R.id.btnPrev:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

    public JSONArray loadJSONFromAsset() {
        JSONArray jsonArray = null;
        String json;
        try {
            InputStream is = getAssets().open("question.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(json);
        } catch (IOException ex) {
            return null;
        } catch (JSONException e) {
            Log.d("Tag", "loadJSONFromAsset: " + e);
        }
        return jsonArray;
    }

    public static ArrayList<ItemQuestion> getQuestion(JSONArray jsonArray, int dataSetLenght) {
        ArrayList<ItemQuestion> itemQuestions = new ArrayList<>();
        List<JSONObject> jsonObjects = new ArrayList<>();
        Random random = new Random();
        int index;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObjects.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                return null;
            }
        }
        for (int i = 0; i < dataSetLenght; i++) {
            index = random.nextInt(jsonObjects.size());
            itemQuestions.add(new ItemQuestion(jsonObjects.get(index)));
            jsonObjects.remove(index);
        }
        return itemQuestions;
    }

    void showDialog() {
        DialogFragment newFragment = ResultDialog.getNewInstance(mItemQuestion);
        newFragment.show(getSupportFragmentManager(), "");
    }
}
