package vn.asiantech.internship.ui.anitation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Contact;

/**
 * Created by Thanh THien on 7/3/2017.
 * ContactAdapter
 */
class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<Contact> mContacts;

    private ContactAdapter(List<Contact> Contacts) {
        mContacts = Contacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_contact, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Contact contact = mContacts.get(position);
        myViewHolder.mTvName.setText(contact.getName());
        myViewHolder.mTvEmail.setText(contact.getEmail());
        myViewHolder.mTvPhone.setText(contact.getContacts().getMobile());
        if (contact.getGender().equals("male")) {
            myViewHolder.mRlContainer.setSelected(true);
            myViewHolder.mImgGender.setSelected(true);
            myViewHolder.mImgGender.setImageResource(R.drawable.male);
        } else {
            myViewHolder.mRlContainer.setSelected(false);
            myViewHolder.mImgGender.setSelected(false);
            myViewHolder.mImgGender.setImageResource(R.drawable.ic_female);
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Viewhoder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvEmail;
        private TextView mTvPhone;
        private ImageView mImgGender;
        private RelativeLayout mRlContainer;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mTvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            mImgGender = (ImageView) itemView.findViewById(R.id.imgGender);
            mRlContainer = (RelativeLayout) itemView.findViewById(R.id.rlContainer);
        }
    }
}
