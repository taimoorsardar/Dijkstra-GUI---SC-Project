
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.MainWindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing strategy for MainWindowTest:
 *
 * 1. Initialization Testing:
 *    - Test the initialization of MainWindow.
 *    - Ensure that the layout and components are set up correctly.
 *
 * 2. Button Actions Testing:
 *    - Test each button's action separately:
 *      - Running the Dijkstra algorithm.
 *      - Resetting the graph.
 *      - Displaying information.
 *
 * 3. Graph Manipulation Testing:
 *    - Test adding nodes and edges to the graph through the GUI.
 *    - Verify that the graph is updated correctly when actions like running Dijkstra are performed.
 *
 * 4. Exception Handling Testing:
 *    - Test handling of exceptions, especially in the runDijkstraAlgorithm method.
 *    - Ensure that appropriate messages are displayed when exceptions occur.
 *
 * 5. Integration Testing:
 *    - Test the integration of the MainWindow with the Graph and GraphPanel classes.
 *    - Verify that actions performed on the GUI components reflect the changes in the underlying data structures.
 *
 * 6. UI Interaction Testing:
 *    - Use tools like FEST or similar libraries to simulate user interactions with the GUI components.
 *    - Verify that the UI behaves as expected and updates the underlying data structures accordingly.
 *
 * 7. Edge Case Testing:
 *    - Test with an empty graph.
 *    - Test with a graph containing a single node.
 *    - Test with various combinations of nodes and edges to cover different scenarios.
 *
 * 8. Performance Testing:
 *    - If applicable, perform tests with a large number of nodes and edges to ensure the application's performance.
 */
class MainWindowTest {

    private MainWindow mainWindow;

    @BeforeEach
    void setUp() {
        // Initialize MainWindow before each test
        mainWindow = new MainWindow();
    }

    @Test
    void initializationTest() {
        // Test the initialization of MainWindow
        // Ensure that the layout and components are set up correctly
        assertNotNull(mainWindow);
        assertNotNull(mainWindow.getComponent(0)); // Check if graph panel is present
        assertNotNull(mainWindow.getComponent(1)); // Check if top panel is present
        assertNotNull(mainWindow.getComponent(2)); // Check if button panel is present
    }

    @Test
    void runDijkstraAlgorithmTest() {
        // Test the runDijkstraAlgorithm method
        // Verify that the graph is updated as expected after running Dijkstra
        // Check the state of the graph panel and any other relevant components

        // Simulate the click on the "run" button
        JButton runButton = (JButton) ((JPanel) mainWindow.getComponent(2)).getComponent(1);
        ActionEvent event = new ActionEvent(runButton, ActionEvent.ACTION_PERFORMED, "run");

        // Before running Dijkstra, ensure the graph is empty
        assertTrue(mainWindow.graph.getNodes().isEmpty());

        // Add nodes to the graph
        mainWindow.graph.addNode(new Point(50, 50));
        mainWindow.graph.addNode(new Point(150, 150));
        mainWindow.graph.addNode(new Point(250, 250));

        // Simulate running Dijkstra
        mainWindow.runDijkstraAlgorithm(event);

        // After running Dijkstra, verify that the graph is updated
        assertFalse(mainWindow.graph.getNodes().isEmpty());
        // Adjust this assertion based on the actual behavior of your application
        // For example, you might want to check if the graph panel is updated in some way
    }

    @Test
    void resetGraphTest() {
        // Test the resetGraph method
        // Verify that the graph is reset to its initial state
        // Check the state of the graph panel and any other relevant components

        // Simulate the click on the "reset" button
        JButton resetButton = (JButton) ((JPanel) mainWindow.getComponent(2)).getComponent(0);
        ActionEvent event = new ActionEvent(resetButton, ActionEvent.ACTION_PERFORMED, "reset");

        // Add nodes to the graph
        mainWindow.graph.addNode(new Point(50, 50));
        mainWindow.graph.addNode(new Point(150, 150));
        mainWindow.graph.addNode(new Point(250, 250));

        // Perform reset
        mainWindow.resetGraph();

        // After reset, verify that the graph is empty
        assertTrue(mainWindow.graph.getNodes().isEmpty());
        // Adjust this assertion based on the actual behavior of your application
        // For example, you might want to check if the graph panel is updated in some way
    }
    @Test
    void createButtonTest() {
        // Test the createButton method
        // Ensure that a button is created with the specified icon

        // Create a MainWindow instance
        MainWindow mainWindow = new MainWindow();

        // Create a button
        JButton button = mainWindow.createButton("run", e -> {});

        // Verify that the button is not null and has the correct icon
        assertNotNull(button);
        assertNotNull(button.getIcon());
    }
    
    @Test
    void setupIconTest() {
        // Test the setupIcon method
        // Ensure that the icon is set up for the button without errors

        // Create a MainWindow instance
        MainWindow mainWindow1 = new MainWindow();

        // Create a button
        JButton button = new JButton();

        // Set up the icon
        assertDoesNotThrow(() -> mainWindow1.setupIcon(button, "run"));
    }
}
