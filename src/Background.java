import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Background extends GameObj {
	 public static final int WIDTH = 532;
	 public static final int HEIGHT = 600;
	 public static final int INIT_X = 0;
	 public static final int INIT_Y = 0;
	 public static final int INIT_VEL_X = 0;
	 public static final int INIT_VEL_Y = 0;
	 
	 private static BufferedImage img;
	 
	 public Background(int courtWidth, int courtHeight, String img_file) {
			super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, 
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
