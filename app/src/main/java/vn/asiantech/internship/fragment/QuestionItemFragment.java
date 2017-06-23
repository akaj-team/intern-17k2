package vn.asiantech.internship.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;

/**
 * Created by ducle on 23/06/2017.
 */

public class QuestionItemFragment extends Fragment {
    private Question mQuestion;
    private TextView mTVQuestion;
    private RadioButton mRbAnswerA;
    private RadioButton mRbAnswerB;
    private RadioButton mRbAnswerC;
    private RadioButton mRbAnswerD;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_question, container, false);
        mQuestion = getArguments().getParcelable("question");
        initViews(view);
        setValue();
        return view;
    }

    public static QuestionItemFragment newInstance(Question question) {
        QuestionItemFragment questionItemFragment = new QuestionItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("question", question);
        questionItemFragment.setArguments(bundle);
        return questionItemFragment;
    }

    private void setValue() {
        mTVQuestion.setText(mQuestion.getQuestion());
        String[] answerArray = new String[]{mQuestion.getAnswerA(), mQuestion.getAnswerB(), mQuestion.getAnswerC(), mQuestion.getAnswerD()};
        Random random = new Random();
        Vector vector = new Vector();
        int position;
        for (int i = 0; i < 4; ) {
            position = random.nextInt(4);
            if (!vector.contains(position)) {
                i++;
                vector.add(position);
            }
        }
        mRbAnswerA.setText(answerArray[(int) vector.get(0)]);
        mRbAnswerB.setText(answerArray[(int) vector.get(1)]);
        mRbAnswerC.setText(answerArray[(int) vector.get(2)]);
        mRbAnswerD.setText(answerArray[(int) vector.get(3)]);
    }

    private void initViews(View view) {
        mTVQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        mRbAnswerA = (RadioButton) view.findViewById(R.id.rbAnswerA);
        mRbAnswerB = (RadioButton) view.findViewById(R.id.rbAnswerB);
        mRbAnswerC = (RadioButton) view.findViewById(R.id.rbAnswerC);
        mRbAnswerD = (RadioButton) view.findViewById(R.id.rbAnswerD);
    }
}
