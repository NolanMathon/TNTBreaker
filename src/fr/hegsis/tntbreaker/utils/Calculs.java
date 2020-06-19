package fr.hegsis.tntbreaker.utils;

public class Calculs {

    public static int isNumber(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }
}
