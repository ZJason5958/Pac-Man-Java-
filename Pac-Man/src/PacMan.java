import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel {
    private int rows = 18;
    private int columns = 32;
    private int tileSize = 80;
    private int boardWidth = columns * tileSize;
    private int boardHeight = rows * tileSize;

    PacMan() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
    }
}
