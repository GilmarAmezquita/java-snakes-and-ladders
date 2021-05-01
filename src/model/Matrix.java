package model;

public class Matrix {
	
	private Box startBox;
	private Box goalBox;
	
	private Player firstP;
	private Player currentPlaying;
	
	private boolean winner;
	private Player winnerPlayer;
	
	private Ladder firstL;
	
	private Snake firstS;
	
	int numRows;
	int numColumns;
	int numBox;
	
	public Matrix(int rows, int columns, int snakes, int ladders, int players) {
		numRows = rows;
		numColumns = columns;
		numBox = rows*columns;
		winner = false;
		createBox();
		createSnakes(snakes);
		createLadders(ladders);
		createPlayers(players);
		currentPlaying = firstP;
	}
	
	public Matrix(int rows, int columns, int snakes, int ladders, int players, String signs) {
		numRows = rows;
		numRows = rows;
		numColumns = columns;
		numBox = rows*columns;
		winner = false;
		createBox();
		createSnakes(snakes);
		createLadders(ladders);
		createPlayers(players, signs);
		currentPlaying = firstP;
	}	
	public boolean getIfWinner() {
		return winner;
	}
	private void setWinnerTrue() {
		winner = true;
	}
	public Player getWinner() {
		return winnerPlayer;
	}
	private void setWinner(Player p) {
		winnerPlayer = p;
	}
	public Player getCurrentPlayer() {
		return currentPlaying;
	}
	public int getNumBox() {
		return numBox;
	}
	
	private void createPlayers(int p) {
		Player newPlayer = new Player(1, 1);
		startBox.setPlayerTrue();
		firstP = newPlayer;
		if(p == 1) {
			firstP.setNext(newPlayer);
		}
		createPlayers(p, 2, firstP);
	}
	private void createPlayers(int p, int currentP, Player current) {
		if(currentP<=p) {
			Player newPlayer = new Player(1, currentP);
			if(currentP==p) {
				newPlayer.setNext(firstP);
			}
			current.setNext(newPlayer);
			createPlayers(p, currentP+1, current.getNext());
		}
	}
	private void createPlayers(int p, String signs) {
		Player newPlayer = new Player(1, signs.charAt(0));
		startBox.setPlayerTrue();
		firstP = newPlayer;
		createPlayers(p, signs, 2, firstP);
	}
	private void createPlayers(int p, String signs, int currentP, Player current) {
		if(currentP<=p) {
			Player newPlayer = new Player(1, signs.charAt(currentP-1));
			if(currentP == p) {
				newPlayer.setNext(firstP);
			}
			current.setNext(newPlayer);
			createPlayers(p, signs, currentP+1, current.getNext());
		}
	}
	
	public int movePlayer() {
		Player current = currentPlaying;
		return movePlayer(current);
	}
	private int movePlayer(Player pl) {
		int nMove = (int) Math.floor(Math.random()*(6)+1);
		int newBox = nMove+pl.getCurrentBox();
		if(numBox>=newBox) {
			newBox = movePlayerLS(newBox);
		}
		if(numBox>=newBox) {
			pl.setCurrentBox(newBox);
			searchBox(newBox, startBox).setPlayerTrue();
			if(numBox == newBox) {
				setWinnerTrue();
				setWinner(currentPlaying);
			}
		}
		currentPlaying.increaseMoves();
		currentPlaying = pl.getNext();
		return nMove;
	}
	private int movePlayerLS(int nBox) {
		Box box = searchBox(nBox, startBox);
		if(box.haveSnake()) {
			Snake s = searchSnake(nBox, firstS);
			if(nBox == s.getStart()) {
				return s.getEnd();
			}else {
				return nBox;
			}
		}else if(box.haveLadder()) {
			Ladder l = searchLadder(nBox, firstL);
			if(nBox == l.getStart()) {
				return l.getEnd();
			}else {
				return nBox;
			}
		}else {
			return nBox;
		}
	}
	public void simulGame() {
		if(!winner) {
			movePlayer(currentPlaying);
		}
	}
	private void createLadders(int l) {
		if(l>0) {
			int start = getStartLadder();
			int end = getEndLadder(start);
			firstL = new Ladder(1, start, end);
			createLadders(l, 2, firstL);
		}
	}
	private void createLadders(int l, int currentL, Ladder current) {
		if(currentL<=l) {
			int start = getStartLadder();
			int end = getEndLadder(start);
			Ladder newLadder = new Ladder(currentL, start, end);
			current.setNext(newLadder);
			createLadders(l, currentL+1, current.getNext());
		}
	}
	private int getStartLadder() {
		int start = (int) Math.floor(Math.random()*(numBox-1-numColumns)+2);
		Box box = searchBox(start, startBox);
		if(box.haveLadder() || box.haveSnake() ) {
			return getStartLadder();
		}else {
			box.setLadderTrue();
			return start;
		}
	}
	private int getEndLadder(int start) {
		if(start%numColumns == 0) {
			int end = (int) Math.floor(Math.random()*(numBox-start-1)+start+1);
			Box box = searchBox(end, startBox);
			if(box.haveLadder() || box.haveSnake()) {
				return getEndLadder(start);
			}else {
				box.setLadderTrue();
				return end;
			}
		}else {
			return getEndLadder(start+1);
		}
	}
	private Ladder searchLadder(int nLadder, Ladder current) {
		if(nLadder == current.getStart() || nLadder == current.getEnd()) {
			return current;
		}else {
			current = current.getNext();
			return searchLadder(nLadder, current);
		}
	}
	
