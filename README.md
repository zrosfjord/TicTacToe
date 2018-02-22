# TicTacToe

A Java Program that plays TicTacToe. Users can play in three ways:
+ Player vs Player
+ Player vs Bot
+ Bot vs Bot

The program will recognize when the game is done, it will assign a winner, and then end.

To create a new TicTacToe board, just type:
```java
TicTacToe ticTacToe = new TicTacToe();
```

From there, if you want to start a player vs bot game, you assign a bot and a player to the game, by doing:
```java
Player player1 = new Player(TicTacToe.Tile.X, ticTacToe);
Bot botO = new Bot(TicTacToe.Tile.O, ticTacToe);

botO.setupOpponents(player1);

ticTacToe.setPlayer1(player1);
ticTacToe.setPlayer2(botO);
```

All that is left to do is execute
```java
ticTacToe.run();
```

Do keep in mind that the TicTacToe class does implement *Runnable*, so if you would like to run this in a seperate thread, you can.


## Bots

The Bot is designed to make intelligent and informed decisions. Every turn, it is running a **MinMax** recursive function. This means that it is assessing every possible scenario, and figuring out which would will lead to the must ideal outcome. However, it also looks at depth to determine which move to go with. This means, that if there is a move that will lead to a win in 5 turns, and there is a move that will lead to an immediate win, it will go with the immediate win.
