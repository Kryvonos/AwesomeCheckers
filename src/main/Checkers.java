package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import main.Board.Cell;
import main.Checker.CheckerType;

public class Checkers {

	private int rowsQuantity;
	private Board board;
	private Checker[][] checkers;
	
	enum MoveError {
		OUT_OF_BOARD, ISNT_EMPTY, MOVE_BACK, NOT_DIAGONALLY, OTHER, NONE, JUMP_OWN_CHECKER, JUMP_SEVERAL_CHECKERS, MOVE_TO_MYSELF
	}

	public Checkers(Board board, int rowsQuantity) {
		this.board = board;
		this.rowsQuantity = rowsQuantity;
		this.checkers = new Checker[board.getCellsPerRow()][board
				.getCellsPerRow()];

		init();
		//initForTest();
	}

	public Checkers(Board board) {
		this(board, 3);
	}

	private void init() {
		int cellsPerRow = board.getCellsPerRow();

		for (int i = 0; i < cellsPerRow; i++) {
			if (i >= rowsQuantity && i < cellsPerRow - rowsQuantity)
				continue;

			for (int j = 0; j < cellsPerRow; j++) {
				if ((i + j) % 2 != 0) {
					checkers[i][j] = new Checker(board, i, j, (i < rowsQuantity) ? getEnemyId() : getPlayerId() , CheckerType.MAN);
				}
			}
		}
	}
	
//	private void initForTest() {
//		checkers[1][2] = new Checker(board, 1, 2, CheckerType.ENEMY_MEN);
//		checkers[1][4] = new Checker(board, 1, 4, CheckerType.ENEMY_MEN);
//		//checkers[3][2] = new Checker(board, 3, 2, CheckerType.ENEMY_MEN);
//		//checkers[3][4] = new Checker(board, 3, 4, CheckerType.ENEMY_MEN);
//
//		checkers[2][3] = new Checker(board, 2, 3, CheckerType.PLAYER_KING);
//		checkers[5][6] = new Checker(board, 5, 6, CheckerType.ENEMY_MEN);
//		//checkers[3][3] = new Checker(board, 3, 3, CheckerType.ENEMY_MEN);
//	}

	public Checker[][] getCheckers() {
		return checkers;
	}

	public Checker getCheckerAt(int row, int col) {
		return checkers[row][col];
	}

	public void removeAt(int row, int col) {
		checkers[row][col].remove();
	}

	public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
		Checker from = checkers[fromRow][fromCol];
		from.moveToCell(toRow, toCol);

		if (getCurrentPlayerId() == getPlayerId() && from.getRow() == 0)
			from.becomeKing();
		
		Checker jumpedChecker = getJumpedChecker(fromRow, fromCol, toRow, toCol);
		if (jumpedChecker != null) {
			jumpedChecker.remove();
			checkers[jumpedChecker.getRow()][jumpedChecker.getCol()] = null;
		}

