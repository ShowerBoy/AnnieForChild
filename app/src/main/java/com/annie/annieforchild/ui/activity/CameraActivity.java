package com.annie.annieforchild.ui.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.annie.annieforchild.Utils.ImageTools;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

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
                    Luban.with(this)
                            .load(SystemUtils.getTempImage().getPath())
                            .ignoreBy(100)
                            .setTargetDir(Environment.getExternalStorageDirectory() + "/annie/")
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(File file) {
                                    onImageSelect(null, file.getAbsolutePath());
                                }

                                @Override
                                public void onError(Throwable throwable) {

                                }
                            }).launch();
//                    Bitmap bmp = ImageTools.getScaleBitmap(this, SystemUtils.getTempImage().getPath());
//                    Bitmap newBitmap;
//                    if (bmp.getWidth() > 800 || bmp.getHeight() > 800) {
//                        newBitmap = ImageTools.zoomBitmap(bmp, bmp.getWidth() / SCALE,  bmp.getHeight() / SCALE);
//                    } else
//                        newBitmap = ImageTools.zoomBitmap(bmp,  bmp.getWidth() / 1, bmp.getWidth() / 1);
//                    bmp.recycle();
//
//                    int route = ImageTools.readPictureDegree(SystemUtils.getTempImage().getPath(), this);
//                    newBitmap = ImageTools.rotaingImageView(route, newBitmap);
//
//                    String name = UUID.randomUUID().toString();
//                    ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory() + "/annie/", name+".JPEG");
//                    if (null != newBitmap) {
//                        //+".png"
//                        onImageSelect(newBitmap, Environment.getExternalStorageDirectory() + "/annie/" + name+".JPEG");
//                    }
                    break;
                case SystemUtils.SELECT_PICTURE:
                    Uri uri = data.getData();
                    String imagePath = null;
                    if (DocumentsContract.isDocumentUri(this, uri)) {
                        //如果是document类型的Uri,则通过document id处理
                        String docId = DocumentsContract.getDocumentId(uri);
                        if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                            String id = docId.split(":")[1];//解析出数字格式的id
                            String selection = MediaStore.Images.Media._ID + "=" + id;
                            imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                        } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                            imagePath = getImagePath(contentUri, null);
                        }
                    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                        //如果是content类型的Uri，则使用普通方式处理
                        imagePath = getImagePath(uri, null);
                    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                        //如果是file类型的Uri，直接获取图片路径即可
                        imagePath = uri.getPath();
                    }

                    Luban.with(this)
                            .load(imagePath)
                            .ignoreBy(100)
                            .setTargetDir(Environment.getExternalStorageDirectory().getPath() + "/annie/")
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(File file) {
                                    onImageSelect(null, file.getAbsolutePath());
                                }

                                @Override
                                public void onError(Throwable throwable) {

                                }
                            }).launch();

//                    ContentResolver resolver = getContentResolver();
//                    Uri originalUri = data.getData();
//                    try {
//                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
//                        if (photo != null) {
//                            Bitmap smallBitmap;
//                            if (photo.getWidth() < 500 || photo.getHeight() < 500) {
//                                smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / 1, photo.getHeight() / 1);
//                            } else {
//                                smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
//                            }
//                            String photoName = UUID.randomUUID().toString();
//                            ImageTools.savePhotoToSDCard(smallBitmap, Environment.getExternalStorageDirectory().getPath() + "/annie/", photoName + ".JPEG");
//                            onImageSelect(smallBitmap, Environment.getExternalStorageDirectory().getPath() + "/annie/" + photoName + ".JPEG");
//                            return;
//                        }
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    onImageSelect(null, null);
                    break;

                default:
                    break;
            }
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    protected abstract void onImageSelect(Bitmap bitmap, String path);
}
