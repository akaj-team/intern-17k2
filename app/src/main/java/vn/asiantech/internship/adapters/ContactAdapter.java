package vn.asiantech.internship.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Contact;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/3/2017.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactItemHolder> {

    private List<Contact> mContacts;

    public ContactAdapter(List<Contact> contacts) {
        this.mContacts = contacts;
    }

    @Override
    public ContactItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactItemHolder holder, int position) {
        holder.mTvName.setText(mContacts.get(position).getName());
        holder.mTvEmail.setText(mContacts.get(position).getEmail());
        holder.mTvPhone.setText(mContacts.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * This class used to custom item of contacts
     */
    final class ContactItemHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        TextView mTvEmail;
        TextView mTvPhone;

        ContactItemHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mTvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
        }
    }
}
