package vn.asiantech.internship.ui.question;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Result;

/**
 * Dialog of QuestionActivity
 * <p>
 * Created by Hai on 6/25/2017.
 */

public class QuestionDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String KEY_INTENT = "intent";
    public static final String KEY_BUNDLE = "bundle";

    private List<Result> mResults = new ArrayList<>();

    public static QuestionDialogFragment newInstance(List<Result> results) {
        QuestionDialogFragment fragment = new QuestionDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(QuestionActivity.KEY_SEND_LIST, (ArrayList<? extends Parcelable>) results);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mResults = getArguments().getParcelableArrayList(QuestionActivity.KEY_SEND_LIST);
        return inflater.inflate(R.layout.fragment_question_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnOk = (Button) view.findViewById(R.id.btnOK);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(KEY_BUNDLE, (ArrayList<? extends Parcelable>) mResults);
                intent.putExtra(KEY_INTENT, bundle);
                startActivity(intent);
                getDialog().dismiss();
                getActivity().finish();
                break;
            case R.id.btnCancel:
                getDialog().dismiss();
                getActivity().finish();
                break;
        }
    }

}
