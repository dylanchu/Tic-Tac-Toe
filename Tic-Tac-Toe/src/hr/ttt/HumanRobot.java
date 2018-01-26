package hr.ttt;

import java.util.Scanner;

public class HumanRobot {
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int row = 1, column = 1;
		Player[] player = {Player.You, Player.Robot};
		Board board = new Board();
		board.init();
		do {
			board.print();
			if(player[board.total%2] == Player.You) {
				System.out.println("Turn: " + player[board.total%2] + ", Please input Row and Column: (seperate with space)");
				row = input.nextInt();
				column = input.nextInt();
			} else {
				System.out.println("Robot is thinking...");
			}
			if(board.put(player[board.total%2], row, column)) { // check if successfully put new piece:
				if(board.isWin(player[(board.total - 1) % 2])) {
					board.print();
					System.out.println(player[(board.total - 1) % 2] + " Win!");
					break;
				} else if(board.total >= 9) {
					board.print();
					System.out.println("Draw!");
				}
			}
		}while(board.total < 9);

		input.close();
	}

}

class Board {
	int total; // count of pieces
	char[][] room;
	char charY = '#';
	char charR = '*';
	
	void init() {
		 room = new char[3][3];
		 this.total = 0;
	}
	
	void print() {
		System.out.println("charY: '" + charY + "', charR: '" + charR + "'");
		System.out.println("┌───┬───┬───┐");
		for(int i = 0; i < 3; i++) {
			System.out.print("|");
			for (int j = 0; j < 3; j++) {
				System.out.print(" ");
				System.out.print(room[i][j]);
				System.out.print(" |");
			}
			if(i != 2) {
				System.out.print("\n├───┼───┼───┤");
			}
			System.out.println();
		}
		System.out.println("└───┴───┴───┘");
	}
	
	boolean put(Player player, int row, int column) {
		if(player == Player.Robot) {
			return robot();
		} else {
			if (row < 1 || row > 3 || column < 1 || column > 3) {
				return false;
			}
			char x = ' ';
			if(player == Player.You)
				x = charY;
			if(this.room[row -1 ][column - 1] == '\u0000') {
				this.room[row -1 ][column - 1] = x;
				total++;
				return true;
			} else {
				return false;
			}
		}
	}
	
	boolean isWin(Player player) {
		char x = ' ';
		if(player == Player.You)
			x = charY;
		if(player == Player.Robot)
			x = charR;
		for(int i = 0; i < 3; i++) {
			if(room[i][0] == x && room[i][0] == room[i][1] &&  room[i][0] == room[i][2]) //row
				return true;
			if(room[0][i] == x && room[0][i] == room[1][i] && room[1][i] == room[2][i]) //column
				return true;
		}
		if(room[0][0] == x && room[0][0] == room[1][1] && room[1][1] == room[2][2])
			return true;
		if(room[2][0] == x && room[2][0] == room[1][1] && room[1][1] == room[0][2])
			return true;
		return false;
	}
	
	boolean robot() {
		if(total >= 9) {
			System.out.println("No place for me to place another piece");
			return false;
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if(room[i][j] == '\u0000') {
					room[i][j] = this.charR;
					if(isWin(Player.Robot)) {
						this.total++;
						return true;
					} else {
						room[i][j] = '\u0000';
					}
				}
			}
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if(room[i][j] == '\u0000') {
					room[i][j] = this.charY;
					if(isWin(Player.You)) {
						room[i][j] = charR;
						this.total++;
						return true;
					} else {
						room[i][j] = '\u0000';
					}
				}
			}
		}
		
		int x = 1, y = 1;
		while(room[x][y] != '\u0000') {
			x = (int) (Math.random() * 100) % 3;
			y = (int) (Math.random() * 100) % 3;
		}
		room[x][y] = this.charR;
		total++;
		return true;
	}
}

enum Player{
	You,
	Robot
}