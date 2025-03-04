package io.after.utils;

import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageIO {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static Mat Input(String fileName) {
        return Imgcodecs.imread(fileName);
    }

    public static void Output(String fileName, Mat file) {
        boolean result = Imgcodecs.imwrite(fileName, file);
        if(result) {
            System.out.println("The image was saved successfully as " + fileName + ".");
        } else {
            System.out.println("Failed to save the image.");
        }
    }
}
