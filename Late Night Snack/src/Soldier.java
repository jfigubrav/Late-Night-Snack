import java.awt.*;
import java.awt.image.BufferedImage;

public class Soldier extends Sprite {

    private int speed = 4;
    private BufferedImage currentImage;
    private BufferedImage idleImage;

    private Animation currentAnimation;
    

    public Soldier(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void setAnimation(Animation animation) {

        currentAnimation = animation;
    }
    public void update() {

        if(currentAnimation != null) {

            currentAnimation.update();

            currentImage = currentAnimation.getCurrentFrame();
        }
    }

    public void draw(Graphics g, int cameraX, int cameraY) {

        if(currentImage != null) {

            g.drawImage(
                currentImage,
                x - cameraX,
                y - cameraY,
                width,
                height,
                null
            );
        }
    }
    public void setImage(BufferedImage image) {

        currentImage = image;
    }
    public void setIdleImage(BufferedImage image) {

        idleImage = image;
    }
    public void idle() {

        currentAnimation = null;
        currentImage = idleImage;
    }
    public void setSpeed(int speed) {

        this.speed = speed;
    }
    
}