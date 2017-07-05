package vn.asiantech.internship.ui.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;

/**
 * Fragment for Test
 * Created by huypham on 23-Jun-17.
 */
public class TestFragment extends Fragment {
    private static final String QUESTION = "question";
    private static final String POSITION = "position";

    private RadioGroup mRgAnswer;

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
        TextView mTvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        mRgAnswer = (RadioGroup) view.findViewById(R.id.rgAnswer);
        RadioButton mRbAnswerA = (RadioButton) view.findViewById(R.id.rbAnswerA);
        RadioButton mRbAnswerB = (RadioButton) view.findViewById(R.id.rbAnswerB);
        RadioButton mRbAnswerC = (RadioButton) view.findViewById(R.id.rbAnswerC);
        RadioButton mRbAnswerD = (RadioButton) view.findViewById(R.id.rbAnswerD);

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

    /**
     * Interface change data
     * Created by huypham on 23-Jun-17.
     */
    public interface OnDataChange {
        void onChooseAnswer(int question, int answer);
    }
}
