package vn.asiantech.internship.ui.fragments;

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
 * Created by PC on 6/23/2017.
 */
public class QuestionFragment extends Fragment {

    private static final String QUESTION_KEY = "question";
    private static final String POSITION_KEY = "position";

    private TextView mTvQuestion;
    private RadioGroup mRgAnswer;
    private RadioButton mRbAnswerA;
    private RadioButton mRbAnswerB;
    private RadioButton mRbAnswerC;
    private RadioButton mRbAnswerD;

    private int mPosition;
    private Question mQuestion;
    private int mAnswer = -1;
    private OnDataChanged mListener;

    public static QuestionFragment getNewInstance(Question question, int position) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION_KEY, question);
        bundle.putInt(POSITION_KEY, position);
        questionFragment.setArguments(bundle);
        return questionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = (Question) getArguments().getSerializable(QUESTION_KEY);
        mPosition = getArguments().getInt(POSITION_KEY);
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
                RadioButton rdChooser = (RadioButton) mRgAnswer.findViewById(checkedId);
            }
        });

        return view;
    }

    public void setListener(OnDataChanged listener) {
        mListener = listener;
    }

    public interface OnDataChanged {
        void onAnswerChoosed(int question, int answer);
    }

}
