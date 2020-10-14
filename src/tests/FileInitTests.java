package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;


public class FileInitTests {
	
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 26;
	public static final int NUM_COLUMNS = 26;
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few others
		assertEquals("Locker Room", board.getRoom('L').getName() );
		assertEquals("Gym", board.getRoom('G').getName() );
		assertEquals("Control Center", board.getRoom('C').getName() );
		assertEquals("Armory", board.getRoom('A').getName() );
		assertEquals("Wine Cellar", board.getRoom('W').getName() );
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumCols());
	}
	
	@Test
	public void FourDoorDirections() {
		//test entrance to locker room
		BoardCell cell = board.getCell(5, 4);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		//test entrance to gym
		cell = board.getCell(17, 4);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		//test entrance to armory
		cell = board.getCell(13, 18);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		//test entrance to trash room
		cell = board.getCell(3, 18);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = board.getCell(8, 19);
		assertFalse(cell.isDoorway());
	}
	
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumCols(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(14, numDoors);
	}
	
	@Test
	public void testRooms() {
		//test room cell
		BoardCell cell = board.getCell( 2, 13);
		Room room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Trash Room" ) ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		//test a label cell
		cell = board.getCell(2, 22);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Wine Cellar" ) ;
		assertTrue( cell.isLabel() );
		assertFalse(cell.isRoomCenter());
		assertTrue( room.getLabelCell() == cell );
		
		//test a center cell
		cell = board.getCell(14, 21);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Armory" ) ;
		assertTrue( cell.isRoomCenter() );
		assertFalse(cell.isLabel());
		assertTrue( room.getCenterCell() == cell );
		
		//test secret passage cell
		cell = board.getCell(20, 19);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Control Center" ) ;
		assertTrue( cell.getSecretPassage() == 'L' );
		
		//test a hallway cell
		cell = board.getCell(6, 18);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Hallway" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
		// test an unused space
		cell = board.getCell(9, 14);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Unused" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
	}
}
