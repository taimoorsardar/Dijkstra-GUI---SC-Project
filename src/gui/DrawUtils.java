package gui;

import models.Edge;
import models.Node;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for drawing nodes and edges on a graphical canvas.
 */
public class DrawUtils {
    private static final int DEFAULT_RADIUS = 20;
    private static final int BOLD_EDGE_STROKE = 8;
    private static final int BASE_EDGE_STROKE = 3;
    private static final int BOLD_EDGE_RADIUS = 13;
    private static final int TEXT_OFFSET = 5;

    private Graphics2D g;
    private int radius = DEFAULT_RADIUS;

    private static final Color NODE_COLOR = parseColor("#9C27B0");
    private static final Color HOVER_COLOR = parseColor("#E1BEE7");
    private static final Color EDGE_COLOR = parseColor("#555555");
    private static final Color PATH_COLOR = parseColor("#00BCD4");
    private static final Color TEXT_COLOR = parseColor("#cccccc");

    /**
     * Constructs a DrawUtils instance with the specified Graphics2D object.
     *
     * @param graphics2D The Graphics2D object to use for drawing.
     */
    public DrawUtils(Graphics2D graphics2D) {
        g = graphics2D;
    }

    /**
     * Checks if a MouseEvent is within the bounds of a given Point.
     *
     * @param e The MouseEvent to check.
     * @param p The Point to check against.
     * @return true if the MouseEvent is within the bounds, false otherwise.
     */
    public static boolean isWithinBounds(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();
        int boundX = (int) p.getX();
        int boundY = (int) p.getY();
        return (x <= boundX + DEFAULT_RADIUS && x >= boundX - DEFAULT_RADIUS) &&
               (y <= boundY + DEFAULT_RADIUS && y >= boundY - DEFAULT_RADIUS);
    }

    /**
     * Checks if a MouseEvent is overlapping with a given Point.
     *
     * @param e The MouseEvent to check.
     * @param p The Point to check against.
     * @return true if the MouseEvent is overlapping, false otherwise.
     */
    public static boolean isOverlapping(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();
        int boundX = (int) p.getX();
        int boundY = (int) p.getY();
        return (x <= boundX + 2.5 * DEFAULT_RADIUS && x >= boundX - 2.5 * DEFAULT_RADIUS) &&
               (y <= boundY + 2.5 * DEFAULT_RADIUS && y >= boundY - 2.5 * DEFAULT_RADIUS);
    }

    /**
     * Checks if a MouseEvent is on an edge defined by two nodes.
     *
     * @param e    The MouseEvent to check.
     * @param edge The Edge defining the two nodes.
     * @return true if the MouseEvent is on the edge, false otherwise.
     */
    public static boolean isOnEdge(MouseEvent e, Edge edge) {
        int dist = distToSegment(e.getPoint(), edge.getNodeOne().getCoord(), edge.getNodeTwo().getCoord());
        return dist < DEFAULT_RADIUS / 3;
    }

    /**
     * Parses a color represented as a hex string.
     *
     * @param colorStr The hex string representing the color (e.g., "#RRGGBB").
     * @return The Color object parsed from the hex string. Returns Color.BLACK in case of parsing errors.
     */
    public static Color parseColor(String colorStr) {
        try {
            return new Color(
                    Integer.valueOf(colorStr.substring(1, 3), 16),
                    Integer.valueOf(colorStr.substring(3, 5), 16),
                    Integer.valueOf(colorStr.substring(5, 7), 16));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            // Handle color parsing errors gracefully
            return Color.BLACK;
        }
    }

    /**
     * Draws the weight of an edge at its midpoint.
     *
     * @param edge The edge for which to draw the weight.
     */
    public void drawWeight(Edge edge) {
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        int x = (from.x + to.x) / 2;
        int y = (from.y + to.y) / 2;

        drawCircle(x, y, DEFAULT_RADIUS / 2);
        drawWeightText(String.valueOf(edge.getWeight()), x, y);
    }

