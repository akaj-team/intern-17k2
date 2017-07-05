package vn.asiantech.internship.ui.question;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.asiantech.internship.R;

/**
 * Dialog of activity Result
 * <p>
 * Created by Hai on 6/25/2017.
 */

public class ResultDialogFragment extends DialogFragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnOk = (Button) view.findViewById(R.id.btnResultOK);
        Button btnCancel = (Button) view.findViewById(R.id.btnResultCancel);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnResultOK:
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                startActivity(intent);
                getActivity().finish();
                getDialog().dismiss();
                break;
            case R.id.btnResultCancel:
                getActivity().finish();
                break;
        }
    }
}
