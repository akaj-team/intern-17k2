package vn.asiantech.internship.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by PC on 6/23/2017.
 */

public class ConfirmDialog extends DialogFragment {

    private static final String COUNT_KEY = "count";
    private static final String COMPLETE_KEY = "complete";

    public static ConfirmDialog getNewInstance(int count, int complete) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(COUNT_KEY, count);
        bundle.putInt(COMPLETE_KEY, complete);
        confirmDialog.setArguments(bundle);
        return confirmDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int count = getArguments().getInt(COUNT_KEY);
        int complete = getArguments().getInt(COMPLETE_KEY);
        View view = inflater.inflate(R.layout.dialog_confirm, container, false);
        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        tvMessage.setText(String.format(getString(R.string.confirm_message), complete, count));
        return view;
    }
}
