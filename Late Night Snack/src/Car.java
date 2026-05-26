public class Car extends Sprite {

    private int xSpeed;
    private int ySpeed;

    public Car(int x, int y, int width, int height,int xSpeed, int ySpeed) {
        super(x, y, width, height);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    // Update car movement
    public void update() {
        x += xSpeed;
        y += ySpeed;
        // Horizontal loop
        if(x > 4050) {

            x = -200;
        }
        // Vertical loop
        if(y > 3200) {
            y = -200;
        }
    }
}