package com.dwell;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Point   pos     = MouseInfo.getPointerInfo().getLocation();
        boolean clicked = false;
        do {
            TimeUnit.MILLISECONDS.sleep(250);
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

                pos = MouseInfo.getPointerInfo().getLocation();
                clicked = true;
//                System.out.println("2");
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
