import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        int rows = 18;
        int columns = 32;
        int tileSize = 80;
        int boardWidth = columns * tileSize;
        int boardHeight = rows * tileSize;

        JFrame frame = new JFrame("Pac-Man");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacManGame = new PacMan();
        frame.add(pacManGame);
        frame.pack();
        frame.setVisible(true);
    }
}
