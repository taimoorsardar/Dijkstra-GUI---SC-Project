package tests;

import org.junit.jupiter.api.Test;

import gui.DrawUtils;
import models.Edge;
import models.Node;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.image.BufferedImage;

class DrawUtilsTest {

    // Testing Strategy:
    // 1. Test drawing a regular node.
    // 2. Test drawing a source node.
    // 3. Test drawing a destination node.
    // 4. Test drawing an edge.
    // 5. Test drawing a path.
    // 6. Test drawing a hovered edge.
    // 7. Test drawing the weight of an edge.

	// Utility method to create a DrawUtils instance
	private DrawUtils createDrawUtils() {
        BufferedImage dummyImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D dummyGraphics = dummyImage.createGraphics();
        return new DrawUtils(dummyGraphics);
    }

    // Helper method to visually inspect the drawing
    private void visuallyInspectDrawing(DrawUtils drawUtils) {
        // For now, this is a placeholder method. In a real scenario, you might use a graphical library or tool to inspect
        // the drawn elements visually during the test execution.
    }

    @Test
    void testDrawRegularNode() {
        DrawUtils drawUtils = createDrawUtils();
        Node node = new Node(new Point(100, 100));
        drawUtils.drawNode(node);
        visuallyInspectDrawing(drawUtils);
    }

    @Test
    void testDrawSourceNode() {
        DrawUtils drawUtils = createDrawUtils();
        Node node = new Node(new Point(100, 100));
        drawUtils.drawSourceNode(node);
        visuallyInspectDrawing(drawUtils);
    }

    @Test
    void testDrawDestinationNode() {
        DrawUtils drawUtils = createDrawUtils();
        Node node = new Node(new Point(100, 100));
        drawUtils.drawDestinationNode(node);
        visuallyInspectDrawing(drawUtils);
    }

    @Test
    void testDrawEdge() {
        DrawUtils drawUtils = createDrawUtils();
        Node node1 = new Node(new Point(50, 50));
        Node node2 = new Node(new Point(100, 100));
        Edge edge = new Edge(node1, node2);
        drawUtils.drawEdge(edge);
        visuallyInspectDrawing(drawUtils);
    }

    @Test
    void testDrawPath() {
        DrawUtils drawUtils = createDrawUtils();
        Node node1 = new Node(new Point(50, 50));
        Node node2 = new Node(new Point(100, 100));
        Edge edge = new Edge(node1, node2);
        drawUtils.drawPath(edge);
        visuallyInspectDrawing(drawUtils);
    }

    @Test
    void testDrawHoveredEdge() {
        DrawUtils drawUtils = createDrawUtils();
        Node node1 = new Node(new Point(50, 50));
        Node node2 = new Node(new Point(100, 100));
        Edge edge = new Edge(node1, node2);
        drawUtils.drawHoveredEdge(edge);
        visuallyInspectDrawing(drawUtils);
    }

    @Test
    void testDrawWeight() {
        DrawUtils drawUtils = createDrawUtils();
        Node node1 = new Node(new Point(50, 50));
        Node node2 = new Node(new Point(100, 100));
        Edge edge = new Edge(node1, node2);
        drawUtils.drawWeight(edge);
        visuallyInspectDrawing(drawUtils);
    }
}
