package com.dwell;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException, AWTException {
        Point     pos         = MouseInfo.getPointerInfo().getLocation();
        Point     location    = MouseInfo.getPointerInfo().getLocation();
        boolean   clicked     = false;
        final int TIMEOUT     = 200;
        final int SCROLL_EDGE = 1430;
        final int JITTER      = 1;


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
            final Robot clicker = new Robot();
            clicker.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            pos = MouseInfo.getPointerInfo().getLocation();
            if (pos.getX() < SCROLL_EDGE) {
                clicker.delay(10);
                clicker.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
            clicked = true;

        } while (true);
    }
}
