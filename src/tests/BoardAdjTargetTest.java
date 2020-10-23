package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {

	private static Board board;

	@BeforeAll
	public static void setUp() {

		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		board.initialize();
	}

	//walkway tests are dark orange on excel sheet
	@Test
	public void testAdjacenciesHallways() {
		//tests against left edge of board
		Set<BoardCell> test = board.getAdjList(7, 0);
		assertEquals(3, test.size());
		assertTrue(test.contains(board.getCell(6, 0)));
		assertTrue(test.contains(board.getCell(7, 1)));
		assertTrue(test.contains(board.getCell(8, 0)));

		//tests in middle of hallway
		test = board.getAdjList(6, 7);
		assertEquals(4, test.size());
		assertTrue(test.contains(board.getCell(5, 7)));
		assertTrue(test.contains(board.getCell(6, 6)));
		assertTrue(test.contains(board.getCell(6, 8)));
		assertTrue(test.contains(board.getCell(7, 7)));

		//tests against right edge of board next to a room
		test = board.getAdjList(7, 25);
		assertEquals(2, test.size());
		assertTrue(test.contains(board.getCell(8, 25)));
		assertTrue(test.contains(board.getCell(7, 24)));

		//tests between unused space and a doorway
		test = board.getAdjList(13, 17);
		assertEquals(3, test.size());
		assertTrue(test.contains(board.getCell(12, 17)));
		assertTrue(test.contains(board.getCell(14, 17)));
		assertTrue(test.contains(board.getCell(13, 18)));
	}

	@Test
	public void testAdjacenciesRooms() {
		//tests locker room adjacency, contains one doorway and a secret passage
		Set<BoardCell> test = board.getAdjList(2, 3);
		assertEquals(2, test.size());
		assertTrue(test.contains(board.getCell(5, 4)));
		assertTrue(test.contains(board.getCell(22, 21)));

		//test empty cell inside of Trash Room
		test = board.getAdjList(0, 15);
		assertEquals(0, test.size());

		//tests dining hall which has 3 entrances on top side
		test = board.getAdjList(22, 13);
		assertEquals(3, test.size());
		assertTrue(test.contains(board.getCell(18, 12)));
		assertTrue(test.contains(board.getCell(18, 13)));
		assertTrue(test.contains(board.getCell(18, 14)));

		//test control center which has two entrances on each sides and a secret passage
		test = board.getAdjList(22, 21);
		assertEquals(3, test.size());
		assertTrue(test.contains(board.getCell(19, 21)));
		assertTrue(test.contains(board.getCell(22, 18)));
	}

	@Test
	public void testAdjacenciesDoors() {
		//tests only entrance to the Wine Cellar
		Set<BoardCell> test = board.getAdjList(7, 21);
		assertEquals(4, test.size());
		assertTrue(test.contains(board.getCell(3, 22)));
		assertTrue(test.contains(board.getCell(7, 20)));
		assertTrue(test.contains(board.getCell(7, 22)));
		assertTrue(test.contains(board.getCell(8, 21)));

		//tests upward facing entrance to the bathroom
		test = board.getAdjList(12, 8);
		assertEquals(3, test.size());
		assertTrue(test.contains(board.getCell(12, 4)));
		assertTrue(test.contains(board.getCell(12, 9)));
		assertTrue(test.contains(board.getCell(13, 8)));

		//tests downward facing entrance to the control center
		test = board.getAdjList(19, 21);
		assertEquals(4, test.size());
		assertTrue(test.contains(board.getCell(18, 21)));
		assertTrue(test.contains(board.getCell(19, 20)));
		assertTrue(test.contains(board.getCell(19, 22)));
		assertTrue(test.contains(board.getCell(22, 21)));
	}

	@Test
	public void testTargetsTrashRoom() {
		// tests leaving room without secret passage

		//test a roll of 1
		board.calcTargets(board.getCell(3, 14), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3, 10)));
		assertTrue(targets.contains(board.getCell(3, 18)));

		//test a roll of 2
		board.calcTargets(board.getCell(3, 14), 2);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 9)));
		assertTrue(targets.contains(board.getCell(2, 10)));
		assertTrue(targets.contains(board.getCell(2, 18)));
		assertTrue(targets.contains(board.getCell(4, 18)));


		//test a roll of 4
		board.calcTargets(board.getCell(3, 14), 4);
		targets = board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCell(0, 10)));
		assertTrue(targets.contains(board.getCell(1, 9)));
		assertTrue(targets.contains(board.getCell(2, 8)));
		assertTrue(targets.contains(board.getCell(0, 18)));
		assertTrue(targets.contains(board.getCell(2, 18)));
		assertTrue(targets.contains(board.getCell(4, 18)));
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 3 blocked directly above
		board.getCell(9, 10).setOccupied(true);
		board.calcTargets(board.getCell(10, 10), 3);
		board.getCell(9, 10).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(8, 9)));
		assertTrue(targets.contains(board.getCell(11, 10)));
		assertTrue(targets.contains(board.getCell(10, 9)));	
		assertTrue( targets.contains( board.getCell(13, 10)));
		assertTrue( targets.contains( board.getCell(12, 9))) ;

		// we want to make sure we can get into a room, even if flagged as occupied
		//this test also covers targets from a doorway
		board.getCell(22, 21).setOccupied(true);
		board.calcTargets(board.getCell(22, 18), 1);
		board.getCell(22, 21).setOccupied(false);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(21, 18)));
		assertTrue(targets.contains(board.getCell(22, 17)));
		assertTrue(targets.contains(board.getCell(23, 18)));
		assertTrue(targets.contains(board.getCell(22, 21)));


		// check leaving a room with a blocked doorway
		board.getCell(17, 4).setOccupied(true);
		board.calcTargets(board.getCell(21, 3), 5);
		board.getCell(17, 4).setOccupied(false);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		//since doorway is blocked, only option is to use secret passage,
		//covers that as well
		assertTrue(targets.contains(board.getCell(3, 22)));
	}

	@Test
	public void testAlongWalkways() {
		//tests with roll of 1
		board.calcTargets(board.getCell(25, 8), 1);
		Set <BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(25, 7)));
		assertTrue(targets.contains(board.getCell(24, 8)));

		//tests with roll of 3
		board.calcTargets(board.getCell(25, 8), 3);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(25, 7)));
		assertTrue(targets.contains(board.getCell(24, 8)));
		assertTrue(targets.contains(board.getCell(23, 7)));
		assertTrue(targets.contains(board.getCell(22, 8)));

		//tests with roll of 4
		board.calcTargets(board.getCell(25, 8), 4);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(23, 8)));
		assertTrue(targets.contains(board.getCell(22, 7)));
		assertTrue(targets.contains(board.getCell(21, 8)));

	}

}
