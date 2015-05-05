package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Checker {
	private int x;
	private int y;
	private int row;
	private int col;
	private int width;
	private int height;
	
	private int margin = 10;
	
	private Board board;
	private CheckerType type;
	
	private boolean isNeedRemove = false;
	private boolean isNeedRepaint = true;
	
	public enum CheckerType {
		PLAYER_MEN, PLAYER_KING, ENEMY_MEN, ENEMY_KING;
	}
	
	public Checker(Board board, int row, int col, CheckerType type) {
		this.board = board;
		this.type = type;
		
		this.x = board.getCellSize() * col;
		this.y = board.getCellSize() * row;
		this.row = row;
		this.col = col;
		this.width = this.height = board.getCellSize();
			
		calculateSize();
	}
	
	public CheckerType getType() {
		return type;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(int delX, int delY) {
		x += delX;
		y += delY;
	}
	
	public void moveToCell(int row, int col) {
	}
	
	public void setMargin(int margin) {
		this.margin = margin;
		calculateSize();
	}
	
	private void calculateSize() {
		x = board.getCellSize() * col + margin;
		y = board.getCellSize() * row + margin;
		
		width = height = board.getCellSize() - 2*margin;
	}
	
	public boolean isNeedRepaint() {
		return isNeedRepaint;
	}

	public void update() {
		// TODO realize checker update
	}
	
	public void remove() {
		
	}
	
	public void render(Graphics g){
		Graphics2D g2 = (Graphics2D) g;

        g2.setColor(type == CheckerType.PLAYER_MEN ? Color.RED : Color.ORANGE);
		g2.fillOval(x, y, width, height);
	}

}
