package clueGame;

import java.util.*;
import java.awt.Color;
import java.io.*;

public class Board {

	private int rows;
	private int cols;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;

	private Random num = new Random();

	private List<Card> deck;
	private List<Card> shuffleDeck;
	private Set<Card> dealtCards;
	private ArrayList<Player> players;
	private Solution theAnswer = Solution.getAnswer();

	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited = new HashSet<>();

	private static Board theInstance = new Board();

	private Board() {

	}

	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		roomMap = new HashMap<>(); //allocates memory for a new roomMap upon each new call to initialize
		try {
			loadConfigFiles();
		} catch (FileNotFoundException | BadConfigFormatException e) {
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
		Card card;
		this.deck = new ArrayList<>();
		this.players = new ArrayList<>();
		File setupFile = new File(setupConfigFile);
		Scanner in = new Scanner(setupFile);
		while (in.hasNextLine()) {
			temp = in.nextLine();
			//ignores any commented lines
			if (!temp.startsWith("//")) {
				if (!temp.startsWith("Space")) {
					card = new Card();
					if (temp.startsWith("Room")) {
						card.setType(CardType.ROOM);
						setCardName(card, temp);
					} else if (temp.startsWith("Player")) {
						card.setType(CardType.PERSON);
						setCardName(card, temp);
						loadPlayers(temp);
					} else if (temp.startsWith("Weapon")) {
						card.setType(CardType.WEAPON);
						setCardName(card, temp);
					} else {
						throw new BadConfigFormatException("Not a recognized data type");
					}
					this.deck.add(card);
				}
				if (temp.startsWith("Room") || temp.startsWith("Space")) {
					room = new Room(); //allocates new memory for each room that must be added to roomMap
					if(temp.startsWith("Room")) {
						room.setRoom(true);
					} else if (temp.startsWith("Space")) {
						room.setRoom(false);
					} else { //since the description must be either Room or Space, throw exception for anything else
						throw new BadConfigFormatException("Not a recognized cell type");
					}	
					fillRoomMap(temp, room);
				}
			}
		}
		in.close();
		if(!players.isEmpty()) {
			dealCards();
		}
	}
	
	public void setCardName(Card card, String temp) {
		int counter = 0;
		for (String val : temp.split(",")) {
			if(val.startsWith(" ")) {
				//removes space from substring and ignores Category
				val = val.substring(1, val.length());
			}
			if (counter == 1) {
				card.setCardName(val);
			}
			counter++;
		}
	}

	public void loadPlayers(String temp) throws BadConfigFormatException {
		String name = null;
		Color color = null;
		int row = 0;
		int column = 0;
		Player player = null;

		int counter = 0;
		for (String val : temp.split(",")) {
			if(val.startsWith(" ")) {
				//removes space from substring and ignores Player
				val = val.substring(1, val.length());
				switch (counter) {
				case 0:
					name = val;
					break;
				case 1:
					if (val.equals("Yellow")) {
						color = Color.yellow;
					} else if (val.equals("Green")) {
						color = Color.green;
					} else if (val.equals("Blue")) {
						color = Color.blue;
					} else if (val.equals("Red")) {
						color = Color.red;
					} else if (val.equals("Purple")) {
						color = Color.magenta;
					} else if (val.equals("White")) {
						color = Color.white;
					}
					break;
				case 2:
					if (val.equals("Human")) {
						player = new HumanPlayer();
					} else if (val.equals("Computer")) {
						player = new ComputerPlayer();
					}
					break;
				case 3:
					int counter2 = 0;
					for (String coordinate : val.split("-")) {
						if (counter2 == 0) {
							row = Integer.parseInt(coordinate);
						} else if (counter2 == 1) {
							column = Integer.parseInt(coordinate);
						}
						counter2++;
					}
					break;
				default:
					throw new BadConfigFormatException("Default case in loadPlayers should not be reached");
				}
				counter++;
			}
		}
		player.setName(name);
		player.setColor(color);
		player.row = row;
		player.column = column;
		this.players.add(player);
	}

