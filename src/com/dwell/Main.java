package com.dwell;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Point     pos          = MouseInfo.getPointerInfo().getLocation();
        boolean   clicked      = false;
        final int TIMEOUT      = 200;
        final int SCROLL_EDGE1 = 1;
        final int SCROLL_EDGE2 = 25;
        final int HALF         = 450;

        do {
            TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            if (MouseInfo.getPointerInfo().getLocation() == null) {
                clicked = false;
                System.out.println("NPE (1)");
                continue;
            }
            if (!MouseInfo.getPointerInfo().getLocation().equals(pos)) {
//                System.out.println("0");
                pos = MouseInfo.getPointerInfo().getLocation();
                clicked = false;
                continue;
            }
            if (MouseInfo.getPointerInfo().getLocation().equals(pos) && clicked) {
//                System.out.println("1");
                pos = MouseInfo.getPointerInfo().getLocation();
                continue;
            }

            try {
                Robot clicker = new Robot();

                clicker.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                clicker.delay(10);
                clicker.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                clicked = true;
                pos = MouseInfo.getPointerInfo().getLocation();
//                System.out.println("2");
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    private static void scroll(int steps, Robot r, boolean up, int delay) {
        for (int i = 0; i < steps; i++) {
            r.mouseWheel(up ? -1 : 1);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
    }
}