		checkers[toRow][toCol] = from;
		checkers[fromRow][fromCol] = null;
	}

	public List<Move> getLegalMoves() {
		ArrayList<Move> jumps = new ArrayList<Move>();
		int currentPlayer = getCurrentPlayerId();
		int cellsPerRow = board.getCellsPerRow();
		
		int forward = -1;
		int back = 1;
		
		if (currentPlayer == getEnemyId()) {
			forward = 1;
			back = -1;
		}
		
		for (int i=0; i<cellsPerRow; i++) {
			for (int j=0; j<cellsPerRow; j++)
			{
				if (checkers[i][j] == null || checkers[i][j].getPlayerId() != currentPlayer) continue;
				
				
				for (int k=0; k<cellsPerRow; k++) {
					if (isOnBoard(i+2*back, j-2) && canMove(i, j, i+2*back, j-2)) 
						jumps.add(new Move(i, j, i+2*back, j-2));
				}
				if (isOnBoard(i+2*back, j+2) && canMove(i, j, i+2*back, j+2))
					jumps.add(new Move(i, j, i+2*back, j+2));
				if (isOnBoard(i+2*forward, j-2) && canMove(i, j, i+2*forward, j-2))
					jumps.add(new Move(i, j, i+2*forward, j-2));
				if (isOnBoard(i+2*forward, j+2) && canMove(i, j, i+2*forward, j+2))
					jumps.add(new Move(i, j, i+2*forward, j+2));
			}
			
		}
		
		return jumps;
	}
	
	public Checker getJumpedChecker(int fromRow, int fromCol, int toRow, int toCol) {
		int currentPlayerId = getCurrentPlayerId();
		boolean isAlreadyBeenChecker = false;
		int rowDirection = (toRow - fromRow < 0) ? -1 : 1;
		int colDirection = (toCol - fromCol < 0) ? -1 : 1;
		Checker jumpedChecker = null;
		
		for (int i=1; i<Math.abs(fromRow-toRow); i++) {
				Checker checker = checkers[fromRow + i*rowDirection][fromCol + i*colDirection];
				
				if (checker == null) continue;
				
				if (checker.getPlayerId() == currentPlayerId) {
					return null;
				}
				else {
					jumpedChecker = checker;
									
					if (!isAlreadyBeenChecker)
						isAlreadyBeenChecker = true;
					else
						return null;
				}
		}
		
		return jumpedChecker;
	}

	public boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
		System.out.println(canMoveWithError(fromRow, fromCol, toRow, toCol));
		return canMoveWithError(fromRow, fromCol, toRow, toCol) == MoveError.NONE ? true : false;
	}
	
	private MoveError canMoveWithError(int fromRow, int fromCol, int toRow, int toCol) {
		int cellsPerRow = board.getCellsPerRow();
		Cell cell = board.getSelectedCell();
		CheckerType currentCheckerType = checkers[cell.row][cell.col].getType();
		int currentPlayerId = getCurrentPlayerId();
		
		int back = (currentPlayerId == getEnemyId()) ? -1 : 1;
		
		if (fromRow == toRow && fromCol == toCol) return MoveError.MOVE_TO_MYSELF;
		
		if (fromRow < 0 || fromCol < 0 || toRow < 0 || toCol < 0
				|| fromRow >= cellsPerRow || fromCol >= cellsPerRow
				|| toRow >= cellsPerRow || toCol >= cellsPerRow)
		return MoveError.OUT_OF_BOARD;
		
		if (checkers[toRow][toCol] != null) return MoveError.ISNT_EMPTY;

		if (currentCheckerType == CheckerType.MAN) {	
			if (Math.abs(fromRow-toRow) == 1 && Math.abs(fromCol-toCol) == 1) {
				if (toRow == fromRow+back && (toCol == fromCol+1 || toCol == fromCol-1))  {
					return MoveError.MOVE_BACK;
				}
			} else if (Math.abs(fromRow-toRow) == 2 && Math.abs(fromCol-toCol) == 2) {
				Checker checker = checkers[(fromRow+toRow) / 2][(fromCol+toCol) / 2];
				if (checker == null || checker.getPlayerId() == currentPlayerId) {
					return MoveError.JUMP_OWN_CHECKER;
				}
			} else {
				return MoveError.NOT_DIAGONALLY;
			}
		} else {
			if (Math.abs(fromRow-toRow) != Math.abs(fromCol-toCol)) {
				return MoveError.NOT_DIAGONALLY;
			} else {
				boolean isAlreadyBeenChecker = false;
				int rowDirection = (toRow - fromRow < 0) ? -1 : 1;
				int colDirection = (toCol - fromCol < 0) ? -1 : 1;
				
				for (int i=1; i<Math.abs(fromRow-toRow); i++) {
						Checker checker = checkers[fromRow + i*rowDirection][fromCol + i*colDirection];
						
						if (checker == null) continue;
						
						if (checker.getPlayerId() == currentPlayerId || checker.getPlayerId() == currentPlayerId) {
							return MoveError.JUMP_OWN_CHECKER;
						} else if (!isAlreadyBeenChecker) {
							isAlreadyBeenChecker = true;
						}
						else
							return MoveError.JUMP_SEVERAL_CHECKERS;
				}
			}
			
		}
			
		return MoveError.NONE;
	}
	
	private ArrayList<Move> getJumpsAtDirection(int fromRow, int fromCol, int rowDirection, int colDirection) {
		ArrayList<Move> jumps = new ArrayList<Move>();
		int cellsPerRow = board.getCellsPerRow();
		boolean isAlreadyBeenChecker = false;
		
		for (int i=2; i<=cellsPerRow; i++) {
			//if (canMoveWithError(fromRow, fromCol, fromRow+i*rowDirection, fromCol+i*colDirection) )
		}
		
		return jumps;
	}

	private boolean isOnBoard(int row, int col) {
		return row < 0 || col < 0 || row > board.getCellsPerRow() || col >= board.getCellsPerRow() ? false : true; 
	}

	public void update() {
		for (int i = 0; i < board.getCellsPerRow(); i++)
			for (int j = 0; j < board.getCellsPerRow(); j++) {
				Checker checker = checkers[i][j];
				if (checker != null) {
					checker.update();
					if (checker.isNeedRemove())
						checker = null;
				}
			}
	}

	public void render(Graphics g) {
		Checker[][] checkers = getCheckers();
		Checker animatingChecker = null;

		for (int i = 0; i < board.getCellsPerRow(); i++)
			for (int j = 0; j < board.getCellsPerRow(); j++)
				if (checkers[i][j] != null) {
					if (checkers[i][j].isAnimate())
						animatingChecker = checkers[i][j];
					else
						checkers[i][j].render(g);
				}
		
		if (animatingChecker != null)
			animatingChecker.render(g);
	}

	private int getCurrentPlayerId() {
		return board.getWorld().getGame().getCurrentPlayerId();
	}
	
	private int getPlayerId() {
		return board.getWorld().getGame().getPlayerId();
	}
	
	private int getEnemyId() {
		return board.getWorld().getGame().getEnemyId();
	}

}
