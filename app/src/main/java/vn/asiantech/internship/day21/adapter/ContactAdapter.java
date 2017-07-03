package vn.asiantech.internship.day21.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day21.model.Contact;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 03/07/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> mContacts;

    public ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.mTvName.setText(mContacts.get(position).getName());
        holder.mTvAddress.setText(mContacts.get(position).getAddress());
        holder.mTvPhone.setText(mContacts.get(position).getPhone());
        if (mContacts.get(position).getGender().equals("male")) {
            holder.mImgGender.setImageResource(R.mipmap.ic_male);
        } else if (mContacts.get(position).getGender().equals("female")) {
            holder.mImgGender.setImageResource(R.mipmap.ic_female);
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Create Contact View Holder
     */
    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvPhone;
        private TextView mTvAddress;
        private ImageView mImgGender;

        ContactViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvNameContact);
            mTvAddress = (TextView) itemView.findViewById(R.id.tvAddressContact);
            mTvPhone = (TextView) itemView.findViewById(R.id.tvPhoneContact);
            mImgGender = (ImageView) itemView.findViewById(R.id.imgGenderContact);
        }
    }
}
