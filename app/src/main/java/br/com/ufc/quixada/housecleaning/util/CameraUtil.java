package br.com.ufc.quixada.housecleaning.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

import androidx.core.app.ActivityCompat;

public class CameraUtil {
    public boolean checkCameraPermission(Activity activity) {
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestCameraPermission(Activity activity) {
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 2);
        }
    }

    public void takeAPicture(Activity activity) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(i, 3);
    }
}
