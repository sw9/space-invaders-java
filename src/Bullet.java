import java.awt.*;

public class Bullet extends GameObj {
	public static final int SIZE = 4;
	public static final int INIT_X = 0;
	public static final int INIT_Y = 0;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public boolean enemy;
	
    /** 
     * Note that because we don't do anything special
     * when constructing a Square, we simply use the
     * superclass constructor called with the correct parameters 
     */
    public Bullet(int vel_x, int vel_y, int x, int y, int courtWidth, 
    		int courtHeight, boolean enemy){
        super(vel_x, vel_y, x, y, 
        		SIZE, SIZE, courtWidth, courtHeight);
        this.enemy = enemy;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(pos_x, pos_y, width, height); 
    }


}
