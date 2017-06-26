package vn.asiantech.internship.day15.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day15.adapter.ResultAdapter;
import vn.asiantech.internship.day15.model.Result;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 23/06/2017.
 */
public class ResultFragment extends Fragment {
    public static final String LIST_RESULT = "Results";
    private List<Result> mResults;

    public static ResultFragment init(List<Result> results) {
        ResultFragment resultFragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(LIST_RESULT, (ArrayList<? extends Parcelable>) results);
        resultFragment.setArguments(args);
        return resultFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResults = getArguments().getParcelableArrayList(LIST_RESULT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewResult);
        Button btnOk= (Button) v.findViewById(R.id.btnOkResult);
        ResultAdapter resultAdapter = new ResultAdapter(mResults);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(resultAdapter);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDismissFragment().show(getFragmentManager(), "dismiss dialog");
            }
        });
        return v;
    }
}
