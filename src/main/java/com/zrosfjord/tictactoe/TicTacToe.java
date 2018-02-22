package com.zrosfjord.tictactoe;

public class TicTacToe {

    public static final int BOARD_SIZE = 3;
    public Tile[][] board;

    /**
     * Main constructor for the TicTacToe class.
     */
    public TicTacToe() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];

        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Tile.SPACE;
            }
        }
    }

    /**
     * Deep copy constructor.
     *
     * @param ticTacToe The object of tictactoe you want to copy.
     */
    public TicTacToe(TicTacToe ticTacToe) {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];

        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = ticTacToe.board[i][j];
            }
        }
    }

    /**
     * Whether of not the board is full
     *
     * @return true if the board is full, false if otherwise.
     */
    public boolean isFilled() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(getTile(i, j) == Tile.SPACE)
                    return false;
            }
        }

        return true;
    }

    /**
     * Tests if a tile type is a winner.
     *
     * @param tile The type of tile.
     * @return Whether or not they won.
     */
    public boolean isWinner(Tile tile) {
        // Rows
        /**
         * O
         * XXX
         *   O
         */
        rowLoop: for(int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
            for(int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
                if(getTile(i, j) != tile)
                    continue rowLoop;
            }

            return true;
        }

        // Col
        /**
         * O X
         *  OX
         *   X
         */
        colLoop: for(int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
            for(int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
                if (getTile(i, j) != tile)
                    continue colLoop;
            }

            return true;
        }

        // Dia
        /**
         * X O
         *  X
         * O X
         */
        diaLoop: for(int i = 0; i < 2; i++) {
            for(int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
                if (getTile(j, j) != tile)
                    break diaLoop;
            }

            return true;
        }

        // Reverse Dia
        /**
         * O X
         *  X
         * X O
         */
        revLoop: for(int i = 0; i < 2; i++) {
            for(int j = TicTacToe.BOARD_SIZE - 1; j >= 0; j--) {
                if (getTile(j, Math.abs(j - (BOARD_SIZE - 1))) != tile)
                    break revLoop;
            }

            return true;
        }

        return false;
    }

    /**
     * Sets a tile in a row in col.
     *
     * @param row The row to set it in.
     * @param col The col to set it in.
     * @param t The type of tile.
     * @return Whether or not setting the tile succeeded.
     */
    public boolean setTile(int row, int col, Tile t) {
        if (row >= BOARD_SIZE || col >= BOARD_SIZE) {
            return false;
        } else if(row < 0 || col < 0) {
            return false;
        } if(board[row][col] == Tile.SPACE) {
            board[row][col] = t;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Resets a tile back to SPACE
     *
     * @param row The row of the tile you want to clear.
     * @param col The col of the tile you want to clear.
     */
    public void clearTile(int row, int col) {
        board[row][col] = Tile.SPACE;
    }

    /**
     * Gets a tile at a given row and col
     *
     * @param row The row you want to check out
     * @param col The column you want to check out
     * @return The tile that is there.
     */
    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    @Override
    public String toString() {
        String boardStr = "";
        for(int i = 0; i < BOARD_SIZE; i++) {
            boardStr += "|";
            for(int j = 0; j < BOARD_SIZE; j++) {
                boardStr += getTile(i, j).getSymbol();
            }

            boardStr += "|\n";
        }

        return boardStr;
    }


    public enum Tile {
        X('x'), O('o'), SPACE(' ');

        private char symbol;

        Tile(char s) {
            symbol = s;
        }

        public char getSymbol() {
            return symbol;
        }
    }

}
