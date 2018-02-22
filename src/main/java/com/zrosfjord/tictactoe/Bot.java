package com.zrosfjord.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot extends Player {

    public static final int REWARD;
    static {
        REWARD = TicTacToe.BOARD_SIZE * TicTacToe.BOARD_SIZE + 1;
    }

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
        int best = -(REWARD + 1);

        // Used when there are moves with the same MinMax score.
        List<Move> bestMoves = new ArrayList<Move>();


        // Iterator through all possible positions
        System.out.println("Possible Bot Moves:");
        for(int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
            for(int j = 0; j < TicTacToe.BOARD_SIZE; j++) {

                // Checks if the setTile method ran.
                if(ticTacToe.setTile(i, j, tile)) {

                    // MinMax value of that move.
                    int value = minMax(ticTacToe, 0, false);

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
     * @param myTurn Whose turn it is.
     * @return The MinMax score of that move.
     */
    public int minMax(TicTacToe toe, int depth, boolean myTurn) {
        depth += 1;

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
        if (toe.isWinner(opponent)) {
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
                        int x = minMax(toe, depth, !myTurn);
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

            for (int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
                for (int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
                    if (toe.setTile(i, j, opponent)) {
                        int x = minMax(toe, depth, !myTurn);
                        if (x < best)
                            best = x;

                        toe.clearTile(i, j);
                    }
                }
            }

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
