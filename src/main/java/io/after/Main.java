package io.after;

import io.after.grammar.ImageDecoding;
import io.after.grammar.ImageEncoding;
import io.after.utils.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("No arguments provided. Use -help for usage information.");
            return;
        }

        int mode = 1;
        String imagePath = "", textPath = "", outputPath = "";
        int password = 2;
        for(int i = 0; i < args.length; ++i) {
            String arg = args[i];
            switch (arg) {
                case "-version":
                    System.out.println("v1.0");
                    System.exit(0);
                    break;
                case "-password":
                    if(i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        password = Integer.parseInt(args[i + 1]);
                        ++i;
                    } else {
                        System.out.println("Error: No value for password.");
                    }
                    break;
                case "-mode":
                    if(i + 1 < args.length && (args[i + 1].equals("1") || args[i + 1].equals("2"))) {
                        mode = args[i + 1].equals("1") ? 1 : 2;
                        ++i;
                    } else {
                        System.out.println("Error: No value for mode.");
                    }
                    break;
                case "-imagePath":
                    if(i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        imagePath = args[i + 1];
                        ++i;
                    } else {
                        System.out.println("Error: No value for imagePath.");
                    }
                    break;
                case "-textPath":
                    if(i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        textPath = args[i + 1];
                        ++i;
                    } else {
                        System.out.println("Error: No value for textPath.");
                    }
                    break;
                case "-outputPath":
                    if(i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        outputPath = args[i + 1];
                        ++i;
                    } else {
                        System.out.println("Error: No value for outputPath.");
                    }
                    break;
                case "-help":
                    printHelp();
                    break;
                default:
                    System.out.println("Unknown arguments: " + arg);
            }
        }
        if(mode == 1) {
            if(outputPath.isEmpty()) {
                System.out.println("Error: Invalid output path");
                System.exit(1);
            }
            if(imagePath.isEmpty()) {
                System.out.println("Error: Invalid image path");
                System.exit(1);
            }
            Mat image = ImageIO.Input(imagePath);
            String content = ImageDecoding.decode(image, password);
            try (FileWriter writer = new FileWriter(outputPath)) {
                writer.write(content);
                System.out.println("Information successfully saved.");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }

        if(mode == 2) {
            if(outputPath.isEmpty()) {
                System.out.println("Error: Invalid output path");
                System.exit(1);
            }
            if(imagePath.isEmpty()) {
                System.out.println("Error: Invalid image path");
                System.exit(1);
            }
            if(textPath.isEmpty()) {
                System.out.println("Error: Invalid text path");
                System.exit(1);
            }
            Mat image = ImageIO.Input(imagePath);
            String content = "";
            try {
                content = new String(Files.readAllBytes(Paths.get(textPath)));
            } catch (IOException e) {
                System.err.println("Failed to get the text: " + e.getMessage());
                System.exit(1);
            }
            Mat ans = ImageEncoding.encode(image, content, password);
            ImageIO.Output(outputPath, ans);
        }
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar ***.jar [options]");
        System.out.println("Options:");
        System.out.println(" -version              Show version information");
        System.out.println(" -help                 Show help information");
        System.out.println(" -mode <value>         [1: decode/2: encode] Default value is 1");
        System.out.println(" -imagePath <value>    The path of the image provided");
        System.out.println(" -textPath <value>     The path of the text provided");
        System.out.println(" -outputPath <value>   The path of the output");
        System.out.println(" -password <value>     Decode or encode with the number password. Default password is 2");
    }
}