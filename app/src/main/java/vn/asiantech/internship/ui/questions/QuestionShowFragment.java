package vn.asiantech.internship.ui.questions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionShowFragment extends Fragment {

    private static final String KEY_QUESTION = "KEY_QUESTION";
    private Question mQuestion;

    public QuestionShowFragment() {
    }

    public static QuestionShowFragment newInstance(Question questions) {
        QuestionShowFragment fragment = new QuestionShowFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_QUESTION, questions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = (Question) getArguments().getSerializable(KEY_QUESTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_show, container, false);
        TextView tvQuestion = (TextView) v.findViewById(R.id.tvQuestion);

        RadioButton rbA = (RadioButton) v.findViewById(R.id.rbA);
        RadioButton rbB = (RadioButton) v.findViewById(R.id.rbB);
        RadioButton rbC = (RadioButton) v.findViewById(R.id.rbC);
        RadioButton rbD = (RadioButton) v.findViewById(R.id.rbD);

        tvQuestion.setText(mQuestion.getQuestion());
        rbA.setText(mQuestion.getAnswerA());
        rbB.setText(mQuestion.getAnswerB());
        rbC.setText(mQuestion.getAnswerC());
        rbD.setText(mQuestion.getAnswerB());

        rbA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                }
            }
        });
        return v;
    }

    /**
     * OnCheckAnswerListener
     */
    public interface OnCheckAnswerListener {
        void onClickAnswer(int question, String answer);
    }
}
