package vn.asiantech.internship.ui.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;

/**
 * Created by AnhHuy on 23-Jun-17.
 */

public class TestFragment extends Fragment {
    private static final String QUESTION = "question";
    private static final String POSITION = "position";

    private TextView mTvQuestion;
    private RadioGroup mRgAnswer;
    private RadioButton mRbAnswerA;
    private RadioButton mRbAnswerB;
    private RadioButton mRbAnswerC;
    private RadioButton mRbAnswerD;

    private int mPosition;
    private Question mQuestion;
    private OnDataChange mOnDataChange;

    public static TestFragment getInstance(Question question, int position) {
        TestFragment testFragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION, question);
        bundle.putInt(POSITION, position);
        testFragment.setArguments(bundle);
        return testFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestion = (Question) getArguments().getSerializable(QUESTION);
            mPosition = getArguments().getInt(POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        mTvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        mRgAnswer = (RadioGroup) view.findViewById(R.id.rgAnswer);
        mRbAnswerA = (RadioButton) view.findViewById(R.id.rbAnswerA);
        mRbAnswerB = (RadioButton) view.findViewById(R.id.rbAnswerB);
        mRbAnswerC = (RadioButton) view.findViewById(R.id.rbAnswerC);
        mRbAnswerD = (RadioButton) view.findViewById(R.id.rbAnswerD);

        mTvQuestion.setText(mQuestion.getQuestion());
        mRbAnswerA.setText(mQuestion.getAnswers().get(0));
        mRbAnswerB.setText(mQuestion.getAnswers().get(1));
        mRbAnswerC.setText(mQuestion.getAnswers().get(2));
        mRbAnswerD.setText(mQuestion.getAnswers().get(3));
        mRgAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rbChoose = (RadioButton) mRgAnswer.findViewById(checkedId);
                mOnDataChange.onChooseAnswer(mPosition, mRgAnswer.indexOfChild(rbChoose));
            }
        });

        return view;
    }

    public void setListener(OnDataChange listener) {
        mOnDataChange = listener;
    }

    public interface OnDataChange {
        void onChooseAnswer(int question, int answer);
    }
}
