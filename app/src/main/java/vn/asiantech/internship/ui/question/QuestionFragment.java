package vn.asiantech.internship.ui.question;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;
import vn.asiantech.internship.models.Result;

/**
 * fragment contain one question
 * <p>
 * Created by Hai on 6/25/2017.
 */
public class QuestionFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    private RadioButton mRbAnswerA;
    private RadioButton mRbAnswerB;
    private RadioButton mRbAnswerC;
    private RadioButton mRbAnswerD;

    private Question mQuestion;
    private int mPosition = 0;
    private OnListener mOnListener;
    private Result mResult = new Result();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_question, container, false);
        TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        RadioGroup rgAnswer = (RadioGroup) view.findViewById(R.id.rgAnswer);
        mRbAnswerA = (RadioButton) view.findViewById(R.id.rbA);
        mRbAnswerB = (RadioButton) view.findViewById(R.id.rbB);
        mRbAnswerC = (RadioButton) view.findViewById(R.id.rbC);
        mRbAnswerD = (RadioButton) view.findViewById(R.id.rbD);
        List<String> answers = randomAnswer();
        Collections.shuffle(answers);
        tvQuestion.setText(mQuestion.getQuestion());
        mRbAnswerA.setText(answers.get(0));
        mRbAnswerB.setText(answers.get(1));
        mRbAnswerC.setText(answers.get(2));
        mRbAnswerD.setText(answers.get(3));
        rgAnswer.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnListener = (OnListener) context;
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void setQuestion(Question question) {
        mQuestion = question;
    }

    private List<String> randomAnswer() {
        List<String> answers = new ArrayList<>();
        answers.add(mQuestion.getAnswerA());
        answers.add(mQuestion.getAnswerB());
        answers.add(mQuestion.getAnswerC());
        answers.add(mQuestion.getAnswerD());
        Collections.shuffle(answers);
        return answers;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbA:
                checkAnswer(mRbAnswerA.getText().toString());
                break;
            case R.id.rbB:
                checkAnswer(mRbAnswerB.getText().toString());
                break;
            case R.id.rbC:
                checkAnswer(mRbAnswerC.getText().toString());
                break;
            case R.id.rbD:
                checkAnswer(mRbAnswerD.getText().toString());
                break;
        }
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    private void checkAnswer(String answer) {
        mResult.setQuestionId(mPosition);
        if (answer.equals(mQuestion.getCorrectAnswer())) {
            mResult.setCorrect(true);
        } else {
            mResult.setCorrect(false);
        }
        if (mOnListener != null) {
            mOnListener.OnSendData(mResult);
        }
    }

    interface OnListener {
        void OnSendData(Result result);
    }
}
