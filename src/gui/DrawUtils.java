package gui;

import models.Edge;
import models.Node;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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

    public DrawUtils(Graphics2D graphics2D) {
        g = graphics2D;
    }

    public static boolean isWithinBounds(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();
        int boundX = (int) p.getX();
        int boundY = (int) p.getY();
        return (x <= boundX + DEFAULT_RADIUS && x >= boundX - DEFAULT_RADIUS) &&
               (y <= boundY + DEFAULT_RADIUS && y >= boundY - DEFAULT_RADIUS);
    }

    public static boolean isOverlapping(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();
        int boundX = (int) p.getX();
        int boundY = (int) p.getY();
        return (x <= boundX + 2.5 * DEFAULT_RADIUS && x >= boundX - 2.5 * DEFAULT_RADIUS) &&
               (y <= boundY + 2.5 * DEFAULT_RADIUS && y >= boundY - 2.5 * DEFAULT_RADIUS);
    }

    public static boolean isOnEdge(MouseEvent e, Edge edge) {
        int dist = distToSegment(e.getPoint(), edge.getNodeOne().getCoord(), edge.getNodeTwo().getCoord());
        return dist < DEFAULT_RADIUS / 3;
    }

    public static Color parseColor(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public void drawWeight(Edge edge) {
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        int x = (from.x + to.x) / 2;
        int y = (from.y + to.y) / 2;

        drawCircle(x, y, DEFAULT_RADIUS / 2);
        drawWeightText(String.valueOf(edge.getWeight()), x, y);
    }

    public void drawPath(List<Node> path) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            edges.add(new Edge(path.get(i), path.get(i + 1)));
        }

        for (Edge edge : edges) {
            drawPath(edge);
        }
    }

    public void drawPath(Edge edge) {
        g.setColor(PATH_COLOR);
        drawBoldEdge(edge);
    }

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

    public void drawHalo(Node node) {
        g.setColor(HOVER_COLOR);
        radius += TEXT_OFFSET;
        drawCircle(node.getX(), node.getY(), radius);
        radius -= TEXT_OFFSET;
    }

    public void drawSourceNode(Node node) {
        g.setColor(NODE_COLOR);
        drawCircle(node.getX(), node.getY(), DEFAULT_RADIUS);

        radius -= TEXT_OFFSET;
        g.setColor(HOVER_COLOR);
        drawCircle(node.getX(), node.getY(), radius);

        radius += TEXT_OFFSET;
        g.setColor(NODE_COLOR);
        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
    }

    public void drawDestinationNode(Node node) {
        g.setColor(parseColor("#F44336"));
        drawCircle(node.getX(), node.getY(), DEFAULT_RADIUS);

        radius -= TEXT_OFFSET;
        g.setColor(parseColor("#FFCDD2"));
        drawCircle(node.getX(), node.getY(), radius);

        radius += TEXT_OFFSET;
        g.setColor(parseColor("#F44336"));
        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
    }

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

    public void drawWeightText(String text, int x, int y) {
        g.setColor(TEXT_COLOR);
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
    }

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
