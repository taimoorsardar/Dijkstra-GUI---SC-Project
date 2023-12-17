package algo;

import models.Edge;
import models.Graph;
import models.Node;

import java.util.*;

/**
 * The DijkstraAlgorithm class implements the Dijkstra algorithm to find the shortest path
 * between two nodes in a graph.
 */
public class DijkstraAlgorithm {

    private boolean safe = false;
    private String message = null;

    private Graph graph;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distances;

    private PriorityQueue<Node> unvisited;
    private HashSet<Node> visited;

    /**
     * A comparator for comparing nodes based on their distances during priority queue operations.
     */
    public class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return distances.get(node1) - distances.get(node2);
        }
    }

    /**
     * Constructs a DijkstraAlgorithm instance with the specified graph.
     *
     * @param graph The graph on which the Dijkstra algorithm will be applied.
     */
    public DijkstraAlgorithm(Graph graph) {
        this.graph = graph;
        predecessors = new HashMap<>();
        distances = new HashMap<>();

        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        visited = new HashSet<>();

        safe = evaluate();
    }

    /**
     * Evaluates the graph's validity for running the Dijkstra algorithm.
     *
     * @return True if the graph is valid, false otherwise.
     */
    public boolean evaluate() {
        if (graph.getSource() == null) {
            message = "Source must be present in the graph";
            return false;
        }

        if (graph.getDestination() == null) {
            message = "Destination must be present in the graph";
            return false;
        }

        for (Node node : graph.getNodes()) {
            if (!graph.isNodeReachable(node)) {
                message = "Graph contains unreachable nodes";
                return false;
            }
        }

        return true;
    }

    /**
     * Runs the Dijkstra algorithm on the graph, finding the shortest path.
     *
     * @throws IllegalStateException If the graph is not valid (as determined by the {@code evaluate} method).
     */
    public void run() throws IllegalStateException {
        if (!safe) {
            throw new IllegalStateException(message);
        }

        unvisited = new PriorityQueue<>(graph.getNodes().size(), new NodeComparator());

        Node source = graph.getSource();
        distances.put(source, 0);
        visited.add(source);

        for (Edge neighbor : getNeighbors(source)) {
            Node adjacent = getAdjacent(neighbor, source);
            if (adjacent == null)
                continue;

            distances.put(adjacent, neighbor.getWeight());
            predecessors.put(adjacent, source);
            unvisited.add(adjacent);
        }

        while (!unvisited.isEmpty()) {
            Node current = unvisited.poll();

            updateDistance(current);

            unvisited.remove(current);
            visited.add(current);
        }

        for (Node node : graph.getNodes()) {
            node.setPath(getPath(node));
        }

        graph.setSolved(true);
    }

    /**
     * Updates the distance values for neighboring nodes of the given node.
     *
     * @param node The current node.
     */
    public void updateDistance(Node node) {
        int distance = distances.get(node);

        for (Edge neighbor : getNeighbors(node)) {
            Node adjacent = getAdjacent(neighbor, node);
            if (visited.contains(adjacent))
                continue;

            int current_dist = distances.get(adjacent);
            int new_dist = distance + neighbor.getWeight();

            if (new_dist < current_dist) {
                distances.put(adjacent, new_dist);
                predecessors.put(adjacent, node);
                unvisited.add(adjacent);
            }
        }
    }

    /**
     * Returns the adjacent node of a given edge and node.
     *
     * @param edge The edge to check.
     * @param node The reference node.
     * @return The adjacent node, or {@code null} if none.
     */
    public Node getAdjacent(Edge edge, Node node) {
        if (edge.getNodeOne() != node && edge.getNodeTwo() != node)
            return null;

        return node == edge.getNodeTwo() ? edge.getNodeOne() : edge.getNodeTwo();
    }

    /**
     * Returns the list of edges connected to the given node.
     *
     * @param node The node for which neighbors are retrieved.
     * @return A list of edges connected to the node.
     */
    public List<Edge> getNeighbors(Node node) {
        List<Edge> neighbors = new ArrayList<>();

        for (Edge edge : graph.getEdges()) {
            if (edge.getNodeOne() == node || edge.getNodeTwo() == node)
                neighbors.add(edge);
        }

        return neighbors;
    }

    /**
     * Returns the distance to the destination node.
     *
     * @return The distance to the destination node.
     */
    public Integer getDestinationDistance() {
        return distances.get(graph.getDestination());
    }

    /**
     * Returns the distance to the specified node.
     *
     * @param node The node for which distance is retrieved.
     * @return The distance to the specified node.
     */
    public Integer getDistance(Node node) {
        return distances.get(node);
    }

    /**
     * Returns the shortest path to the destination node.
     *
     * @return A list of nodes representing the shortest path to the destination.
     */
    public List<Node> getDestinationPath() {
        return getPath(graph.getDestination());
    }

    /**
     * Returns the path from the source to the specified node.
     *
     * @param node The node for which the path is retrieved.
     * @return A list of nodes representing the path from the source to the specified node.
     */
    public List<Node> getPath(Node node) {
        List<Node> path = new ArrayList<>();

        Node current = node;
        path.add(current);
        while (current != graph.getSource()) {
            current = predecessors.get(current);
            path.add(current);
        }

        Collections.reverse(path);

        return path;
    }
}
