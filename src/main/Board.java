package main;

import javax.swing.*;
import java.awt.*;


public class Board {
	private BoardArea world;
	
    private int cellsPerRow;
    private final int SIZE;
    private boolean isNeedRepaint = true;
    
    private static final Color ODD_COLOR = new Color(217, 189, 129);
    private static final Color EVEN_COLOR = new Color(141, 52, 53);
    
    public Board(BoardArea world, int size, int cellsPerRow){
    	this.world = world;
        this.SIZE = size;
        this.cellsPerRow = cellsPerRow;
  
    }
    
    public boolean isNeedRepaint() {
    	return isNeedRepaint;
    }
    
    public double getCellDimension() {
    	return SIZE / (double) cellsPerRow;
    }
    
    public int getCellsPerRow() {
    	return cellsPerRow;
    }
    
    public int getSize() {
    	return SIZE;
    }

    protected void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        double cellSize = SIZE / cellsPerRow;

        for (int i = 0; i < cellsPerRow; i++) {
            for (int j = 0; j < cellsPerRow; j++) {
                g2.setColor(((j +i) % 2 == 0) ? EVEN_COLOR : ODD_COLOR );
                g2.fillRect((int) (j * cellSize), (int) (i * cellSize), (int) cellSize, (int) cellSize);
            }
        }
        
        System.out.println("Painted checkers board.");
        isNeedRepaint = false;
    }

	public void update() {
		
	}

}