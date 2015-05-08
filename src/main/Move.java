package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;

public class Move implements Serializable {

	private int fromCell;
	private int fromRow;
	private int toCell;
	private int toRow;
	
	public Move(int fromRow, int fromCol, int toRow, int toCol) {
		this.fromCell = fromRow;
		this.fromRow = fromCol;
		this.toCell = toRow;
		this.toRow = toCol;
	}

	public int getFromRow() {
		return fromCell;
	}

	public void setFromRow(int fromCell) {
		this.fromCell = fromCell;
	}

	public int getFromCol() {
		return fromRow;
	}

	public void setFromCol(int fromRow) {
		this.fromRow = fromRow;
	}

	public int getToRow() {
		return toCell;
	}

	public void setToRow(int toCell) {
		this.toCell = toCell;
	}

	public int getToCol() {
		return toRow;
	}

	public void setToCol(int toRow) {
		this.toRow = toRow;
	}

	@Override
	public String toString() {
		return "Move [fromCell=" + fromCell + ", fromRow=" + fromRow + ", toCell=" + toCell
				+ ", toRow=" + toRow + "]";
	}

}
