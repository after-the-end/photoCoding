package io.after.grammar;

import io.after.utils.Hash;
import org.opencv.core.Mat;

import java.util.Objects;

public class ImageDecoding {
    public static String decode(Mat image, int password) {
        int width = image.width();
        int height = image.height();

        int[][][] rgbValues = new int[height][width][3]; // 0 is blue, 1 is green, 2 is red.
        int[][][] ans = new int[height][width][3];

        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                double[] pixel = image.get(y, x);
                rgbValues[y][x][0] = (int) pixel[0];
                rgbValues[y][x][1] = (int) pixel[1];
                rgbValues[y][x][2] = (int) pixel[2];
                ans[y][x][0] = rgbValues[y][x][0] % 2;
                ans[y][x][1] = rgbValues[y][x][1] % 2;
                ans[y][x][2] = rgbValues[y][x][2] % 2;
            }
        }
        StringBuilder ansString = new StringBuilder();
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width / 8; ++x) {
                int value = 0;
                for(int i = 0; i <= 7; ++i) {
                    value = value * 2 + ans[y][x * 8 + i][Hash.getHash(password, x * 8 + i, y)];
                }
                ansString.append((char) value);
            }
        }

        return ansString.toString();
    }
}
