package com.zrosfjord.tictactoe;

import java.util.Scanner;

public class Player {

    protected TicTacToe.Tile tile;
    protected TicTacToe ticTacToe;

    private Scanner scanner;

    public Player(TicTacToe.Tile tile, TicTacToe ticTacToe) {
        this.tile = tile;
        this.ticTacToe = ticTacToe;


        scanner = new Scanner(System.in);
    }

    /**
     * Runs the players turn.
     */
    public void turn() {
        int x = -1;
        int y = -1;

        // Will keep asking player for next move until they provide usable arguments.
        do {
            System.out.print("\n" + tile + "\'s turn. Where do you want to go? (row,col): ");
            String[] splitInput = scanner.nextLine().split(",");
            if(splitInput.length < 2)
                continue;

            x = Integer.parseInt(splitInput[0].trim());
            y = Integer.parseInt(splitInput[1].trim());
        } while(!ticTacToe.setTile(x, y, tile));
    }

    public TicTacToe.Tile getTile() {
        return tile;
    }
}
