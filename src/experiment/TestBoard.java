package experiment;

import java.util.*;

public class TestBoard {

	final static int ROWS = 4;
	final static int COLS = 4;

	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();;
	private Set<TestBoardCell> visited = new HashSet<TestBoardCell>();;

	public TestBoard() {
		initializeBoard();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				generateAdjList(i, j);
			}
		}
	}

	public void generateAdjList(int row, int col) {
		TestBoardCell temp = this.getCell(row, col);
		//test for row edge cases
		if (row == 0) {
			temp.adjList.add(this.getCell(row+1, col));
		} else if (row == ROWS-1) {
			temp.adjList.add(this.getCell(row - 1, col));
		} else {
			//if not edge, must be in middle
			temp.adjList.add(this.getCell(row+1, col));
			temp.adjList.add(this.getCell(row - 1, col));
		}
		//test for col edge cases
		if (col == 0) {
			temp.adjList.add(this.getCell(row, col+1));
		} else if (col == COLS-1) {
			temp.adjList.add(this.getCell(row, col-1));
		} else {
			//not edge so must be in middle
			temp.adjList.add(this.getCell(row, col+1));
			temp.adjList.add(this.getCell(row, col-1));
		}

	}

	public void initializeBoard() {
		grid = new TestBoardCell[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
	}

	public void findAllTargets(TestBoardCell startCell, int pathlength) {
		for (TestBoardCell cell : startCell.adjList) {
			if (!visited.contains(cell) && cell.getIsOccupied() == false) {
				visited.add(cell);
				if (cell.getIsRoom() == true) {
					targets.add(cell);
					//if the cell is a room, then the path must stop here so it adds cell to targets and doesn't execute recursive function
				} else if (pathlength == 1) {
					//if the pathlength is 1 then we add to targets
					targets.add(cell);
				} else {
					//assuming the adj cell isn't a room and we still have more steps, execute recursive function
					findAllTargets(cell, pathlength-1);
				}
				//after testing adj cells, remove cell from visited so it doesn't show up on different paths
				visited.remove(cell);
			}
		}
	}


	public void calcTargets(TestBoardCell startCell, int pathlength) {
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}

	public Set<TestBoardCell> getTargets() {
		return targets;
	}

	public TestBoardCell getCell(int row, int col) {
		TestBoardCell temp = this.grid[row][col];
		return temp;
	}

}
