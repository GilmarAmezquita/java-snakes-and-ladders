package model;

public class Ladder {
	private int ladder;
	
	private int start;
	private int end;
	
	private Ladder next;
	
	public Ladder(int l, int s, int e) {
		ladder = l;
		start = s;
		end = e;
	}
	public int getLadder() {
		return ladder;
	}
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
	public Ladder getNext() {
		return next;
	}
	public void setNext(Ladder n) {
		next = n;
	}
}
