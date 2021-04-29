package model;

public class Snake {
	private char snake;
	
	private int start;
	private int end;
	
	public Snake next;
	
	public Snake(char sN, int s, int e) {
		snake = sN;
		start = s;
		end = e;
	}
	public char getSnake() {
		return snake;
	}
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
	public Snake getNext() {
		return next;
	}
	public void setNext(Snake n) {
		next = n;
	}
}
