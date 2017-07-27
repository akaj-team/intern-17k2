package vn.asiantech.internship.contact;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Author AsianTech Inc.
 * Created by at-hangtran on 03/07/2017.
 */
class ContactAdapter extends RecyclerView.Adapter {
    private final List<Contact> mContacts;

    ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_contact_recyclerview, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
        contactViewHolder.mTvName.setText(mContacts.get(position).getName());
        contactViewHolder.mTvMail.setText(mContacts.get(position).getMail());
        contactViewHolder.mTvPhone.setText(mContacts.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * MyViewHolder register for contact.
     */
    private class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvMail;
        private TextView mTvPhone;

        ContactViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvContactName);
            mTvMail = (TextView) itemView.findViewById(R.id.tvContactMail);
            mTvPhone = (TextView) itemView.findViewById(R.id.tvContactPhone);
        }
    }
}