    /**
     * Draws a path defined by a list of nodes.
     *
     * @param path The list of nodes defining the path.
     */
    public void drawPath(List<Node> path) {
        if (path != null) {
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < path.size() - 1; i++) {
                edges.add(new Edge(path.get(i), path.get(i + 1)));
            }

            for (Edge edge : edges) {
                drawPath(edge);
            }
        }
    }

    /**
     * Draws a path defined by an edge.
     *
     * @param edge The edge defining the path.
     */
    public void drawPath(Edge edge) {
        g.setColor(PATH_COLOR);
        drawBoldEdge(edge);
    }

    /**
     * Draws a hovered edge with a bold stroke.
     *
     * @param edge The edge to draw in a hovered state.
     */
    public void drawHoveredEdge(Edge edge) {
        g.setColor(HOVER_COLOR);
        drawBoldEdge(edge);
    }

    private void drawBoldEdge(Edge edge) {
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        g.setStroke(new BasicStroke(BOLD_EDGE_STROKE));
        g.drawLine(from.x, from.y, to.x, to.y);

        int x = (from.x + to.x) / 2;
        int y = (from.y + to.y) / 2;
        drawCircle(x, y, BOLD_EDGE_RADIUS);
    }

    /**
     * Draws a regular edge.
     *
     * @param edge The edge to draw.
     */
    public void drawEdge(Edge edge) {
        g.setColor(EDGE_COLOR);
        drawBaseEdge(edge);
        drawWeight(edge);
    }

    private void drawBaseEdge(Edge edge) {
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        g.setStroke(new BasicStroke(BASE_EDGE_STROKE));
        g.drawLine(from.x, from.y, to.x, to.y);
    }

    /**
     * Draws a halo around a node.
     *
     * @param node The node around which to draw the halo.
     */
    public void drawHalo(Node node) {
        g.setColor(HOVER_COLOR);
        radius += TEXT_OFFSET;
        drawCircle(node.getX(), node.getY(), radius);
        radius -= TEXT_OFFSET;
    }

    /**
     * Draws a source node with specific colors.
     *
     * @param node The source node to draw.
     */
    public void drawSourceNode(Node node) {
        drawColoredNode(node, "#0356fc", "#03cffc");
    }

    /**
     * Draws a destination node with specific colors.
     *
     * @param node The destination node to draw.
     */
    public void drawDestinationNode(Node node) {
        drawColoredNode(node, "#F44336", "#FFCDD2");
    }

    private void drawColoredNode(Node node, String nodeColor, String hoverColor) {
        g.setColor(parseColor(nodeColor));
        drawCircle(node.getX(), node.getY(), DEFAULT_RADIUS);

        radius -= TEXT_OFFSET;
        g.setColor(parseColor(hoverColor));
        drawCircle(node.getX(), node.getY(), radius);

        radius += TEXT_OFFSET;
        g.setColor(parseColor(nodeColor));
        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
    }

    /**
     * Draws a regular node with hover effects.
     *
     * @param node The node to draw.
     */
    public void drawNode(Node node) {
        g.setColor(NODE_COLOR);
        drawCircle(node.getX(), node.getY(), DEFAULT_RADIUS);

        radius -= TEXT_OFFSET;
        g.setColor(HOVER_COLOR);
        drawCircle(node.getX(), node.getY(), radius);

        radius += TEXT_OFFSET;
        g.setColor(NODE_COLOR);
        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
    }

    /**
     * Draws text representing the weight of an edge.
     *
     * @param text The text to draw.
     * @param x    The x-coordinate of the text.
     * @param y    The y-coordinate of the text.
     */
    public void drawWeightText(String text, int x, int y) {
        g.setColor(TEXT_COLOR);
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
    }

    /**
     * Draws centered text on the canvas.
     *
     * @param text The text to draw.
     * @param x    The x-coordinate of the text.
     * @param y    The y-coordinate of the text.
     */
    public void drawCentreText(String text, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
    }

    // Calculations

    private void drawCircle(int x, int y, int diameter) {
        g.fillOval(x - diameter, y - diameter, 2 * diameter, 2 * diameter);
    }

    private static int sqr(int x) {
        return x * x;
    }

    private static int dist2(Point v, Point w) {
        return sqr(v.x - w.x) + sqr(v.y - w.y);
    }

    private static int distToSegmentSquared(Point p, Point v, Point w) {
        double l2 = dist2(v, w);
        if (l2 == 0) return dist2(p, v);
        double t = ((p.x - v.x) * (w.x - v.x) + (p.y - v.y) * (w.y - v.y)) / l2;
        if (t < 0) return dist2(p, v);
        if (t > 1) return dist2(p, w);
        return dist2(p, new Point(
                (int) (v.x + t * (w.x - v.x)),
                (int) (v.y + t * (w.y - v.y))
        ));
    }

    private static int distToSegment(Point p, Point v, Point w) {
        return (int) Math.sqrt(distToSegmentSquared(p, v, w));
    }
}
