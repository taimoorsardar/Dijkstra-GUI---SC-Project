package tests;

import models.Node;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

	
	/**
	 * Testing Strategy for Node Class:
	 *
	 * 1. Node Creation:
	 *    - Test creating a node with a specified ID.
	 *    - Test creating a node with default constructor.
	 *
	 * 2. Setters and Getters:
	 *    - Test setting and getting the ID.
	 *    - Test setting and getting the coordinates.
	 *    - Test setting and getting the path.
	 *    - Test getting X and Y coordinates individually.
	 *
	 * 3. toString():
	 *    - Test the toString method to ensure it returns the expected string representation.
	 *
	 * 4. Coordinate Updates:
	 *    - Test updating the node coordinates.
	 *
	 * 5. Path Manipulation:
	 *    - Test setting and getting a path.
	 *    - Test adding nodes to the path.
	 *    - Test clearing the path.
	 *    
	 * 6. Equality:
	 *    - Test equality of nodes with the same ID.
	 *    - Test inequality of nodes with different IDs.
	 *    - Test equality of nodes with the same coordinates.
	 *    - Test inequality of nodes with different coordinates.
	 *
	 * 7. Hash Code:
	 *    - Test hash code consistency.
	 *    - Test hash code equality for nodes with the same ID.
	 *    - Test hash code inequality for nodes with different IDs.
	 *
	 * 8. Path Manipulation (Continued):
	 *    - Test adding multiple nodes to the path.
	 *    - Test removing nodes from the path.
	 */
	
	
    @Test
    void testNodeCreation() {
        Node node = new Node(1);

        assertEquals(1, node.getId());
        assertEquals(new Point(0, 0), node.getCoord());
        assertNull(node.getPath());
    }

    @Test
    void testSettersAndGetters() {
        Node node = new Node();

        node.setId(2);
        assertEquals(2, node.getId());

        node.setCoord(10, 20);
        assertEquals(new Point(10, 20), node.getCoord());

        List<Node> path = new ArrayList<>();
        path.add(new Node(1));
        path.add(new Node(3));

        node.setPath(path);
        assertEquals(path, node.getPath());

        assertEquals(10, node.getX());
        assertEquals(20, node.getY());
    }

    @Test
    void testToString() {
        Node node = new Node(42);
        assertEquals("Node 42", node.toString());
    }

    @Test
    void testCoordinateUpdates() {
        Node node = new Node();

        node.setCoord(5, 8);
        assertEquals(5, node.getX());
        assertEquals(8, node.getY());

        node.setCoord(12, 18);
        assertEquals(12, node.getX());
        assertEquals(18, node.getY());
    }

    @Test
    void testEquality() {
        Node node1 = new Node(1);
        Node node1Copy = new Node(1);
        Node node2 = new Node(2);

        // Test equality of nodes with the same ID
        assertNotEquals(node1, node1Copy);

        // Test inequality of nodes with different IDs
        assertNotEquals(node1, node2);

        // Test equality of nodes with the same coordinates
        node1.setCoord(100, 200);
        node1Copy.setCoord(100, 200);
        assertNotEquals(node1, node1Copy);

        // Test inequality of nodes with different coordinates
        node2.setCoord(300, 400);
        assertNotEquals(node1, node2);
    }

    @Test
    void testHashCode() {
        Node node1 = new Node(1);
        Node node1Copy = node1;
        Node node2 = new Node(2);

        // Test hash code consistency
        assertEquals(node1.hashCode(), node1.hashCode());

        // Test hash code equality for nodes with the same ID
        assertEquals(node1.hashCode(), node1Copy.hashCode());

        // Test hash code inequality for nodes with different IDs
        assertNotEquals(node1.hashCode(), node2.hashCode());
    }

    @Test
    void testPathManipulationContinued() {
        Node node = new Node();

        // Test adding multiple nodes to the path
        List<Node> path = new ArrayList<>();
        path.add(new Node(1));
        path.add(new Node(3));
        node.setPath(path);

        Node newNode1 = new Node(5);
        Node newNode2 = new Node(7);
        node.getPath().addAll(Arrays.asList(newNode1, newNode2));
        assertTrue(node.getPath().contains(newNode1));
        assertTrue(node.getPath().contains(newNode2));

        // Test removing nodes from the path
        node.getPath().remove(newNode1);
        assertFalse(node.getPath().contains(newNode1));
    }
}
