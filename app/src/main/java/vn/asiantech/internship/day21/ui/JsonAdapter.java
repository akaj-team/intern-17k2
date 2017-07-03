package vn.asiantech.internship.day21.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day21.models.Contact;

/**
 * Created by at-dinhvo on 03/07/2017.
 */
public class JsonAdapter extends RecyclerView.Adapter<JsonAdapter.ItemViewHolder> {

    private List<Contact> mContacts;

    public JsonAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_contact, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.mTvName.setText(contact.getName());
        holder.mTvPhone.append(contact.getPhone().getMobile());
        holder.mTvEmail.append(contact.getEmail());
        if (!contact.getGender().equals("male")) {
            holder.mTvName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_female, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Created ItemViewHolder
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvEmail;
        private TextView mTvPhone;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvContactName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvContactEmail);
            mTvPhone = (TextView) itemView.findViewById(R.id.tvContactPhone);
        }
    }
}
