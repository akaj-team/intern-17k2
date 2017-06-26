package vn.asiantech.internship.drawer.day15.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day15.models.Result;

/**
 * Created by at-dinhvo on 26/06/2017.
 */
public class ResultDialog extends DialogFragment {

    public static final String DATA_KEY = "data";
    private ShowResultListener mShowResultListener;

    interface ShowResultListener {
        void onShowResult();
    }

    public void setShowResultListener(ShowResultListener showResultListener) {
        mShowResultListener = showResultListener;
    }

    public static ResultDialog newInstance(List<Result> results) {
        ResultDialog dialog = new ResultDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList(DATA_KEY, (ArrayList<? extends Parcelable>) results);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final List<Result> results = getArguments().getParcelableArrayList(DATA_KEY);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        Button btnExit = (Button) view.findViewById(R.id.btnExit);
        TextView tvPoint = (TextView) view.findViewById(R.id.tvPoint);
        int point = 0;
        int size = results.size();
        for (int i = 0; i < size; i++) {
            if (results.get(i).isCorrect()) {
                ++point;
            }
        }
        tvPoint.setText(String.valueOf(point));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
                mShowResultListener.onShowResult();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
