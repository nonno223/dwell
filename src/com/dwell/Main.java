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
        final int TIMEOUT      = 175;
        final int SCROLL_EDGE1 = 1425;
        final int SCROLL_EDGE2 = 1325;

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
                pos = MouseInfo.getPointerInfo().getLocation();
                if (pos.x >= SCROLL_EDGE1) {
                    while (pos.x >= SCROLL_EDGE2) {
                        TimeUnit.MILLISECONDS.sleep(10);
                        pos = MouseInfo.getPointerInfo().getLocation();
                    }
                }
                clicker.delay(10);
                clicker.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                pos = MouseInfo.getPointerInfo().getLocation();
                clicked = true;
//                System.out.println("2");
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
