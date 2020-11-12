package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel{
	
	private JTextField turnField = new JTextField();
	private JTextField rollField = new JTextField();
	
	GameControlPanel() {
		setLayout(new GridLayout(2, 0));
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 4));
		add(topPanel);
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		turnPanel.add(new JLabel("Whose turn?"));
		turnPanel.add(turnField);
		JPanel rollPanel = new JPanel();
		rollPanel.add(new JLabel("Roll:"));
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
		// TODO Auto-generated method stub
		
	}

	private void setGuess(String guess) {
		// TODO Auto-generated method stub
		
	}

	private void setTurn(Player player, int roll) {
		setRollField(Integer.toString(roll));
		setTurnField(player.getName());
	}

	public void setTurnField(String turnField) {
		this.turnField.setText(turnField);
	}

	public void setRollField(String rollField) {
		this.rollField.setText(rollField);
	}
	
}
