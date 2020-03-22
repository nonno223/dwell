package com.dwell;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Point     pos          = MouseInfo.getPointerInfo().getLocation();
        Point     location     = MouseInfo.getPointerInfo().getLocation();
        boolean   clicked      = false;
        boolean   middleScroll = false;
        final int TIMEOUT      = 330;
        final int JITTER       = 1;
        final int RIGHT_EDGE   = 1410;

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

                location = MouseInfo.getPointerInfo().getLocation();
                if (location.getX() >= RIGHT_EDGE) {
                    clicker.mousePress(InputEvent.BUTTON2_DOWN_MASK);
                    clicker.delay(10);
                    clicker.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
                    middleScroll = !middleScroll;
                    TimeUnit.MILLISECONDS.sleep(TIMEOUT);
                    continue;
                } else if (middleScroll) {
                    continue;
                }

                clicker.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                clicker.delay(10);
                clicker.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                clicked = true;
                pos = MouseInfo.getPointerInfo().getLocation();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
