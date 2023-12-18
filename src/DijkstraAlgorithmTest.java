
import algo.DijkstraAlgorithm;
import models.Edge;
import models.Graph;
import models.Node;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testing strategy for each operation of DijkstraAlgorithm:
 *
 * Constructor: public DijkstraAlgorithm(Graph graph)
 * - Test Cases:
 * - Valid graph with source, destination, and reachable nodes.
 *
 * Method: private boolean evaluate()
 * - Test Cases:
 * - Valid graph with source, destination, and reachable nodes.
 * - Graph with null source or destination.
 * - Graph with unreachable nodes.
 *
 * Method: public void run() throws IllegalStateException
 * - Test Cases:
 * - Valid graph with source, destination, and reachable nodes.
 * - Graph with null source or destination.
 * - Graph with unreachable nodes.
 *
 * Method: private Node getAdjacent(Edge edge, Node node)
 * - Test Cases:
 * - Valid edge and node.
 *
 * Method: public Integer getDistance(Node node)
 * - Test Cases:
 * - Solved graph.
 * 
 * Method: public List<Node> getDestinationPath()
 * - Test Cases:
 * - Solved graph.
 * 
 * Method: public List<Node> getPath(Node node)
 * - Test Cases:
 * - Solved graph.
 */

public class DijkstraAlgorithmTest {

    private Graph validGraph;
    private Graph graphWithNullSource;
    private Graph graphWithNullDestination;
    private Graph graphWithUnreachableNodes;

    @Before
    public void setUp() {
        validGraph = createValidGraph();
        graphWithNullSource = createGraphWithNullSource();
        graphWithNullDestination = createGraphWithNullDestination();
        graphWithUnreachableNodes = createGraphWithUnreachableNodes();
    }

