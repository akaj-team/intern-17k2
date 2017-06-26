package vn.asiantech.internship.test;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to display  dialog to choose cancel or review result.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-23
 */
public class ResultDialog extends DialogFragment {
    public static final String TEST_LIST = "test list";

    public static ResultDialog newInstance(List<Test> tests) {
        ResultDialog dialog = new ResultDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList(TEST_LIST, (ArrayList<? extends Parcelable>) tests);
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
        final List<Test> tests = getArguments().getParcelableArrayList(TEST_LIST);
        int rightAnswerNumber = 0;
        for (int i = 0; i < (tests != null ? tests.size() : 0); i++) {
            if (tests.get(i).isTrue()) {
                rightAnswerNumber++;
            }
        }
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.textView_yourResult) + rightAnswerNumber + getString(R.string.text_cross) + tests.size());
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putParcelableArrayListExtra(TEST_LIST, (ArrayList<? extends Parcelable>) tests);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }
}
