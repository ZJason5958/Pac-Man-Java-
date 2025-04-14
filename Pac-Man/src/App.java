import java.awt.*;
import javax.swing.JFrame;

public class App {
    static int rows = 18;
    static int columns = 32;
    static int tileSize;
    public static void main(String[] args) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (screenSize.getWidth() / columns < screenSize.getHeight() / rows) {
            tileSize = (int)screenSize.getWidth() / columns;
        } else {
            tileSize = (int)screenSize.getHeight() / rows;
        }
        JFrame frame = new JFrame("Pac-Man");
        frame.setSize(columns * tileSize, rows * tileSize);
        PacMan pacManGame = new PacMan();
        frame.setUndecorated(true);
        frame.setContentPane(pacManGame);
        frame.setVisible(true);
    }
}
