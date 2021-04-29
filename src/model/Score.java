package model;

import java.io.Serializable;

public class Score implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nickname;
	private char sign;
	private int score;
	
	private Score parent;
	private Score left;
	private Score right;
	
	public Score(String n, char si, int s) {
		nickname = n;
		sign = si;
		score = s;
	}
	public String getNickname() {
		return nickname;
	}
	public char getSign() {
		return sign;
	}
	public int getScore() {
		return score;
	}
	public Score getParent() {
		return parent;
	}
	public void setParent(Score p) {
		parent = p;
	}
	public Score getLeft() {
		return left;
	}
	public void setLeft(Score n) {
		left = n;
	}
	public Score getRight() {
		return right;
	}
	public void setRight(Score n) {
		right = n;
	}
	public String toString() {
		String msg = "";
		msg += "** Nickname: "+this.getNickname()+"\n";
		msg += "** Sign: "+this.getSign()+"\n";
		msg += "** Score: "+this.getScore()+"\n";
		return msg;
	}
}
