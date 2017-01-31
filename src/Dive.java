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
public class Dive extends GameObj {
	 public static final String img_file = "dive.png";
	 public static final int WIDTH = 24;
	 public static final int HEIGHT = 11;
	 
	 private static BufferedImage img;
	 
	 public Dive(int vel_x, int vel_y, int pos_x, int pos_y, 
			 int courtWidth, int courtHeight) {
		super(vel_x, vel_y, pos_x, pos_y, 
				WIDTH, HEIGHT, courtWidth, courtHeight);
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
