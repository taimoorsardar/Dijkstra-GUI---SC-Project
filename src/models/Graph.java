package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Graph class represents a graph consisting of nodes and edges.
 * It provides functionality for managing nodes, edges, and solving path-related problems.
 */
public class Graph {

    // Fields
    private int count = 1;             // Counter for assigning unique IDs to nodes
    private List<Node> nodes = new ArrayList<>();           // List of nodes in the graph
    private List<Edge> edges = new ArrayList<>();           // List of edges in the graph
    private Node source;                // Source node for path-related problems
    private Node destination;           // Destination node for path-related problems
    private boolean solved;             // Flag indicating whether a path has been solved

    /**
     * Sets the flag indicating whether a path has been solved.
     *
     * @param solved True if a path has been solved, false otherwise.
     */
    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    /**
     * Checks whether a path has been solved.
     *
     * @return True if a path has been solved, false otherwise.
     */
    public boolean isSolved() {
        return solved;
    }

    /**
     * Sets the list of nodes in the graph.
     *
     * @param nodes The list of nodes to set.
     */
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Gets the list of nodes in the graph.
     *
     * @return The list of nodes in the graph.
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Sets the list of edges in the graph.
     *
     * @param edges The list of edges to set.
     */
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    /**
     * Gets the list of edges in the graph.
     *
     * @return The list of edges in the graph.
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Checks whether a node is reachable in the graph.
     *
     * @param node The node to check for reachability.
     * @return True if the node is reachable, false otherwise.
     */
    public boolean isNodeReachable(Node node) {
        for (Edge edge : edges)
            if (node == edge.getNodeOne() || node == edge.getNodeTwo())
                return true;

        return false;
    }

    /**
     * Sets the source node for path-related problems.
     *
     * @param node The source node to set.
     */
    public void setSource(Node node) {
        if (nodes.contains(node))
            source = node;
    }

    /**
     * Sets the destination node for path-related problems.
     *
     * @param node The destination node to set.
     */
    public void setDestination(Node node) {
        if (nodes.contains(node))
            destination = node;
    }

    /**
     * Gets the source node for path-related problems.
     *
     * @return The source node for path-related problems.
     */
    public Node getSource() {
        return source;
    }

    /**
     * Gets the destination node for path-related problems.
     *
     * @return The destination node for path-related problems.
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * Checks whether a node is the source node for path-related problems.
     *
     * @param node The node to check.
     * @return True if the node is the source, false otherwise.
     */
    public boolean isSource(Node node) {
        return node == source;
    }

    /**
     * Checks whether a node is the destination node for path-related problems.
     *
     * @param node The node to check.
     * @return True if the node is the destination, false otherwise.
     */
    public boolean isDestination(Node node) {
        return node == destination;
    }

    /**
     * Adds a new node to the graph with the given coordinates.
     *
     * @param coord The coordinates of the new node.
     */
    public void addNode(Point coord) {
        Node node = new Node(coord);
        addNode(node);
    }

    /**
     * Adds a new node to the graph.
     *
     * @param node The node to add to the graph.
     */
    public void addNode(Node node) {
        node.setId(count++);
        nodes.add(node);
        if (node.getId() == 1)
            source = node;
    }

    /**
     * Adds a new edge to the graph.
     *
     * @param new_edge The edge to add to the graph.
     */
    public void addEdge(Edge new_edge) {
        boolean added = false;
        for (Edge edge : edges) {
            if (edge.equals(new_edge)) {
                added = true;
                break;
            }
        }
        if (!added)
            edges.add(new_edge);
    }

    /**
     * Deletes a node from the graph along with its associated edges.
     *
     * @param node The node to delete from the graph.
     */
    public void deleteNode(Node node) {
        List<Edge> delete = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.hasNode(node)) {
                delete.add(edge);
            }
        }
        for (Edge edge : delete) {
            edges.remove(edge);
        }
        nodes.remove(node);
    }

    /**
     * Clears the graph, resetting the counter, clearing nodes and edges,
     * and setting source, destination, and solved flags to default values.
     */
    public void clear() {
        count = 1;
        nodes.clear();
        edges.clear();
        solved = false;

        source = null;
        destination = null;
    }
}
