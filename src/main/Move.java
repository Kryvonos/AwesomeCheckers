package main;

import java.io.Serializable;

public class Move implements Serializable {
	
	private int fromX;
	private int fromY;
	private int toX;
	private int toY;
	
	
	
	public Move(int fromRow, int fromCol, int toRow, int toCol){
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
	}

	public int getFromX() {
		return fromX;
	}

	public void setFromX(int fromX) {
		this.fromX = fromX;
	}

	public int getFromY() {
		return fromY;
	}

	public void setFromY(int fromY) {
		this.fromY = fromY;
	}

	public int getToX() {
		return toX;
	}

	public void setToX(int toX) {
		this.toX = toX;
	}

	public int getToY() {
		return toY;
	}

	public void setToY(int toY) {
		this.toY = toY;
	}

	@Override
	public String toString() {
		return "Move [fromX=" + fromX + ", fromY=" + fromY + ", toX=" + toX
				+ ", toY=" + toY + "]";
	}

}
