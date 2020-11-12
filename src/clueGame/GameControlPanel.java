package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel{
	
	private JTextField turnField = new JTextField();
	private JTextField rollField = new JTextField();
	private JTextField guess = new JTextField();
	private JTextField guessResult = new JTextField();
	
	GameControlPanel() {
		setLayout(new GridLayout(2, 0));
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 4));
		add(topPanel);
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		turnPanel.add(new JLabel("Whose turn?"));
		turnField.setEditable(false);
		turnPanel.add(turnField);
		JPanel rollPanel = new JPanel();
		rollPanel.add(new JLabel("Roll: "));
		rollField.setEditable(false);
		rollField.setPreferredSize(new Dimension(40, 20));
		rollPanel.add(rollField);
		JButton accuseButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("NEXT!");
		topPanel.add(turnPanel);
		topPanel.add(rollPanel);
		topPanel.add(accuseButton);
		topPanel.add(nextButton);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(0, 2));
		add(bottomPanel);
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1, 0));
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		guess.setEditable(false);
		guessPanel.add(guess);
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1, 0));
		resultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		guessResult.setEditable(false);
		resultPanel.add(guessResult);
		bottomPanel.add(guessPanel);
		bottomPanel.add(resultPanel);
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
        JFrame frame = new JFrame();  // create the frame
        frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(750, 180);  // size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        frame.setVisible(true); // make it visible

        // test filling in the data
        panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "Yellow"), 5);
        panel.setGuess( "I have no guess!");
        panel.setGuessResult( "So you have nothing?");
	}
	
	private void setGuessResult(String guessResult) {
		this.guessResult.setText(guessResult);
	}

	private void setGuess(String guess) {
		this.guess.setText(guess);
	}

	private void setTurn(Player player, int roll) {
		String newRoll = Integer.toString(roll);
		setRollField(newRoll);
		setTurnField(player.getName(), player.getColor());
	}

	public void setTurnField(String turnField, Color color) {
		this.turnField.setText(turnField);
		this.turnField.setBackground(color);
	}

	public void setRollField(String rollField) {
		this.rollField.setText(rollField);
	}
	
}
