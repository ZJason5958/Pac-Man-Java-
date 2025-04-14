import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Pac-Man");
        PacMan pacManGame = new PacMan();
        frame.add(pacManGame);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        pacManGame.requestFocus();
        frame.setVisible(true);
    }
}
