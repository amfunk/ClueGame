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
	private Map<Character, Room> roomMap;

	private BoardCell[][] grid;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();

	private static Board theInstance = new Board();

	private Board() {

	}

	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		roomMap = new HashMap<Character, Room>(); //allocates memory for a new roomMap upon each new call to initialize
		try {
			loadConfigFiles();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (BadConfigFormatException e) {
			System.out.println(e);
		}
	}

	public void setConfigFiles(String layoutFile, String setupFile) {
		this.layoutConfigFile = layoutFile;
		this.setupConfigFile = setupFile;
	}

	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {
		loadSetupConfig();
		loadLayoutConfig();
	}

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		Room room;
		String temp;
		char symbol;
		File setupFile = new File(setupConfigFile);
		Scanner in = new Scanner(setupFile);
		while (in.hasNextLine()) {
			temp = in.nextLine();
			//ignores any commented lines
			if (!temp.startsWith("//")) {
				room = new Room(); //allocates new memory for each room that must be added to roomMap
				if(temp.startsWith("Room")) {
					room.setRoom(true);
				} else if (temp.startsWith("Space")) {
					room.setRoom(false);
				} else { //since the description must be either Room or Space, throw exception for anything else
					throw new BadConfigFormatException("Not a recognized cell type");
				}
				for (String val : temp.split(",")) {
					if(val.startsWith(" ")) {
						//removes space from substring
						val = val.substring(1, val.length());
						if(val.length() == 1) {
							symbol = val.charAt(0); // converts string into char
							room.setSymbol(symbol);
						} else {
							room.setName(val);
						}
					}
				}
				roomMap.put(room.getSymbol(), room);				
			}
		}
		in.close();
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		
		//does initial scan of layout file to determine rows and cols
		parseRowsCols();

		//initializes the board and adjList
		initializeBoard();
		initializeAdjLists();

		//populates the board with the values from the layout file
		populateBoardCells();
	}
	
	// Helper Functions are found below this point
	//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
	//
	//
	//
	
	private void populateBoardCells() throws FileNotFoundException, BadConfigFormatException {
		
		String temp;
		Character symbol;
		BoardCell cell;
		int rowCounter = 0;
		int colCounter = 0;
		Character modifier;
		
		File layoutFile = new File(layoutConfigFile);
		rowCounter = 0;
		colCounter = 0;
		Scanner in = new Scanner(layoutFile);
		while (in.hasNextLine()) {
			temp = in.nextLine();
			colCounter = 0;
			//splits up each row by the commas
			for (String val : temp.split(",")) {
				//if the string length is 1, this must be a normal cell
				if (val.length() == 1) {
					//converts string input into a char
					symbol = val.charAt(0);
					cell = getCell(rowCounter, colCounter);
					if (!roomMap.containsKey(symbol)) {
						throw new BadConfigFormatException("Room on board is not listed under setup file");
					}
					cell.setRoom(roomMap.get(symbol));
					cell.setIsRoom(cell.getRoom().isRoom());
				} else {
					cell = getCell(rowCounter,colCounter);
					//since this cell has more than one character, splits it into the initial symbol and then modifier
					symbol = val.charAt(0);
					modifier = val.charAt(1);
					cell.setRoom(roomMap.get(symbol));
					cell.setIsRoom(cell.getRoom().isRoom());
					interpretModifier(cell, modifier); //called to determine the type of board cell
				}
				colCounter++;
			}
			rowCounter++;
		}
		in.close();		
	}

	private void interpretModifier(BoardCell cell, Character modifier) {
		
		if (modifier.equals('#')) {
			cell.getRoom().setLabelCell(cell);
			cell.setRoomLabel(true);
		} else if (modifier.equals('*')) {
			cell.getRoom().setCenterCell(cell);
			cell.setRoomCenter(true);
		} else if (modifier.equals('^')) {
			cell.setIsDoorway(true);
			cell.setDoorDirection(DoorDirection.UP);
		} else if (modifier.equals('v')) {
			cell.setIsDoorway(true);
			cell.setDoorDirection(DoorDirection.DOWN);
		} else if (modifier.equals('<')) {
			cell.setIsDoorway(true);
			cell.setDoorDirection(DoorDirection.LEFT);
		} else if (modifier.equals('>')) {
			cell.setIsDoorway(true);
			cell.setDoorDirection(DoorDirection.RIGHT);
		} else {
			cell.setSecretPassage(modifier);
		}	
		
	}

	public void parseRowsCols() throws FileNotFoundException, BadConfigFormatException {
		
		String temp;
		int colsPerRow = 0;
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
			//finds size of initial row
			if (rowCounter == 0) {
				colsPerRow = colCounter;
			} else if (colCounter != colsPerRow){ // if any rows don't match the size of initial row, throws exception
				throw new BadConfigFormatException("Number of columns per row isn't constant");
			}
			this.cols = colCounter;
			rowCounter++;
		}
		this.rows = rowCounter;
		in.close();
	}
	
	public void initializeBoard() {
		grid = new BoardCell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
	}

	public void initializeAdjLists() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				generateAdjList(i, j);
			}
		}
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
				if (cell.isRoom() == true) {
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
		Room temp = roomMap.get(symbol);
		return temp;
	}

	public Room getRoom(BoardCell cell) {
		Room temp = cell.getRoom();
		return temp;
	}

	public int getNumRows() {
		return this.rows;
	}

	public int getNumCols() {
		return this.cols;
	}

}
