package vn.asiantech.internship.day15.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day15.ui.activity.QuizActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 26/06/2017.
 */
public class MyDismissFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_dismiss, container, false);
        TextView tvNotify = (TextView) v.findViewById(R.id.tvNotifyDialogDismiss);
        Button btnOkDismiss = (Button) v.findViewById(R.id.btnOKDismissDialog);
        Button btnCancelDismiss = (Button) v.findViewById(R.id.btnCancelDismissDialog);
        getDialog().setTitle("CHOOSE");
        tvNotify.setText(R.string.dismiss_dialog);
        btnOkDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), QuizActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        btnCancelDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return v;
    }
}
