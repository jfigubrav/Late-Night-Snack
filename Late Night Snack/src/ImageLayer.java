import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageLayer {

    private BufferedImage image;

    private int x;
    private int y;

    public ImageLayer(BufferedImage image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {

        if(image != null) {
            g.drawImage(image, x, y, null);
        }
    }
}