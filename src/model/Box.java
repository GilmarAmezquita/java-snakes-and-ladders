package model;

public class Box {
	int numBox;
	
	Box previous;
	Box next;
	
	boolean ladder;
	boolean snake;
	boolean player;
	
	public Box(int nB) {
		numBox = nB;
		ladder = false;
		snake = false;
		player = false;
	}
	
	public int getNumBox() {
		return numBox;
	}
	public Box getPrevious() {
		return previous;
	}
	public void setPrevious(Box p) {
		previous = p;
	}
	public Box getNext() {
		return next;
	}
	public void setNext(Box n) {
		next = n;
	}	
	public boolean haveLadder() {
		return ladder;
	}
	public void setLadderTrue() {
		ladder = true;
	}
	public boolean haveSnake() {
		return snake;
	}
	public void setSnakeTrue() {
		snake = true;
	}
	public boolean havePlayer() {
		return player;
	}
	public void setPlayerTrue() {
		player = true;
	}
	public void setPlayerFalse() {
		player = false;
	}

	public String toString() {
		return "[("+numBox+")]";
	}
}
