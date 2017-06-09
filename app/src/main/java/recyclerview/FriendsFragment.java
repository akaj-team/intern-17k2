package recyclerview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

public class FriendsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_fragment, container);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        List<User> users = new ArrayList<>();
        String[] names = getResources().getStringArray(R.array.name);
        String[] descriptions = getResources().getStringArray(R.array.description);
        for (int i = 0; i < names.length; i++) {
            users.add(new User(names[i], descriptions[i]));
        }
        FriendsAdapter adapter = new FriendsAdapter(users);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
