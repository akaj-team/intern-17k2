package vn.asiantech.internship.exday15;

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

/**
 * Created by datbu on 26-06-2017.
 */
public class ResultDialog extends DialogFragment {
    public static final String QUESTION_SET_KEY = "questions";
    private ArrayList<ItemQuestion> itemQuestions;

    public static ResultDialog newInstance(ArrayList<ItemQuestion> questions) {
        ResultDialog confirmDialog = new ResultDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(QUESTION_SET_KEY, questions);
        confirmDialog.setArguments(bundle);
        return confirmDialog;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemQuestions = getArguments().getParcelableArrayList(QUESTION_SET_KEY);
        View view = inflater.inflate(R.layout.dialog_alert, container, false);
        getDialog().setTitle(R.string.dialog_title_show);
        TextView tvDialog = (TextView) view.findViewById(R.id.tvDialog);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        tvDialog.setText(getString(R.string.result_message, getCountQuestionComplete(), itemQuestions.size()));

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PointActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(QUESTION_SET_KEY, itemQuestions);
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
        for (ItemQuestion itemQuestion : itemQuestions) {
            if (itemQuestion.getAnswer() > -1) {
                count++;
            }
        }
        return count;
    }
}
