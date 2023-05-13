package org.cis1200.twentyfortyeight;

import javax.swing.*;
import java.awt.*;

public class Run2048 implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("2048");
        frame.setLocation(410, 410);
        frame.setResizable(false);

        // Game board
        Board board = new Board();
        frame.add(board, BorderLayout.CENTER);

        // Control panel
        final JPanel control_panel = new JPanel(new GridLayout(2, 3));
        frame.add(control_panel, BorderLayout.NORTH);

        // Instructions button
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> board.instructions(frame));
        control_panel.add(instructions);

        // Save button
        final JButton save = new JButton("Save");
        save.addActionListener(e -> board.save("savedGame.txt"));
        control_panel.add(save);

        // Load button
        final JButton load = new JButton("Load");
        load.addActionListener(e -> board.load("savedGame.txt"));
        control_panel.add(load);

        // Reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        // Undo button
        final JButton undo = new JButton("Undo");
        undo.addActionListener(e -> board.undo());
        control_panel.add(undo);

        // Score label
        final JLabel score = new JLabel("Score: 0");
        control_panel.add(score);

        Timer timer = new Timer(50, e -> {
            if (board.getPlaying()) {
                score.setText("Score: " + board.getScore());
                score.setHorizontalAlignment(SwingConstants.CENTER);
            }
        });
        timer.start();

        // Add the control panel to the frame
        frame.add(control_panel, BorderLayout.NORTH);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}