    // Constructor Tests
    @Test
    // Valid graph with source, destination, and reachable nodes.
    public void testConstructorValidGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        assertNotNull(dijkstraAlgorithm);
        assertTrue(dijkstraAlgorithm.evaluate());
    }

    // Evaluate Method Tests

    @Test
    // Valid graph with source, destination, and reachable nodes.
    public void testEvaluateValidGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        assertTrue(dijkstraAlgorithm.evaluate());
    }

    @Test
    // Graph with null source.
    public void testEvaluateGraphWithNullSource() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graphWithNullSource);
        assertFalse(dijkstraAlgorithm.evaluate());
    }

    @Test
    // Graph with null destination.
    public void testEvaluateGraphWithNullDestination() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graphWithNullDestination);
        assertFalse(dijkstraAlgorithm.evaluate());
    }

    @Test
    // Graph with unreachable nodes.
    public void testEvaluateGraphWithUnreachableNodes() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graphWithUnreachableNodes);
        assertFalse(dijkstraAlgorithm.evaluate());
    }

    // Run Method Tests

    @Test
    // Valid graph with source, destination, and reachable nodes.
    public void testRunValidGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        dijkstraAlgorithm.run();
        assertTrue(validGraph.isSolved());
    }

    @Test(expected = IllegalStateException.class)
    // Graph with null source.
    public void testRunGraphWithNullSource() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graphWithNullSource);
        dijkstraAlgorithm.run();
    }

    @Test(expected = IllegalStateException.class)
    // Graph with null destination.
    public void testRunGraphWithNullDestination() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graphWithNullDestination);
        dijkstraAlgorithm.run();
    }

    @Test(expected = IllegalStateException.class)
    // Graph with unreachable nodes.
    public void testRunGraphWithUnreachableNodes() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graphWithUnreachableNodes);
        dijkstraAlgorithm.run();
    }

    // getDistance method test cases
    @Test
    // Solved Graph
    public void testGetDistanceSolvedGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        dijkstraAlgorithm.run();
        Node nodeB = validGraph.getNodes().get(1);
        Integer distance = dijkstraAlgorithm.getDistance(nodeB);
        assertNotNull(distance);
        assertEquals(Integer.valueOf(1), distance);
    }

    // getDestinationPath method test cases
    @Test
    // Solved Graph
    public void testGetDestinationPathSolvedGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        dijkstraAlgorithm.run();
        List<Node> path = dijkstraAlgorithm.getDestinationPath();
        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(validGraph.getSource(), path.get(0));
        assertEquals(validGraph.getNodes().get(1), path.get(1));
        assertEquals(validGraph.getDestination(), path.get(2));
    }

    // getPath method test cases
    @Test
    // Solved Graph
    public void testGetPathSolvedGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        dijkstraAlgorithm.run();
        Node nodeB = validGraph.getNodes().get(1);
        List<Node> path = dijkstraAlgorithm.getPath(nodeB);
        assertNotNull(path);
        assertEquals(2, path.size());
        assertEquals(validGraph.getSource(), path.get(0));
        assertEquals(validGraph.getNodes().get(1), path.get(1));
    }

    // getAdjacent method test cases
    @Test
    // Valid edge and node.
    public void testGetAdjacentValidEdgeAndNode() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        Node nodeA = validGraph.getNodes().get(0);
        Edge edgeAB = validGraph.getEdges().get(0);
        Node adjacentNode = dijkstraAlgorithm.getAdjacent(edgeAB, nodeA);
        assertNotNull(adjacentNode);
        assertEquals(validGraph.getNodes().get(1), adjacentNode);
    }

    // Helper Methods for Test Graph Creation
    private Graph createValidGraph() {
        Graph graph = new Graph();
        Node nodeA = new Node(new Point(0, 0));
        Node nodeB = new Node(new Point(1, 1));
        Node nodeC = new Node(new Point(2, 2));
        Edge edgeAB = new Edge(nodeA, nodeB);
        Edge edgeBC = new Edge(nodeB, nodeC);
        graph.setNodes(Arrays.asList(nodeA, nodeB, nodeC));
        graph.setEdges(Arrays.asList(edgeAB, edgeBC));
        graph.setSource(nodeA);
        graph.setDestination(nodeC);
        return graph;
    }

    private Graph createGraphWithNullSource() {
        Graph graph = new Graph();
        Node nodeA = new Node(new Point(0, 0));
        Node nodeB = new Node(new Point(1, 1));
        Node nodeC = new Node(new Point(2, 2));
        Edge edgeAB = new Edge(nodeA, nodeB);
        Edge edgeBC = new Edge(nodeB, nodeC);
        graph.setNodes(Arrays.asList(nodeA, nodeB, nodeC));
        graph.setEdges(Arrays.asList(edgeAB, edgeBC));
        graph.setSource(null);
        graph.setDestination(nodeC);
        return graph;
    }

    private Graph createGraphWithNullDestination() {
        Graph graph = new Graph();
        Node nodeA = new Node(new Point(0, 0));
        Node nodeB = new Node(new Point(1, 1));
        Node nodeC = new Node(new Point(2, 2));
        Edge edgeAB = new Edge(nodeA, nodeB);
        Edge edgeBC = new Edge(nodeB, nodeC);
        graph.setNodes(Arrays.asList(nodeA, nodeB, nodeC));
        graph.setEdges(Arrays.asList(edgeAB, edgeBC));
        graph.setSource(nodeA);
        graph.setDestination(null);
        return graph;
    }

    private Graph createGraphWithUnreachableNodes() {
        Graph graph = new Graph();
        Node nodeA = new Node(new Point(0, 0));
        Node nodeB = new Node(new Point(1, 1));
        Node nodeC = new Node(new Point(2, 2));
        Node unreachableNode = new Node(new Point(3, 3)); // Node not connected to any edges
        Edge edgeAB = new Edge(nodeA, nodeB);
        Edge edgeBC = new Edge(nodeB, nodeC);
        graph.setNodes(Arrays.asList(nodeA, nodeB, nodeC, unreachableNode));
        graph.setEdges(Arrays.asList(edgeAB, edgeBC));
        graph.setSource(nodeA);
        graph.setDestination(nodeC);
        return graph;
    }
}