package models;

/**
 * The Edge class represents an edge between two nodes in a graph.
 * It contains information about the nodes it connects and its weight.
 */
public class Edge {

    // Fields
    private Node one;       // First node of the edge
    private Node two;       // Second node of the edge
    private int weight = 1;  // Weight of the edge (default value is 1)

    /**
     * Constructs an Edge between two nodes.
     *
     * @param one The first node of the edge.
     * @param two The second node of the edge.
     */
    public Edge(Node one, Node two) {
        this.one = one;
        this.two = two;
    }

    /**
     * Gets the first node of the edge.
     *
     * @return The first node of the edge.
     */
    public Node getNodeOne() {
        return one;
    }

    /**
     * Gets the second node of the edge.
     *
     * @return The second node of the edge.
     */
    public Node getNodeTwo() {
        return two;
    }

    /**
     * Sets the weight of the edge.
     *
     * @param weight The weight to set for the edge.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Gets the weight of the edge.
     *
     * @return The weight of the edge.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Checks whether the edge contains a specific node.
     *
     * @param node The node to check for in the edge.
     * @return True if the edge contains the node, false otherwise.
     */
    public boolean hasNode(Node node) {
        return one == node || two == node;
    }

    /**
     * Checks whether two edges are equal.
     *
     * @param edge The edge to compare with.
     * @return True if the edges are equal (connecting the same nodes), false otherwise.
     */
    public boolean equals(Edge edge) {
        return (one == edge.one && two == edge.two) || (one == edge.two && two == edge.one);
    }

    /**
     * Returns a string representation of the edge.
     *
     * @return A string representation of the edge, showing the IDs of connected nodes.
     */
    @Override
    public String toString() {
        return "Edge ~ "
                + getNodeOne().getId() + " - "
                + getNodeTwo().getId();
    }
}
