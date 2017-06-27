package vn.asiantech.internship.drawer.day15.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by at-dinhvo on 27/06/2017.
 */
public class FinishDialog extends DialogFragment {

    public static FinishDialog newInstance() {
        return new FinishDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        Button btnExit = (Button) view.findViewById(R.id.btnExit);
        TextView tvPoint = (TextView) view.findViewById(R.id.tvPoint);
        tvPoint.setVisibility(View.GONE);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
                getActivity().finish();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }
}
