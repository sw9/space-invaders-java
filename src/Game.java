// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** 
 * Game
 * Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run(){
        // NOTE : recall that the 'final' keyword notes inmutability
		  // even for local variables. 

        // Top-level frame in which game components live
		  // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Space Invaders");
        frame.setLocation(532,600);
        
		  // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        
        final JLabel livesDisp = new JLabel("Lives");
        final JLabel waveDisp = new JLabel("Wave");
        final JLabel scoreDisp = new JLabel("Score");
        status_panel.add(scoreDisp);
        status_panel.add(waveDisp);
        status_panel.add(livesDisp);
        
        // Main playing area
        final GameCourt court = new GameCourt(livesDisp, waveDisp, scoreDisp);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
        // Note here that when we add an action listener to the reset
        // button, we define it as an anonymous inner class that is 
        // an instance of ActionListener with its actionPerformed() 
        // method overridden. When the button is pressed,
        // actionPerformed() will be called.
        final JButton reset = new JButton("New Game");
        reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	court.resetState();
                	court.reset();
                }
            });
        control_panel.add(reset);
        
     // help button
        final JButton help = new JButton("Open Help/Pause");
        help.setFocusable(false);
        help.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	court.pause();  
                	JOptionPane.showMessageDialog(frame,
                      	"Welcome to Shichao's Space Invaders game.\n\n" +
                      	"To play this game, use the arrow keys to move your spaceship" +
                      	"and the space bar to shoot.\n\n" +
                      	"Special features: \n" +
                      	"* Bonus kamikaze saucers that try to crash into your ship. These" +
                      	" saucers are worth more points and sometimes extra lives.\n"+
                      	"* Alien enemies that fire back\n"+
                      	"* A timer that only lets you shoot once every quarter-second," +
                      	"increasing game difficulty\n" +
                      	"* The ability to pause and resume the game"
                      	
                      	, "Help", JOptionPane.PLAIN_MESSAGE);
                }
            });
        control_panel.add(help);
        
        final JButton start = new JButton("Continue");
        start.setFocusable(false);
        start.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	court.start();
                }});
        control_panel.add(start);
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.resetState();
        court.reset();
    }


	/*
     * Main method run to start and run the game
     * Initializes the GUI elements specified in Game and runs it
     * NOTE: Do NOT delete! You MUST include this in the final submission of your game.
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Game());
    }
}
