package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import model.Matrix;
import model.Score;

public class Menu {
	private final static String SAVE_PATH_FILE_SCORES = "data/scores.sal";
	
	private final static int PLAY = 1;
	private final static int PRINT_INFO = 2;
	private final static int EXIT = 3;
	
	private Matrix matrix;
	private Score rootScore;
	
	private boolean playing;
	private boolean winner;
	
	private static Scanner sc;
	
	public Menu() {
		sc = new Scanner(System.in);
		try {
			loadDataScores();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		playing = false;
		winner = false;
	}
	public void saveDataScores() throws FileNotFoundException, IOException {
		ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE_SCORES));
		oss.writeObject(rootScore);
		oss.close();
	}
	public void loadDataScores() throws ClassNotFoundException, IOException {
		File f = new File(SAVE_PATH_FILE_SCORES);
		if(f.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			rootScore = (Score) ois.readObject();
			ois.close();
		}
	}
	public void registerScore(String nickname, char sign, int moves) throws FileNotFoundException, IOException {
		int score = moves*matrix.getNumBox();
		Score newScore = new Score(nickname, sign, score);
		if(rootScore == null) {
			rootScore = newScore;
		}else {
			registerScore(newScore, rootScore);
		}
		saveDataScores();
	}
	private void registerScore(Score newScore, Score current) {
		if(newScore.getScore() >= current.getScore()) {
			if(current.getLeft() == null) {
				newScore.setParent(current);
				current.setLeft(newScore);
			}else {
				registerScore(newScore, current.getLeft());
			}
		}else {
			if(current.getRight() == null) {
				newScore.setParent(current);
				current.setRight(newScore);
			}else {
				registerScore(newScore, current.getRight());
			}
		}
	}
	public void getScores() {
		getScores(rootScore);
	}
	private void getScores(Score current) {
		if(current != null) {
			getScores(current.getLeft());
			System.out.println(current.toString());
			getScores(current.getRight());
		}
	}
	public void showMenu() {
		System.out.println("************** MENU **************");
		System.out.println("1) Jugar");
		System.out.println("2) Ver Posiciones");
		System.out.println("3) Salir");
		System.out.println("************** MENU **************");
	}
	public void showMenu2() {
		System.out.println("************** MENU **************");
		System.out.println("1) Jugar");
		System.out.println("2) Puntajes");
		System.out.println("3) Salir");
		System.out.println("************** MENU **************");
	}
	public int readChoice() {
		System.out.print("Opcion: ");
		int choice = sc.nextInt();
		sc.nextLine();
		return choice;
	}
	private void play() {
		if(!playing) {
			System.out.println("Ingrese los parametros para iniciar el juego:");
		}else {
			System.out.println("Ingrese un salto de linea para jugar.");
		}
		String inst = sc.nextLine();
		if(!playing) {
			playing = true;
			getGameData(inst);
		}
		play(inst);
	}
	private void getGameData(String inst) {
		String[] a = inst.split(" ");
		int rows = Integer.parseInt(a[0]);
		int columns = Integer.parseInt(a[1]);
		int snakes = Integer.parseInt(a[2]);
		int ladders = Integer.parseInt(a[3]);
		try {
			int players = Integer.parseInt(a[4]);
			matrix = new Matrix(rows, columns, snakes, ladders, players);
		}catch(NumberFormatException e){
			int players = a[4].length();
			matrix = new Matrix(rows, columns, snakes, ladders, players, a[4]);
		}
		System.out.println(matrix.toString());
		System.out.println(matrix.getGame());
		play();
	}
	private void gameSimulate() {
		if(!winner) {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
			}
			System.out.println("El jugador "+matrix.getCurrentPlayer().getSign()+" ha lanzado el dado y obtuvo "+matrix.movePlayer());
			System.out.println(matrix.getGame());
			if(matrix.getIfWinner()) {
				winner = true;
				System.out.println("Gana el jugador: "+matrix.getWinner().getSign()+" con "+matrix.getWinner().getMoves()+" tiradas");
				System.out.println("Jugador "+matrix.getWinner().getSign()+", ingresa tu nombre:");
				String nickname = sc.nextLine();
				try {
					registerScore(nickname, matrix.getWinner().getSign(), matrix.getWinner().getMoves());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			gameSimulate();
		}
	}
	private void play(String inst) {
		if(inst.equals("menu")) {
			return;
		}else if(inst.equals("num")) {
			System.out.println(matrix.toString());
			play();
		}else if(inst.equals("simul")) {
			gameSimulate();
		}else if(inst.isEmpty()) {
			if(!matrix.getIfWinner()) {
				winner = false;
				System.out.println("El jugador "+matrix.getCurrentPlayer().getSign()+" ha lanzado el dado y obtuvo "+matrix.movePlayer());
			}
			System.out.println(matrix.getGame());
			if(matrix.getIfWinner()) {
				winner = true;
				System.out.println("Gana el jugador: "+matrix.getWinner().getSign()+" con "+matrix.getWinner().getMoves()+" tiradas");
				System.out.println("Jugador "+matrix.getWinner().getSign()+", ingresa tu nombre:");
				String nickname = sc.nextLine();
				try {
					registerScore(nickname, matrix.getWinner().getSign(), matrix.getWinner().getMoves());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			play();
		}
	}
	public void doOperation(int choice) {
		switch(choice) {
			case PLAY:
				if(!winner) {
					play();
				}else {
					playing = false;
					winner = false;
					play();
				}
				break;
			case PRINT_INFO:
				if(!winner) {
					try {
						System.out.println(matrix.getGame());
					}catch(NullPointerException e) {
						System.out.println("No hay ningun juego en curso");
					}
				}else {
					getScores();
				}
			case EXIT:
				break;
			default:
				System.out.println("Opcion invalida, ingrese otra opcion");
		}
	}
	public void startProgram() {
		int choice;
		if(!winner) {
			showMenu();
		}else {
			showMenu2();
		}
		choice = readChoice();
		doOperation(choice);
		if(choice != EXIT) {
			startProgram();
		}
	}
}
