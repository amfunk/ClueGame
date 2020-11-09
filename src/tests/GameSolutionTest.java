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
		Solution suggestion1 = new Solution(); // all cards match answer
		Solution suggestion2 = new Solution(); // room doesn't match answer
		Solution suggestion3 = new Solution(); // weapon doesn't match answer
		Solution suggestion4 = new Solution(); // person doesn't match answer

		Card player = new Card();
		player.setType(CardType.PERSON);
		player.setCardName("Colonel Mustard");
		theAnswer.person = player;
		suggestion1.person = player;
		suggestion2.person = player;
		suggestion3.person = player;
		Card weapon = new Card();
		weapon.setType(CardType.WEAPON);
		weapon.setCardName("Poleax");
		theAnswer.weapon = weapon;
		suggestion1.weapon = weapon;
		suggestion2.weapon = weapon;
		suggestion4.weapon = weapon;
		Card room = new Card();
		room.setType(CardType.ROOM);
		room.setCardName("Bathroom");
		theAnswer.room = room;
		suggestion1.room = room;
		suggestion3.room = room;
		suggestion4.room = room;
		
		Card wrongPlayer = new Card();
		wrongPlayer.setType(CardType.PERSON);
		wrongPlayer.setCardName("Professor Plum");
		suggestion4.person = wrongPlayer;
		Card wrongWeapon = new Card();
		wrongWeapon.setType(CardType.WEAPON);
		wrongWeapon.setCardName("Poison");
		suggestion3.weapon = wrongWeapon;
		Card wrongRoom = new Card();
		wrongRoom.setType(CardType.ROOM);
		wrongRoom.setCardName("Control Center");
		suggestion2.room = wrongRoom;
		
		
		//need to update method depending on how I decide to implement checkAccusation()
		assertTrue(board.checkAccusation(suggestion1));
		assertFalse(board.checkAccusation(suggestion2));
		assertFalse(board.checkAccusation(suggestion3));
		assertFalse(board.checkAccusation(suggestion4));


	}
}
