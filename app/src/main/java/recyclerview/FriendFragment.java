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

/**
 * Used to display recyclerView.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */
public class FriendFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewListFriend);
        List<User> users = new ArrayList<>();
        String[] names = getResources().getStringArray(R.array.names);
        String[] descriptions = getResources().getStringArray(R.array.descriptions);
        for (int i = 0; i < names.length; i++) {
            users.add(new User(names[i], descriptions[i]));
        }
        FriendAdapter adapter = new FriendAdapter(users);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
