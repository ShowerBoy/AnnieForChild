package com.annie.annieforchild.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.annie.annieforchild.Utils.ImageTools;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by WangLei on 2018/1/24 0024
 */

public abstract class CameraActivity extends BaseActivity {

    private static final int SCALE = 4;//照片缩小比例


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SystemUtils.SELECT_CAMER:
                    Bitmap bmp = ImageTools.getScaleBitmap(this, SystemUtils.getTempImage().getPath());
                    Bitmap newBitmap;
                    if (bmp.getWidth() > 800 || bmp.getHeight() > 800) {
                        newBitmap = ImageTools.zoomBitmap(bmp, bmp.getWidth() / SCALE,  bmp.getHeight() / SCALE);
                    } else
                        newBitmap = ImageTools.zoomBitmap(bmp,  bmp.getWidth() / 1, bmp.getWidth() / 1);
                    bmp.recycle();

                    int route = ImageTools.readPictureDegree(SystemUtils.getTempImage().getPath(), this);
                    newBitmap = ImageTools.rotaingImageView(route, newBitmap);

                    String name = UUID.randomUUID().toString();
                    ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory() + "/annie/", name+".JPEG");
                    if (null != newBitmap) {
                        //+".png"
                        onImageSelect(newBitmap, Environment.getExternalStorageDirectory() + "/annie/" + name+".JPEG");
                    }
                    break;
                case SystemUtils.SELECT_PICTURE:
                    ContentResolver resolver = getContentResolver();
                    Uri originalUri = data.getData();
                    try {
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            Bitmap smallBitmap;
                            if (photo.getWidth() < 500 || photo.getHeight() < 500) {
                                smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / 1, photo.getHeight() / 1);
                            } else {
                                smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                            }
                            String photoName = UUID.randomUUID().toString();
                            ImageTools.savePhotoToSDCard(smallBitmap, Environment.getExternalStorageDirectory().getPath() + "/annie/", photoName + ".JPEG");
                            onImageSelect(smallBitmap, Environment.getExternalStorageDirectory().getPath() + "/annie/" + photoName + ".JPEG");
                            return;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    onImageSelect(null, null);
                    break;

                default:
                    break;
            }
        }
    }

    protected abstract void onImageSelect(Bitmap bitmap, String path);
}
