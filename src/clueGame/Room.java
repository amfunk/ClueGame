package clueGame;

public class Room {
	private String name;
	private char symbol;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	public char getSymbol() {
		return this.symbol;
	}
	
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	
	public BoardCell getLabelCell() {
		return this.labelCell;
	}
	
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	
	public BoardCell getCenterCell() {
		return this.centerCell;
	}
}