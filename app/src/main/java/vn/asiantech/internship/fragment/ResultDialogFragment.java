package vn.asiantech.internship.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.activity.ResultActivity;
import vn.asiantech.internship.models.Question;

/**
 * Created by ducle on 26/06/2017.
 */

public class ResultDialogFragment extends DialogFragment {
    public static final String KEY_QUESTIONS = "questions";
    private List<Question> mQuestions;

    public static ResultDialogFragment newInstance(ArrayList<Question> questions) {
        ResultDialogFragment resultDialogFragment = new ResultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_QUESTIONS, questions);
        resultDialogFragment.setArguments(bundle);
        return resultDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mQuestions = getArguments().getParcelableArrayList(KEY_QUESTIONS);
        int checkedQuestionNumber = getCheckedQuestionNumber();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialogfragment_title))
                .setMessage(getString(R.string.dialogfragment_message, checkedQuestionNumber));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putParcelableArrayListExtra(KEY_QUESTIONS, (ArrayList<Question>) mQuestions);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }

    private int getCheckedQuestionNumber() {
        int checkedQuestionNumber = 0;
        for (int i = 0; i < mQuestions.size(); i++) {
            if (mQuestions.get(i).isChecked()) {
                checkedQuestionNumber++;
            }
        }
        return checkedQuestionNumber;
    }
}
