package models;

import java.awt.*;
import java.util.List;

/**
 * The {@code Node} class represents a node in a graph, characterized by its unique identifier,
 * coordinates, and an optional path.
 */
public class Node {

    /** The coordinates of the node. */
    private Point coord = new Point();

    /** The unique identifier of the node. */
    private int id;

    /** The list of nodes representing a path associated with this node. */
    private List<Node> path;

    /**
     * Constructs an empty {@code Node}. The coordinates and identifier need to be set separately.
     */
    public Node() {}

    /**
     * Constructs a {@code Node} with the specified identifier and default coordinates (0, 0).
     *
     * @param id The unique identifier for the node.
     */
    public Node(int id) {
        this.id = id;
    }

    /**
     * Constructs a {@code Node} with the specified coordinates.
     *
     * @param p The coordinates of the node.
     */
    public Node(Point p) {
        this.coord = new Point(p);
    }

    /**
     * Sets the unique identifier for the node.
     *
     * @param id The unique identifier for the node.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the coordinates of the node.
     *
     * @param x The x-coordinate of the node.
     * @param y The y-coordinate of the node.
     */
    public void setCoord(int x, int y) {
        coord.setLocation(x, y);
    }

    /**
     * Gets the coordinates of the node.
     *
     * @return The coordinates of the node.
     */
    public Point getCoord() {
        return coord;
    }

    /**
     * Sets the path associated with this node.
     *
     * @param path The list of nodes representing a path.
     */
    public void setPath(List<Node> path) {
        this.path = path;
    }

    /**
     * Gets the path associated with this node.
     *
     * @return The list of nodes representing a path.
     */
    public List<Node> getPath() {
        return path;
    }

    /**
     * Gets the x-coordinate of the node.
     *
     * @return The x-coordinate of the node.
     */
    public int getX() {
        return (int) coord.getX();
    }

    /**
     * Gets the y-coordinate of the node.
     *
     * @return The y-coordinate of the node.
     */
    public int getY() {
        return (int) coord.getY();
    }

    /**
     * Gets the unique identifier of the node.
     *
     * @return The unique identifier of the node.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a string representation of the node.
     *
     * @return A string representation of the node, including its identifier.
     */
    @Override
    public String toString() {
        return "Node " + id;
    }
}
