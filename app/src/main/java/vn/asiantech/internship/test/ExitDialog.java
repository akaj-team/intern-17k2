package vn.asiantech.internship.test;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Used to custom and show exit dialog.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-23
 */
public class ExitDialog extends DialogFragment {
    private static final String DIALOG_TITLE = "title";

    public static ExitDialog newInstance() {
        ExitDialog dialog = new ExitDialog();
        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE, "Do you want to restart this app?");
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(getArguments().getString(DIALOG_TITLE));
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TestActivity.class));
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
                getActivity().finish();
            }
        });
    }
}
