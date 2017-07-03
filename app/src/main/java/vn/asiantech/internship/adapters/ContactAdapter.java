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
 * Created by quanghai on 03/07/2017.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> mContacts = new ArrayList<>();

    public ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.mTvName.setText(contact.getName());
        holder.mTvEmail.setText(contact.getEmail());
        holder.mTvPhone.setText(contact.getPhone().getMobile());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Item Contact
     */
    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvEmail;
        private TextView mTvPhone;

        ContactViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mTvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
        }
    }
}
