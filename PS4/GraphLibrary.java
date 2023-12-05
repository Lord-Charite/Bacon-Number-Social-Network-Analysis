import java.util.*;
/**
 * Implements the GraphLibrary with BFS-related methods  for the Kevin Bacon Game
 *
 * @author Lord Charité Igirimbabazi
 * CS10, Fall 2023, PS4
 */
public class GraphLibrary<V, E> {

    /**
     * Perform BFS to create a shortest path tree from a source vertex in the graph.
     *
     * @param g      The graph in which to perform BFS.
     * @param source The source vertex to start BFS from.
     * @return A new graph representing the shortest path tree.
     */
    public static <V, E> Graph<V, E> bfs(Graph<V, E> g, V source) {
        if (g == null) {
            System.err.println("Empty Graph in bfs");
            return new AdjacencyMapGraph<>(); // Return an empty graph
        }

        if (!g.hasVertex(source)) {
            System.err.println("Vertex source not found in bfs");
            return new AdjacencyMapGraph<>(); // Return an empty graph
        }

        Graph<V, E> graphPath = new AdjacencyMapGraph<>(); // Initialize the path tree
        Queue<V> queue = new LinkedList<>(); // Create a queue for BFS traversal
        Set<V> visited = new HashSet<>(); // Track visited vertices

        queue.add(source); // Start with the source vertex
        visited.add(source);
        graphPath.insertVertex(source);

        while (!queue.isEmpty()) {
            V current = queue.remove(); // Process the current vertex
            for (V v : g.outNeighbors(current)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                    graphPath.insertVertex(v); // Add vertex to path tree
                    graphPath.insertDirected(v, current, g.getLabel(current, v)); // Add edge to path tree
                }
            }
        }

        return graphPath; // Return the path tree
    }

    /**
     * Find the path from a vertex in the shortest path tree back to the root.
     *
     * @param tree The shortest path tree.
     * @param v    The vertex for which to find the path.
     * @return A list of vertices representing the path from v to the root.
     */
    public static <V, E> List<V> getPath(Graph<V, E> tree, V v) {
        List<V> path = new ArrayList<>(); // Initialize the path list

        if (tree == null) {
            System.out.println("Sorry Empty Graph in getPath");
            return path;
        }

        if (!tree.hasVertex(v)) {
            System.out.println("Sorry Vertex not Found while getting Path");
            return path;
        }

        V current = v;
        path.add(current); // Add the current vertex to the path

        while (tree.outNeighbors(current).iterator().hasNext()) {
            current = tree.outNeighbors(current).iterator().next();
            path.add(current); // Add the next vertex to the path
        }

        return path; // Return the path from v to the root
    }

    /**
     * Determine vertices in the main graph that are not in the subgraph.
     *
     * @param graph    The main graph.
     * @param subgraph The subgraph to compare with.
     * @return A set of vertices in the main graph not present in the subgraph.
     */
    public static <V, E> Set<V> missingVertices(Graph<V, E> graph, Graph<V, E> subgraph) {
        Set<V> missingVertices = new HashSet<V>();

        if (graph == null || subgraph == null) {
            return missingVertices;
        }

        for (V vertex : graph.vertices()) {
            // If the vertex is not present in the subgraph, add it to the missing vertices set
            if (!subgraph.hasVertex(vertex)) {
                missingVertices.add(vertex);
            }
        }

        return missingVertices; // Return the set of missing vertices
    }

    /**
     * Calculate the average separation from the root in the shortest path tree.
     *
     * @param tree The shortest path tree.
     * @param root The root vertex.
     * @return The average separation in the tree from the root vertex.
     */
    public static <V, E> double averageSeparation(Graph<V, E> tree, V root) {
        if (tree == null || !tree.hasVertex(root)) {
            return 0;
        }
        return averageSeparationRecursive(tree, root, 0) / (tree.numVertices() - 1); // excluding vertex of reference
    }

    /**
     * A private recursive helper method to calculate average separation.
     *
     * @param tree    The shortest path tree.
     * @param current The current vertex being processed.
     * @param depth   The depth of the current vertex in the tree.
     * @return The updated total steps.
     */
    private static <V, E> double averageSeparationRecursive(Graph<V, E> tree, V current, double depth) {
        if (tree == null || !tree.hasVertex(current)) {
            return 0;
        }
        double res = depth;
        for (V vertex : tree.inNeighbors(current)) {
            res += averageSeparationRecursive(tree, vertex, depth + 1);
        }
        return res;
    }

}










