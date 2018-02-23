# TicTacToe

A Java Program that plays TicTacToe. Users can play in three ways:
+ Player vs Player
+ Player vs Bot
+ Bot vs Bot

The program will recognize when the game is done, it will assign a winner, and then end.

## Implementation
To create a new TicTacToe board, just type:
```java
TicTacToe ticTacToe = new TicTacToe();
```

From there, if you want to start a player vs bot game, you assign a bot and a player to the game, by doing:
```java
Player player1 = new Player(TicTacToe.Tile.X, ticTacToe);
Bot botO = new Bot(TicTacToe.Tile.O, ticTacToe);

ticTacToe.setPlayer1(player1);
ticTacToe.setPlayer2(botO);
```

All that is left to do is execute: `ticTacToe.run();`

Do keep in mind that the TicTacToe class does implement *Runnable*, so if you would like to run this in a seperate thread, you can.


## Bots

The Bot is designed to make intelligent and informed decisions. Every turn, it is running a **MinMax** recursive function. It utilizes [alpha beta pruning][wiki prune] in order to optimize the number of nodes it searchers. This means that it doesn't have to access every single scenario in order to find the best move.

The MinMax function was based around this Wikipedia psuedocode:
![alt text][wikipedia pic]

However, these TicTacToe Bots go beyond that, and also look at depth to determine which move to go with. This means, that if there is a move that will lead to a win in 5 turns, and there is a move that will lead to an immediate win, it will go with the immediate win. And conversely, if there is a move to prolong a lose, it will take it.

Here is an image from http://neverstopbuilding.com which shows this in action.
For more information on depth and its applicability, see their [article][the good fight] on it.

![alt text][depth minmax]


[wiki prune]: https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
[wikipedia pic]: https://i.imgur.com/6eEg9T0.png
[the good fight]: https://www.neverstopbuilding.com/blog/2013/12/13/tic-tac-toe-understanding-the-minimax-algorithm13#fighting-the-good-fight-depth
[depth minmax]: https://static1.squarespace.com/static/5a0c6978bff2001ef7581170/t/5a36dbfb085229e36df6b35f/1513544702989/end-states-taking-depth-into-account.png?format=1500w
