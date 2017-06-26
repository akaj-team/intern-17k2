package vn.asiantech.internship.drawer.day15.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day15.models.Result;

import static vn.asiantech.internship.drawer.day15.ui.QuestionActivity.RESULT_KEY;

/**
 * Created by at-dinhvo on 26/06/2017.
 */

public class ResultDialog extends DialogFragment {

    public static final String DATA_KEY = "data";

    private Button mBtnOk;
    private Button mBtnExit;

    public static ResultDialog newInstance(ArrayList<Result> results) {
        ResultDialog dialog = new ResultDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList(DATA_KEY, results);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_dialog, container);
        mBtnOk = (Button) layout.findViewById(R.id.btnOk);
        mBtnExit = (Button) layout.findViewById(R.id.btnExit);
        getDialog().setTitle("Result Dialog");
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // lấy giá trị tự bundle
        String data = getArguments().getString(DATA_KEY, "");
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = 
                ResultFragment resultFragment = new ResultFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(RESULT_KEY, (ArrayList<? extends Parcelable>) mResults);
                resultFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frContainerQuestion, resultFragment);
                fragmentTransaction.commit();
            }
        });
        mBtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getDialog().dismiss();
                getActivity().finish();
            }
        });
    }
}
