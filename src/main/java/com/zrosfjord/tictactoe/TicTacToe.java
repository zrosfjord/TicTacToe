package com.zrosfjord.tictactoe;

public class TicTacToe implements Runnable {

    public static final int BOARD_SIZE = 3;
    public Tile[][] board;

    private Player player1, player2;

    private boolean running, done;
    private Player winner;

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

        player1 = ticTacToe.player1;
        player2 = ticTacToe.player2;

        running = ticTacToe.running;
        done = ticTacToe.done;
    }

    /**
     * Implemented from Runnable interface. Runs the game.
     */
    public void run() {
        if(running)
            return;

        running = true;

        if(player1 == null || player2 == null) {
            running = false;
            return;
        }

        System.out.println(this);
        while(!isWinner(Tile.O) && !isWinner(Tile.X) && !isFilled()) {
            player1.turn();
            System.out.println(this);

            if(isWinner(player1.getTile()) || isFilled())
                break;

            player2.turn();
            System.out.println(this);
        }

        // Assign the winner!
        if(isWinner(player1.getTile()))
            winner = player1;
        else if(isWinner(player2.getTile()))
            winner = player2;
        else
            winner = null;

        running = false;
        done = true;
    }

    /**
     * Tests if a tile type is a winner.
     *
     * @param tile The type of tile.
     * @return Whether or not they won.
     */
    public boolean isWinner(Tile tile) {
        // Rows
        rowLoop: for(int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
            for(int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
                if(getTile(i, j) != tile)
                    continue rowLoop;
            }

            return true;
        }

        // Columns
        colLoop: for(int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
            for(int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
                if (getTile(i, j) != tile)
                    continue colLoop;
            }

            return true;
        }

        // Dia
        diaLoop: for(int i = 0; i < 2; i++) {
            for(int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
                if (getTile(j, j) != tile)
                    break diaLoop;
            }

            return true;
        }

        // Reverse Dia
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
        } if(getTile(row, col) == Tile.SPACE) {
            board[row][col] = t;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets a tile at a given row and col
     *
     * @param row The row you want to check out
     * @param col The column you want to check out
     * @return The tile that is there.
     */
    public Tile getTile(int row, int col) {
        if (row >= BOARD_SIZE || col >= BOARD_SIZE) {
            return null;
        } else if(row < 0 || col < 0) {
            return null;
        } else {
            return board[row][col];
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
     * Clear the board
     */
    public void clearBoard() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Tile.SPACE;
            }
        }
    }

    /**
     * Used for printing the board
     *
     * @return A string representation of the board.
     */
    @Override
    public String toString() {
        String boardStr = "\n";
        for(int i = 0; i < BOARD_SIZE; i++) {
            boardStr += "|";
            for(int j = 0; j < BOARD_SIZE; j++) {
                boardStr += getTile(i, j).getSymbol();
            }

            boardStr += "|\n";
        }

        return boardStr;
    }


    /**
     * Sets player1
     *
     * @param player1 The first player
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * Sets player2
     *
     * @param player2 The second player
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * Whether or not the game is done.
     *
     * @return The boolean done.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Gets the winner
     *
     * @return The winning player, or null. Null means it is a tie.
     */
    public Player getWinner() {
        return winner;
    }


    public enum Tile {
        X('X'), O('O'), SPACE(' ');

        private char symbol;

        Tile(char s) {
            symbol = s;
        }

        public char getSymbol() {
            return symbol;
        }

        public static Tile getOpposite(Tile t) {
            switch (t) {
                case X:
                    return O;
                case O:
                    return X;
            }

            return SPACE;
        }
    }

}
