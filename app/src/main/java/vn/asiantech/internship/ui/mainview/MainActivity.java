package vn.asiantech.internship.ui.mainview;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;
import vn.asiantech.internship.ui.leftmenu.DrawerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_DATA = "data";
    private static final int REQUEST_GALLERY = 1234;
    private static final int REQUEST_CAMERA = 1235;
    private static final int REQUEST_CROP = 1236;
    private static final int TYPE_GALLERY = 0;

    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerViewDrawer;
    private LinearLayout mLinearLayoutContent;
    private TextView mTvContent;
    private Toolbar mToolbar;

    private DrawerAdapter mAdapterDrawer;
    private List<DrawerItem> mTitleLeftMenus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
        initAdapter();
        initLeftMenu();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mRecyclerViewDrawer = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mLinearLayoutContent = (LinearLayout) findViewById(R.id.lnContent);
        mTvContent = (TextView) findViewById(R.id.tvContent);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initLeftMenu() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                mDrawerLayout.closeDrawers();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mLinearLayoutContent.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initAdapter() {
        String[] titles = {"Feed", "Activity", "Profile", "Friends",
                "Map", "Chat", "Settings", "Home",
                "Store", "History", "Back", "Exit"};
        mTitleLeftMenus = initData(titles);
        mAdapterDrawer = new DrawerAdapter(MainActivity.this, mTitleLeftMenus, new DrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                TextView tvTitleScreen = (TextView) findViewById(R.id.tvTitleFragment);
                DrawerItem drawerItem = mTitleLeftMenus.get(position);
                tvTitleScreen.setText(drawerItem.getTitle());
                mTvContent.setText(drawerItem.getTitle());
                drawerItem.setSelected(true);
                int size = mTitleLeftMenus.size();
                for (int i = 0; i < size; i++) {
                    if (i != position) {
                        mTitleLeftMenus.get(i).setSelected(false);
                    }
                }
                mDrawerLayout.closeDrawers();
                mAdapterDrawer.notifyDataSetChanged();
            }

            @Override
            public void onImageClick() {
                Dialog dialog = onCreateDialog();
                dialog.show();
            }
        });
        mRecyclerViewDrawer.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerViewDrawer.setHasFixedSize(true);
        mRecyclerViewDrawer.setAdapter(mAdapterDrawer);
    }

    /**
     * Initialize List and add DrawerItem
     *
     * @param items list title of navigation
     * @return list of DrawerItem
     */
    private List<DrawerItem> initData(String[] items) {
        List<DrawerItem> list = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            list.add(new DrawerItem(items[i]));
        }
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (data != null && resultCode == RESULT_OK) {
                    functionCropImage(data.getData());
                }
                break;
            case REQUEST_CAMERA:
                if (data != null & resultCode == RESULT_OK) {
                    functionCropImage(data.getData());
                }
                break;
            default:
                if (data != null && resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap selectedBitmap = extras.getParcelable(KEY_DATA);
                    mAdapterDrawer.setImageBitmap(selectedBitmap);
                    mAdapterDrawer.notifyItemChanged(0);
                }
        }
    }

    /*private File getFileFromIntent(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }*/

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_title)
                .setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case TYPE_GALLERY:
                                Intent galleryIntent = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, REQUEST_GALLERY);
                                break;
                            default:
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, REQUEST_CAMERA);
                        }
                    }
                });
        return builder.create();
    }

    public void functionCropImage(Uri picUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", R.dimen.header_image_size);
        cropIntent.putExtra("outputY", R.dimen.header_image_size);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, REQUEST_CROP); //cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    }
}
