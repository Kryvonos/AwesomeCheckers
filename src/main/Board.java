package main;

import javax.swing.*;
import java.awt.*;


public class Board {
	private CheckersPanel world;
	private Checkers checkers;
	
    private int cellsPerRow;
    private final int SIZE;
    private boolean isNeedRepaint = true;
    
    private int selectedRow = -1;
    private int selectedCol = -1;
    
    private static final Color ODD_COLOR = new Color(141, 52, 53);
    private static final Color EVEN_COLOR = new Color(217, 189, 129);
    
    public class Cell {
    	public int row;
    	public int col;
    	
    	public Cell(int row, int col) {
    		this.row = row;
    		this.col = col;
    	}
    }
    
    public Board(CheckersPanel world, int size, int cellsPerRow){
    	this.world = world;
        this.SIZE = size/cellsPerRow * cellsPerRow;
        this.cellsPerRow = cellsPerRow;
    	this.checkers = new Checkers(this);
    }
    
    public boolean isNeedRepaint() {
    	return isNeedRepaint;
    }
    
    public int getCellSize() {
    	return SIZE / cellsPerRow;
    }
    
    public int getCellsPerRow() {
    	return cellsPerRow;
    }
    
    public int getSize() {
    	return SIZE;
    }
    
    public CheckersPanel getWorld() {
    	return world;
    }
    
    public void selectCell(int row, int col) {
    	selectedRow = row;
    	selectedCol = col;
    }
    
    public void deselect() {
    	selectCell(-1, -1);
    }
    
    public Cell getSelectedCell() {
    	return (selectedRow > 0 || selectedCol > 0) ? new Cell(selectedRow, selectedCol) : null;
    }

    public void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        int cellSize = getCellSize();

        g2.fillRect(10, 10, 40, 20);
        
        for (int i = 0; i < cellsPerRow; i++) {
            for (int j = 0; j < cellsPerRow; j++) {
                g2.setColor(((j +i) % 2 == 0) ? EVEN_COLOR : ODD_COLOR );
                g2.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
        
        if (selectedRow != -1 && selectedCol != -1 && selectedRow < cellsPerRow && selectedCol < cellsPerRow) {
        	g2.setColor(Color.WHITE);
        	g2.drawRect(selectedCol*cellSize, selectedRow*cellSize, cellSize-1, cellSize-1);
        	g2.drawRect(selectedCol*cellSize+1, selectedRow*cellSize+1, cellSize-3, cellSize-3);
        }
  
        isNeedRepaint = false;
    }

	public void update() {
		
	}

	public Checkers getCheckers() {
		return checkers;
	}

}