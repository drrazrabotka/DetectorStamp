package com.f.boof.myboof;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;



import java.util.List;

import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.filter.blur.GBlurImageOps;
import boofcv.alg.shapes.ShapeFittingOps;
import boofcv.android.ConvertBitmap;
import boofcv.struct.ConnectRule;
import boofcv.struct.PointIndex_I32;
import boofcv.struct.image.GrayU8;

public class HelpBoofCv {
    public static Bitmap detectStamp(Bitmap bitmap){

        double cornerPenalty = 0.07;
        int minSide = 22;
        int pointPoligon = 4;

        GrayU8 input = ConvertBitmap.bitmapToGray(bitmap,(GrayU8) null, null);
        GrayU8 blur = new GrayU8(bitmap.getWidth(), bitmap.getHeight());
        GrayU8 output = new GrayU8(bitmap.getWidth(),bitmap.getHeight());

        GBlurImageOps.gaussian(input, blur, -1, 2, null);

        double threshold = GThresholdImageOps.computeOtsu(blur, 0, 256);
        ThresholdImageOps.threshold(blur, output, (int) threshold, true);


        GrayU8 filtered= BinaryImageOps.dilate8(output, 1, null);
        GrayU8 result = BinaryImageOps.erode4(filtered, 1, null);

        Bitmap outBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(outBitmap);
        Path path = new Path();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb(0, 255, 255));
        paint.setStrokeWidth(3);

        List<Contour> contours = BinaryImageOps.contour(result, ConnectRule.EIGHT, null);
        for (Contour contour : contours){
            List<PointIndex_I32> vertexes = ShapeFittingOps.fitPolygon(contour.external, true, minSide, cornerPenalty);

            if (vertexes.size() == pointPoligon){
                path.moveTo(vertexes.get(0).x,  vertexes.get(0).y);
                path.lineTo(vertexes.get(1).x,  vertexes.get(1).y);
                path.lineTo(vertexes.get(2).x,  vertexes.get(2).y);
                path.lineTo(vertexes.get(3).x,  vertexes.get(3).y);
                path.close();


            }
        }

        canvas.drawPath(path, paint);


        return outBitmap;
    }


}
