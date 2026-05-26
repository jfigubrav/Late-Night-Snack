import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class GameBase extends JPanel
implements Runnable, KeyListener {

    protected Thread gameThread;

    protected boolean running = true;

    protected boolean upPressed;
    protected boolean downPressed;
    protected boolean leftPressed;
    protected boolean rightPressed;
    protected boolean shiftPressed;

    public GameBase() {

        setPreferredSize(new Dimension(800, 600));

        setFocusable(true);

        addKeyListener(this);
    }

    // Start game loop
    public void startGame() {

        gameThread = new Thread(this);

        gameThread.start();
    }

    @Override
    public void run() {

        while(running) {

            updateGame();

            repaint();

            try {

                Thread.sleep(16);
            }
            catch(Exception e) {

                e.printStackTrace();
            }
        }
    }

    public abstract void updateGame();

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        drawGame(g);
    }

    public abstract void drawGame(Graphics g);

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W) upPressed = true;

        if(key == KeyEvent.VK_S) downPressed = true;

        if(key == KeyEvent.VK_A) leftPressed = true;

        if(key == KeyEvent.VK_D) rightPressed = true;

        if(key == KeyEvent.VK_SHIFT) shiftPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W) upPressed = false;

        if(key == KeyEvent.VK_S) downPressed = false;

        if(key == KeyEvent.VK_A) leftPressed = false;

        if(key == KeyEvent.VK_D) rightPressed = false;

        if(key == KeyEvent.VK_SHIFT) shiftPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}