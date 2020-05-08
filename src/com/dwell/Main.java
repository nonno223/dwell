package com.dwell;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException, AWTException {
        Point     pos           = MouseInfo.getPointerInfo().getLocation();
        Point     location      = MouseInfo.getPointerInfo().getLocation();
        boolean   clicked       = false;
        boolean   scroll        = false;
        final int TIMEOUT       = 200;
        final int TIMEOUT_BIG   = 400;
        final int SCROLL_EDGE   = 1439;
        final int JITTER        = 1;


        do {
            final Robot clicker = new Robot();
            location = MouseInfo.getPointerInfo().getLocation();
            if (scroll && location.getX() >= SCROLL_EDGE) {
                scroll = false;
                TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            } else if (location.getX() >= SCROLL_EDGE) {
                TimeUnit.MILLISECONDS.sleep(TIMEOUT_BIG);
                clicker.mousePress(InputEvent.BUTTON2_DOWN_MASK);
                clicker.delay(10);
                clicker.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
                scroll = true;
            } else if (scroll) {
                TimeUnit.MILLISECONDS.sleep(TIMEOUT);
                continue;
            }


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

            clicker.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            clicker.delay(10);
            clicker.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            clicked = true;
            pos = MouseInfo.getPointerInfo().getLocation();

        } while (true);
    }
}
