package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
		
	ClueGame() {

	}
	
	public static void main(String[] args) {
		ClueGame frame = new ClueGame();
		frame.setUp();
		frame.setVisible(true);
		frame.setTitle("Clue Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Board board = Board.getInstance();
		JOptionPane.showMessageDialog(frame, "You are " + board.getCurPlayer().getName() + ".\n Can you find the solution before the Computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public void setUp() {
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		board.initialize();
		setSize(new Dimension(800, 800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(board,BorderLayout.CENTER);
		Player player = board.getCurPlayer();
		add(new KnownCardsPanel(player), BorderLayout.EAST);
		add(new GameControlPanel(), BorderLayout.SOUTH);
	}
}
