package model;

public class Player {
	private char sign;
	private int currentBox;
	
	private int moves;
	
	private Player next;
	
	public Player(int c, char s) {
		sign = s;
		currentBox = c;
		moves = 0;
	}
	public Player(int c, int i) {
		currentBox = c;
		setSign(i);
	}
	
	private void setSign(int i) {
		switch(i) {
			case 1:			
				sign = '*';
				break;
			case 2:
				sign = '!';
				break;
			case 3:
				sign = 'O';
				break;
			case 4:
				sign = 'X';
				break;
			case 5:
				sign = '%';
				break;
			case 6:
				sign = '$';
				break;
			case 7:
				sign = '#';
				break;
			case 8:
				sign = '+';
				break;
			case 9:
				sign = '&';
				break;
		}
	}
	public char getSign() {
		return sign;
	}
	public int getCurrentBox() {
		return currentBox;
	}
	public void setCurrentBox(int c) {
		currentBox = c;
	}
	public Player getNext() {
		return next;
	}
	public void setNext(Player p) {
		next = p;
	}
	public int getMoves() {
		return moves;
	}
	public void increaseMoves() {
		moves++;
	}
}
