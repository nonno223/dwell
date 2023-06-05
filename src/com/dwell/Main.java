package com.dwell;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

public class Main {
    private static       boolean clicked          = false;
    private static final int     TIMEOUT          = 150;
    private static final int     JITTER           = 1;

    public static void main(String[] args) throws InterruptedException, AWTException {
        Point pos      = MouseInfo.getPointerInfo().getLocation();
        Point location = MouseInfo.getPointerInfo().getLocation();

        do {
            TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            if (MouseInfo.getPointerInfo() == null || MouseInfo.getPointerInfo().getLocation() == null) {
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

            TimeUnit.MILLISECONDS.sleep(10);
            final Robot clicker = new Robot();
            clicker.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            clicker.delay(10);
            clicker.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            clicked = true;

        } while (true);
    }
}
