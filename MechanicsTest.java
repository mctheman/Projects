package org.cis1200.twentyfortyeight;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class MechanicsTest {

    // left tests
    @Test
    public void testLeftOneSquare() {
        Board board = new Board();
        board.add(4, 1, 2);
        board.left();
        assertEquals(4, board.getNumAtPos(1, 0));
    }

    @Test
    public void testLeftOneRow() {
        Board board = new Board();
        board.add(4, 1, 2);
        board.add(4, 1, 3);
        assertEquals(4, board.getNumAtPos(1, 2));
        assertEquals(4, board.getNumAtPos(1, 3));
        board.left();
        assertEquals(8, board.getNumAtPos(1, 0));
    }

    @Test
    public void testLeftMultipleSquares() {
        Board board = new Board();
        board.add(8, 0, 0);
        board.add(4, 0, 1);
        board.add(4, 0, 2);
        board.add(8, 1, 0);
        board.add(8, 1, 1);
        board.add(2, 3, 3);
        board.left();
        assertEquals(8, board.getNumAtPos(0, 0));
        assertEquals(8, board.getNumAtPos(0, 1));
        assertEquals(16, board.getNumAtPos(1, 0));
        assertEquals(2, board.getNumAtPos(3, 0));
    }

    @Test
    public void testLeftMultipleSquares1() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 0, 1);
        board.add(4, 0, 2);
        board.add(16, 1, 1);
        board.add(8, 2, 0);
        board.add(8, 2, 2);
        board.add(128, 3, 0);
        board.add(256, 3, 1);
        board.add(256, 3, 3);
        board.left();
        assertEquals(4, board.getNumAtPos(0, 0));
        assertEquals(4, board.getNumAtPos(0, 1));
        assertEquals(16, board.getNumAtPos(1, 0));
        assertEquals(16, board.getNumAtPos(2, 0));
        assertEquals(128, board.getNumAtPos(3, 0));
        assertEquals(512, board.getNumAtPos(3, 1));
    }

    @Test
    public void testLeftMultipleSquares2() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 0, 1);
        board.add(2, 0, 2);
        board.add(2, 0, 3);
        board.add(2, 1, 1);
        board.add(2, 1, 2);
        board.add(4, 2, 1);
        board.add(4, 2, 2);
        board.add(16, 3, 1);
        board.add(32, 3, 3);
        board.left();
        assertEquals(4, board.getNumAtPos(0, 0));
        assertEquals(4, board.getNumAtPos(0, 1));
        assertEquals(4, board.getNumAtPos(1, 0));
        assertEquals(8, board.getNumAtPos(2, 0));
        assertEquals(16, board.getNumAtPos(3, 0));
        assertEquals(32, board.getNumAtPos(3, 1));
    }

    @Test
    public void testLeftCantMove() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.save("savedGameTest.txt");
        board.left();
        int[][] check = board.getArr();
        board.load("savedGameTest.txt");
        int[][] check1 = board.getArr();
        assertTrue(Arrays.deepEquals(check, check1));
    }

    // right tests
    @Test
    public void testRightOneSquare() {
        Board board = new Board();
        board.add(4, 1, 1);
        board.right();
        assertEquals(4, board.getNumAtPos(1, 3));
    }

    @Test
    public void testRightOneRow() {
        Board board = new Board();
        board.add(4, 1, 2);
        board.add(4, 1, 1);
        board.right();
        assertEquals(8, board.getNumAtPos(1, 3));
    }

    @Test
    public void testRightMultipleSquares() {
        Board board = new Board();
        board.add(8, 0, 0);
        board.add(4, 0, 1);
        board.add(4, 0, 2);
        board.add(8, 1, 0);
        board.add(8, 1, 1);
        board.add(2, 3, 0);
        board.right();
        assertEquals(8, board.getNumAtPos(0, 3));
        assertEquals(8, board.getNumAtPos(0, 2));
        assertEquals(16, board.getNumAtPos(1, 3));
        assertEquals(2, board.getNumAtPos(3, 3));
    }

    @Test
    public void testRightMultipleSquares1() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 0, 1);
        board.add(4, 0, 2);
        board.add(16, 1, 1);
        board.add(8, 2, 0);
        board.add(8, 2, 2);
        board.add(128, 3, 0);
        board.add(256, 3, 1);
        board.add(256, 3, 3);
        board.right();
        assertEquals(4, board.getNumAtPos(0, 3));
        assertEquals(4, board.getNumAtPos(0, 2));
        assertEquals(16, board.getNumAtPos(1, 3));
        assertEquals(16, board.getNumAtPos(2, 3));
        assertEquals(128, board.getNumAtPos(3, 2));
        assertEquals(512, board.getNumAtPos(3, 3));
    }

    @Test
    public void testRightMultipleSquares2() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 0, 1);
        board.add(2, 0, 2);
        board.add(2, 0, 3);
        board.add(2, 1, 1);
        board.add(2, 1, 2);
        board.add(4, 2, 1);
        board.add(4, 2, 2);
        board.add(16, 3, 1);
        board.add(32, 3, 3);
        board.right();
        assertEquals(4, board.getNumAtPos(0, 2));
        assertEquals(4, board.getNumAtPos(0, 3));
        assertEquals(4, board.getNumAtPos(1, 3));
        assertEquals(8, board.getNumAtPos(2, 3));
        assertEquals(16, board.getNumAtPos(3, 2));
        assertEquals(32, board.getNumAtPos(3, 3));
    }

    @Test
    public void testRightCantMove() {
        Board board = new Board();
        board.add(2, 0, 3);
        board.save("savedGameTest.txt");
        board.right();
        int[][] check = board.getArr();
        board.load("savedGameTest.txt");
        int[][] check1 = board.getArr();
        assertTrue(Arrays.deepEquals(check, check1));
    }

    // up tests
    @Test
    public void testUpOneSquare() {
        Board board = new Board();
        board.add(4, 2, 1);
        board.up();
        assertEquals(4, board.getNumAtPos(0, 1));
    }

    @Test
    public void testUpOneCol() {
        Board board = new Board();
        board.add(4, 2, 1);
        board.add(4, 3, 1);
        assertEquals(4, board.getNumAtPos(2, 1));
        assertEquals(4, board.getNumAtPos(3, 1));
        board.up();
        assertEquals(8, board.getNumAtPos(0, 1));
    }

    @Test
    public void testUpMultipleSquares() {
        Board board = new Board();
        board.add(8, 0, 0);
        board.add(4, 1, 0);
        board.add(4, 2, 0);
        board.add(8, 0, 1);
        board.add(8, 1, 1);
        board.add(2, 3, 3);
        board.up();
        assertEquals(8, board.getNumAtPos(0, 0));
        assertEquals(8, board.getNumAtPos(1, 0));
        assertEquals(16, board.getNumAtPos(0, 1));
        assertEquals(2, board.getNumAtPos(0, 3));
    }

    @Test
    public void testUpMultipleSquares1() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 1, 0);
        board.add(4, 2, 0);
        board.add(16, 1, 1);
        board.add(8, 0, 2);
        board.add(8, 2, 2);
        board.add(128, 0, 3);
        board.add(256, 1, 3);
        board.add(256, 3, 3);
        board.up();
        assertEquals(4, board.getNumAtPos(0, 0));
        assertEquals(4, board.getNumAtPos(1, 0));
        assertEquals(16, board.getNumAtPos(0, 1));
        assertEquals(16, board.getNumAtPos(0, 2));
        assertEquals(128, board.getNumAtPos(0, 3));
        assertEquals(512, board.getNumAtPos(1, 3));
    }

    @Test
    public void testUpMultipleSquares2() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 1, 0);
        board.add(2, 2, 0);
        board.add(2, 3, 0);
        board.add(2, 1, 1);
        board.add(2, 2, 1);
        board.add(4, 1, 2);
        board.add(4, 2, 2);
        board.add(16, 1, 3);
        board.add(32, 3, 3);
        board.up();
        assertEquals(4, board.getNumAtPos(0, 0));
        assertEquals(4, board.getNumAtPos(1, 0));
        assertEquals(4, board.getNumAtPos(0, 1));
        assertEquals(8, board.getNumAtPos(0, 2));
        assertEquals(16, board.getNumAtPos(0, 3));
        assertEquals(32, board.getNumAtPos(1, 3));
    }

    @Test
    public void testUpCantMove() {
        Board board = new Board();
        board.add(2, 0, 3);
        board.save("savedGameTest.txt");
        board.up();
        int[][] check = board.getArr();
        board.load("savedGameTest.txt");
        int[][] check1 = board.getArr();
        assertTrue(Arrays.deepEquals(check, check1));
    }

    // down tests
    @Test
    public void testDownOneSquare() {
        Board board = new Board();
        board.add(4, 1, 1);
        board.down();
        assertEquals(4, board.getNumAtPos(3, 1));
    }

    @Test
    public void testDownOneCol() {
        Board board = new Board();
        board.add(4, 1, 1);
        board.add(4, 2, 1);
        board.down();
        assertEquals(8, board.getNumAtPos(3, 1));
    }

    @Test
    public void testDownMultipleSquares() {
        Board board = new Board();
        board.add(8, 0, 0);
        board.add(4, 1, 0);
        board.add(4, 2, 0);
        board.add(8, 0, 1);
        board.add(8, 1, 1);
        board.add(2, 0, 3);
        board.down();
        assertEquals(8, board.getNumAtPos(3, 0));
        assertEquals(8, board.getNumAtPos(2, 0));
        assertEquals(16, board.getNumAtPos(3, 1));
        assertEquals(2, board.getNumAtPos(3, 3));
    }

    @Test
    public void testDownMultipleSquares1() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 1, 0);
        board.add(4, 2, 0);
        board.add(16, 1, 1);
        board.add(8, 0, 2);
        board.add(8, 2, 2);
        board.add(128, 0, 3);
        board.add(256, 1, 3);
        board.add(256, 3, 3);
        board.down();
        assertEquals(4, board.getNumAtPos(3, 0));
        assertEquals(4, board.getNumAtPos(2, 0));
        assertEquals(16, board.getNumAtPos(3, 1));
        assertEquals(16, board.getNumAtPos(3, 2));
        assertEquals(128, board.getNumAtPos(2, 3));
        assertEquals(512, board.getNumAtPos(3, 3));
    }

    @Test
    public void testDownMultipleSquares2() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 1, 0);
        board.add(2, 2, 0);
        board.add(2, 3, 0);
        board.add(2, 1, 1);
        board.add(2, 2, 1);
        board.add(4, 1, 2);
        board.add(4, 2, 2);
        board.add(16, 1, 3);
        board.add(32, 3, 3);
        board.down();
        assertEquals(4, board.getNumAtPos(2, 0));
        assertEquals(4, board.getNumAtPos(3, 0));
        assertEquals(4, board.getNumAtPos(3, 1));
        assertEquals(8, board.getNumAtPos(3, 2));
        assertEquals(16, board.getNumAtPos(2, 3));
        assertEquals(32, board.getNumAtPos(3, 3));
    }

    @Test
    public void testDownCantMove() {
        Board board = new Board();
        board.add(2, 3, 3);
        board.save("savedGameTest.txt");
        board.down();
        int[][] check = board.getArr();
        board.load("savedGameTest.txt");
        int[][] check1 = board.getArr();
        assertTrue(Arrays.deepEquals(check, check1));
    }

}
