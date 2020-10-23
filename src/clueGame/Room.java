package clueGame;

public class Room {
	private String name;
	private Character symbol;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private char secretPassage;
	private boolean isRoom = false;
	private boolean isWalkway = false;
	private boolean hasSecretPassage = false;
	
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

	public boolean isRoom() {
		return isRoom;
	}

	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

	public boolean isWalkway() {
		return isWalkway;
	}

	public void setWalkway(boolean isWalkway) {
		this.isWalkway = isWalkway;
	}

	public boolean isHasSecretPassage() {
		return hasSecretPassage;
	}

	public void setHasSecretPassage(boolean hasSecretPassage) {
		this.hasSecretPassage = hasSecretPassage;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
}
