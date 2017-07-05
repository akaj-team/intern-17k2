package vn.asiantech.internship.day15.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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
import vn.asiantech.internship.day15.model.Result;
import vn.asiantech.internship.day15.ui.activity.QuizActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 25/06/2017.
 */
public class MyDialogFragment extends DialogFragment {
    public static final String TYPE_RESULT = "result";
    private String mResult;
    private QuizActivity mQuizActivity;
    private List<Result> mResults;

    public static MyDialogFragment init(List<Result> results, String result) {
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_RESULT, result);
        args.putParcelableArrayList(ResultFragment.LIST_RESULT, (ArrayList<? extends Parcelable>) results);
        myDialogFragment.setArguments(args);
        return myDialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mQuizActivity = (QuizActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResult = getArguments() != null ? getArguments().getString(TYPE_RESULT) : " ";
        mResults = getArguments().getParcelableArrayList(ResultFragment.LIST_RESULT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        TextView tvNotify = (TextView) v.findViewById(R.id.tvNotifyDialog);
        Button btnOk = (Button) v.findViewById(R.id.btnOKDialog);
        Button btnCancel = (Button) v.findViewById(R.id.btnCancelDialog);
        getDialog().setTitle("RESULT");
        tvNotify.setText(getString(R.string.question_order, mResult));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuizActivity.replaceFragment(ResultFragment.init(mResults), false);
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }
}
