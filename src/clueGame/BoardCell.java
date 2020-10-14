package clueGame;

import java.util.*;

public class BoardCell {
	
	private int row;
	private int col;
	private char secretPassage;
	private DoorDirection doorDirection;
	private Room room;
	private boolean isRoomLabel = false;
	private boolean isRoomCenter = false;
	private boolean isRoom = false;
	private boolean isOccupied = false;
	private boolean isDoorway = false;
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
	
	public boolean isRoom() {
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
		return this.isDoorway;
	}
	
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}

	public boolean isLabel() {
		return this.isRoomLabel;
	}

	public boolean isRoomCenter() {
		return this.isRoomCenter;
	}

	public char getSecretPassage() {
		return this.secretPassage;
	}
	
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}

	public void setRoomLabel(boolean isRoomLabel) {
			this.isRoomLabel = isRoomLabel;
	}

	public void setRoomCenter(boolean isRoomCenter) {
		this.isRoomCenter = isRoomCenter;
	}

	
}