import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;

public class GameS26 extends GameBase
implements java.awt.event.MouseListener {

    // Player
    private Soldier player;

    // Map
    private BufferedImage mapImage;
    private int mapWidth;
    private int mapHeight;

    // Camera
    private int cameraX;
    private int cameraY;

    // Collision + roads
    private ArrayList<Rect> walls = new ArrayList<>();
    private ArrayList<Rect> roads = new ArrayList<>();

    // Cars
    private ArrayList<Car> cars = new ArrayList<>();

    // Delivery system
    private Food packageFood;
    private ArrayList<Point> foodSpawnPoints = new ArrayList<>();
    private ArrayList<Point> deliveryPoints = new ArrayList<>();

    private boolean carryingPackage = false;

    private Point activeDeliveryPoint;

    private int deliveriesCompleted = 0;

    private final int MAX_DELIVERIES = 2;

    // UI messages
    private String message = "";

    private int messageTimer = 0;

    // Debug
    private boolean showCollisionBoxes = false;
    private boolean showRoads = false;

    // Game states
    private final int MENU_STATE = 0;
    private final int STORY_STATE = 1;
    private final int PLAY_STATE = 2;
    private final int CONTROLS_STATE = 3;
    private final int WIN_STATE = 4;

    private int currentState = MENU_STATE;

    // Images
    private BufferedImage upLF;
    private BufferedImage upRF;

    private BufferedImage downLF;
    private BufferedImage downRF;

    private BufferedImage leftWalk;
    private BufferedImage leftWalkAlt;

    private BufferedImage rightRun;
    private BufferedImage rightRunAlt;

    private BufferedImage idle;

    private BufferedImage mcdonalds;
    private BufferedImage dropOffImage;

    private BufferedImage menuImage;
    private BufferedImage storyImage;
    private BufferedImage controlsImage;
    private BufferedImage winImage;

    private BufferedImage carDown;
    private BufferedImage carLeft;

    // Animations
    private Animation walkUpAnimation;
    private Animation walkDownAnimation;
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;

    public GameS26() {

        setPreferredSize(new Dimension(1000, 700));

        addMouseListener(this);

        loadImages();

        mapWidth = mapImage.getWidth() * 3;
        mapHeight = mapImage.getHeight() * 3;

        // Create player
        player = new Soldier(500, 500, 64, 64);

        player.setImage(upLF);

        player.setIdleImage(idle);

        // Create world
        createHouseCollisions();

        createRoads();

        createDeliveryPoints();

        createFoodSpawnPoints();

        createBoundaries();

        createAnimations();

        createCars();

        spawnNextFood();
    }

    // Load images
    private void loadImages() {

        try {

            mapImage = ImageIO.read(new File("images/map.png"));

            upLF = ImageIO.read(new File("images/leftLeg.png"));
            upRF = ImageIO.read(new File("images/rightFoot.png"));

            downLF = ImageIO.read(new File("images/dLeft.png"));
            downRF = ImageIO.read(new File("images/dRight.png"));

            leftWalk = ImageIO.read(new File("images/leftWalk.png"));
            leftWalkAlt = ImageIO.read(new File("images/leftWalkAlt.png"));

            rightRun = ImageIO.read(new File("images/rightRun.png"));
            rightRunAlt = ImageIO.read(new File("images/rightRunningAlt.png"));

            idle = ImageIO.read(new File("images/idle.png"));

            mcdonalds = ImageIO.read(new File("images/mcdonaldsBag.png"));

            dropOffImage = ImageIO.read(new File("images/dropoff.png"));

            menuImage = ImageIO.read(new File("images/menuImage.png"));
            storyImage = ImageIO.read(new File("images/storyImage.png"));
            controlsImage = ImageIO.read(new File("images/controlsImage.png"));
            winImage = ImageIO.read(new File("images/winImage.png"));

            carDown = ImageIO.read(new File("images/carDown.png"));
            carLeft = ImageIO.read(new File("images/carLeft.png"));
        }
        catch(Exception e) {

            e.printStackTrace();
        }
    }

    // Create animations
    private void createAnimations() {
        BufferedImage[] upFrames = {upLF, upRF};
        BufferedImage[] downFrames = {downLF, downRF};
        BufferedImage[] leftFrames = {leftWalk, leftWalkAlt};
        BufferedImage[] rightFrames = {rightRun, rightRunAlt};

        walkUpAnimation = new Animation(upFrames, 200);

        walkDownAnimation = new Animation(downFrames, 200);

        walkLeftAnimation = new Animation(leftFrames, 200);

        walkRightAnimation = new Animation(rightFrames, 200);
    }

    // Create cars
    private void createCars() {

        Car horizontalCar =
            new Car(0, 1160, 120, 120, 6, 0);

        Car verticalCar =
            new Car(2100, 0, 120, 120, 0, 6);

        horizontalCar.setImage(carLeft);

        verticalCar.setImage(carDown);

        cars.add(horizontalCar);

        cars.add(verticalCar);
    }

    // Create boundaries
    private void createBoundaries() {

        walls.add(new Rect(-100, -100, mapWidth + 100, 270));

        walls.add(new Rect(-100, mapHeight,
            mapWidth + 100, mapHeight + 100));

        walls.add(new Rect(-100, 0, 0, mapHeight));

        walls.add(new Rect(mapWidth, 0,
            mapWidth + 100, mapHeight));
    }

    // Create food spawn points
    private void createFoodSpawnPoints() {

        foodSpawnPoints.add(new Point(250, 400));

        foodSpawnPoints.add(new Point(700, 900));

        foodSpawnPoints.add(new Point(1200, 500));

        foodSpawnPoints.add(new Point(2000, 1700));
    }

    // Create delivery points
    private void createDeliveryPoints() {

        deliveryPoints.add(new Point(400, 500));

        deliveryPoints.add(new Point(900, 300));

        deliveryPoints.add(new Point(1600, 700));
    }

    // Create roads
    private void createRoads() {

        roads.add(new Rect(2000, 0, 2220, 3000));

        roads.add(new Rect(0, 1100, 4050, 1250));
    }

    // Create house collisions
    private void createHouseCollisions() {

        int topRowY = 200;

        // Top row
        walls.add(new Rect(560, topRowY + 55, 700, topRowY + 205));
        walls.add(new Rect(800, topRowY + 55, 900, topRowY + 205));
        walls.add(new Rect(1150, topRowY + 55, 1300, topRowY + 205));
        walls.add(new Rect(1350, topRowY + 55, 1500, topRowY + 205));
        walls.add(new Rect(1550, topRowY + 55, 1675, topRowY + 205));
        walls.add(new Rect(1800, topRowY + 55, 1950, topRowY + 205));
        walls.add(new Rect(2400, topRowY + 55, 2570, topRowY + 205));
        walls.add(new Rect(2650, topRowY + 55, 3800, topRowY + 205));

        // Middle right
        walls.add(new Rect(2615, topRowY + 460, 3600, topRowY + 600));
        walls.add(new Rect(2250, topRowY + 700, 3600, topRowY + 825));
        walls.add(new Rect(2250, topRowY + 1050, 2925, topRowY + 1200));
        walls.add(new Rect(2250, topRowY + 1350, 2925, topRowY + 1500));
        walls.add(new Rect(2250, topRowY + 1700, 3800, topRowY + 1850));

        // Right island
        walls.add(new Rect(3200, topRowY + 1050, 3600, topRowY + 1200));
        walls.add(new Rect(3100, topRowY + 1350, 3600, topRowY + 1500));

        // Far right
        walls.add(new Rect(3850, topRowY + 460, 3950, topRowY + 800));
    }

    @Override
    public void updateGame() {

        if(currentState != PLAY_STATE) {

            return;
        }

        updatePlayerMovement();

        updateCamera();

        updateFoodCollection();

        updateDelivery();

        updateCars();

        updateMessages();
    }

    // Update movement
    private void updatePlayerMovement() {

        int currentSpeed = shiftPressed ? 8 : 4;

        player.setSpeed(currentSpeed);

        if(upPressed) {

            player.moveUp();

            if(collides()) {

                player.moveDown();
            }

            player.setAnimation(walkUpAnimation);
        }

        if(downPressed) {

            player.moveDown();

            if(collides()) {

                player.moveUp();
            }

            player.setAnimation(walkDownAnimation);
        }

        if(leftPressed) {

            player.moveLeft();

            if(collides()) {

                player.moveRight();
            }

            player.setAnimation(walkLeftAnimation);
        }

        if(rightPressed) {

            player.moveRight();

            if(collides()) {

                player.moveLeft();
            }

            player.setAnimation(walkRightAnimation);
        }

        if(!upPressed &&
           !downPressed &&
           !leftPressed &&
           !rightPressed) {

            player.idle();
        }

        player.keepInside(mapWidth, mapHeight);

        player.update();
    }

    // Update camera
    private void updateCamera() {

        cameraX = player.getX() - getWidth() / 2 + 32;

        cameraY = player.getY() - getHeight() / 2 + 32;
    }

    // Update food pickup
    private void updateFoodCollection() {

        if(player.getBounds().intersects(packageFood.getBounds()) &&
           !packageFood.isCollected()) {

            packageFood.collect();

            carryingPackage = true;

            chooseDeliveryPoint();

            message = "Package acquired";

            messageTimer = 120;
        }
    }

    // Update delivery system
    private void updateDelivery() {

        if(!carryingPackage || activeDeliveryPoint == null) {

            return;
        }

        Rect deliveryRect =
            new Rect(activeDeliveryPoint.x,
                     activeDeliveryPoint.y,
                     activeDeliveryPoint.x + 40,
                     activeDeliveryPoint.y + 40);

        if(player.getBounds().intersects(deliveryRect)) {

            carryingPackage = false;

            deliveriesCompleted++;

            message = "Mission Accomplished";

            messageTimer = 180;

            if(deliveriesCompleted >= MAX_DELIVERIES) {

                currentState = WIN_STATE;
            }
            else {

                spawnNextFood();
            }
        }
    }

    // Update cars
    private void updateCars() {

        for(Car car : cars) {

            car.update();

            if(player.getBounds().intersects(car.getBounds())) {

                resetMission();
            }
        }
    }

    // Update messages
    private void updateMessages() {

        if(messageTimer > 0) {

            messageTimer--;
        }
    }

    // Collision check
    private boolean collides() {

        Rect playerBox = player.getBounds();

        for(Rect wall : walls) {

            if(playerBox.intersects(wall)) {

                return true;
            }
        }

        return false;
    }

    // Choose delivery point
    private void chooseDeliveryPoint() {

        int randomIndex =
            (int)(Math.random() * deliveryPoints.size());

        activeDeliveryPoint =
            deliveryPoints.get(randomIndex);
    }

    // Spawn food
    private void spawnNextFood() {

        int randomIndex =
            (int)(Math.random() * foodSpawnPoints.size());

        Point spawnPoint =
            foodSpawnPoints.get(randomIndex);

        packageFood =
            new Food(spawnPoint.x, spawnPoint.y, 32, 32);

        packageFood.setImage(mcdonalds);
    }

    // Reset mission
    private void resetMission() {

        deliveriesCompleted = 0;

        carryingPackage = false;

        activeDeliveryPoint = null;

        message = "";

        messageTimer = 0;

        player.setPosition(500, 500);

        spawnNextFood();
    }

    // Draw menu
    private void drawMenu(Graphics g) {

        int centerX = getWidth() / 2;

        int playButtonY = getHeight() - 220;

        int controlsButtonY = getHeight() - 120;

        g.drawImage(menuImage, 0, 0,
            getWidth(), getHeight(), null);

        g.setColor(Color.WHITE);

        g.fillRect(centerX - 150, playButtonY, 300, 70);

        g.fillRect(centerX - 200, controlsButtonY, 400, 70);

        g.setColor(Color.BLACK);

        g.setFont(new Font("Arial", Font.BOLD, 30));

        g.drawString("Play", centerX - 30, playButtonY + 45);

        g.drawString("Game Controls",
            centerX - 120, controlsButtonY + 45);
    }

    // Draw story screen
    private void drawStory(Graphics g) {

        int centerX = getWidth() / 2;

        int beginButtonY = getHeight() - 140;

        g.drawImage(storyImage, 0, 0,
            getWidth(), getHeight(), null);

        g.setColor(Color.WHITE);

        g.fillRect(centerX - 150, beginButtonY, 300, 70);

        g.setColor(Color.BLACK);

        g.setFont(new Font("Arial", Font.BOLD, 30));

        g.drawString("Begin", centerX - 45, beginButtonY + 45);
    }

    // Draw controls screen
    private void drawControls(Graphics g) {

        int centerX = getWidth() / 2;

        int backButtonY = getHeight() - 120;

        g.drawImage(controlsImage, 0, 0,
            getWidth(), getHeight(), null);

        g.setColor(Color.WHITE);

     // Back button
        int buttonWidth = 250;
        int buttonHeight = 70;

        int buttonX = getWidth() - buttonWidth - 40;
        int buttonY = getHeight() - buttonHeight - 40;

        g.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        g.drawString("Back", buttonX + 80, buttonY + 45);
    }

    // Draw win screen
    private void drawWin(Graphics g) {

        int centerX = getWidth() / 2;

        int playAgainY = getHeight() - 220;

        int exitY = getHeight() - 120;

        g.drawImage(winImage, 0, 0,
            getWidth(), getHeight(), null);

        g.setColor(Color.WHITE);

        g.fillRect(centerX - 170, playAgainY, 340, 70);

        g.fillRect(centerX - 170, exitY, 340, 70);

        g.setColor(Color.BLACK);

        g.setFont(new Font("Arial", Font.BOLD, 30));

        g.drawString("Play Again",
            centerX - 95, playAgainY + 45);

        g.drawString("Exit Game",
            centerX - 80, exitY + 45);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int mouseX = e.getX();

        int mouseY = e.getY();

        int centerX = getWidth() / 2;

        int playButtonY = getHeight() - 220;

        int controlsButtonY = getHeight() - 120;

        int beginButtonY = getHeight() - 140;

        requestFocusInWindow();

        // Menu
        if(currentState == MENU_STATE) {

            if(mouseX >= centerX - 150 &&
               mouseX <= centerX + 150 &&
               mouseY >= playButtonY &&
               mouseY <= playButtonY + 70) {

                currentState = STORY_STATE;
            }

            if(mouseX >= centerX - 200 &&
               mouseX <= centerX + 200 &&
               mouseY >= controlsButtonY &&
               mouseY <= controlsButtonY + 70) {

                currentState = CONTROLS_STATE;
            }
        }

        // Story
        else if(currentState == STORY_STATE) {

            if(mouseX >= centerX - 150 &&
               mouseX <= centerX + 150 &&
               mouseY >= beginButtonY &&
               mouseY <= beginButtonY + 70) {

                currentState = PLAY_STATE;
            }
        }

        // Controls
        else if(currentState == CONTROLS_STATE) {

            int buttonWidth = 250;
            int buttonHeight = 70;

            int buttonX = getWidth() - buttonWidth - 40;
            int buttonY = getHeight() - buttonHeight - 40;

            if(mouseX >= buttonX &&
               mouseX <= buttonX + buttonWidth &&
               mouseY >= buttonY &&
               mouseY <= buttonY + buttonHeight) {

                currentState = MENU_STATE;
            }
        }

        // Win
        else if(currentState == WIN_STATE) {

            int playAgainY = getHeight() - 220;

            int exitY = getHeight() - 120;

            if(mouseX >= centerX - 170 &&
               mouseX <= centerX + 170 &&
               mouseY >= playAgainY &&
               mouseY <= playAgainY + 70) {

                resetMission();

                currentState = PLAY_STATE;
            }

            if(mouseX >= centerX - 170 &&
               mouseX <= centerX + 170 &&
               mouseY >= exitY &&
               mouseY <= exitY + 70) {
                System.exit(0);
            }
        }
    }
    @Override
    public void drawGame(Graphics g) {
        // Menu screen
        if(currentState == MENU_STATE) {
            drawMenu(g);
            return;
        }
        // Story screen
        if(currentState == STORY_STATE) {
            drawStory(g);
            return;
        }
        // Controls screen
        if(currentState == CONTROLS_STATE) {
            drawControls(g);
            return;
        }
        // Win screen
        if(currentState == WIN_STATE) {
            drawWin(g);
            return;
        }
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Draw map
        g.drawImage(mapImage,
            -cameraX,
            -cameraY,
            mapWidth,
            mapHeight,
            null);

        // Draw collisions
        if(showCollisionBoxes) {

            g.setColor(Color.RED);

            for(Rect wall : walls) {

                g.drawRect(
                    wall.left - cameraX,
                    wall.top - cameraY,
                    wall.width(),
                    wall.height()
                );
            }
        }
        // Draw roads
        if(showRoads) {
            g.setColor(Color.RED);
            for(Rect road : roads) {
                g.drawRect(
                    road.left - cameraX,
                    road.top - cameraY,
                    road.width(),
                    road.height()
                );
            }
        }
        // Draw food
        packageFood.draw(g, cameraX, cameraY);
        // Draw dropoff
        if(carryingPackage &&
           activeDeliveryPoint != null) {

            g.drawImage(dropOffImage,activeDeliveryPoint.x - cameraX,activeDeliveryPoint.y 
            		- cameraY, 40,40,null);
        }
        // Draw player
        player.draw(g, cameraX, cameraY);
        // Draw cars
        for(Car car : cars) {
            car.draw(g, cameraX, cameraY);
        }
        // Draw message
        if(messageTimer > 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString(message, 250, 100);
        }
        // Draw UI
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Deliveries: " + deliveriesCompleted + "/" + MAX_DELIVERIES,20,30);
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Late Night Snack");
        GameS26 game = new GameS26();
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.startGame();
        game.requestFocusInWindow();
    }
}