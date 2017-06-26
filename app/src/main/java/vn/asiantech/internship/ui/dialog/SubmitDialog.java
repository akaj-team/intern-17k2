package vn.asiantech.internship.ui.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;
import vn.asiantech.internship.ui.view.ResultActivity;

/**
 * Created by AnhHuy on 26-Jun-17.
 */

public class SubmitDialog extends DialogFragment {
    public static final String QUESTION_SET = "Question set";
    private ArrayList<Question> mQuestionList;

    public static SubmitDialog getInstance(ArrayList<Question> questions){
        SubmitDialog submitDialog = new SubmitDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(QUESTION_SET, questions);
        submitDialog.setArguments(bundle);
        return submitDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_submit, container, false);
        mQuestionList = getArguments().getParcelableArrayList(QUESTION_SET);
        getDialog().setTitle(R.string.title_dialog_confirm_submit);

        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        tvMessage.setText(getString(R.string.message_dialog_message_submit, getCountQuestion(), mQuestionList.size()));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(QUESTION_SET, mQuestionList);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    private int getCountQuestion(){
        int count = 0;
        for (Question question: mQuestionList) {
            if (question.getAnswerChoose() > -1){
                count++;
            }
        }
        return count;
    }
}
