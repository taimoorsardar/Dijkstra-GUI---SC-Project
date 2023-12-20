package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a graph with nodes and edges for various graph algorithms.
 * The graph can be used for pathfinding and related applications.
 */
public class Graph {
    private static final int INITIAL_NODE_ID = 1;

    private int count = INITIAL_NODE_ID;
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    private Node source;
    private Node destination;

    private boolean solved = false;

    /**
     * Sets the source node for the graph.
     * If the provided node is in the graph, it becomes the source.
     *
     * @param node The source node to set.
     * @throws IllegalArgumentException if the node is not in the list of nodes.
     */
    public void setSource(Node node) {
        if (nodes.contains(node)) {
            source = node;
        } else {
            throw new IllegalArgumentException("Source node must be in the list of nodes.");
        }
    }

    /**
     * Sets the destination node for the graph.
     * If the provided node is in the graph, it becomes the destination.
     *
     * @param node The destination node to set.
     * @throws IllegalArgumentException if the node is not in the list of nodes.
     */
    public void setDestination(Node node) {
        if (nodes.contains(node)) {
            destination = node;
        } else {
            throw new IllegalArgumentException("Destination node must be in the list of nodes.");
        }
    }

    /**
     * Gets the count of nodes in the graph.
     *
     * @return The count of nodes in the graph.
     */
    public int getNodeCount() {
        return count;
    }

    /**
     * Checks if a node is the first node added to the graph.
     *
     * @param node The node to check.
     * @return true if the node is the first node, false otherwise.
     */
    private boolean isFirstNode(Node node) {
        return node.getId() == INITIAL_NODE_ID;
    }

    /**
     * Adds a new node to the graph with the specified coordinates.
     *
     * @param coordinates The coordinates of the new node.
     * @return true if the added node is the first node, false otherwise.
     */
    public boolean addNode(Point coordinates) {
        Node newNode = new Node(coordinates);
        return addNode(newNode);
    }

    /**
     * Adds the provided node to the graph.
     *
     * @param node The node to add.
     * @return true if the added node is the first node, false otherwise.
     */
    private boolean addNode(Node node) {
        node.setId(count++);
        boolean isFirst = isFirstNode(node);
        nodes.add(node);

        if (isFirst) {
            source = node;
        }

        return isFirst;
    }

    /**
     * Adds a new edge to the graph.
     * If an equivalent edge already exists, it will not be added.
     *
     * @param newEdge The new edge to add.
     * @return true if the edge is added, false if an equivalent edge already exists.
     */
    public boolean addEdge(Edge newEdge) {
        boolean isEdgeAdded = edges.stream().noneMatch(existingEdge -> existingEdge.equals(newEdge));

        if (isEdgeAdded) {
            edges.add(newEdge);
        }

        return isEdgeAdded;
    }

    /**
     * Deletes the specified node from the graph along with its associated edges.
     *
     * @param node The node to delete.
     */
    public void deleteNode(Node node) {
        List<Edge> delete = new ArrayList<>();
        for (Edge edge : edges) {
            if (hasNodeInEdges(node)) {
                delete.add(edge);
            }
        }
        edges.removeAll(delete);
        nodes.remove(node);
    }

    /**
     * Clears the graph, resetting node count, nodes, edges, solved status, source, and destination.
     */
    public void clear() {
        count = INITIAL_NODE_ID;
        nodes.clear();
        edges.clear();
        solved = false;

        source = null;
        destination = null;
    }

    /**
     * Checks if a node is present in any of the edges in the graph.
     *
     * @param node The node to check.
     * @return true if the node is present in any of the edges, false otherwise.
     */
    private boolean hasNodeInEdges(Node node) {
        for (Edge edge : edges) {
            if (node == edge.getNodeOne() || node == edge.getNodeTwo()) {
                return true;
            }
        }
        return false;
    }
}
