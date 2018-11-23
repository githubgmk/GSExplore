package com.gmk.geisa.helper;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by kenjinsan on 8/14/17.
 */

public class ImgDiffPercent {
    public static double DiffImages(Bitmap firstImg,Bitmap secondImg){
        Bitmap img1 = null;
        Bitmap img2 = null;
        img1 = firstImg;
        img2 = secondImg;
        int width1 = img1.getWidth();
        int width2 = img2.getWidth();
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();
        if ((width1 != width2) || (height1 != height2)) {
            Log.e("err","Error: Images dimensions mismatch");
            return 0;
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getPixel(x, y);
                int rgb2 = img2.getPixel(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >>  8) & 0xff;
                int b1 = (rgb1      ) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >>  8) & 0xff;
                int b2 = (rgb2      ) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;

        return p;
    }


}
