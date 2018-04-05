/* SELF ASSESSMENT

Connect4Game class (35 marks) 35
My class creates references to the Connect 4 Grid and two Connect 4 Players. It asks the user whether he/she would like to play/quit inside a loop. If the user decides to play then: 1. Connect4Grid2DArray is created using the Connect4Grid interface, 2. the two players are initialised - must specify the type to be ConnectPlayer, and 3. the game starts. In the game, I ask the user where he/she would like to drop the piece. I perform checks by calling methods in the Connect4Grid interface. Finally a check is performed to determine a win. 
Comment: yes, game works and is replayable

Connect4Grid interface (10 marks) 10
I define all 7 methods within this interface.
Comment: yes, all methods defined

Connect4Grid2DArray class (25 marks) 25
My class implements the Connect4Grid interface. It creates a grid using a 2D array Implementation of the method to check whether the column to drop the piece is valid. It provides as implementation of the method to check whether the column to drop the piece is full. It provides as implementation of the method to drop the piece.  It provides as implementation of the method to check whether there is a win.
Comment: yes, all methods work as so, using a 2D array as the grid and nested for loops to scan for the various methods.

ConnectPlayer abstract class (10 marks) 10
My class provides at lest one non-abstract method and at least one abstract method. 
Comment: yes, provides abstract 'choose column' method which is overridden in the AI class

C4HumanPlayer class (10 marks) 10
My class extends the ConnectPlayer claas and overrides the abstract method(s). It provides the Human player functionality.
Comment: yes, extends ConnectPlayer and provides human functionality.

C4RandomAIPlayer class (10 marks) 10
My class extends the ConnectPlayer claas and overrides the abstract method(s). It provides AI player functionality. 
Comment: yes, overrides chooseColumn

Total Marks out of 100: 100

*/

import java.util.Random;
import java.util.Scanner;

public class Connect4Game {
	
	public boolean finished;
	
	public static void main(String[] args) {
		print("Welcome to Connect4!");
		ConnectPlayer player1 = player1Setup();
		ConnectPlayer player2 = player2Setup(player1.piece);
		Connect4Grid2DArray grid = new Connect4Grid2DArray();
		Connect4Game game = new Connect4Game();
		game.finished = false;
		while(!game.finished) {
			makeMove(player1, grid, game);
			makeMove(player2, grid, game);
		}
	}
	
	public static void restartGame() {
		print("Would you like to play again? (yes / no)");
		Scanner input = new Scanner(System.in);
		while(input.hasNext()) {
			String userInput = input.next();
			if(userInput.equals("yes")) {
				main(null);
			} else if (userInput.equals("no")){
				print("Goodbye - thanks for playing!");
			} else {
				print("Please enter either 'yes' or 'no' to answer.");
			}
		}
	}
	
	public static void makeMove(ConnectPlayer player, Connect4Grid2DArray grid, Connect4Game game) {
		if(!game.finished) {
			boolean playerWins = false;
			if(player.isHuman) {
				print("Player " + player.number + ": where do you want to drop your next piece? (column number)");
				Scanner input = new Scanner(System.in);
				boolean gotValidInput = false;
				while(!gotValidInput && input.hasNextInt()) {
					int userInput = input.nextInt();
					int chosenColumn = userInput;
					if(grid.isValidColumn(chosenColumn)) {
						chosenColumn -= 1;
						if(!grid.isColumnFull(chosenColumn)) {
							grid.dropPiece(player, chosenColumn);
							if(grid.didLastPieceConnect4()) {
								playerWins = true;
								game.finished = true;
							} else if(grid.isGridFull()) {
								print("It's a draw!");
								game.finished = true;
							}
							gotValidInput = true;
							print("Player " + player.number + "'s move:");
							print(grid.toString());
							if(playerWins) {
								print("Player " + player.number + " wins!");
								restartGame();
							}
						} else {
							print("That column is full! Try another one.");
						}
					}else {
						print("Input a number between 1 and 7 to choose a column.");
					}
				}
			} else {
				boolean pieceDropped = false;
				while(!pieceDropped) {
					int aiColumnChoice = player.chooseColumn(grid.NUM_OF_COLUMNS);
					if(!grid.isColumnFull(aiColumnChoice)) {
						grid.dropPiece(player, aiColumnChoice);
						pieceDropped = true;
						if(grid.didLastPieceConnect4()) {
							playerWins = true;
							game.finished = true;
						} else if(grid.isGridFull()) {
							print("It's a draw!");
							game.finished = true;
						}
						print("AI move:");
						print(grid.toString());
						if(playerWins) {
							print("Player " + player.number + " wins!");
							restartGame();
						}
					}
				}
				
			}
		}
	}
	
	public static ConnectPlayer player1Setup() {
		Scanner input = new Scanner(System.in);
		boolean finished = false;
		print("What colour would you like to play as?");
		print("Type 'red' or 'yellow' to choose.");
		ConnectPlayer player1;
		while(!finished && input.hasNext()) {
			String userInput = input.next();
			if(userInput.equals("red")) {
				player1 = new C4HumanPlayer('R', 1);
				print("You are now playing as Red");
				return player1;
			} else if(userInput.equals("yellow")) {
				player1 = new C4HumanPlayer('Y', 1);
				print("You are now playing as Yellow");
				return player1;
			} else {
				print("Please type either 'red' or 'yellow' to choose your colour.");
			}
		}
		return null;
	}
	
	public static ConnectPlayer player2Setup(char player1Piece) {
		Scanner input = new Scanner(System.in);
		boolean finished = false;
		print("Would you like to play against another person or an AI bot?");
		print("Type 'human' or 'ai' to choose.");
		ConnectPlayer player2;
		char piece;
		if(player1Piece == 'R') {
			piece = 'Y';
		} else {
			piece = 'R';
		}
		while(!finished && input.hasNext()) {
			String userInput = input.next();
			if(userInput.equals("human")) {
				player2 = new C4HumanPlayer(piece, 2);
				print("Now playing against a human player");
				return player2;
			} else if(userInput.equals("ai")) {
				player2 = new C4RandomAIPlayer(piece);
				print("Now playing against an AI bot");
				return player2;
			} else {
				print("Please type either 'red' or 'yellow' to choose your colour.");
			}
		}
		return null;
	}
	
	public static void print(String string) {
		System.out.println(string);
	}
}
