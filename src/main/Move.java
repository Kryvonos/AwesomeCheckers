package main;

import java.io.Serializable;

public class Move implements Serializable {
	
	private int fromX;
	private int fromY;
	private int toX;
	private int toY;
	
	
	
	public Move(int fromRow, int fromCol, int toRow, int toCol){
		this.fromX = fromRow;
		this.fromY = fromCol;
		this.toX = toRow;
		this.toY = toCol;
	}

	public int getFromRow() {
		return fromX;
	}

	public void setFromRow(int fromX) {
		this.fromX = fromX;
	}

	public int getFromCol() {
		return fromY;
	}

	public void setFromCol(int fromY) {
		this.fromY = fromY;
	}

	public int getToRow() {
		return toX;
	}

	public void setToRow(int toX) {
		this.toX = toX;
	}

	public int getToCol() {
		return toY;
	}

	public void setToCol(int toY) {
		this.toY = toY;
	}

	@Override
	public String toString() {
		return "Move [fromX=" + fromX + ", fromY=" + fromY + ", toX=" + toX
				+ ", toY=" + toY + "]";
	}

}
