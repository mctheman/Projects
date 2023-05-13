package org.cis1200.twentyfortyeight;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class Board extends JPanel {

    // sets up the 2d array that stores positions of numbers on the game board
    private int[][] arr;

    // sets up the collection that stores moves in order to implement undo
    private List<int[][]> list;

    // sets up the list that stores which squares are currently filled
    private List<Integer> posList = new LinkedList<Integer>();

    // sets up a boolean that determines whether the game is running
    private boolean playing;

    // sets up a boolean that determines whether the player has lost
    private boolean lost = false;

    // sets up a boolean that determines whether the player has won
    private boolean won = false;

    // sets up an integer that keeps track of the game score
    private int score;

    // helper function that simplifies repetitive code
    public void action() {
        winLoss();
        draw();
        if (!Arrays.deepEquals(list.get(list.size() - 1), arr)) {
            place();
            saveArr();
            draw();
            setScore();
        }
    }

    // initializes an empty board
    public Board() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (playing) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT
                            || e.getKeyCode() == KeyEvent.VK_A) {
                        left();
                        action();
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT
                            || e.getKeyCode() == KeyEvent.VK_D) {
                        right();
                        action();
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN
                            || e.getKeyCode() == KeyEvent.VK_S) {
                        down();
                        action();
                    } else if (e.getKeyCode() == KeyEvent.VK_UP
                            || e.getKeyCode() == KeyEvent.VK_W) {
                        up();
                        action();
                    }
                }
            }
        });

        arr = new int[4][4];
        list = new LinkedList<int[][]>();
    }

    // adds a tile at a specified location into the game board
    public void add(int num, int row, int col) {
        double log = Math.log(num) / Math.log(2);
        if (log != (int)log) {
            throw new IllegalArgumentException("Invalid input");
        } else {
            arr[row][col] = num;
        }
    }

    // adds the current array to list
    public void saveArr() {
        int[][] array = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                array[i][j] = arr[i][j];
            }
        }
        list.add(array);
    }

    // updates the score by adding all numbers within the grid
    public void setScore() {
        int score = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                score = score + arr[i][j];
            }
        }
        this.score = score;
    }

    // mechanics functions

    // chooses an empty position to generate a number
    public int place() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Integer a = arr[row][col];
                if (a.equals(0)) {
                    posList.add(4 * row + col);
                }
            }
        }

        Random random = new Random();
        try {
            Integer r = posList.get(random.nextInt(posList.size()));
            System.out.println(r);
            add(generate24(), r / 4, r % 4);
            posList.clear();

            return r;
        } catch (RuntimeException e) {
            return -1;
        }
    }

    // helper function that checks whether the board is full
    public boolean checkBoard() {
        boolean full = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer a = arr[i][j];
                if (a.equals(0)) {
                    full = false;
                }
            }
        }
        return full;
    }

    // checks win/lose condition and acts accordingly
    public void winLoss() {
        // checks whether user has won
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (arr[i][j] == 2048) {
                    won = true;
                    playing = false;
                }
            }
        }

        // checks whether user has lost
        boolean checkLost = checkBoard();

        // Check if there are any adjacent numbers that are the same
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer num = arr[i][j];
                if (i + 1 < 4) {
                    Integer comp = arr[i + 1][j];
                    if (comp.equals(num)) {
                        checkLost = false;
                    }
                }
                if (j + 1 < 4) {
                    Integer comp = arr[i][j + 1];
                    if (comp.equals(num)) {
                        checkLost = false;
                    }
                }
                if (i - 1 >= 0) {
                    Integer comp = arr[i - 1][j];
                    if (comp.equals(num)) {
                        checkLost = false;
                    }
                }
                if (j - 1 >= 0) {
                    Integer comp = arr[i][j - 1];
                    if (comp.equals(num)) {
                        checkLost = false;
                    }
                }
            }
        }

        lost = checkLost;
        if (checkLost) {
            playing = false;
        }
    }

    // generates a number, either 2 (90% of the time) or 4 (10% of the time)
    public int generate24() {
        double y = Math.random();
        int x = 2;
        if (y > 0.9) {
            x = 4;
        }
        System.out.println(x);
        return x;
    }

    // accounts for key presses up, down, left and right
    public void left() {
        int curr = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 1; col < 4; col++) {
                if (col == 1) {
                    curr = 0;
                }
                Integer i = arr[row][curr];
                Integer c = arr[row][col];
                Integer c1 = arr[row][curr + 1];
                if (!c.equals(0)) {
                    if (i.equals(0)) {
                        arr[row][curr] = c;
                        arr[row][col] = 0;
                    } else if (i.equals(c)) {
                        arr[row][curr] = 2 * c;
                        arr[row][col] = 0;
                        curr = curr + 1;
                    } else if (c1.equals(0)) {
                        arr[row][curr + 1] = arr[row][col];
                        arr[row][col] = 0;
                        curr = curr + 1;
                    } else {
                        curr = col;
                    }
                }
            }
        }
    }

    public void right() {
        int curr = 3;
        for (int row = 0; row < 4; row++) {
            for (int col = 2; col >= 0; col--) {
                if (col == 2) {
                    curr = 3;
                }
                Integer i = arr[row][curr];
                Integer c = arr[row][col];
                Integer c1 = arr[row][curr - 1];
                if (!c.equals(0)) {
                    if (i.equals(0)) {
                        arr[row][curr] = c;
                        arr[row][col] = 0;
                    } else if (i.equals(c)) {
                        arr[row][curr] = 2 * c;
                        arr[row][col] = 0;
                        curr = curr - 1;
                    } else if (c1.equals(0)) {
                        arr[row][curr - 1] = arr[row][col];
                        arr[row][col] = 0;
                        curr = curr - 1;
                    } else {
                        curr = col;
                    }
                }
            }
        }
    }

    public void up() {
        int curr = 0;
        for (int col = 0; col < 4; col++) {
            for (int row = 1; row < 4; row++) {
                if (row == 1) {
                    curr = 0;
                }
                Integer i = arr[curr][col];
                Integer c = arr[row][col];
                Integer c1 = arr[curr + 1][col];
                if (!c.equals(0)) {
                    if (i.equals(0)) {
                        arr[curr][col] = c;
                        arr[row][col] = 0;
                    } else if (i.equals(c)) {
                        arr[curr][col] = 2 * c;
                        arr[row][col] = 0;
                        curr = curr + 1;
                    } else if (c1.equals(0)) {
                        arr[curr + 1][col] = arr[row][col];
                        arr[row][col] = 0;
                        curr = curr + 1;
                    } else {
                        curr = row;
                    }
                }
            }
        }
    }

    public void down() {
        int curr = 3;
        for (int col = 0; col < 4; col++) {
            for (int row = 2; row >= 0; row--) {
                if (row == 2) {
                    curr = 3;
                }
                Integer i = arr[curr][col];
                Integer c = arr[row][col];
                Integer c1 = arr[curr - 1][col];
                if (!c.equals(0)) {
                    if (i.equals(0)) {
                        arr[curr][col] = c;
                        arr[row][col] = 0;
                    } else if (i.equals(c)) {
                        arr[curr][col] = 2 * c;
                        arr[row][col] = 0;
                        curr = curr - 1;
                    } else if (c1.equals(0)) {
                        arr[curr - 1][col] = arr[row][col];
                        arr[row][col] = 0;
                        curr = curr - 1;
                    } else {
                        curr = row;
                    }
                }
            }
        }
    }

    // draw functions

    // draws the game
    public void draw() {
        repaint();
    }

    // resets the grid
    public void reset() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                arr[row][col] = 0;
            }
        }
        list.clear();
        place();
        saveArr();
        draw();

        lost = false;
        won = false;
        score = 0;

        playing = true;
        requestFocusInWindow();
    }

    // creates a new JFrame that accounts for instructions panel behavior
    public void instructions(JFrame frame) {
        JOptionPane.showMessageDialog(frame,
                "2048 game rules:\n" +
                        "- Use the arrow/wasd keys to shift tiles in the game \n" +
                        "- Like tiles combine, unlike tiles will stack \n" +
                        "- With each move, a new tile (2/4) will be generated \n" +
                        "- Total score, which is the sum of all the tiles, " +
                        "will be at the bottom \n" +
                        "- The reset button starts a new game \n" +
                        "- The undo button will undo the last move \n" +
                        "- The save button will save the current game state \n" +
                        "- The save button will load in the last saved game state \n" +
                "- If no more moves can be made, you lose \n" +
                "- If you get to the 2048 tile, you win");
        requestFocusInWindow();
    }

    // undoes the last move
    public void undo() {
        if (list.size() == 1) {
            System.out.println("only one");
        }
        if (list.size() != 1) {
            int[][] a = list.get(list.size() - 2);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j ++) {
                    arr[i][j] = a[i][j];
                }
            }
            list.remove(list.size() - 1);
        }
        setScore();
        draw();

        requestFocusInWindow();
    }

    // specifies how to draw the grid
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (won) {
            g.setFont(new Font("Avenir Next", Font.BOLD, 32));
            g.setColor(Color.BLACK);
            g.drawString("You Win!", 139, 215);
        } else if (lost) {
            g.setFont(new Font("Avenir Next", Font.BOLD, 32));
            g.setColor(Color.BLACK);
            g.drawString("You Lose", 139, 215);
        } else {
            // Draws board grid
            Graphics2D g2d = (Graphics2D) g;
            Stroke stroke = new BasicStroke(10);
            g2d.setStroke(stroke);

            g2d.drawLine(105, 0, 105, 405);
            g2d.drawLine(205, 0, 205, 405);
            g2d.drawLine(305, 0, 305, 405);
            g2d.drawLine(0, 105, 405, 105);
            g2d.drawLine(0, 205, 405, 205);
            g2d.drawLine(0, 305, 405, 305);

            // draws individual squares
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    Integer x = arr[row][col];
                    if (!x.equals(0)) {
                        Square square = new Square(x);
                        g.setColor(square.getColor());
                        g.fillRect(col * 100 + 10, row * 100 + 10, 90, 90);
                        if (x < 10) {
                            g.setFont(new Font("Avenir Next", Font.BOLD, 52));
                            if (x == 2 || x == 4) {
                                g.setColor(new Color(58, 52, 49, 218));
                            } else {
                                g.setColor(Color.WHITE);
                            }
                            g.drawString(String.valueOf(x), col * 100 + 39, row * 100 + 72);
                        } else if (x < 100) {
                            g.setFont(new Font("Avenir Next", Font.BOLD, 45));
                            g.setColor(Color.WHITE);
                            g.drawString(String.valueOf(x), col * 100 + 25, row * 100 + 68);
                        } else if (x < 1000) {
                            g.setFont(new Font("Avenir Next", Font.BOLD, 32));
                            g.setColor(Color.WHITE);
                            g.drawString(String.valueOf(x), col * 100 + 25, row * 100 + 68);
                        } else {
                            g.setFont(new Font("Avenir Next", Font.BOLD, 26));
                            g.setColor(Color.WHITE);
                            g.drawString(String.valueOf(x), col * 100 + 21, row * 100 + 65);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(410, 410);
    }

    // file reader functions

    public void save(String fileName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

            // this saves the current array to load in to arr
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (i == 3 && j == 3) {
                        bw.write(arr[i][j] + " " + "\n");
                    } else {
                        bw.write(arr[i][j] + " ");
                    }
                }
            }

            // this saves all previous arrays to load into list
            for (int[][] array : list) {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (i == 3 && j == 3) {
                            bw.write(array[i][j] + " " + "\n");
                        } else {
                            bw.write(array[i][j] + " ");
                        }
                    }
                }
            }

            bw.close();
            playing = true;
            lost = false;
            won = false;
            draw();
        } catch (IOException e) {
            System.out.print(e);
        }

        requestFocusInWindow();
    }

    public void load(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // this loads from the saved game the current array
            String[] fst = br.readLine().split(" ");
            for (int i = 0; i < fst.length; i++) {
                arr[i / 4][i % 4] =  Integer.parseInt(fst[i]);
            }


            // this loads from the saved game previous moves into list
            list.clear();
            String line;

            while ((line = br.readLine()) != null) {
                String[] forList = line.split(" ");
                int[][] toAdd = new int[4][4];
                for (int i = 0; i < fst.length; i++) {
                    toAdd[i / 4][i % 4] =  Integer.parseInt(forList[i]);
                    if (i == 15) {
                        list.add(toAdd);
                    }
                }
            }

            br.close();
            draw();
        } catch (IOException e) {
            System.out.print(e);
        }

        requestFocusInWindow();
    }

    // getter functions

    public int[][] getArr() {
        int[][] array = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                array[i][j] = arr[i][j];
            }
        }
        return array;
    }

    public List<int[][]> getList() {
        return list;
    }

    public int getNumAtPos(int row, int col) {
        return arr[row][col];
    }

    public boolean getPlaying() {
        return playing;
    }

    public boolean getWon() {
        return won;
    }

    public boolean getLost() {
        return lost;
    }

    public int getScore() {
        return score;
    }
}