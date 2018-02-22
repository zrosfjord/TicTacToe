package com.zrosfjord.tictactoe;

import java.util.*;

public class Bot {

    public static final int REWARD;
    static {
        REWARD = TicTacToe.BOARD_SIZE * TicTacToe.BOARD_SIZE + 1;
    }

    private TicTacToe.Tile tile;
    private TicTacToe ticTacToe;

    private Random random;

    private Bot opponent;

    /**
     * Main Bot constructor
     *
     * @param tile The tile the bot is playing
     * @param ticTacToe The board the bot is playing on
     */
    public Bot(TicTacToe.Tile tile, TicTacToe ticTacToe) {
        this.tile = tile;
        this.ticTacToe = ticTacToe;

        random = new Random();
    }

    /**
     * Sets up the opponent bot before the game.
     *
     * @param b Opponent bot.
     */
    public void setOpponent(Bot b) {
        this.opponent = b;
    }

    /**
     * Plays the bots turn.
     */
    public void turn() {
        int best = -(REWARD + 1);

        // Used when there are moves with the same MinMax score.
        List<Move> bestMoves = new ArrayList<Move>();

        // Copies the current state of the board.
        TicTacToe copy = new TicTacToe(ticTacToe);

        // Iterator through all possible positions
        for(int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
            for(int j = 0; j < TicTacToe.BOARD_SIZE; j++) {

                // Checks if the setTile method ran.
                if(copy.setTile(i, j, tile)) {

                    // MinMax value of that move.
                    int value = minMax(copy, 0, false);

                    System.out.printf("Move Value: %s; (row, col): %s,%s\n", value, i, j);

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
                    copy.clearTile(i, j);
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
     * @param myTurn Whose turn it is.
     * @return The MinMax score of that move.
     */
    public int minMax(TicTacToe toe, int depth, boolean myTurn) {
        /*
         If the move results in a win, it returns the reward, minus the depth, in order to encourage the system
         to look for quicker ways to win.
          */
        if (toe.isWinner(tile)) {
            return  REWARD - depth;
        }

        /*
         If the move results in a lose, it returns the negative of the reward plus the depth, in order to encourage
         the system to stay in the game as long as possible.
          */
        if (toe.isWinner(opponent.tile)) {
            return -REWARD + depth;
        }

        /*
          If the move results in a tie, it returns 0.
         */
        if (toe.isFilled()) {
            return 0;
        }

        if (myTurn) {
            // Maximising your move.
            int worst = -(REWARD + 1);

            for (int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
                for (int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
                    if (toe.setTile(i, j, tile)) {
                        int x = minMax(toe, depth + 1, !myTurn);
                        if (x > worst)
                            worst = x;

                        toe.clearTile(i, j);
                    }
                }
            }

            return worst;
        } else {
            // Minimizing the opponent's move.
            int best = REWARD + 1;

            int x = -1 * opponent.minMax(toe, depth, !myTurn);
            if (x < best)
                best = x;

            return best;

        }
    }

    public class Move {

        private int row;
        private int col;

        public Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

    }

}
