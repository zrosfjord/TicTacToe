package test.zrosfjord.tictactoe;

import com.zrosfjord.tictactoe.Bot;
import com.zrosfjord.tictactoe.Player;
import com.zrosfjord.tictactoe.TicTacToe;

public class TicTacToeTester {

    /**
     * The main method that starts the program and runs it. Once this method returns, the program is over.
     *
     * @param args Starting arguments.
     */
    public static void main(String[] args) {
        TicTacToe toe = new TicTacToe();

        Player player1 = new Player(TicTacToe.Tile.X, toe);
        Bot botO = new Bot(TicTacToe.Tile.O, toe);

        toe.setPlayer1(player1);
        toe.setPlayer2(botO);

        toe.run();

        Player winner = toe.getWinner();
        if (winner != null)
            System.out.println(winner.getTile() + "\'s has won!");
        else
            System.out.println("It was a draw.");
    }

}
