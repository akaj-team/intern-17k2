package vn.asiantech.internship.dialogs;

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

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;
import vn.asiantech.internship.ui.main.ResultActivity;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/23/2017
 */
public class ConfirmDialog extends DialogFragment {

    public static final String QUESTION_SET_KEY = "questions";

    private ArrayList<Question> mQuestionSet;

    public static ConfirmDialog getNewInstance(ArrayList<Question> questions) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(QUESTION_SET_KEY, questions);
        confirmDialog.setArguments(bundle);
        return confirmDialog;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mQuestionSet = getArguments().getParcelableArrayList(QUESTION_SET_KEY);
        View view = inflater.inflate(R.layout.dialog_confirm, container, false);
        getDialog().setTitle(getString(R.string.confirm_title));
        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        tvMessage.setText(getString(R.string.confirm_message, getCountQuestionComplete(), mQuestionSet.size()));

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(QUESTION_SET_KEY, mQuestionSet);
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

    private int getCountQuestionComplete() {
        int count = 0;
        for (Question q : mQuestionSet) {
            if (q.getUserAnswer() > -1) {
                count++;
            }
        }
        return count;
    }
}