	public void fillRoomMap(String temp, Room room) {
		char symbol;
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
	
	public void dealCards() {
		int index;
		Card card;
		dealtCards = new HashSet<>();
		shuffleDeck = new ArrayList<>();
		do {
			index = num.nextInt(this.deck.size());
			card = deck.get(index);
		} while (!card.getType().equals(CardType.PERSON) || this.dealtCards.contains(card));
		theAnswer.person = card;
		dealtCards.add(card);
		do {
			index = num.nextInt(this.deck.size());
			card = deck.get(index);
		} while (!card.getType().equals(CardType.ROOM) || this.dealtCards.contains(card));
		theAnswer.room = card;
		dealtCards.add(card);
		do {
			index = num.nextInt(this.deck.size());
			card = deck.get(index);
		} while (!card.getType().equals(CardType.WEAPON) || this.dealtCards.contains(card));
		theAnswer.weapon = card;
		dealtCards.add(card);
		int counter = 0;
		for (int i = 0; i < deck.size(); i++) {
			shuffleDeck.add(deck.get(i));
		}
		Collections.shuffle(shuffleDeck); // shuffles the deck
		while (dealtCards.size() < deck.size()) {
			for (int i = 0; i < players.size(); i++) {
				if (dealtCards.size() >= deck.size()) {
					break;
				}
				card = deck.get(counter);
				players.get(i).updateHand(card);
				dealtCards.add(card);
				counter++;
			}
		}
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {

		//does initial scan of layout file to determine rows and cols
		parseRowsCols();

		//initializes the board
		initializeBoard();

		//populates the board with the values from the layout file
		populateBoardCells();

		//generates adjacency lists for all boardcells that can be targets
		intializeAdjLists();
	}

	// Helper Functions are found below this point
	//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
	//
	//
	//

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

	private void populateBoardCells() throws FileNotFoundException, BadConfigFormatException {

		String temp;
		Character symbol;
		BoardCell cell;
		int rowCounter = 0;
		int colCounter = 0;
		Character modifier;

		File layoutFile = new File(layoutConfigFile);
		rowCounter = 0;
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
				} else {
					cell = getCell(rowCounter,colCounter);
					//since this cell has more than one character, splits it into the initial symbol and then modifier
					symbol = val.charAt(0);
					modifier = val.charAt(1);
					cell.setRoom(roomMap.get(symbol));
					interpretModifier(cell, modifier); //called to determine the type of board cell
					if (cell.isDoorway()) {
						cell.getRoom().setWalkway(true);
					}
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
			cell.getRoom().setSecretPassage(modifier);
			cell.getRoom().setHasSecretPassage(true);
		}	

	}

	//
	//
	//CONTAINS ALL ADJ FUNCTIONS
	//vvvvvvvvvvvvvvvvvvvvvvvv
	//

	public Set<BoardCell> getAdjList(int row, int col) {
		BoardCell temp = this.getCell(row, col);

		return temp.adjList;
	}

	public void intializeAdjLists() throws BadConfigFormatException {
		for(int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				generateAdjList(i, j);
			}
		}
	}

	public void generateAdjList(int row, int col) throws BadConfigFormatException {
		BoardCell temp = this.getCell(row, col);
		Room room = temp.getRoom();
		//differentiates between room and walkway before creating adj list
		if (room.isRoom()) {
			if (temp.isRoomCenter()) {
				roomAdj(temp);
			}
		} else if (room.isWalkway()) {
			//doorways are walkways, but only doorways will connect to a room, needs to run doorAdj()
			if (temp.isDoorway()) {
				doorAdj(temp);
			}
			walkwayAdj(temp);
		}

	}

	public void walkwayAdj(BoardCell cell) {
		//only adds cells to adjlist if they are also a walkway to ensure that unused spaces and rooms aren't added		
		calcRowAdj(cell); //calculates adjacencies in the same row
		calcColAdj(cell); //calculates adjacencies in the same col
	}

	public void calcRowAdj(BoardCell cell) {
		BoardCell test;
		//test for row edge cases
		if (cell.getRow() == 0) { // left edge of board
			test = this.getCell(cell.getRow()+1, cell.getCol());
			if(test.getRoom().isWalkway()) {
				cell.adjList.add(this.getCell(cell.getRow()+1, cell.getCol()));
			}
		} else if (cell.getRow() == this.rows-1) { //right edge of board
			test = this.getCell(cell.getRow() - 1, cell.getCol());
			if(test.getRoom().isWalkway()) {
				cell.adjList.add(this.getCell(cell.getRow() - 1, cell.getCol()));
			}
		} else { 
			//if not edge, must be in middle
			test = this.getCell(cell.getRow()+1, cell.getCol());
			if(test.getRoom().isWalkway()) {
				cell.adjList.add(this.getCell(cell.getRow()+1, cell.getCol()));
			}
			test = this.getCell(cell.getRow() - 1, cell.getCol());
			if(test.getRoom().isWalkway()) {
				cell.adjList.add(this.getCell(cell.getRow() - 1, cell.getCol()));
			}
		}
	}

