import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class PacMan extends JPanel {
    class Block {
        int x;
        int y;
        Image image;
        int startX;
        int startY;

        Block(Image image, int x, int y) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.startX = x;
            this.startY = y;
        }
    }

    private int rows = 18;
    private int columns = 32;
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

    PacMan() {
        setBackground(Color.BLACK);
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
        } catch (FileNotFoundException e) {
        }
        loadMap();
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
        g.drawImage(pacMan.image, pacMan.x * 80, pacMan.y * 80, 80, 80, null);
        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x * 80, ghost.y * 80, 80, 80, null);
        }
        for (Block wall : walls) {
            g.drawImage(wall.image, wall.x * 80, wall.y * 80, 80, 80, null);
        }
        g.setColor(Color.WHITE);
        for (Block food : foods) {
            g.fillRect(food.x * 80 + 30, food.y * 80 + 30, 20, 20);
        }
    }
}
