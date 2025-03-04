package pack;

import io.after.grammar.*;
import io.after.utils.*;
import org.junit.Assert;
import org.opencv.core.Mat;
import org.junit.Test;

public class ImageDecodingTest {
    @Test
    public void test() {
        int password = 2;
        Mat image = ImageIO.Input("D:\\Program\\photoCoding\\src\\main\\resources\\png\\test.png");
        String ans = ImageDecoding.decode(image, password);
        System.out.println(ans);
    }
}
