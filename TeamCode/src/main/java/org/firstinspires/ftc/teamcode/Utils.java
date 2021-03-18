package org.firstinspires.ftc.teamcode;

public class Utils {

    public static class Time {

        public static void sleep(double seconds) {
            try {
                Thread.sleep((long)(seconds * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void sleepMs(double milliseconds) {
            try {
                Thread.sleep((long)milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
