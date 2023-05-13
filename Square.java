package org.cis1200.twentyfortyeight;

import java.awt.*;
import javax.swing.*;

public class Square extends JPanel {
    private Color color;
    public Square(int num) {
        if (num == 2) {
            this.color = new Color(213, 204, 174);
        }
        if (num == 4) {
            this.color = new Color(150, 139, 92);
        }
        if (num == 8) {
            this.color = new Color(180, 118, 75);
        }
        if (num == 16) {
            this.color = new Color(196, 89, 33);
        }
        if (num == 32) {
            this.color = new Color(201, 60, 28);
        }
        if (num == 64) {
            this.color = new Color(206, 23, 23);
        }
        if (num == 128) {
            this.color = new Color(238, 202, 69, 218);
        }
        if (num == 256) {
            this.color = new Color(218, 185, 41, 218);
        }
        if (num == 512) {
            this.color = new Color(178, 156, 30, 218);
        }
        if (num == 1024) {
            this.color = new Color(206, 188, 2, 218);
        }
        if (num == 2048) {
            this.color = new Color(255, 205, 0, 255);
        }
    }

    // getter functions
    public Color getColor() {
        return color;
    }
}
