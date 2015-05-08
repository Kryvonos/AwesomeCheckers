package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import main.Board.Cell;
import main.Checker.CheckerType;

public class CheckersPanel extends JPanel implements MouseListener {
	private Game game;
	
	public final int SIZE;
	
	public static final int CELLS_PER_ROW = 8;
	
	private Board board;
	private Checkers checkers;
	
	public CheckersPanel(Game game) {
		this.game = game;
		this.board = new Board(this, 600, CELLS_PER_ROW);
		this.checkers = board.getCheckers();
		this.SIZE = board.getSize();
		
		init();
	}
	
	private void init() {
		this.addMouseListener(this);

		System.out.println("size: " + board.getSize() + "; cellSize: " + board.getCellSize());
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Checker[][] getCheckers() {
		return checkers.getCheckers();
	}

	public void update() {
		checkers.update();
		board.update();
	}
	
	public Game getGame() {
		return game;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(SIZE, SIZE);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(SIZE, SIZE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		board.render(g);
		checkers.render(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = e.getY()/board.getCellSize();
		int col = e.getX()/board.getCellSize();
		
		Cell cell = board.getSelectedCell();
		Checker clickedChecker = checkers.getCheckerAt(row, col);
		
		if (cell == null) {
			board.selectCell(row, col);
			cell = board.getSelectedCell();
		}
		
		if (clickedChecker != null && clickedChecker.getPlayerId() != game.getCurrentPlayerId()) return;
		
		Checker selectedChecker = checkers.getCheckerAt(cell.row, cell.col);
		
		if (clickedChecker != null && selectedChecker != null && clickedChecker.getType() == selectedChecker.getType()) {
			board.selectCell(row, col);
			return;
		}
		
		if (checkers.canMove(cell.row, cell.col, row, col)) {
			checkers.makeMove(cell.row, cell.col, row, col);
			board.deselect();
			game.toggleCurrentPlayerId();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
