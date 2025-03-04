package io.after.utils;

import java.util.Objects;

public class Hash {
    public static int getHash(int password, int x, int y) {
        if(password == 0) {return 0;}
        if(password == 1) {return 1;}
        if(password == 2) {return 2;}
        return Objects.hash(password, x, y) % 3;
    }
}
