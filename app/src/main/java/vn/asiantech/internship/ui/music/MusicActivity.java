package vn.asiantech.internship.ui.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Music;

/**
 * MusicActivity created by Thanh Thien
 */
public class MusicActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ArrayList<Music> mMusics = new ArrayList<>();
    private MusicPlayFragment mMusicPlayFragment;
    private MusicShowListFragment mMusicShowListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        addData();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mMusicPlayFragment, mMusicShowListFragment));
    }

    public ArrayList<Music> getSongs() {
        return mMusics;
    }

    public void playSong(int position) {
        mMusicPlayFragment.changePosition(position);
        mViewPager.setCurrentItem(1, true);
        setHighLight(position);
    }

    public void setHighLight(int position) {
        mMusicShowListFragment.setHighLight(position);
    }

    private void addData() {
        mMusicPlayFragment = MusicPlayFragment.newInstance(0);
        mMusicShowListFragment = MusicShowListFragment.newInstance(mMusics);
        mMusics.add(new Music("Stay (Acoustic)",
                "Zedd ft Alessia Cara",
                "http://vip.img.cdn.keeng.vn/medias/images/images_thumb/f_medias/singer/2012/07/27/f02fd92988c427d6c3363594c157b6a7c1a62daa_103_103.jpg",
                "http://hot5.medias.keeng.vn/mp3/sas_01/songv3/Uni1206/Stay_Acoustic.mp3"));
        mMusics.add(new Music(
                "Don't Look Down",
                "Martin Garrix ft Usher",
                "http://vip.img.cdn.keeng.vn/sata11/images/images_thumb/f_sata11/singer/2017/05/30/Ln6RoalPYNg3EP0pcGl1592d6df7588d6_103_103.jpg",
                "http://hot5.medias.keeng.vn/mp3/sata04/songv3/2017/05/29/Vr5nMj34rpwb4sNXOGOM592b793b2d5ba.mp3"
        ));
        mMusics.add(new Music(
                "Swish Swish",
                "Katy Perry ft Nicki Minaj",
                "http://vip.img.cdn.keeng.vn/sata11/images/images_thumb/f_sata11/singer/2017/04/26/hdn8fSltRRRUnfrNgPOQ590054708c2fa_103_103.jpg",
                "http://hot4.medias.keeng.vn/mp3/sata10/songv3/2017/05/22/j9xne09Q62csEmQEb8qh5922a987dee3f.mp3"
        ));
        mMusics.add(new Music(
                "Bad Liar",
                "Selena Gomez",
                "http://vip.img.cdn.keeng.vn/medias/images/images_thumb/f_medias_6/singer/2014/10/13/b5cd6b2b2e4d5b6fca2695cac29d908ce5d58639_103_103.jpg",
                "http://hot4.medias.keeng.vn/mp3/sata07/songv3/2017/05/19/dD7cIyqVFkOviCIpVdlE591e666439e1c.mp3"
        ));
    }
}
