package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Checker {
	private int x;
	private int y;
	private int width;
	private int height;

	private int row;
	private int col;
	
	private int margin = 10;
	private float opacity = 1f;
	
	private Board board;
	private CheckerType type;
	
	private boolean isNeedRemove = false;
	private boolean isNeedRepaint = true;
	private boolean isAnimate = false;
	private boolean isAnimateLocation = false;

	private long lastAnimationTime;
	private long animationStarted;
	private long animationDuration;
	private static final int ANIMATION_REFRESH = 1000 / 60;
	private int dx;
	private int dy;
	private float dOpacity;
	
	public enum CheckerType {
		PLAYER_MEN, PLAYER_KING, ENEMY_MEN, ENEMY_KING;
	}
	
	public Checker(Board board, int row, int col, CheckerType type) {
		this.board = board;
		this.type = type;

		this.row = row;
		this.col = col;
		this.width = this.height = board.getCellSize();
			
		calculateBounds();
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
	
	public void setCel(int row, int col) {
		this.row = row;
		this.col = col;
				
		calculateBounds();
	}
	
	public void setMargin(int margin) {
		this.margin = margin;
		calculateBounds();
	}
	
	public boolean isNeedRepaint() {
		return isNeedRepaint;
	}
	
	public void moveToCell(int row, int col) {
		moveToCellAnimation(row, col, 300);
	}
	
	private void moveToCellAnimation(int row, int col, int millis) {
		this.row = row;
		this.col = col;
		
		animationMode(millis);
		isAnimateLocation = true;
		dx = col * board.getCellSize() + margin;
		dy = row * board.getCellSize() + margin;
	}
	
	private void calculateBounds() {
		x = board.getCellSize() * col + margin;
		y = board.getCellSize() * row + margin;
		
		width = height = board.getCellSize() - 2*margin;
	}


	public void update() {
		if (isAnimate) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastAnimationTime < ANIMATION_REFRESH)  return;
			
			double progress = (currentTime - animationStarted)/(double) animationDuration;
			if (progress > 1) progress = 1;
			
			if (isAnimateLocation) {
				x += (dx - x) * progress;
				y += (dy - y) * progress;
			}
			
			if (dOpacity != opacity) {
				opacity *= (1-progress);		
				if (opacity == 0) isNeedRemove = true;
			}
			
			if (progress >= 1) {
				x = dx;
				y = dy;
				isAnimate = false;
				isAnimateLocation = false;
			}
			lastAnimationTime = currentTime;
		}
	}
	
	public void remove() {
		fadeOut(200);
	}
	
	private void fadeOut(int millis) {
		animationMode(millis);
		dOpacity = 0;
	}
	
	private void animationMode(int millis) {
		isAnimate = true;
		animationDuration = millis;
		animationStarted = lastAnimationTime = System.currentTimeMillis();
	}
	
	public void render(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		
        g2.setColor(type == CheckerType.PLAYER_MEN ? Color.RED : Color.ORANGE);
		g2.fillOval(x, y, width, height);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public boolean isNeedRemove() {
		return isNeedRemove;
	}

}