	public void calcColAdj(BoardCell cell) {
		BoardCell test;
		//test for col edge cases
		if (cell.getCol() == 0) { //top edge of board
			test = this.getCell(cell.getRow(), cell.getCol()+1);
			if(test.getRoom().isWalkway()) {
				cell.adjList.add(this.getCell(cell.getRow(), cell.getCol()+1));
			}
		} else if (cell.getCol() == this.cols-1) { //bottom edge of board
			test = this.getCell(cell.getRow(), cell.getCol()-1);
			if(test.getRoom().isWalkway()) {
				cell.adjList.add(this.getCell(cell.getRow(), cell.getCol()-1));
			}
		} else { //
			//not edge so must be in middle
			test = this.getCell(cell.getRow(), cell.getCol()+1);
			if(test.getRoom().isWalkway()) {
				cell.adjList.add(this.getCell(cell.getRow(), cell.getCol()+1));
			}
			test = this.getCell(cell.getRow(), cell.getCol()-1);
			if(test.getRoom().isWalkway()) {
				cell.adjList.add(this.getCell(cell.getRow(), cell.getCol()-1));
			}
		}
	}

	public void doorAdj(BoardCell cell) throws BadConfigFormatException {
		//adds the room center to the doorways adjlist depending on the direction of the enum
		//the last line of each case statement also adds the doorway to the rooms adjlist; this is much simpler than trying to find all doorways for each room center
		BoardCell test;
		switch (cell.getDoorDirection()) {
		case UP:
			test = this.getCell(cell.getRow() - 1,cell.getCol());
			cell.adjList.add(test.getRoom().getCenterCell());
			test.getRoom().getCenterCell().adjList.add(cell);
			break;
		case DOWN:
			test = this.getCell(cell.getRow()+1,cell.getCol());
			cell.adjList.add(test.getRoom().getCenterCell());
			test.getRoom().getCenterCell().adjList.add(cell);
			break;
		case LEFT:
			test = this.getCell(cell.getRow(),cell.getCol()-1);
			cell.adjList.add(test.getRoom().getCenterCell());
			test.getRoom().getCenterCell().adjList.add(cell);
			break;
		case RIGHT:
			test = this.getCell(cell.getRow(),cell.getCol()+1);
			cell.adjList.add(test.getRoom().getCenterCell());
			test.getRoom().getCenterCell().adjList.add(cell);
			break;
		case NONE:
			throw new BadConfigFormatException("Doorway at ( " + cell.getRow() + ", " + cell.getCol() + " ) had NONE enum associated with it");
		default:
			throw new BadConfigFormatException("Doorway at ( " + cell.getRow() + ", " + cell.getCol() + " ) didn't have any enum associated with it");

		}
	}

	public void roomAdj(BoardCell cell) {
		Room temp;
		if (cell.getRoom().hasSecretPassage()) {
			//gets the room that is connected to this by a secretpassage
			temp = this.roomMap.get(cell.getRoom().getSecretPassage());
			cell.adjList.add(temp.getCenterCell());
		}
	}

	//
	//^^^^^^^^^^^^^^^^^^^^^^^^^
	//END OF ADJACENCY FUNCTIONS
	//
	//
	
	public boolean checkAccusation(Solution suggestion) {
		Solution answer = Solution.getAnswer();
		return answer.equals(suggestion);
	}
	
	public Card handleSuggestion() {
		return new Card();
	}

	public void findAllTargets(BoardCell startCell, int pathlength) {
		for (BoardCell cell : startCell.adjList) {
			//only visits space if it hasn't been visited, and if either the space isn't occupied or it is a room
			if (!visited.contains(cell) && (!cell.getIsOccupied() || cell.isRoomCenter())) {
				visited.add(cell);
				if (cell.getRoom().isRoom()) {
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
		//allocate memory for targets in calctargets function to prevent targets from carrying over from previous calculations
		targets = new HashSet<>();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
		visited.remove(startCell);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell(int row, int col) { 
		return this.grid[row][col];
	}

	public Room getRoom(char symbol) {
		return roomMap.get(symbol);
	}

	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
	}

	public int getNumRows() {
		return this.rows;
	}

	public int getNumCols() {
		return this.cols;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public List<Card> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}

	public Set<Card> getDealtCards() {
		return dealtCards;
	}

	public void setDealtCards(Set<Card> dealtCards) {
		this.dealtCards = dealtCards;
	}

}
