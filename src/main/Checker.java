package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Checker {
	private int x;
	private int y;
	private int width;
	private int height;
	
	private int playerId;

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
	private boolean isAnimateOpacity = false;

	private long lastAnimationTime;
	private long animationStarted;
	private long animationDuration;
	private static final int ANIMATION_REFRESH = 1000 / 60;
	private int dx;
	private int dy;
	private float dOpacity;
	
	public enum CheckerType {
		MAN, KING
	}
	
	public Checker(Board board, int row, int col, int playerId, CheckerType type) {
		this.board = board;
		this.type = type;
		
		this.playerId = playerId;

		this.row = row;
		this.col = col;
		this.width = this.height = board.getCellSize();
		
		this.type = CheckerType.KING;
			
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
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void becomeKing() {
		type = CheckerType.KING;
		System.out.println("I'm king motherfuck");
	}
	
	public void moveToCell(int row, int col) {
		moveToCellAnimation(row, col, 1000);
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
			
			if (isAnimateOpacity) {
				opacity *= (1-progress);	
				System.out.println(opacity);
				if (opacity == 0) isNeedRemove = true;
			}
			
			if (progress >= 1) {
				x = dx;
				y = dy;
				isAnimate = false;
				isAnimateLocation = false;
				isAnimateOpacity = false;
			}
			lastAnimationTime = currentTime;
		}
	}
	
	public void remove() {
		fadeOut(350);
	}	

	public boolean isAnimate() {
		return isAnimate;
	}
	
	private void fadeOut(int millis) {
		animationMode(millis);
		dOpacity = 0;
		isAnimateOpacity = true;
	}
	
	private void animationMode(int millis) {
		isAnimate = true;
		animationDuration = millis;
		animationStarted = lastAnimationTime = System.currentTimeMillis();
	}
	
	private int getCurrentPlayerId() {
		return board.getWorld().getGame().getCurrentPlayerId();
	}
	
	public void render(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		
        g2.setColor(playerId == 0 ? Color.RED : Color.ORANGE);
		g2.fillOval(x, y, width, height);
		
		if (type == CheckerType.KING) {
			g2.setFont(new Font("Tahom", Font.PLAIN, 25));
			g2.setColor(playerId != 0 ? Color.RED : Color.ORANGE);
			g2.drawString("K", x + width/2-7, y + height/2+9);
		}
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public boolean isNeedRemove() {
		return isNeedRemove;
	}
	
	public String toString() {
		return String.format("Checker [row: %s, col: %s]", row, col);
	}

	public int getPlayerId() {
		return playerId;
	}

}
