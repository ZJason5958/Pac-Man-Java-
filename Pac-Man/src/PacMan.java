import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class PacMan extends JPanel implements ActionListener, KeyListener {
    class Block {
        float x;
        float y;
        Image image;
        float startX;
        float startY;
        float velocityX = 0;
        float velocityY = 0;
        char direction;

        Block(Image image, int x, int y) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.startX = x;
            this.startY = y;
        }
        void updateDirection() {
            if (inputted == true) {
                boolean willCollide = false;
                if (direction == 'U') {
                    this.y -= 1;
                    for (Block wall : walls) {
                        if (collision(this, wall)) {
                            willCollide = true;
                        }
                    }
                    this.y += 1;
                    if (willCollide == false) {
                        this.velocityX = 0;
                        this.velocityY = -1;
                        inputted = false;
                        this.image = pacManUpImage;
                    }
                }
                if (direction == 'D') {
                    this.y += 1;
                    for (Block wall : walls) {
                        if (collision(this, wall)) {
                            willCollide = true;
                        }
                    }
                    this.y -= 1;
                    if (willCollide == false) {
                        this.velocityX = 0;
                        this.velocityY = 1;
                        inputted = false;
                        this.image = pacManDownImage;
                    }
                }
                if (direction == 'L') {
                    this.x -= 1;
                    for (Block wall : walls) {
                        if (collision(this, wall)) {
                            willCollide = true;
                        }
                    }
                    this.x += 1;
                    if (willCollide == false) {
                        this.velocityX = -1;
                        this.velocityY = 0;
                        inputted = false;
                        this.image = pacManLeftImage;
                    }
                }
                if (direction == 'R') {
                    this.x += 1;
                    for (Block wall : walls) {
                        if (collision(this, wall)) {
                            willCollide = true;
                        }
                    }
                    this.x -= 1;
                    if (willCollide == false) {
                        this.velocityX = 1;
                        this.velocityY = 0;
                        inputted = false;
                        this.image = pacManRightImage;
                    }
                }
            }
        }
        void ghostUpdateDirection() {
            boolean willCollide = false;
            if (direction == 'U') {
                this.y -= 1;
                for (Block wall : walls) {
                    if (collision(this, wall)) {
                        willCollide = true;
                    }
                }
                this.y += 1;
                if (willCollide == false) {
                    this.velocityX = 0;
                    this.velocityY = -1;
                }
            }
            if (direction == 'D') {
                this.y += 1;
                for (Block wall : walls) {
                    if (collision(this, wall)) {
                        willCollide = true;
                    }
                }
                this.y -= 1;
                if (willCollide == false) {
                    this.velocityX = 0;
                    this.velocityY = 1;
                }
            }
            if (direction == 'L') {
                this.x -= 1;
                for (Block wall : walls) {
                    if (collision(this, wall)) {
                        willCollide = true;
                    }
                 }
                 this.x += 1;
                 if (willCollide == false) {
                    this.velocityX = -1;
                    this.velocityY = 0;
                }
            }
            if (direction == 'R') {
                this.x += 1;
                for (Block wall : walls) {
                    if (collision(this, wall)) {
                        willCollide = true;
                    }
                }
                this.x -= 1;
                if (willCollide == false) {
                    this.velocityX = 1;
                    this.velocityY = 0;
                }
            }
        }
        void reset() {
            this.x = this.startX;
            this.y = this.startY;
            this.velocityX = 0;
            this.velocityY = 0;
        }
    }

    private int rows = App.rows;
    private int columns = App.columns;
    private int tileSize = App.tileSize;
    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;
    private Image pacManUpImage;
    private Image pacManDownImage;
    private Image pacManLeftImage;
    private Image pacManRightImage;
    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacMan;
    private String[] tileMap = new String[18];
    Timer gameLoop;
    private int frame = 0;
    private boolean inputted = false;
    char[] directions = {'U', 'D', 'L', 'R'};
    Random random = new Random();
    int score = 0;
    int lives = 3;
    boolean gameOver = false;
    int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    PacMan() {
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        wallImage = new ImageIcon(getClass().getResource("./wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("./blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("./orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("./pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("./redGhost.png")).getImage();
        pacManUpImage = new ImageIcon(getClass().getResource("./pacManUp.png")).getImage();
        pacManDownImage = new ImageIcon(getClass().getResource("./pacManDown.png")).getImage();
        pacManLeftImage = new ImageIcon(getClass().getResource("./pacManLeft.png")).getImage();
        pacManRightImage = new ImageIcon(getClass().getResource("./pacManRight.png")).getImage();
        try {
            Scanner scanner = new Scanner(new File("Pac-Man/src/tileMap.txt"));
            int index = 0;
            while (scanner.hasNext()) {
                tileMap[index++] = scanner.nextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
        }
        loadMap();
        for (Block ghost : ghosts) {
            ghost.direction = directions[random.nextInt(4)];
            ghost.ghostUpdateDirection();
        }
        gameLoop = new Timer(50, this);
        gameLoop.start();
    }
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }
    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                char character = tileMap[row].charAt(column);
                if (character == 'X') {
                    walls.add(new Block(wallImage, column, row));
                } else if (character == 'b') {
                    ghosts.add(new Block(blueGhostImage, column, row));
                } else if (character == 'o') {
                    ghosts.add(new Block(orangeGhostImage, column, row));
                } else if (character == 'p') {
                    ghosts.add(new Block(pinkGhostImage, column, row));
                } else if (character == 'r') {
                    ghosts.add(new Block(redGhostImage, column, row));
                } else if (character == 'P') {
                    pacMan = new Block(pacManLeftImage, column, row);
                } else {
                    foods.add(new Block(null, column, row));
                }
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        g.drawImage(pacMan.image, (int)(pacMan.x * tileSize), (int)(pacMan.y * tileSize), tileSize, tileSize, null);
        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, (int)(ghost.x * tileSize), (int)(ghost.y * tileSize), tileSize, tileSize, null);
        }
        for (Block wall : walls) {
            g.drawImage(wall.image, (int)(wall.x * tileSize), (int)(wall.y * tileSize), tileSize, tileSize, null);
        }
        g.setColor(Color.WHITE);
        for (Block food : foods) {
            g.fillRect((int)((food.x + 0.375) * tileSize), (int)((food.y + 0.375) * tileSize), tileSize / 4, tileSize / 4);
        }
        
        if (gameOver) {
            drawCenteredString(g, "GAME OVER", new Rectangle(0, 0, screenWidth, (int)(screenHeight * 0.8)), new Font("Arial", Font.PLAIN, screenHeight / 6));
            drawCenteredString(g, "Final Score: " + score, new Rectangle(0, (int)(screenHeight * 0.2), screenWidth, (int)(screenHeight * 0.8)), new Font("Arial", Font.PLAIN, screenHeight / 9));
        } else {
            g.setFont(new Font("Arial", Font.PLAIN, screenHeight / 12));
            g.drawString("Lives: " + lives + "  Score: " + score, 13, screenHeight - 15);
        }
    }
    public void move() {
        pacMan.x += pacMan.velocityX / 4;
        pacMan.y += pacMan.velocityY / 4;
        for (Block wall : walls) {
            if (collision(pacMan, wall)) {
                pacMan.x -= pacMan.velocityX / 4;
                pacMan.y -= pacMan.velocityY / 4;
            }
        }
        for (Block ghost : ghosts) {
            ghost.x += ghost.velocityX / 4;
            ghost.y += ghost.velocityY / 4;
            for (Block wall : walls) {
                if (collision(ghost, wall)) {
                    ghost.x -= ghost.velocityX / 4;
                    ghost.y -= ghost.velocityY / 4;
                    ghost.velocityX = 0;
                    ghost.velocityY = 0;
                }
            }
        }
        Block foodEaten = null;
        for (Block food : foods) {
            if (collision(pacMan, food)) {
                foodEaten = food;
                score += 1;
            }
        }
        foods.remove(foodEaten);
        if (foods.isEmpty()) {
            loadMap();
            resetPositions();
        }
    }
    public boolean collision(Block a, Block b) {
        if (a.x == b.x) {
            return a.y < b.y + 1 && a.y + 1 > b.y;
        }
        if (a.y == b.y) {
            return a.x < b.x + 1 && a.x + 1 > b.x;
        }
        return false;
    }
    public void resetPositions() {
        pacMan.reset();
        for (Block ghost : ghosts) {
            ghost.reset();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame == 0) {
            for (Block ghost : ghosts) {
                    if (ghost.velocityX == 0 && ghost.velocityY == 0 || random.nextInt(4) == 0) {
                        ghost.direction = directions[random.nextInt(4)];
                        ghost.ghostUpdateDirection();
                    }
            }
        }
        for (Block ghost : ghosts) {
            if (collision(ghost, pacMan)) {
                lives -= 1;
                if (lives == 0) {
                    gameOver = true;
                } else {
                    resetPositions();
                }
            }
        }
        move();
        repaint();
        if (gameOver == true) {
            gameLoop.stop();
        }
        if (frame == 3) {
            frame = 0;
            pacMan.updateDirection();
        } else {
            frame++;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            loadMap();
            resetPositions();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoop.start();
            frame = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            pacMan.direction = 'U';
            inputted = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            pacMan.direction = 'D';
            inputted = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            pacMan.direction = 'L';
            inputted = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            pacMan.direction = 'R';
            inputted = true;
        }
    }
}
