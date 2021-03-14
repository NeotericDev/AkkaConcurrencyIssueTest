package com.test.actortest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {

    public static final String RESET = "\u001B[0m";

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
