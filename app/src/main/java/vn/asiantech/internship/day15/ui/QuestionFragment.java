package vn.asiantech.internship.day15.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day15.models.Question;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    private RadioButton mRbAnswerA;
    private RadioButton mRbAnswerB;
    private RadioButton mRbAnswerC;
    private RadioButton mRbAnswerD;
    private String mAnswer;
    private Question mQuestion;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_question, container, false);
        initUI(layout);
        return layout;
    }

    public void setQuestion(Question question) {
        mQuestion = question;
    }

    private void initUI(View view) {
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rgAnswer);
        mRbAnswerA = (RadioButton) view.findViewById(R.id.rbAnswerA);
        mRbAnswerB = (RadioButton) view.findViewById(R.id.rbAnswerB);
        mRbAnswerC = (RadioButton) view.findViewById(R.id.rbAnswerC);
        mRbAnswerD = (RadioButton) view.findViewById(R.id.rbAnswerD);
        TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        tvQuestion.setText(mQuestion.getQuestion());
        mRbAnswerA.setText(mQuestion.getAnswers()[0]);
        mRbAnswerB.setText(mQuestion.getAnswers()[1]);
        mRbAnswerC.setText(mQuestion.getAnswers()[2]);
        mRbAnswerD.setText(mQuestion.getAnswers()[3]);
        addEvents(radioGroup);
    }

    private void addEvents(RadioGroup rg) {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbAnswerA:
                        mAnswer = mRbAnswerA.getText().toString();
                        break;
                    case R.id.rbAnswerB:
                        mAnswer = mRbAnswerB.getText().toString();
                        break;
                    case R.id.rbAnswerC:
                        mAnswer = mRbAnswerC.getText().toString();
                        break;
                    case R.id.rbAnswerD:
                        mAnswer = mRbAnswerD.getText().toString();
                        break;
                }
                checkAnswer(mAnswer);
            }
        });
    }

    private void checkAnswer(String answer) {
        if (answer.equals(mQuestion.getCorrectAnswer())) {
            mQuestion.setCorrect(true);
        } else {
            mQuestion.setCorrect(false);
        }
    }
}
