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
	
	public void removeAt(int row, int col) {
		checkers[row][col].remove();
	}
	
	//It isn't ready method
	public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {			
		Checker from = checkers[fromRow][fromCol];
		from.moveToCell(toRow, toCol);
		
		checkers[toRow][toCol] = from;
		from = null;
	}
	
	public boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
		Player player = board.getWorld().getGame().getCurrentPlayer();
		
		return false;
	}
	
	public boolean canJump(int fromRow, int fromCol, int toRow, int toCol) {
		Player player = board.getWorld().getGame().getCurrentPlayer();
		
		return false;
	}
	
	public void update() {
		for (int i=0; i<board.getCellsPerRow(); i++)
			for (int j=0; j<board.getCellsPerRow(); j++) {
				Checker checker = checkers[i][j];
				if (checker != null) {
					checker.update();
					if (checker.isNeedRemove()) checker = null;
				}
			}
	}

	public void render(Graphics g) {
		Checker[][] checkers = getCheckers();
		
		for (int i=0; i<board.getCellsPerRow(); i++)
			for (int j=0; j<board.getCellsPerRow(); j++)
				if (checkers[i][j] != null) checkers[i][j].render(g);
	}

}
