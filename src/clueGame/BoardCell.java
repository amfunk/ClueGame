package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	private boolean isTarget = false;

	BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		adjList = new HashSet<>();
		isOccupied = false;
	}

	public void draw(Graphics g, Dimension cellSize, int x, int y) {
		int width = cellSize.width;
		int height = cellSize.height;
		if (this.getRoom().isRoom()) {
			g.setColor(Color.gray);
			g.fillRect(x, y, width, height);
		} else if (this.getRoom().isWalkway()){
			g.setColor(Color.orange);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawRect(x, y, width, height);
		} else {
			g.setColor(Color.black);
			g.fillRect(x, y, width, height);
		}
	}


	public void drawDoorLabel(Graphics g, Dimension cellSize, int x, int y) {
		int width = cellSize.width;
		int height = cellSize.height;

		if (this.isDoorway) {
			switch (this.getDoorDirection()) {
			case UP:
				g.setColor(Color.blue);
				g.fillRect(x, y-height/5, width, height/5);
				break;
			case DOWN:
				g.setColor(Color.blue);
				g.fillRect(x, y+height, width, height/5);
				break;
			case LEFT:
				g.setColor(Color.blue);
				g.fillRect(x-width/5, y, width/5, height);
				break;
			case RIGHT:
				g.setColor(Color.blue);
				g.fillRect(x+width, y, width/5, height);
				break;
			case NONE:
				g.setColor(Color.white);
				g.fillRect(x, y, width, height);
				break;
			default:
				break;
			}
		} else if (this.isRoomLabel) {
			g.setColor(Color.blue);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, width/2));
			g.drawString(this.getRoom().getName(), x, y);
		}
	}

	public void highlightCell(Graphics g, Dimension cellSize, int x, int y) {
		int width = cellSize.width;
		int height = cellSize.height;
		g.setColor(Color.cyan);
		g.fillRect(x, y, width, height);
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

	public boolean isTarget() {
		return isTarget;
	}

	public void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}

}