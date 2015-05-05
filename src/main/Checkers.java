package main;

import java.awt.Graphics;

import main.Checker.CheckerType;

public class Checkers {
	
	private int rowsQuantity;
	private Board board;
	private Checker[][] checkers;
	
	public Checkers(Board board, int rowsQuantity) {
		this.board = board;
		this.rowsQuantity = rowsQuantity;
		this.checkers = new Checker[board.getCellsPerRow()][board.getCellsPerRow()];

		System.out.println("!" + board.getCellsPerRow());
		
		init();
	}
	
	public Checkers(Board board) {
		this(board, 3);
	}
	
	private void init() {
		int cellsPerRow = board.getCellsPerRow();

		for (int i=0; i<cellsPerRow; i++) {
			if (i >= rowsQuantity && i < cellsPerRow - rowsQuantity) continue;
			
			for (int j=0; j<cellsPerRow; j++) {
				if ((i+j) % 2 != 0) {
					checkers[i][j] = new Checker(board, i, j, (i > rowsQuantity) ? CheckerType.ENEMY_MEN : CheckerType.PLAYER_MEN);
				}
			}
		}
		
	}
	
	public Checker[][] getCheckers() {
		return checkers;
	}
	
	public Checker getCheckerAt(int row, int col) {
		return checkers[row][col];
	}
	
	//It isn't ready method
	public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {		
		checkers[toRow][toCol] = checkers[fromRow][toCol];
		checkers[fromRow][toCol] = null;
		
		checkers[fromRow][toCol].moveToCell(toRow, toCol);
	}

	public void update() {
		for (int i=0; i<board.getCellsPerRow(); i++)
			for (int j=0; j<board.getCellsPerRow(); j++)
				if (checkers[i][j] != null) checkers[i][j].update();
	}

	public void render(Graphics g) {
		System.out.println("rendering checkers");
		Checker[][] checkers = getCheckers();
		
		for (int i=0; i<board.getCellsPerRow(); i++)
			for (int j=0; j<board.getCellsPerRow(); j++)
				if (checkers[i][j] != null) checkers[i][j].render(g);
	}

}
