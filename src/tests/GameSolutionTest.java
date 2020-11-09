package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;

public class GameSolutionTest {
	
	private static Board board;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		board.initialize();
	}

	@Test
	public void testCheckAccusation() {
		//This sets theAnswer to known cards to allow me to test checkAccusation
		Solution theAnswer = Solution.getAnswer();
		Card player = new Card();
		player.setType(CardType.PERSON);
		player.setCardName("Colonel Mustard");
		theAnswer.person = player;
		Card weapon = new Card();
		weapon.setType(CardType.WEAPON);
		weapon.setCardName("Poleax");
		theAnswer.weapon = weapon;
		Card room = new Card();
		room.setType(CardType.ROOM);
		room.setCardName("Bathroom");
		theAnswer.room = room;
		
		//need to update method depending on how I decide to implement checkAccusation()
		assertTrue(board.checkAccusation());
		assertFalse(board.checkAccusation());
		assertFalse(board.checkAccusation());
		assertFalse(board.checkAccusation());


	}
}
