Edge class:

package models;

/**
 * Represents an edge connecting two nodes in a graph.
 */
public class Edge {
    private static final int DEFAULT_WEIGHT = 1;

    private Node startNode;
    private Node endNode;
    private int weight = DEFAULT_WEIGHT;

    /**
     * Constructs an Edge object with two connected nodes.
     *
     * @param startNode The starting node of the edge.
     * @param endNode   The ending node of the edge.
     */
    public Edge(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    /**
     * Gets the starting node of the edge.
     *
     * @return The starting node.
     */
    public Node getStartNode() {
        return startNode;
    }

    /**
     * Gets the ending node of the edge.
     *
     * @return The ending node.
     */
    public Node getEndNode() {
        return endNode;
    }

    /**
     * Sets the weight of the edge.
     *
     * @param weight The new weight value.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Gets the weight of the edge.
     *
     * @return The weight value.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Checks if the given node is part of the edge.
     *
     * @param node The node to check.
     * @return True if the node is part of the edge, false otherwise.
     */
    public boolean containsNode(Node node) {
        return startNode == node || endNode == node;
    }

    /**
     * Checks if the given edge is equal to this edge.
     *
     * @param edge The edge to compare.
     * @return True if the edges are equal, false otherwise.
     */
    public boolean equals(Edge edge) {
        return (startNode == edge.startNode && endNode == edge.endNode) ||
                (startNode == edge.endNode && endNode == edge.startNode);
    }

    /**
     * Returns a string representation of the edge.
     *
     * @return String representation of the edge.
     */
    @Override
    public String toString() {
        return "Edge ~ " + getStartNode().getId() + " - " + getEndNode().getId();
    }
}
