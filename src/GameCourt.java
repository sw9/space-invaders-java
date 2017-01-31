import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;


/**
 * GameCourt
 * 
 * This class holds the primary game logic of how different objects 
 * interact with one another.  Take time to understand how the timer 
 * interacts with the different methods and how it repaints the GUI 
 * on every tick().
 *
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	private Background background;
	private Tank tank;
	private HashSet<GameObj> aliens;
	private HashSet<Bullet> bullets;
	private HashSet<GameObj> divers;
	
	private JLabel livesDisp;
	private JLabel waveDisp;
	private JLabel scoreDisp;
	
	private int lives;
	private int score;
	private int alien_velocity = 1;
	private long lastShot;
	private Timer timer;
	
	// Game constants
	public static final int COURT_WIDTH = 532;
	public static final int COURT_HEIGHT = 600;
	public static final int TANK_VELOCITY = 10;
	public static final int BULLET_VELOCITY = 10;
	
	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 35; 
	
	public GameCourt(JLabel livesDisp, JLabel waveDisp, JLabel scoreDisp){
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.darkGray);
        
        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called 
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
		timer = new Timer(INTERVAL, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!
		
		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// this key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually 
		// moves the square.)
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					tank.v_x = -TANK_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					tank.v_x = TANK_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					if ((System.currentTimeMillis() - lastShot) > 250)
					{
						bullets.add(new Bullet(0, -BULLET_VELOCITY, 
								tank.pos_x + 19, tank.pos_y, COURT_WIDTH, 
								COURT_HEIGHT, false));
						lastShot = System.currentTimeMillis();
					}

				}
			}
			
			public void keyReleased(KeyEvent e){
				tank.v_x = 0;
				tank.v_y = 0;
			}
		});

		this.livesDisp = livesDisp;
		this.waveDisp = waveDisp;
		this.scoreDisp = scoreDisp;
	}

	/** (Re-)set the state of the game to its initial state.
	 */
	
	public void resetState() {
		lives = 6;
		alien_velocity = 1;
		score = 0;
		lastShot = 0;
	}
	
	public void reset() {
		background = new Background(COURT_WIDTH, COURT_HEIGHT, "planet.png");
		tank = new Tank(COURT_WIDTH, COURT_HEIGHT);
		aliens = new HashSet<GameObj>();
		bullets = new HashSet<Bullet>();
		divers = new HashSet<GameObj>();
		
		int st = 10; //y value to start first alien row
		int sp = 15; //space between each alien row
		
		//add a row of Alien1 ships
		for (int x = 56; x <= 456; x += 40)
		{
			aliens.add(new Alien1(alien_velocity, 
					x, st, COURT_WIDTH, COURT_HEIGHT));
		}
		
		//add two rows of Alien2 ships
		for (int x = 52; x <= 452; x += 40)
		{
			aliens.add(new Alien2(alien_velocity, x, 
					st + 21 + sp, COURT_WIDTH, COURT_HEIGHT));
			aliens.add(new Alien2(alien_velocity, x, 
					st + 21 + sp + 22 + sp, COURT_WIDTH, COURT_HEIGHT));
		}
		
		//add two rows of Alien3 ships
		for (int x = 50; x <= 450; x += 40)
		{
			aliens.add(new Alien3(alien_velocity, x, 
					st + 21 + sp + 22 + sp + 22 + sp, 
					COURT_WIDTH, COURT_HEIGHT));
			aliens.add(new Alien3(alien_velocity, x, 
					st + 21 + sp + 22 + sp + 22 + sp + 23 + sp,
					COURT_WIDTH, COURT_HEIGHT));
		}
	
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	public void pause () {
		timer.stop();
	}
	
	public void start () {
		timer.start();
	}
	
    /**
     * This method is called every time the timer defined
     * in the constructor triggers.
     */
	void tick(){
		if (lives > 0) {
			// move the various objects
			
			tank.move();		
			
			Random m = new Random();
			if (m.nextDouble() < 0.0035) {
				divers.add(new Dive(0, 1, m.nextInt(500-32)+32, 0, 
						COURT_WIDTH, COURT_HEIGHT));
			}
			
			Iterator<Bullet> bmove = bullets.iterator();
			while(bmove.hasNext()) {
				GameObj bt = bmove.next();
				bt.move();
				if (bt.hitWall() != null) bmove.remove();
			}
			
			Iterator<GameObj> awall = aliens.iterator();
			while (awall.hasNext()) {
				if (awall.next().hitWall() != null) {
					Iterator<GameObj> itr = aliens.iterator();
					while (itr.hasNext()) {
						GameObj x = itr.next();
						x.v_x = x.v_x* -1;
						x.pos_y = x.pos_y + 15;
					}
					break;
				}	
			}
			
			
			Iterator<GameObj> ait = aliens.iterator();
			while (ait.hasNext()) {
				GameObj al = ait.next();
				al.move();

				if (m.nextDouble() <= 0.002) {
					bullets.add(new Bullet(0, BULLET_VELOCITY, 
							al.pos_x + al.width/2, al.pos_y, COURT_WIDTH, 
							COURT_HEIGHT, true));
				}
				
				if ((COURT_HEIGHT - al.pos_y) < 20) {
					lives = lives - 1;
					if (lives > 0) reset();
				}
				
				Iterator<Bullet> bit = bullets.iterator();
				while (bit.hasNext()) {
					Bullet bt = bit.next();
					if (bt.intersects(al) && !bt.enemy) {
						if (al.width == 32)
							score += 1;
						else if (al.width == 29)
							score += 2;
						else if (al.width == 21)
							score += 3;
						ait.remove();
						bit.remove();
						
						break;
					}
					
					if (bt.intersects(tank) && bt.enemy) {
						lives = lives - 1;
						bit.remove();
					}
				}
			}
			
			Iterator<GameObj> dit = divers.iterator();
			while (dit.hasNext()) {
				GameObj div = dit.next();
				
				if (div.pos_y < 250) {
					int x = (tank.pos_x - div.pos_x);
					int y = (tank.pos_y - div.pos_y);

					div.v_x = x/43;
					div.v_y = y/65;
					System.out.println(div.v_x +", "+ div.v_y);
					
				} else {
					div.v_x = 0;
					div.v_y = 30;
				}
				
				div.move();
				
				if (div.intersects(tank)) {
					lives = lives - 1;
					dit.remove();
				} else if (div.hitWall() != null)
				{
					dit.remove();
				} else {
					Iterator<Bullet> bit = bullets.iterator();
					while (bit.hasNext()) {
						Bullet bt = bit.next();
						if (bt.intersects(div) && !bt.enemy) {
							score += 8;
							if (m.nextDouble() < 0.7) 
								lives += 1;
							dit.remove();
							bit.remove();
							break;
						}
					}
				}
			}
			
			if (aliens.isEmpty() && divers.isEmpty()) {
				alien_velocity += 1;
				reset();
			}
			
			// update the display
			scoreDisp.setText("Score: " + score);
			waveDisp.setText("Wave: " + alien_velocity);
			livesDisp.setText("Lives: " + lives);
			
			repaint();			
		
		} else {
		livesDisp.setText("the aliens destroyed you!");
		}
	}

	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		background.draw(g);
		tank.draw(g);
		
		for (GameObj b: bullets) {
			b.draw(g);
		}
		
		for (GameObj d: divers) {
			d.draw(g);
		}
		
		for (GameObj a: aliens) {
			a.draw(g);
		}
		
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}
}
