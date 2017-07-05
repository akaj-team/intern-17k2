package vn.asiantech.internship.interfaces;

import vn.asiantech.internship.models.Song;

/**
 * Created by ducle on 05/07/2017.
 */

public interface OnUpdateDataListerner {
    void onShowProgressDialog();
    void onAddSong(Song song);
    void onShowFragment();
    void onCloseProgressDialog();
}
