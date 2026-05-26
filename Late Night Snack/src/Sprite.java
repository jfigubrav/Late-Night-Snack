import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {

    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected BufferedImage image;

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void draw(
    	    Graphics g,
    	    int cameraX,
    	    int cameraY
    	) {

    	    if(image != null) {

    	        g.drawImage(
    	            image,
    	            x - cameraX,
    	            y - cameraY,
    	            width,
    	            height,
    	            null
    	        );
    	    }
    	    else {

    	        g.setColor(Color.RED);

    	        g.fillRect(
    	            x - cameraX,
    	            y - cameraY,
    	            width,
    	            height
    	        );
    	    }
    	}

    public Rect getBounds() {
        return new Rect(x, y, x + width, y + height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void keepInside(
    	    int mapWidth,
    	    int mapHeight
    	) {

    	    if(x < 0) x = 0;

    	    if(y < 0) y = 0;

    	    if(x + width > mapWidth) {

    	        x = mapWidth - width;
    	    }

    	    if(y + height > mapHeight) {

    	        y = mapHeight - height;
    	    }
    	}
}