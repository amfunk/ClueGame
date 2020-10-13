package clueGame;

import java.util.*;
import java.io.*;

public class Board {

	private int rows;
	private int cols;
	private String roomName;
	private char roomSymbol;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private BoardCell[][] grid;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();

	private static Board theInstance = new Board();

	private Board() {
		
	}
	
	public void initializeAdjLists() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				generateAdjList(i, j);
			}
		}
	}

	public static Board getInstance() {
		return theInstance;
	}

	public void initializeBoard() {
		grid = new BoardCell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
	}

	public void initialize() {
		try {
			loadConfigFiles();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		initializeBoard();
		initializeAdjLists();
	}

	public void setConfigFiles(String layoutFile, String setupFile) {
		this.layoutConfigFile = layoutFile;
		this.setupConfigFile = setupFile;
	}

	public void loadConfigFiles() throws FileNotFoundException {
		loadSetupConfig();
		loadLayoutConfig();
	}

	public void loadSetupConfig() throws FileNotFoundException {
		Room room = new Room();
		String temp;
		char symbol;
		File setupFile = new File(setupConfigFile);
		Scanner in = new Scanner(setupFile);
		while (in.hasNextLine()) {
			temp = in.nextLine();
			if (!temp.startsWith("//")) {
				for (String val : temp.split(",")) {
					if(val.startsWith(" ")) {
						if(val.length() == 1) {
							symbol = val.charAt(0);
							room.setSymbol(symbol);
						} else {
							val = val.substring(1, val.length());
							room.setName(val);
						}
					}
				}
				roomMap.put(room.getSymbol(), room);
			}
		}
		in.close();
	}

	public void loadLayoutConfig() throws FileNotFoundException {
		String temp;
		char symbol;
		int rowCounter = 0;
		int colCounter = 0;
		File layoutFile = new File(layoutConfigFile);
		//First gets the num rows and cols
		Scanner in = new Scanner(layoutFile);
		while (in.hasNextLine()) {
			temp = in.nextLine();
			colCounter = 0;
			for (String val : temp.split(",")) {
				colCounter++;
			}
			this.cols = colCounter;
			rowCounter++;
		}
		this.rows = rowCounter;
		System.out.println("Rows: " + this.rows + ", Cols: " + this.cols);
		in.close();
		
		Scanner in2 = new Scanner(layoutFile);
		while (in2.hasNextLine()) {
			temp = in2.nextLine();
			for (String val : temp.split(",")) {
				if (val.length() == 1) {
					symbol = val.charAt(0);
					roomMap.get(symbol);
				} else {
					
				}
			}
		}
		in2.close();
	}

	public void generateAdjList(int row, int col) {
		BoardCell temp = this.getCell(row, col);
		//test for row edge cases
		if (row == 0) {
			temp.adjList.add(this.getCell(row+1, col));
		} else if (row == this.rows-1) {
			temp.adjList.add(this.getCell(row - 1, col));
		} else {
			//if not edge, must be in middle
			temp.adjList.add(this.getCell(row+1, col));
			temp.adjList.add(this.getCell(row - 1, col));
		}
		//test for col edge cases
		if (col == 0) {
			temp.adjList.add(this.getCell(row, col+1));
		} else if (col == this.cols-1) {
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
		return this.rows;
	}

	public int getNumCols() {
		return this.cols;
	}

	public Room getRoom(BoardCell cell) {
		Room temp = new Room();
		return temp;
	}

}