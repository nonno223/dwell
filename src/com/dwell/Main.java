package com.dwell;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Point     pos             = MouseInfo.getPointerInfo().getLocation();
        Point     location        = MouseInfo.getPointerInfo().getLocation();
        Point     prevLocation    = MouseInfo.getPointerInfo().getLocation();
        boolean   clicked         = false;
        final int TIMEOUT         = 300;
        final int DOWN_SCROLL_MIN = 50;
        final int DOWN_SCROLL_MAX = 500;
        final int SCROLL_JITTER   = 5;
        final int TIMEOUT_SCROLL  = 5000;
        final int JITTER          = 1;

        do {
            TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            if (MouseInfo.getPointerInfo().getLocation() == null) {
                clicked = false;
                continue;
            }

            location = MouseInfo.getPointerInfo().getLocation();
            if (Math.abs(location.getX() - pos.getX()) > JITTER
                || Math.abs(location.getY() - pos.getY()) > JITTER) {
                pos = MouseInfo.getPointerInfo().getLocation();
                clicked = false;
                continue;
            }

            location = MouseInfo.getPointerInfo().getLocation();
            if ((Math.abs(location.getX() - pos.getX()) <= JITTER
                 && Math.abs(location.getY() - pos.getY()) <= JITTER) && clicked) {
                pos = MouseInfo.getPointerInfo().getLocation();
                continue;
            }

            try {
                Robot clicker = new Robot();

       //         System.out.println(prevLocation + " -- " + location);
                location = MouseInfo.getPointerInfo().getLocation();
                if (Math.abs(location.getY() - prevLocation.getY()) >= DOWN_SCROLL_MIN
                    && Math.abs(location.getY() - prevLocation.getY()) <= DOWN_SCROLL_MAX
                    && Math.abs(location.getX() - prevLocation.getX()) <= SCROLL_JITTER) {
                    clicker.mousePress(InputEvent.BUTTON2_DOWN_MASK);
                    clicker.delay(10);
                    clicker.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
                    TimeUnit.MILLISECONDS.sleep(TIMEOUT_SCROLL);
                    clicked = true;
                    prevLocation = MouseInfo.getPointerInfo().getLocation();
                    continue;
                }

                clicker.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                clicker.delay(10);
                clicker.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                clicked = true;
                pos = MouseInfo.getPointerInfo().getLocation();
                prevLocation = MouseInfo.getPointerInfo().getLocation();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
