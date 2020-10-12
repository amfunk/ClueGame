package clueGame;

import java.util.*;

public class BoardCell {
	
	private int row;
	private int col;
	private char initial;
	private char secretPassage;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean isRoom = false;
	private boolean isOccupied = false;
	Set<BoardCell> adjList = new HashSet<BoardCell>();
	
	BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Set<BoardCell> getAdjList() {
		return this.adjList;
	}
	
	public void setIsRoom(boolean isRoom) {
		if (isRoom) {
			this.isRoom = true;
		} else {
			this.isRoom = false;
		}
	}
	
	public boolean getIsRoom() {
		return this.isRoom;
	}
	
	public void setIsOccupied(boolean isOccupied) {
		if (isOccupied) {
			this.isOccupied = true;
		} else {
			this.isOccupied = false;
		}
	}
	
	public boolean getIsOccupied() {
		return this.isOccupied;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return DoorDirection.NONE;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return this.secretPassage;
	}
	
}