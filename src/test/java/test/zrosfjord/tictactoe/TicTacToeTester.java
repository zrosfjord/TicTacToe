package test.zrosfjord.tictactoe;

import com.zrosfjord.tictactoe.Bot;
import com.zrosfjord.tictactoe.TicTacToe;

import java.util.Scanner;

public class TicTacToeTester {

    /**
     * The main method that starts the program and runs it. Once this method returns, the program is over.
     *
     * @param args
     */
    public static void main(String[] args) {
        TicTacToe toe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);

        // Cheating is the only way the player can win!
        // toe.setTile(0, 1, TicTacToe.Tile.X);

        // Setting up the bots
        Bot b = new Bot(TicTacToe.Tile.O, toe);
        Bot b1 = new Bot(TicTacToe.Tile.X, toe);

        // In the background, Bot 'b' is actually playing against Bot 'b1'.
        b.setOpponent(b1);
        b1.setOpponent(b);

        System.out.println(toe);

        // Game Loop
        while(!toe.isWinner(TicTacToe.Tile.O) && !toe.isWinner(TicTacToe.Tile.X) && !toe.isFilled()) {
            // Player's Turn
            int x = -1;
            int y = -1;
            do {
                System.out.print("\nYour turn. Where do you want to go? (row,col):");
                String[] splitInput = scanner.nextLine().split(",");
                if(splitInput.length < 2)
                    continue;

                x = Integer.parseInt(splitInput[0].trim());
                y = Integer.parseInt(splitInput[1].trim());
            } while(!toe.setTile(x, y, TicTacToe.Tile.X));
            System.out.println(toe);

            // See if the player wins after their turn
            if(toe.isWinner(TicTacToe.Tile.X) || toe.isFilled())
                break;

            // Bot's Turn
            b.turn();
            System.out.println(toe);
        }

        // Announce the winner!
        boolean winner = false;
        for(TicTacToe.Tile t : TicTacToe.Tile.values()) {
            if(toe.isWinner(t)) {
                System.out.println(t + " has won!");
                winner = true;
            }
        }

        // If there is no winner
        if(!winner)
            System.out.println("Tie Game!");
    }

}
