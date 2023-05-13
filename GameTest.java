package org.cis1200.twentyfortyeight;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    @Test
    public void test() {
        assertNotEquals("CIS 120", "CIS 160");
    }

    // add num tests
    @Test
    public void testAddInvalid() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class,
                () -> board.add(3, 0, 0));
    }

    @Test
    public void testAddValid() {
        Board board = new Board();
        board.add(2, 0, 0);
        assertEquals(2, board.getNumAtPos(0, 0));
        board.add(4, 0, 0);
        assertEquals(4, board.getNumAtPos(0, 0));
    }

    // generate position tests
    @Test
    public void testGeneratePosFullRows() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 0, 1);
        board.add(2, 0, 2);
        board.add(2, 0, 3);
        board.add(2, 1, 0);
        board.add(2, 1, 1);
        board.add(2, 1, 2);
        board.add(2, 1, 3);
        board.add(2, 2, 0);
        board.add(2, 2, 1);
        board.add(2, 2, 2);
        board.add(2, 2, 3);
        assertTrue(board.place() >= 12);
    }

    @Test
    public void testGeneratePosFullCols() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 1, 0);
        board.add(2, 2, 0);
        board.add(2, 3, 0);
        board.add(2, 0, 1);
        board.add(2, 1, 1);
        board.add(2, 2, 1);
        board.add(2, 3, 1);
        board.add(2, 0, 2);
        board.add(2, 1, 2);
        board.add(2, 2, 2);
        board.add(2, 3, 2);
        assertTrue(board.place() % 4 > 2);
    }

    // tests undo button
    @Test
    public void testUndoOneMove() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 0, 1);
        board.add(2, 0, 2);
        board.add(2, 0, 3);
        board.saveArr();
        board.left();
        board.saveArr();
        board.undo();
        assertEquals(2, board.getNumAtPos(0, 0));
        assertEquals(2, board.getNumAtPos(0, 1));
        assertEquals(2, board.getNumAtPos(0, 2));
        assertEquals(2, board.getNumAtPos(0, 3));
    }

    @Test
    public void testUndoMultipleMoves() {
        Board board = new Board();
        board.reset();
        assertEquals(1, board.getList().size());
        board.left();
        board.place();
        board.saveArr();
        assertEquals(2, board.getList().size());
        board.right();
        board.place();
        board.saveArr();
        assertEquals(3, board.getList().size());
        board.up();
        board.place();
        board.saveArr();
        assertEquals(4, board.getList().size());
        board.undo();
        assertEquals(3, board.getList().size());
        board.undo();
        assertEquals(2, board.getList().size());
        board.undo();
        assertEquals(1, board.getList().size());
        int[][] temp = board.getList().get(0);
        board.down();
        board.place();
        board.saveArr();
        assertEquals(2, board.getList().size());
        board.right();
        board.place();
        board.saveArr();
        assertEquals(3, board.getList().size());
        board.down();
        board.place();
        board.saveArr();
        assertEquals(4, board.getList().size());
        board.undo();
        assertEquals(3, board.getList().size());
        board.undo();
        assertEquals(2, board.getList().size());
        board.undo();
        assertEquals(1, board.getList().size());
        int[][] temp1 = board.getList().get(0);
        assertEquals(temp, temp1);
    }

    // tests action helper function
    @Test
    public void testAction() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.saveArr();
        board.left();
        board.action();
        assertEquals(1, board.getList().size());
    }

    // tests checkBoard helper function
    @Test
    public void testCheckFullFull() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(2, 0, 1);
        board.add(2, 0, 2);
        board.add(2, 0, 3);
        board.add(2, 1, 0);
        board.add(2, 1, 1);
        board.add(2, 1, 2);
        board.add(2, 1, 3);
        board.add(4, 2, 0);
        board.add(4, 2, 1);
        board.add(4, 2, 2);
        board.add(4, 2, 3);
        board.add(2, 3, 0);
        board.add(16, 3, 1);
        board.add(2, 3, 2);
        board.add(32, 3, 3);
        assertTrue(board.checkBoard());
    }

    // tests saveArr function
    @Test
    public void testSaveArr() {
        Board board = new Board();
        board.add(2, 0, 0);
        board.add(4, 1, 1);
        board.saveArr();
        assertTrue(Arrays.deepEquals(board.getArr(), board.getList().get(0)));
    }

    // tests game over
    @Test
    public void testGameLost() {
        Board board = new Board();
        board.reset();
        board.add(2, 0, 0);
        board.add(4, 0, 1);
        board.add(2, 0, 2);
        board.add(4, 0, 3);
        board.add(4, 1, 0);
        board.add(2, 1, 1);
        board.add(4, 1, 2);
        board.add(2, 1, 3);
        board.add(2, 2, 0);
        board.add(4, 2, 1);
        board.add(2, 2, 2);
        board.add(4, 2, 3);
        board.add(4, 3, 0);
        board.add(2, 3, 1);
        board.add(4, 3, 2);
        board.add(2, 3, 3);
        board.winLoss();
        assertTrue(board.getLost());
        assertFalse(board.getPlaying());
    }

    @Test
    public void testGameWon() {
        Board board = new Board();
        board.reset();
        board.add(2048, 0, 0);
        board.add(4, 0, 1);
        board.add(2, 0, 2);
        board.add(4, 0, 3);
        board.add(4, 1, 0);
        board.add(2, 1, 1);
        board.add(4, 1, 2);
        board.add(2, 1, 3);
        board.add(2, 2, 0);
        board.add(4, 2, 1);
        board.add(2, 2, 2);
        board.add(4, 2, 3);
        board.add(4, 3, 0);
        board.add(2, 3, 1);
        board.add(4, 3, 2);
        board.add(2, 3, 3);
        board.winLoss();
        assertTrue(board.getWon());
        assertFalse(board.getPlaying());
    }

    // tests generate24 function
    @Test
    public void testGenerate24() {
        Board board = new Board();
        assertTrue(board.generate24() <= 2 ||
                board.generate24() <= 4);
    }

    // tests setting score function
    @Test
    public void testSetScore0() {
        Board board = new Board();
        board.setScore();
        assertEquals(0, board.getScore());
    }

    @Test
    public void testSetScoreNon0() {
        Board board = new Board();
        board.add(2048, 0, 0);
        board.add(2, 1, 1);
        board.add(256, 2, 2);
        board.add(4, 3, 3);
        board.setScore();
        assertEquals(2310, board.getScore());
    }

    // tests reset function
    @Test
    public void testReset() {
        Board board = new Board();
        board.add(2048, 0, 0);
        board.add(4, 0, 1);
        board.add(2, 0, 2);
        board.add(4, 0, 3);
        board.add(4, 1, 0);
        board.add(2, 1, 1);
        board.add(4, 1, 2);
        board.add(2, 1, 3);
        board.add(2, 2, 0);
        board.add(4, 2, 1);
        board.add(2, 2, 2);
        board.add(4, 2, 3);
        board.reset();
        assertEquals(board.getList().size(),1);
        assertEquals(board.getScore(), 0);
        assertFalse(board.getLost());
        assertFalse(board.getWon());
        assertTrue(board.getPlaying());
    }

    // tests save and read file functions
    @Test
    public void testSaveLoadEmpty() {
        Board board = new Board();
        board.save("savedGameTest.txt");
        board.add(2048, 0, 0);
        board.add(2, 1, 1);
        board.add(256, 2, 2);
        board.add(4, 3, 3);
        board.load("savedGameTest.txt");
        int[][] empty = new int[4][4];
        assertTrue(Arrays.deepEquals(board.getArr(), empty));
    }

    @Test
    public void testSaveLoadNonEmpty() {
        Board board = new Board();
        board.add(2048, 0, 0);
        board.add(2, 1, 1);
        board.add(256, 2, 2);
        board.add(4, 3, 3);
        board.save("savedGameTest.txt");
        board.reset();
        board.add(2, 1, 1);
        board.add(4, 1, 2);
        board.add(2, 1, 3);
        board.add(2, 2, 0);
        board.add(4, 2, 1);
        board.add(2, 2, 2);
        board.load("savedGameTest.txt");
        int[][] checker = board.getArr();
        assertEquals(2048, checker[0][0]);
        assertEquals(2, checker[1][1]);
        assertEquals(256, checker[2][2]);
        assertEquals(4, checker[3][3]);
    }

    // test that simulates real gameplay
    @Test
    public void testSimulatedGameplay() {
        Board board = new Board();

        // here we manually add in numbers instead of using reset to account for randomness
        board.add(2, 0, 1);
        board.add(2, 0, 3);
        board.add(4, 1, 3);

        // now we save the initial game state to be the first array in list
        board.saveArr();

        // call left and simulate/check functionality (minus place() to account for randomness)
        board.left();
        board.saveArr();
        assertEquals(board.getNumAtPos(0, 0), 4);
        assertEquals(board.getNumAtPos(1, 0), 4);

        // saves game state, will check later
        board.save("savedGameTest.txt");

        // call down and simulate/check functionality (minus place() to account for randomness)
        board.down();
        board.saveArr();
        board.setScore();
        assertEquals(board.getScore(), 8);
        assertEquals(board.getNumAtPos(3, 0), 8);

        // call left and simulate/check functionality (minus place() to account for randomness)
        board.left();
        board.saveArr();
        board.setScore();
        assertEquals(board.getScore(), 8);
        assertEquals(board.getNumAtPos(3, 0), 8);

        // call right and simulate/check functionality (minus place() to account for randomness)
        board.right();
        board.saveArr();
        board.setScore();
        assertEquals(board.getScore(), 8);
        assertEquals(board.getNumAtPos(3, 3), 8);

        // undo moves up until last saved game state
        board.undo();
        board.undo();
        board.undo();

        // checks that the two game states are equal after loading in saved game state
        int[][] checker = board.getArr();
        board.load("savedGameTest.txt");
        int[][] checker1 = board.getArr();
        assertTrue(Arrays.deepEquals(checker, checker1));

        // resets the board and makes sure list is cleared
        board.reset();
        assertEquals(board.getList().size(), 1);
    }
}