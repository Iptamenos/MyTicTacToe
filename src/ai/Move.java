package ai;

import java.io.Serializable;

/* A class describing a move in the board
 * Every produced child corresponds to a move
 * and we need to keep the moves as well as the states.
 */
public class Move implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3166547244121566931L;
	
	private int row;
	private int column;
	private int value;
	
	public Move() {
		this.row = 0;
		this.column = 0;
	}
	
	public Move(int row, int col) {
		this.row = row;
		this.column = col;
		this.value = 0;
	}
	
	public Move(int value) {
		this.row = 0;
		this.column = 0;
		this.value = value;
	}
	
	public Move(int row, int col, int value) {
		this.row = row;
		this.column = col;
		this.value = value;
	}
	
	public Move(Move otherMove) {
		this.row = otherMove.getRow();
		this.column = otherMove.getColumn();
		this.value = otherMove.getValue();
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "move [" + row + "][" + column + "]: " + value; 
	}
	
}
