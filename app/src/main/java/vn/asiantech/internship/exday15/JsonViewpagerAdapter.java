package vn.asiantech.internship.exday15;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by datbu on 23-06-2017.
 */
class JsonViewpagerAdapter extends FragmentPagerAdapter {
    private List<ItemQuestion> mItemQuestions;

    JsonViewpagerAdapter(FragmentManager fm, List<ItemQuestion> itemQuestions) {
        super(fm);
        mItemQuestions = itemQuestions;
    }

    @Override
    public Fragment getItem(final int position) {
        JsonFragment jsonFragment = JsonFragment.newInstance(mItemQuestions.get(position), position);
        jsonFragment.setListener(new JsonFragment.OnListener() {
            @Override
            public void onChoose(int question, int answer) {
                mItemQuestions.get(position).setAnswer(answer);
            }
        });
        return jsonFragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