	private void createSnakes(int s) {
		if(s>0) {
			int start = getStartSnake();
			int end = getEndSnake(start);
			char snakeC = 'A';
			firstS = new Snake(snakeC, start, end);
			snakeC++;
			createSnakes(s, 2, snakeC, firstS);
		}
	}
	private void createSnakes(int s, int currentS,char snakeC, Snake current) {
		if(currentS<=s) {
			int start = getStartSnake();
			int end = getEndSnake(start);
			Snake newSnake = new Snake(snakeC, start, end);
			current.setNext(newSnake);
			snakeC++;
			createSnakes(s, currentS+1, snakeC, current.getNext());
		}
	}
	private int getStartSnake() {
		int start = (int) Math.floor(Math.random()*(numBox-numColumns-1)+numColumns+1);
		Box box = searchBox(start, startBox); 
		if(box.haveLadder() || box.haveSnake()) {
			return getStartSnake();
		}else {
			box.setSnakeTrue();
			return start;
		}
	}
	private int getEndSnake(int start) {
		if(start%numColumns == 0) {
			int end = (int) Math.floor(Math.random()*(start-1)+1);
			Box box = searchBox(end, startBox);
			if(box.haveLadder() || box.haveSnake()) {
				return getEndSnake(start);
			}else {
				box.setSnakeTrue();
				return end;
			}
		}else {
			return getEndSnake(start-1);
		}
	}
	private Snake searchSnake(int nSnake, Snake current) {
		if(nSnake == current.getStart() || nSnake == current.getEnd()) {
			return current;
		}else {
			return searchSnake(nSnake, current.getNext());
		}
	}
	
	public void createBox() {
		startBox = new Box(1);
		createBox(startBox, 1);
	}
	private void createBox(Box current, int i) {
		if(i<numBox) {
			Box nBox = new Box(i+1);
			current.setNext(nBox);
			nBox.setPrevious(current);
			if(i == numBox-1) {
				goalBox = nBox;
			}
			createBox(nBox, i+1);
		}
	}
	private Box searchBox(int nBox, Box current) {
		if(nBox>0) {
			if(nBox == current.getNumBox()) {
				return current;
			}else {
				current = current.getNext();
				return searchBox(nBox, current);
			}
		}else return null;		
	}
	
	public String toString() {
		String msg;
		int tempColumns = numColumns;
		int tempBox = numBox;
		int tempRow = numRows;
		msg = toStringRow(goalBox, tempColumns, tempBox, tempRow);
		return msg;
	}
	private String toStringRow(Box firstRow, int tempColumns, int tempBox, int tempRow) {
		String msg = "";
		if(firstRow!=null) {
			msg = toStringBox(firstRow, tempColumns, tempRow) + "\n";
			tempBox -= numColumns;
			tempRow--;
			msg += toStringRow(searchBox(tempBox, startBox), tempColumns, tempBox, tempRow);
		}
		return msg;
	}
	private String toStringBox(Box current, int tempColumns, int tempRow) {
		String msg = "";
		if(current!=null && tempColumns>0) {
			msg = "[( "+current.getNumBox();
			if(current.haveLadder()) {
				Ladder l = searchLadder(current.getNumBox(), firstL);
				msg += " "+l.getLadder();
			}else if(current.haveSnake()) {
				Snake s = searchSnake(current.getNumBox(), firstS);
				msg += " "+s.getSnake();
			}else {
				
			}
			msg += " )]";
			if(tempRow % 2 == 0) {
				msg += toStringBox(current.getPrevious(), tempColumns-1, tempRow);
			}else msg = toStringBox(current.getPrevious(), tempColumns-1, tempRow)+msg;
		}
		return msg;
	}
	
	public String getGame() {
		String msg;
		int tempColumns = numColumns;
		int tempBox = numBox;
		int tempRow = numRows;
		msg = getGameRow(goalBox, tempColumns, tempBox, tempRow);
		return msg;
	}
	private String getGameRow(Box firstRow, int tempColumns, int tempBox, int tempRow) {
		String msg = "";
		if(firstRow!=null) {
			msg = getGameBox(firstRow, tempColumns, tempRow) + "\n";
			tempBox -= numColumns;
			tempRow--;
			msg += getGameRow(searchBox(tempBox, startBox), tempColumns, tempBox, tempRow);
		}
		return msg;
	}
	private String getGameBox(Box current, int tempColumns, int tempRow) {
		String msg = "";
		if(current!=null && tempColumns>0) {
			msg = "[( ";
			if(current.haveLadder()) {
				Ladder l = searchLadder(current.getNumBox(), firstL);
				msg += ""+l.getLadder();
			}
			if(current.haveSnake()) {
				Snake s = searchSnake(current.getNumBox(), firstS);
				msg += ""+s.getSnake();
			}
			if(current.havePlayer()) {
				msg += getPlayerBox(current.getNumBox());
			}
			msg += " )]";
			if(tempRow % 2 == 0) {
				msg += getGameBox(current.getPrevious(), tempColumns-1, tempRow);
			}else msg = getGameBox(current.getPrevious(), tempColumns-1, tempRow)+msg;
		}
		return msg;
	}
	private String getPlayerBox(int nBox) {
		String msg = "";
		Player currentP = firstP;
		if(currentP.getCurrentBox() == nBox) {
			msg += currentP.getSign();
		}
		currentP = currentP.getNext();
		return getPlayerBox(nBox, currentP, msg);
	}
	private String getPlayerBox(int nBox, Player currentP, String msg) {
		if(currentP != firstP) {
			if(currentP.getCurrentBox() == nBox) {
				msg += currentP.getSign();
			}
			return getPlayerBox(nBox, currentP.getNext(), msg);
		}else {
			return msg;
		}
	}
}
