package clueGame;

import java.util.*;

public class Board {

	final static int ROWS = 25;
	final static int COLS = 24;

	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private BoardCell[][] grid;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();

	private static Board theInstance = new Board();

	private Board() {
		initializeBoard();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				generateAdjList(i, j);
			}
		}
	}

	public static Board getInstance() {
		return theInstance;
	}

	public void initializeBoard() {
		grid = new BoardCell[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
	}
	
	public void initialize() {
		
	}
	
	public void setConfigFiles(String layoutFile, String setupFile) {
		this.layoutConfigFile = layoutFile;
		this.setupConfigFile = setupFile;
	}
	
	public void loadConfigFiles() {
		
	}
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}

	public void generateAdjList(int row, int col) {
		BoardCell temp = this.getCell(row, col);
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

	public void findAllTargets(BoardCell startCell, int pathlength) {
		for (BoardCell cell : startCell.adjList) {
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


	public void calcTargets(BoardCell startCell, int pathlength) {
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell(int row, int col) {
		BoardCell temp = this.grid[row][col];
		return temp;
	}
	
	public Room getRoom(char symbol) {
		Room temp = new Room();
		return temp;
	}
	
	public int getNumRows() {
		return ROWS;
	}
	
	public int getNumCols() {
		return COLS;
	}

	public Room getRoom(BoardCell cell) {
		Room temp = new Room();
		return temp;
	}

}
