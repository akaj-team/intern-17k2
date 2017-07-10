package vn.asiantech.internship.day15.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day15.adapter.ResultAdapter;
import vn.asiantech.internship.day15.models.Result;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_result, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerViewResult);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addListResult();
    }

    private void addListResult() {
        List<Result> results = getArguments().getParcelableArrayList(QuestionActivity.RESULT_KEY);
        ResultAdapter resultAdapter = new ResultAdapter(results);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(resultAdapter);
    }
}
