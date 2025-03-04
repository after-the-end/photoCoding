package io.after.grammar;

import io.after.utils.Hash;
import org.opencv.core.Mat;

public class ImageEncoding {
    public static Mat encode(Mat image, String content, int password) {
        int width = image.width();
        int height = image.height();

        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width/8; ++x) {
                if(y * (width / 8) + x == content.length()) return image;
                for(int i = 0; i <= 7; ++i) {
                    double[] pixel = image.get(y, x * 8 + i);
                    int[] rgbValues = new int[3];
                    rgbValues[0] = (int) pixel[0];
                    rgbValues[1] = (int) pixel[1];
                    rgbValues[2] = (int) pixel[2];
                    int position = Hash.getHash(password, x * 8 + i, y);
                    rgbValues[position] = rgbValues[position] - (rgbValues[position] % 2) + (((int) (content.toCharArray()[y * (width / 8) + x]) >> (7 - i)) % 2);
                    pixel[0] = (double) rgbValues[0];
                    pixel[1] = (double) rgbValues[1];
                    pixel[2] = (double) rgbValues[2];
                    image.put(y, x * 8 + i, pixel);
                }
            }
        }
        return image;
    }
}
