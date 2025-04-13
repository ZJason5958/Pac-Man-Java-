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
    private int tileSize = 80;
    private int boardWidth = columns * tileSize;
    private int boardHeight = rows * tileSize;
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
        setPreferredSize(new Dimension(boardWidth, boardHeight));
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
            Scanner scanner = new Scanner(new File("tileMap.txt"));
            int index = 0;
            while (scanner.hasNext()) {
                tileMap[index++] = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
        }
    }
}
