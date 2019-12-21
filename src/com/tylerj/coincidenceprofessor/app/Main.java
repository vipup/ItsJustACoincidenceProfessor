package com.tylerj.coincidenceprofessor.app;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        ScoreBot sb = new ScoreBot("TestCode/helloWorld.java");
        

        // Start all Swing applications on the EDT.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new appWindow(sb);
            }
        });
    }
}
