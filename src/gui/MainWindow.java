package gui;

import algo.DijkstraAlgorithm;
import models.Graph;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

/**
 * The main window of the Dijkstra Shortest Path Visualizer application.
 * This window contains the graph panel, buttons for actions, and informational labels.
 */
public class MainWindow extends JPanel {

    private static final long serialVersionUID = 1L;

    // Constants for panel and scroll pane dimensions
    private static final int PANEL_WIDTH = 9000;
    private static final int PANEL_HEIGHT = 4096;
    private static final int SCROLL_PANE_WIDTH = 750;
    private static final int SCROLL_PANE_HEIGHT = 500;

    public Graph graph;
    public GraphPanel graphPanel;

    /**
     * Constructs the main window with the layout and initializes the graph panel.
     * Also sets up buttons for actions.
     */
    public MainWindow() {
        super.setLayout(new BorderLayout());
        initializeGraphPanel();
        setupButtons();
    }

    /**
     * Initializes the graph panel, sets its preferred size, and adds it to the scroll pane.
     * Also sets up the top panel containing informational labels.
     */
    private void initializeGraphPanel() {
        graph = new Graph();
        graphPanel = new GraphPanel(graph);
        graphPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(graphPanel);
        scrollPane.setPreferredSize(new Dimension(SCROLL_PANE_WIDTH, SCROLL_PANE_HEIGHT));
        scrollPane.getViewport().setViewPosition(new Point(PANEL_WIDTH / 2, 0));
        add(scrollPane, BorderLayout.CENTER);
        setupTopPanel();
    }

    /**
     * Sets up the top panel containing an informational label.
     */
    private void setupTopPanel() {
        JLabel infoLabel = new JLabel("Dijkstra Shortest Path Visualiser");
        infoLabel.setForeground(new Color(230, 220, 250));
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(130, 50, 250));
        topPanel.add(infoLabel);
        topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Sets up buttons for actions and adds them to the button panel.
     */
    private void setupButtons() {
        JButton runButton = createButton("run", this::runDijkstraAlgorithm);
        JButton resetButton = createButton("reset", e -> resetGraph());
        JButton infoButton = createButton("info", e -> displayInfo());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(DrawUtils.parseColor("#DDDDDD"));
        buttonPanel.add(resetButton);
        buttonPanel.add(runButton);
        buttonPanel.add(infoButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates a button with the specified icon and action listener.
     *
     * @param iconName       The name of the icon resource.
     * @param actionListener The action listener for the button.
     * @return The created JButton.
     */
    public JButton createButton(String iconName, ActionListener actionListener) {
        JButton button = new JButton();
        setupIcon(button, iconName);
        button.addActionListener(actionListener);
        return button;
    }

    /**
     * Sets up the icon for a button using the specified image resource.
     *
     * @param button The JButton for which to set up the icon.
     * @param img    The name of the image resource.
     */
    public void setupIcon(JButton button, String img) {
        try {
            Image icon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/" + img + ".png")));
            ImageIcon imageIcon = new ImageIcon(icon);
            button.setIcon(imageIcon);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the Dijkstra algorithm on the graph and updates the graph panel with the result.
     *
     * @param event The action event triggering the method.
     */
    public void runDijkstraAlgorithm(ActionEvent event) {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        try {
            dijkstraAlgorithm.run();
            graphPanel.setPath(dijkstraAlgorithm.getDestinationPath());
        } catch (IllegalStateException ise) {
            JOptionPane.showMessageDialog(null, ise.getMessage());
        }
    }

    /**
     * Resets the graph by calling the reset method of the graph panel.
     */
    public void resetGraph() {
        graphPanel.reset();
    }

    /**
     * Displays information about the application's functionalities in a dialog box.
     */
    public void displayInfo() {
        JOptionPane.showMessageDialog(null,
                "Click on empty space to create a new node\n" +
                        "Drag from node to node to create an edge\n" +
                        "Click on edges to set the weight\n\n" +
                        "Combinations:\n" +
                        "Shift + Left Click       :    Set node as the source\n" +
                        "Shift + Right Click     :    Set node as the destination\n" +
                        "Ctrl  + Drag               :    Reposition Node\n" +
                        "Ctrl  + Click                :    Get the Path of Node\n" +
                        "Ctrl  + Shift + Click   :    Delete Node/Edge\n");
    }
}
