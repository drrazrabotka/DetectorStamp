package com.f.boof.utility;


import android.graphics.Bitmap;
import android.graphics.Matrix;

public class MyUtility {
    public static Bitmap bitmapResize(Bitmap bitmap, float scale) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap scaleSize(Bitmap bitmap){

        if(bitmap.getWidth() == bitmap.getHeight()){
            if(bitmap.getWidth() > 1080){
                if(bitmap.getWidth() < 1500){
                    if(bitmap.getWidth() < 2000){
                        bitmap = bitmapResize(bitmap,0.8f);
                    }
                }
                if(bitmap.getWidth() > 2000){
                    if(bitmap.getWidth() < 3000){
                        bitmap = bitmapResize(bitmap,0.5f);
                    }
                }
                if(bitmap.getWidth() > 3000){
                    bitmap = bitmapResize(bitmap,0.19f);
                }

            }
        }
        if(bitmap.getWidth() > bitmap.getHeight()){
            if(bitmap.getWidth() > 1080){
                if(bitmap.getWidth() < 1500){
                    if(bitmap.getWidth() < 2000){
                        bitmap = bitmapResize(bitmap,0.8f);
                    }
                }
                if(bitmap.getWidth() > 2000){
                    if(bitmap.getWidth() < 3000){
                        bitmap = bitmapResize(bitmap,0.5f);
                    }
                }
                if(bitmap.getWidth() > 3000){
                    bitmap = bitmapResize(bitmap,0.19f);
                }
            }
        }

        if(bitmap.getWidth() < bitmap.getHeight()){
            if(bitmap.getHeight() > 1080){
                if(bitmap.getHeight() > 1500 ){
                    if(bitmap.getHeight() < 2000){
                        bitmap = bitmapResize(bitmap,0.7f);
                    }
                }
                if(bitmap.getHeight() > 2000 ){
                    if(bitmap.getHeight() < 3000){
                        bitmap = bitmapResize(bitmap,0.5f);
                    }
                }
                if(bitmap.getHeight() > 3000 ){
                    bitmap = bitmapResize(bitmap,0.2f);
                }

            }
        }

        return bitmap;

    }
}
