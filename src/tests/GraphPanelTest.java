package tests;

import models.Edge;
import models.Graph;
import models.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.GraphPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.UIManager;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing strategy for GraphPanel class:
 * 1. Test setting and getting path.
 * 2. Test adding, deleting, and moving nodes.
 * 3. Test adding, deleting, and modifying edges.
 * 4. Test setting source and destination nodes.
 * 5. Test overlapping node prevention.
 */
class GraphPanelTest {

    private GraphPanel graphPanel;
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();
        graphPanel = new GraphPanel(graph);
    }
    
    @Test
    void testPreventOverlappingNodes() {
        // Test preventing overlapping nodes
        graph.addNode(new Point(50, 50));
        MouseEvent overlappingNodeEvent = new MouseEvent(graphPanel, MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(), 0, 50, 50, 1, false);
        graphPanel.mouseClicked(overlappingNodeEvent);
        assertEquals(1, graph.getNodes().size(), "Overlapping node was created");
    }

    @Test
    void testAddNodeOnEdge() {
        // Test adding a node on an existing edge
        graph.addNode(new Point(0, 0));
        graph.addNode(new Point(100, 100));
        graph.addEdge(new Edge(graph.getNodes().get(0), graph.getNodes().get(1)));
        MouseEvent addNodeOnEdgeEvent = new MouseEvent(graphPanel, MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(), 0, 50, 50, 1, false);
        graphPanel.mouseClicked(addNodeOnEdgeEvent);
        assertEquals(2, graph.getNodes().size(), "Node should be added on an existing edge");
    }

    @Test
    void testAddEdgeWithControlDown() {
        // Test adding an edge without selecting a node (Control is down)
        MouseEvent addEdgeEvent = new MouseEvent(graphPanel, MouseEvent.MOUSE_RELEASED,
                System.currentTimeMillis(), 0, 100, 100, 1, true);
        graphPanel.mouseReleased(addEdgeEvent);
        assertEquals(0, graph.getEdges().size(), "Edge should not be added without selecting nodes");
    }

    @Test
    void testDeleteNodeWithoutControlShift() {
        // Test deleting a node without holding Control and Shift
        graph.addNode(new Point(100, 100));
        MouseEvent deleteNodeEvent = new MouseEvent(graphPanel, MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(), 0, 100, 100, 1, false);
        graphPanel.mouseClicked(deleteNodeEvent);
        assertEquals(1, graph.getNodes().size(), "Node should not be deleted without Control and Shift");
    }

    @Test
    void testAddNodeOverlappingPrevented() {
        // Test preventing overlapping nodes with existing nodes
        graph.addNode(new Point(50, 50));
        graph.addNode(new Point(80, 80));
        MouseEvent overlappingNodeEvent = new MouseEvent(graphPanel, MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(), 0, 80, 80, 1, false);
        graphPanel.mouseClicked(overlappingNodeEvent);
        assertEquals(2, graph.getNodes().size(), "Overlapping node should not be created");
    }

    @Test
    void testAddNodeWhileDragging() {
        // Test adding a node while dragging another node
        graph.addNode(new Point(50, 50));
        MouseEvent dragNodeEvent = new MouseEvent(graphPanel, MouseEvent.MOUSE_DRAGGED,
                System.currentTimeMillis(), 0, 80, 80, 1, true);
        graphPanel.mouseDragged(dragNodeEvent);
        MouseEvent addNodeEvent = new MouseEvent(graphPanel, MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(), 0, 100, 100, 1, false);
        graphPanel.mouseClicked(addNodeEvent);
        assertEquals(1, graph.getNodes().size(), "Node should not be added while dragging another node");
    }

    @Test
    void testDeleteEdgeWithNullInput() {
        // Test deleting an edge with null input (no edge is hovered)
        MouseEvent deleteEdgeEvent = new MouseEvent(graphPanel, MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(), 0, 50, 50, 1, true);
        graphPanel.mouseClicked(deleteEdgeEvent);
        assertEquals(0, graph.getEdges().size(), "Edge should not be deleted with null input");
    }

}
