import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] frames;
    private int currentFrame = 0;
    private long previousTime;
    private int speed;

    public Animation(BufferedImage[] frames, int speed) {
        this.frames = frames;
        this.speed = speed;
        previousTime = System.currentTimeMillis();
    }
    // Update animation frame
    public void update() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - previousTime > speed) {
            currentFrame++;
            if(currentFrame >= frames.length) {
                currentFrame = 0;
            }
            previousTime = currentTime;
        }
    }
    public BufferedImage getCurrentFrame() {

        return frames[currentFrame];
    }
}