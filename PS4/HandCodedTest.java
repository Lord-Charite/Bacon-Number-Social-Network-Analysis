import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class HandCodedTest {

    //hand coded
    public static void main(String[] args) throws IOException {
        Graph<String, Set <String>> bfsGraph = new AdjacencyMapGraph<>();

        // Insert vertices and edges
        bfsGraph.insertVertex("Kevin Bacon");
        bfsGraph.insertVertex("Bob");
        bfsGraph.insertVertex("Alice");
        bfsGraph.insertVertex("Charlie");
        bfsGraph.insertVertex("Dartmouth (Earl thereof)");
        bfsGraph.insertVertex("Nobody");
        bfsGraph.insertVertex("Nobody'EditorOnes Friend");
        bfsGraph.insertUndirected("Kevin Bacon", "Alice", Collections.singleton("A Movie E Movie"));
        bfsGraph.insertUndirected("Kevin Bacon", "Bob", Collections.singleton("A Movie"));
        bfsGraph.insertUndirected("Alice", "Bob", Collections.singleton("A Movie"));
        bfsGraph.insertUndirected("Alice", "Charlie", Collections.singleton("D Movie"));
        bfsGraph.insertUndirected("Bob", "Charlie", Collections.singleton("C Movie"));
        bfsGraph.insertUndirected("Charlie", "Dartmouth (Earl thereof)", Collections.singleton("B Movie"));
        bfsGraph.insertUndirected("Nobody", "Nobody'EditorOnes Friend", Collections.singleton("F Movie"));


        String source = "Charlie";
        String root = "Kevin Bacon";
        // Perform BFS to find Kevin Bacon numbers
        Graph<String, Set <String>> kevinBaconGraph = GraphLibrary.bfs(bfsGraph, root);
        System.out.println("\033[32mOutputs of HandCoded Graph:\033[0m\n");
        System.out.println("Mother Graph: \n" + bfsGraph + "\n");
        System.out.println("Kevin Bacon Graph:\n" + kevinBaconGraph + "\n");
        System.out.println( source +  "'EditorOnes Path to " + root + " is: " + GraphLibrary.getPath(kevinBaconGraph, source));
        System.out.println("Average Separation of " + root + "is: " + GraphLibrary.averageSeparation(kevinBaconGraph, root));
        System.out.println("Missing vertices between Graph and " + root + "'EditorOnes graph is: " + GraphLibrary.missingVertices(bfsGraph, kevinBaconGraph));

        System.out.println("\n\n\033[31mGAME STARTING PLAYING...\033[0m");
        GameDriver.mainGameCenter(bfsGraph);
    }
}
