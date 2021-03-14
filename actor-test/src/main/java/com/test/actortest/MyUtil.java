package com.test.actortest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {


    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED_BG = "\u001B[41m";
    public static final String RED = "\u001B[31m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\033[0;1m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE_BG = "\u001B[45m";
    public static final String GREEN_BG = "\u001B[42m";
    public static final String BLACK_BAG = "\u001B[40m";

    public static void PrintF(String fColor, String msg) {
        PrintFB(fColor, "", msg);
    }

    public static void PrintB(String bColor, String msg) {
        PrintFB("", bColor, msg);
    }

    public static void PrintFB(String fColor, String bColor, String msg) {
        //System.out.println(Instant.now().toString() + "  --  " + bColor + fColor + msg + RESET);
        System.out.println(currDate() + "  --  " + bColor + fColor + msg + RESET);
    }

    public static void PrintErr(String msg) {
        //System.err.println(Instant.now().toString() + "  --  " + msg);
        System.err.println(currDate() + "  --  " + msg);
    }

    private static String currDate() {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSSSSS");
        return format.format(d);
    }

    public static void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
