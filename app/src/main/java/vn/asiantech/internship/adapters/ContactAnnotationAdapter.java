package vn.asiantech.internship.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Contact;

/**
 *
 * Created by quanghai on 04/07/2017.
 */
public class ContactAnnotationAdapter extends RecyclerView.Adapter<ContactAnnotationAdapter.ContactHolder> {
    private List<Contact> mContacts = new ArrayList<>();

    public ContactAnnotationAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_annotation, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        holder.mTvName.setText(mContacts.get(position).getName());
        holder.mTvEmail.setText(mContacts.get(position).getEmail());
        holder.mTvPhone.setText(mContacts.get(position).getPhone().getMobile());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Created by quanghai on 04/07/2017.
     */
    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvEmail;
        private TextView mTvPhone;

        ContactHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mTvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
        }
    }
}
