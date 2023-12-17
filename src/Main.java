import gui.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * The main class responsible for initializing and launching the Dijkstra Algorithm application.
 */
public class Main {

    /**
     * The main entry point for the application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        // Set the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            // Handle the look and feel exception (optional)
            e.printStackTrace();
        }

        // Create and configure the main JFrame
        JFrame mainWindowFrame = new JFrame("Dijkstra Algorithm");
        mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindowFrame.setSize(new Dimension(900, 600));
        mainWindowFrame.add(new MainWindow());
        mainWindowFrame.setVisible(true);
    }
}
