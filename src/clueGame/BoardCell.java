package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;

public class BoardCell {

	private int row;
	private int col;
	private char secretPassage;
	private DoorDirection doorDirection;
	private Room room;
	private boolean isRoomLabel = false;
	private boolean isRoomCenter = false;
	private boolean isOccupied = false;
	private boolean isDoorway = false;
	Set<BoardCell> adjList;

	BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		adjList = new HashSet<>();
		isOccupied = false;
	}
	
	public void draw(Graphics g, Dimension cellSize, int x, int y) {
		g.setColor(Color.BLUE);
		g.drawRect(x, y, cellSize.width, cellSize.height);
	}

	public void setOccupied(boolean isOccupied) {
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

	public void setRoomLabel(boolean isRoomLabel) {
		this.isRoomLabel = isRoomLabel;
	}

	public boolean isRoomCenter() {
		return this.isRoomCenter;
	}

	public void setRoomCenter(boolean isRoomCenter) {
		this.isRoomCenter = isRoomCenter;
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

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

}