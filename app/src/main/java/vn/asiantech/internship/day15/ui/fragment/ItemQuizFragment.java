package vn.asiantech.internship.day15.ui.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day15.model.Question;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 23/06/2017.
 */
public class ItemQuizFragment extends Fragment {
    public static final String TYPE_QUESTION = "question";
    private Question mQuestion;
    private RadioButton mRbAnswerA;
    private RadioButton mRbAnswerB;
    private RadioButton mRbAnswerC;
    private RadioButton mRbAnswerD;

    public static ItemQuizFragment init(Question question) {
        ItemQuizFragment truitonFrag = new ItemQuizFragment();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(TYPE_QUESTION, question);
        truitonFrag.setArguments(args);
        return truitonFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mQuestion = getArguments().getParcelable(TYPE_QUESTION);
        } catch (NullPointerException e) {
            Log.d("Exception", "NullPointerexception");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_question, container, false);
        TextView tvQuestion = (TextView) v.findViewById(R.id.tvQuestion);
        RadioGroup rgAnswer = (RadioGroup) v.findViewById(R.id.rgAnswer);
        mRbAnswerA = (RadioButton) v.findViewById(R.id.rbAnswerA);
        mRbAnswerB = (RadioButton) v.findViewById(R.id.rbAnswerB);
        mRbAnswerC = (RadioButton) v.findViewById(R.id.rbAnswerC);
        mRbAnswerD = (RadioButton) v.findViewById(R.id.rbAnswerD);

        tvQuestion.setText(mQuestion.getQuestion());
        mRbAnswerA.setText(mQuestion.getAnswerA());
        mRbAnswerB.setText(mQuestion.getAnswerB());
        mRbAnswerC.setText(mQuestion.getAnswerC());
        mRbAnswerD.setText(mQuestion.getAnswerD());
        rgAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int checkedRadioId = group.getCheckedRadioButtonId();
                if (checkedRadioId == R.id.rbAnswerA) {
                    mQuestion.setAnswered(mRbAnswerA.getText().toString());
                } else if (checkedRadioId == R.id.rbAnswerB) {
                    mQuestion.setAnswered(mRbAnswerB.getText().toString());
                } else if (checkedRadioId == R.id.rbAnswerC) {
                    mQuestion.setAnswered(mRbAnswerC.getText().toString());
                } else if (checkedRadioId == R.id.rbAnswerD) {
                    mQuestion.setAnswered(mRbAnswerD.getText().toString());
                }
            }
        });
        return v;
    }
}
