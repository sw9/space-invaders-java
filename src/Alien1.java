import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** A game object displayed using an image. 
 * 
 * Note that the image is read from the file when the object is 
 * constructed, and that all objects created by this constructor share
 * the same image data (i.e. img is static). This important for 
 * efficiency, your program will go very slowing if you try to create 
 * a new BufferedImage every time the draw method is invoked. */
public class Alien1 extends GameObj {
	 public static final String img_file = "alien1.png";
	 public static final int SIZE = 21;
	 public static final int INIT_VEL_Y = 0;
	 
	 private static BufferedImage img;
	 
	 public Alien1(int vel_x, int pos_x, int pos_y, int courtWidth, int courtHeight) {
		super(vel_x, INIT_VEL_Y, pos_x, pos_y, 
				SIZE, SIZE, courtWidth, courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}

   @Override
	public void draw(Graphics g){
		 g.drawImage(img, pos_x, pos_y, width, height, null); 
	}

}
