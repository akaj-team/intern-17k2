package vn.asiantech.internship.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.QuestionAdapter;

/**
 * Created by ducle on 23/06/2017.
 */

public class MainTestFragment extends android.support.v4.app.Fragment {
    private ViewPager mViewPagerQuestion;
    private QuestionAdapter mQuestionAdapter;
    private List<android.support.v4.app.Fragment> mQuestionItemFragments;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test_main,container,false);
        mQuestionItemFragments=new ArrayList<>();
        mQuestionItemFragments.add(new QuestionItemFragment());
        mViewPagerQuestion=(ViewPager) view.findViewById(R.id.viewPagerQuestion);
        mQuestionAdapter=new QuestionAdapter(getFragmentManager(), mQuestionItemFragments);
        mViewPagerQuestion.setAdapter(mQuestionAdapter);
        return view;
    }
}
