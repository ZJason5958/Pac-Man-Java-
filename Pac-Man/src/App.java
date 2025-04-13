import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Pac-Man");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacManGame = new PacMan();
        frame.add(pacManGame);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
    }
}
