//package main.java;

import java.awt.*;

public class Main {
    public static void main (String [] args) {
        System.out.println("Hello in SLR(1) Parsing Simulator!");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new main.java.GUI();
            }
        });
    }
}
