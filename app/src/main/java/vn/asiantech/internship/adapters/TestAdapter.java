package vn.asiantech.internship.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pc.mycanvas.sad.QuestionFragment;import java.util.List;

import vn.asiantech.internship.ui.fragments.QuestionFragment;

/**
 * Created by PC on 6/23/2017.
 */

public class TestAdapter extends FragmentStatePagerAdapter {
    private List<QuestionFragment> questionFragments;

    public TestAdapter(FragmentManager fm, List<QuestionFragment> list) {
        super(fm);
        questionFragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        return questionFragments.get(position);
    }

    @Override
    public int getCount() {
        return questionFragments.size();
    }
}
