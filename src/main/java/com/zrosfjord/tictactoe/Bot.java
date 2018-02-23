package com.zrosfjord.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot extends Player {

    private static final int BIG_NUMBER = Integer.MAX_VALUE;

    private Random random;
    private TicTacToe.Tile opponent;

    /**
     * Main Bot constructor
     *
     * @param tile The tile the bot is playing
     * @param ticTacToe The board the bot is playing on
     */
    public Bot(TicTacToe.Tile tile, TicTacToe ticTacToe) {
        super(tile, ticTacToe);

        random = new Random();
        opponent = TicTacToe.Tile.getOpposite(tile);
    }

    /**
     * Plays the bots turn.
     */
    @Override
    public void turn() {
        int best = -BIG_NUMBER;

        // Used when there are moves with the same MinMax score.
        List<Move> bestMoves = new ArrayList<Move>();

        // Iterator through all possible positions
        System.out.println("Possible Bot Moves:");
        for(int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
            for(int j = 0; j < TicTacToe.BOARD_SIZE; j++) {

                // Checks if the setTile method ran.
                if(ticTacToe.setTile(i, j, tile)) {

                    // MinMax value of that move.
                    int value = minMax(ticTacToe, 0, -BIG_NUMBER, BIG_NUMBER, false);

                    System.out.printf("  - Move Value: %s; (row, col): %s,%s\n", value, i, j);

                    /*
                    If the MinMax value of the current move is the best, it sets that as the best, and makes it the
                    sole move in the bestMoves list.
                     */
                    if(value > best) {
                        best = value;
                        bestMoves.clear();
                        bestMoves.add(new Move(i, j));
                    } else if(value == best) {
                        bestMoves.add(new Move(i, j));
                    }

                    // Undoes the move on the board.
                    ticTacToe.clearTile(i, j);
                }
            }
        }

        // Makes sure there are moves to play.
        if(bestMoves.size() != 0) {
            Move chosenMove = bestMoves.get(random.nextInt(bestMoves.size()));
            ticTacToe.setTile(chosenMove.getRow(), chosenMove.getCol(), tile);
        }
    }

    /**
     * Recursive function that determines the MinMax score of a move.
     *
     * @param toe The board
     * @param depth How many moves in is it.
     * @param alpha Best max score.
     * @param beta Best min score.
     * @param max Whose turn it is.
     * @return The MinMax score of that move.
     */
    public int minMax(TicTacToe toe, int depth, int alpha, int beta, boolean max) {
        /*
         If the move results in a win, it returns the reward, minus the depth, in order to encourage the system
         to look for quicker ways to win.
          */
        if (toe.isWinner(tile)) {
            return  10 - depth;
        }

        /*
         If the move results in a lose, it returns the negative of the reward plus the depth, in order to encourage
         the system to stay in the game as long as possible.
        */
        if (toe.isWinner(opponent)) {
            return -10 + depth;
        }

        /*
          If the move results in a tie, it returns 0.
         */
        if (toe.isFilled()) {
            return 0;
        }

        // MinMax switch
        if (max) {
            // Maximizing your moves.
            int low = -BIG_NUMBER;

            maxLoop:
            for (int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
                for (int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
                    if (toe.setTile(i, j, tile)) {
                        low = Math.max(low, minMax(toe, depth + 1, alpha, beta, !max));
                        alpha = Math.max(low, alpha);

                        toe.clearTile(i, j);

                        // Beta Cutoff
                        if(alpha >= beta) break maxLoop;
                    }
                }
            }

            return low;
        } else {
            // Minimizing the opponent's move.
            int high = BIG_NUMBER;

            minLoop:
            for (int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
                for (int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
                    if (toe.setTile(i, j, opponent)) {
                        high = Math.min(high, minMax(toe, depth + 1, alpha, beta, !max));
                        beta = Math.min(high, beta);

                        toe.clearTile(i, j);

                        // Alpha Cutoff
                        if(alpha >= beta) break minLoop;
                    }
                }
            }

            return high;
        }
    }


    public class Move {

        private int row;
        private int col;

        /**
         * Constructor for Move class
         *
         * @param row The row
         * @param col The col
         */
        public Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

        /**
         * Gets the row
         *
         * @return The row
         */
        public int getRow() {
            return row;
        }

        /**
         * Gets the column
         *
         * @return The column
         */
        public int getCol() {
            return col;
        }

    }

}
