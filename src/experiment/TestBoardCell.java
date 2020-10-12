package experiment;

import java.util.*;

public class TestBoardCell {
	
	private int row;
	private int col;
	private Boolean isRoom;
	private Boolean isOccupied;
	Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
	
	TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Set<TestBoardCell> getAdjList() {
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
	
}
