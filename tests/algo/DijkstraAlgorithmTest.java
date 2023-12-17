package algo;
import algo.DijkstraAlgorithm;
import models.Edge;
import models.Graph;
import models.Node;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Testing strategy for each operation of DijkstraAlgorithm:
 *
 * Constructor: public DijkstraAlgorithm(Graph graph)
 * - Test Cases:
 *   - Valid graph with source, destination, and reachable nodes.
 *   - Graph with null source or destination.
 *   - Empty graph.
 *
 * Method: private boolean evaluate()
 * - Test Cases:
 *   - Valid graph with source, destination, and reachable nodes.
 *   - Graph with null source or destination.
 *   - Graph with unreachable nodes.
 *
 * Method: public void run() throws IllegalStateException
 * - Test Cases:
 *   - Valid graph with source, destination, and reachable nodes.
 *   - Graph with null source or destination.
 *   - Graph with unreachable nodes.
 *
 * Method: private void updateDistance(Node node)
 * - Test Cases:
 *   - Valid node with neighbors.
 *   - Node with null neighbors.
 *
 * Method: private Node getAdjacent(Edge edge, Node node)
 * - Test Cases:
 *   - Valid edge and node.
 *   - Null edge or node.
 *
 * Method: private List<Edge> getNeighbors(Node node)
 * - Test Cases:
 *   - Valid node with neighbors.
 *   - Valid node without neighbors.
 *
 * Method: public Integer getDestinationDistance()
 * - Test Cases:
 *   - Solved graph.
 *   - Unsolved graph.
 *
 * Method: public Integer getDistance(Node node)
 * - Test Cases:
 *   - Solved graph.
 *   - Unsolved graph.
 *
 * Method: public List<Node> getDestinationPath()
 * - Test Cases:
 *   - Solved graph.
 *   - Unsolved graph.
 *
 * Method: public List<Node> getPath(Node node)
 * - Test Cases:
 *   - Solved graph.
 *   - Unsolved graph.
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
    @Test(expected = IllegalStateException.class)
    // Graph with null source.
    public void testConstructorGraphWithNullSource() {
        new DijkstraAlgorithm(graphWithNullSource);
    }

    @Test(expected = IllegalStateException.class)
    // Graph with null destination.
    public void testConstructorGraphWithNullDestination() {
        new DijkstraAlgorithm(graphWithNullDestination);
    }

    @Test(expected = IllegalStateException.class)
    // Graph with unreachable nodes.
    public void testConstructorGraphWithUnreachableNodes() {
        new DijkstraAlgorithm(graphWithUnreachableNodes);
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

    // getDestinationDistance method test cases
    @Test
    //Solved Graph
    public void testGetDestinationDistanceSolvedGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        dijkstraAlgorithm.run();
        Integer distance = dijkstraAlgorithm.getDestinationDistance();
        assertNotNull(distance);
        assertEquals(Integer.valueOf(3), distance);
    }

    @Test
    //Unsolved Graph
    public void testGetDestinationDistanceUnsolvedGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        Integer distance = dijkstraAlgorithm.getDestinationDistance();
        assertNull(distance);
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

    @Test
    //Unsolved Graph
    public void testGetDistanceUnsolvedGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        Node nodeB = validGraph.getNodes().get(1);
        Integer distance = dijkstraAlgorithm.getDistance(nodeB);
        assertNull(distance);
    }

    // getDestinationPath method test cases
    @Test
    //Solved Graph
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

    @Test
    // Unsolved Graph
    public void testGetDestinationPathUnsolvedGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        List<Node> path = dijkstraAlgorithm.getDestinationPath();
        assertNotNull(path);
        assertTrue(path.isEmpty());
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

    @Test
    // Unsolved Graph
    public void testGetPathUnsolvedGraph() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        Node nodeB = validGraph.getNodes().get(1);
        List<Node> path = dijkstraAlgorithm.getPath(nodeB);
        assertNotNull(path);
        assertTrue(path.isEmpty());
    }

    // getNeighbors test cases
    @Test
    // Valid node with neighbors.
    public void testGetNeighborsValidNodeWithNeighbors() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        Node nodeB = validGraph.getNodes().get(1);
        List<Edge> neighbors = dijkstraAlgorithm.getNeighbors(nodeB);
        assertNotNull(neighbors);
        assertEquals(1, neighbors.size());
        assertEquals(validGraph.getEdges().get(0), neighbors.get(0));
    }

    @Test
    // Valid node without neighbors.
    public void testGetNeighborsValidNodeWithoutNeighbors() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        Node nodeC = validGraph.getNodes().get(2);
        List<Edge> neighbors = dijkstraAlgorithm.getNeighbors(nodeC);
        assertNotNull(neighbors);
        assertTrue(neighbors.isEmpty());
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

    @Test
    // Null edge or node.
    public void testGetAdjacentNullEdgeOrNode() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        Edge edge = validGraph.getEdges().get(0);
        Node node = validGraph.getNodes().get(0);
        Node adjacentNodeWithNullEdge = dijkstraAlgorithm.getAdjacent(null, node);
        Node adjacentNodeWithNullNode = dijkstraAlgorithm.getAdjacent(edge, null);
        assertNull(adjacentNodeWithNullEdge);
        assertNull(adjacentNodeWithNullNode);
    }

    // updateDistance method Graph
    @Test
    // Valid node with neighbors.
    public void testUpdateDistanceValidNodeWithNeighbors() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        Node nodeA = validGraph.getNodes().get(0);
        dijkstraAlgorithm.updateDistance(nodeA);
        Integer distanceToB = dijkstraAlgorithm.getDistance(validGraph.getNodes().get(1));
        assertNotNull(distanceToB);
        assertEquals(Integer.valueOf(1), distanceToB);
    }

    @Test
    // Node with null neighbors.
    public void testUpdateDistanceNodeWithNullNeighbors() {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(validGraph);
        Node nodeC = validGraph.getNodes().get(2);
        dijkstraAlgorithm.updateDistance(nodeC);
        // No changes expected as nodeC has no neighbors
        Integer distanceToA = dijkstraAlgorithm.getDistance(validGraph.getNodes().get(0));
        assertNull(distanceToA);
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