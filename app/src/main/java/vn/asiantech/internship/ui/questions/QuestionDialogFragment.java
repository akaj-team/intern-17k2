package vn.asiantech.internship.ui.questions;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Dialog Fragment show result
 */
public class QuestionDialogFragment extends DialogFragment {

    private static final String KEY_RESULT = "KEY_RESULT";

    private int mResult;

    public static QuestionDialogFragment newInstance(int result) {
        QuestionDialogFragment fragment = new QuestionDialogFragment();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        Bundle args = new Bundle();
        args.putInt(KEY_RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResult = getArguments().getInt(KEY_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_question_dialog, container);

        // Set none background
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);

        TextView tvResult = (TextView) rootView.findViewById(R.id.tvContent);
        Button btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        Button btnOk = (Button) rootView.findViewById(R.id.btnOk);

        String s = getString(R.string.text_you_have_answered) + " " + mResult + "/10";
        tvResult.setText(s);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof QuestionActivity) {
                    ((QuestionActivity) getActivity()).showDialogFragment();
                    getDialog().cancel();
                }
            }
        });
        return rootView;
    }
}
