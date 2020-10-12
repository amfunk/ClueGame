package experiment;

import java.util.*;

public class TestBoard {

	final static int ROWS = 4;
	final static int COLS = 4;

	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;

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
		if (row == 0) {
			temp.adjList.add(this.getCell(row+1, col));
		} else if (row == ROWS-1) {
			temp.adjList.add(this.getCell(row - 1, col));
		} else {
			temp.adjList.add(this.getCell(row+1, col));
			temp.adjList.add(this.getCell(row - 1, col));
		}
		if (col == 0) {
			temp.adjList.add(this.getCell(row, col+1));
		} else if (col == COLS-1) {
			temp.adjList.add(this.getCell(row, col-1));
		} else {
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

	public void calcTargets(TestBoardCell startCell, int pathlength) {

	}

	public Set<TestBoardCell> getTargets() {
		Set<TestBoardCell> temp = new HashSet<TestBoardCell>();
		return temp;
	}

	public TestBoardCell getCell(int row, int col) {
		TestBoardCell temp = this.grid[row][col];
		return temp;
	}

}
