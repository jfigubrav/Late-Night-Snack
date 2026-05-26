import java.awt.*;

public class Food extends Sprite {

    private boolean collected = false;

    public Food(int x, int y, int width, int height) {

        super(x, y, width, height);
    }

    public boolean isCollected() {

        return collected;
    }

    public void collect() {

        collected = true;
    }

    
    public void draw(Graphics g, int cameraX, int cameraY) {

        if(!collected) {

            g.drawImage(
                image,
                x - cameraX,
                y - cameraY,
                width,
                height,
                null
            );
        }
    }
}