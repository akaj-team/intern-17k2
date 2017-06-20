package vn.asiantech.internship.ui.note;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.NoteAdapter;
import vn.asiantech.internship.models.Note;

/**
 * A simple Note class.
 * Create by Thanh Thien
 */
public class NoteFragment extends Fragment {

    RecyclerView mRecyclerView;
    Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        initView(v);

        List<Note> notes = new ArrayList<>();
        mRecyclerView.setAdapter(new NoteAdapter(R.layout.item_note, notes));
        return v;
    }

    private void initView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        if (getActivity() instanceof NoteActivity) {
            ((NoteActivity) getActivity()).setToolbar(mToolbar);
        }
    }

    //TODO for next ex
    private String saveImageToSdCard(int id) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), id);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, id + ".thg");
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            Log.d("FeedsFragment", "saveImageToSdCard: " + e.toString());
        } catch (IOException e) {
            Log.d("FeedsFragment", "saveImageToSdCard: " + e.toString());
        }
        return file.toString();
    }


}
