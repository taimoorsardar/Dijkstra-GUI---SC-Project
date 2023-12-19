package gui;

import models.Edge;
import models.Graph;
import models.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

/**
 * The GraphPanel class represents a JPanel for drawing and interacting with a graph.
 * It handles mouse events for creating, deleting, and modifying nodes and edges.
 */
public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener {

    private DrawUtils drawUtils;
    private Graph graph;

    private Node selectedNode = null;
    private Node hoveredNode = null;
    private Edge hoveredEdge = null;

    private List<Node> pathList = null;

    private Point cursor;

    private static final double OVERLAPPING_RADIUS_FACTOR = 2.5;

    /**
     * Constructs a new GraphPanel with the specified graph.
     *
     * @param graph The graph to be displayed and interacted with.
     */
    public GraphPanel(Graph graph) {
        this.graph = graph;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Sets the path to be displayed on the graph.
     *
     * @param path The list of nodes representing the path.
     */
    public void setPath(List<Node> path) {
        this.pathList = path;
        hoveredEdge = null;
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawUtils = new DrawUtils(graphics2d);

        if (graph.isSolved()) {
            drawUtils.drawPath(pathList);
        }

        if (selectedNode != null && cursor != null) {
            Edge e = new Edge(selectedNode, new Node(cursor));
            drawUtils.drawEdge(e);
        }

        for (Edge edge : graph.getEdges()) {
            if (edge == hoveredEdge)
                drawUtils.drawHoveredEdge(edge);
            drawUtils.drawEdge(edge);
        }

        for (Node node : graph.getNodes()) {
            if (node == selectedNode || node == hoveredNode)
                drawUtils.drawHalo(node);
            if (graph.isSource(node))
                drawUtils.drawSourceNode(node);
            else if (graph.isDestination(node))
                drawUtils.drawDestinationNode(node);
            else
                drawUtils.drawNode(node);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        Node selected = null;
        for (Node node : graph.getNodes()) {
            if (DrawUtils.isWithinBounds(event, node.getCoord())) {
                selected = node;
                break;
            }
        }

        if (selected != null) {
            if (event.isControlDown() && event.isShiftDown()) {
                graph.deleteNode(selected);
                graph.setSolved(false);
                repaint();
                return;
            } else if (event.isControlDown() && graph.isSolved()) {
                pathList = selected.getPath();
                repaint();
                return;
            } else if (event.isShiftDown()) {
                if (SwingUtilities.isLeftMouseButton(event)) {
                    if (!graph.isDestination(selected))
                        graph.setSource(selected);
                    else
                        JOptionPane.showMessageDialog(null, "Destination can't be set as Source");
                } else if (SwingUtilities.isRightMouseButton(event)) {
                    if (!graph.isSource(selected))
                        graph.setDestination(selected);
                    else
                        JOptionPane.showMessageDialog(null, "Source can't be set as Destination");
                } else
                    return;

                graph.setSolved(false);
                repaint();
                return;
            }
        }

        if (hoveredEdge != null) {
            if (event.isControlDown() && event.isShiftDown()) {
                graph.getEdges().remove(hoveredEdge);
                hoveredEdge = null;
                graph.setSolved(false);
                repaint();
                return;
            }

            String input = JOptionPane.showInputDialog("Enter weight for " + hoveredEdge.toString()
                    + " : ");
            try {
                int weight = Integer.parseInt(input);
                if (weight > 0) {
                    hoveredEdge.setWeight(weight);
                    graph.setSolved(false);
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Weight should be positive");
                }
            } catch (NumberFormatException nfe) {
                // Log or provide more meaningful error message
            }
            return;
        }

        for (Node node : graph.getNodes()) {
            if (DrawUtils.isOverlapping(event, node.getCoord())) {
                JOptionPane.showMessageDialog(null, "Overlapping Node can't be created");
                return;
            }
        }

        graph.addNode(event.getPoint());
        graph.setSolved(false);
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Leave empty if not used
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (Node node : graph.getNodes()) {
            if (selectedNode != null && node != selectedNode && DrawUtils.isWithinBounds(e, node.getCoord())) {
                Edge newEdge = new Edge(selectedNode, node);
                graph.addEdge(newEdge);
                graph.setSolved(false);
            }
        }
        selectedNode = null;
        hoveredNode = null;
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Leave empty if not used
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Leave empty if not used
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        hoveredNode = null;

        for (Node node : graph.getNodes()) {
            if (selectedNode == null && DrawUtils.isWithinBounds(e, node.getCoord())) {
                selectedNode = node;
            } else if (DrawUtils.isWithinBounds(e, node.getCoord())) {
                hoveredNode = node;
            }
        }

        if (selectedNode != null) {
            if (e.isControlDown()) {
                selectedNode.setCoord(e.getX(), e.getY());
                cursor = null;
                repaint();
                return;
            }

            cursor = new Point(e.getX(), e.getY());
            repaint();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.isControlDown()) {
            hoveredNode = null;
            for (Node node : graph.getNodes()) {
                if (DrawUtils.isWithinBounds(e, node.getCoord())) {
                    hoveredNode = node;
                }
            }
        }

        hoveredEdge = null;

        for (Edge edge : graph.getEdges()) {
            if (DrawUtils.isOnEdge(e, edge)) {
                hoveredEdge = edge;
            }
        }

        repaint();
    }

    /**
     * Resets the GraphPanel by clearing the graph and resetting selected and hovered states.
     */
    public void reset() {
        graph.clear();
        selectedNode = null;
        hoveredNode = null;
        hoveredEdge = null;
        repaint();
    }
}
