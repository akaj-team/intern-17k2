package vn.asiantech.internship.notesqlite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to collect and display list data to the View.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */

class NoteAdapter extends RecyclerView.Adapter {
    private List<Note> mNotes = new ArrayList<>();
    private Context mContext;

    NoteAdapter(Context context, List<Note> notes) {
        this.mContext = context;
        this.mNotes = notes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_note_list, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
        noteViewHolder.mTvDayOfWeek.setText(mNotes.get(position).getDayOfWeek());
        noteViewHolder.mTvDay.setText(mNotes.get(position).getDay());
        noteViewHolder.mTvMonth.setText(mNotes.get(position).getMonth());
        noteViewHolder.mTvHour.setText(mNotes.get(position).getHour());
        noteViewHolder.mTvTitle.setText(mNotes.get(position).getTitle());
        noteViewHolder.mTvContent.setText(mNotes.get(position).getContent());
        noteViewHolder.mImage.setImageBitmap(BitmapFactory.decodeFile(mNotes.get(position).getPathImage()));
        saveImage(noteViewHolder.mImage, position);
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    /**
     * Used to register for note.
     */
    private class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvDayOfWeek;
        private final TextView mTvDay;
        private final TextView mTvMonth;
        private final TextView mTvHour;
        private final TextView mTvTitle;
        private final TextView mTvContent;
        private final ImageView mImage;

        NoteViewHolder(View itemView) {
            super(itemView);
            mTvDayOfWeek = (TextView) itemView.findViewById(R.id.tvDayOfWeek);
            mTvDay = (TextView) itemView.findViewById(R.id.tvDay);
            mTvMonth = (TextView) itemView.findViewById(R.id.tvMonth);
            mTvHour = (TextView) itemView.findViewById(R.id.tvHour);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mImage = (ImageView) itemView.findViewById(R.id.imgNote);
        }
    }

    private void saveImage(ImageView imageView, int position) {
        BitmapDrawable btmpDr = (BitmapDrawable) imageView.getDrawable();
        Bitmap bm = btmpDr.getBitmap();
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File image = new File(sdCardDirectory, position + "image.png");
        boolean success = false;
        try {

            FileOutputStream outStream = new FileOutputStream(image);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            try {
                outStream.flush();
                outStream.close();
            } catch (IOException e) {
                Log.e("SaveImage: ", e.toString());
            }
            success = true;
        } catch (FileNotFoundException e) {
            Log.e("SaveImage: ", e.toString());
        }
        if (success) {
            Toast.makeText(mContext, "Image saved with success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Error during image saving", Toast.LENGTH_LONG).show();
        }
    }
}
