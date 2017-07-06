package vn.asiantech.internship.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Contact;

/**
 * Adapter for Json Parsing.
 * Created by AnhHuy on 03-Jul-17.
 */
public class JsonParsingAdapter extends RecyclerView.Adapter<JsonParsingAdapter.ViewHolder> {
    private ArrayList<Contact> mContacts;

    public JsonParsingAdapter(ArrayList<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_json_parsing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * View Holder for Json Parsing Item.
     * Created by huypham on 03-Jul-17.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mEmail;
        private TextView mPhone;

        ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tvParsingName);
            mEmail = (TextView) itemView.findViewById(R.id.tvParsingEmail);
            mPhone = (TextView) itemView.findViewById(R.id.tvParsingPhone);
        }

        private void setData(int position) {
            Contact contact = mContacts.get(position);
            mName.setText(contact.getName());
            mEmail.setText(contact.getEmail());
            mPhone.setText(contact.getPhone());
        }
    }
}
