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

public class MainWindow extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int PANEL_WIDTH = 9000;
    private static final int PANEL_HEIGHT = 4096;
    private static final int SCROLL_PANE_WIDTH = 750;
    private static final int SCROLL_PANE_HEIGHT = 500;

    private Graph graph;
    private GraphPanel graphPanel;

    public MainWindow() {
        super.setLayout(new BorderLayout());
        initializeGraphPanel();
        setupButtons();
    }

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

    private void setupTopPanel() {
        JLabel infoLabel = new JLabel("Dijkstra Shortest Path Visualiser");
        infoLabel.setForeground(new Color(230, 220, 250));
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(130, 50, 250));
        topPanel.add(infoLabel);
        topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(topPanel, BorderLayout.NORTH);
    }

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

    private JButton createButton(String iconName, ActionListener actionListener) {
        JButton button = new JButton();
        setupIcon(button, iconName);
        button.addActionListener(actionListener);
        return button;
    }

    private void setupIcon(JButton button, String img) {
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

    private void runDijkstraAlgorithm(ActionEvent event) {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        try {
            dijkstraAlgorithm.run();
            graphPanel.setPath(dijkstraAlgorithm.getDestinationPath());
        } catch (IllegalStateException ise) {
            JOptionPane.showMessageDialog(null, ise.getMessage());
        }
    }

    private void resetGraph() {
        graphPanel.reset();
    }

    private void displayInfo() {
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
