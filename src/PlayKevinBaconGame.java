/**
 * Implements the command for the Kevin Bacon Game
 *
 * @author Lord Charité Igirimbabazi
 * CS10, Fall 2023, PS4
 */

import java.util.*;

public class PlayKevinBaconGame {

    Graph<String, Set<String>> centeredGraph;
    String currentCenter;

    /**
     * List the top <#> centers of the universe, sorted by average separation.
     *
     * @param actors The graph containing actors and their connections.
     * @param num    The number of centers to list (positive for top, negative for bottom).
     */
    public void topCenters(Graph<String, Set<String>> actors, int num) {
        Map<String, Double> centersMap = new HashMap<>();
        PriorityQueue<String> centersQueue;

        if (num < 0) { // Sort them in descending order
            centersQueue = new PriorityQueue<>((str1, str2) -> Double.compare(centersMap.get(str2), centersMap.get(str1)));
        } else { // Sort them in ascending order
            centersQueue = new PriorityQueue<>(Comparator.comparingDouble(centersMap::get));
        }

        for (String actor : actors.vertices()) {
            // Calculate the average separation for each actor
            Graph<String, Set<String>> graph = GraphLibrary.bfs(actors, actor);
            double avgSeparation = GraphLibrary.averageSeparation(graph, actor);
            centersMap.put(actor, avgSeparation);
            centersQueue.add(actor);
        }

        ArrayList<String> topList = new ArrayList<>();
        int i = 0;
        while (i < Math.abs(num) && !centersQueue.isEmpty()) {
            topList.add(centersQueue.remove());
            i++;
        }

        System.out.println(">>> The top " + num + " actors sorted by average separation are the following: " + topList);
    }

    /**
     * Find the path from a given actor to the current center of the universe.
     *
     * @param actors The graph containing actors and their connections.
     * @param input  The name of the actor for which the path is to be found.
     */
    public void findShortestPath(Graph<String, Set<String>> actors, String input) {
        if (actors == null) {
            System.out.println("Add the center of the universe first using the command u");
            return;
        }
        if (!actors.hasVertex(input)) {
            System.out.println(">>> " + input + " is not found in the actors graph.");
            System.out.println(">>> Check your input and center(!!)");
        } else {
            // Find the shortest path from the given actor to the current center
            List<String> shortestPath = GraphLibrary.getPath(actors, input);
            System.out.println(input + "'EditorOnes number is " + (shortestPath.size() - 1));
            System.out.println("The path is " + shortestPath);
        }
    }

    /**
     * List actors with infinite separation from the current center.
     *
     * @param actorsGraph   The graph containing actors and their connections.
     * @param currentCenter The current center of the universe.
     */
    public void findActorsWithInfiniteSeparation(Graph<String, Set<String>> actorsGraph, String currentCenter) {
        // Create a local graph variable with the current center as the center
        Graph<String, Set<String>> centerGraph = GraphLibrary.bfs(actorsGraph, currentCenter);
        if (!actorsGraph.hasVertex(currentCenter)) {
            System.out.println(">>> " + currentCenter + " is not found in the actors graph.");
            return;
        }
        // Find actors with infinite separation from the current center

        Set<String> infiniteSeparationActors = GraphLibrary.missingVertices(actorsGraph, centerGraph);
        System.out.println(">>> Actors with infinite separation from the current center (" + currentCenter + "):");

        this.printActors(infiniteSeparationActors);

    }

    public void printActors(Collection<String> actors) {
        int current = 1;

        for (String actor : actors) {
            System.out.println(current++ + ": " + actor);
        }
    }

    /**
     * List actors sorted by non-infinite separation from the current center, with separation between low and high.
     *
     * @param currentTree The graph containing actors and their connections.
     * @param currCenter  The current center'EditorOnes name.
     * @param low         The lower bound of the separation range.
     * @param high        The upper bound of the separation range.
     */
    public void nonInfiniteSeparation(Graph<String, Set<String>> currentTree, String currCenter, double low, double high) {
        if (currentTree == null) {
            System.out.println("Initialize center using command \"u\"");
            return;
        }

        if (!currentTree.hasVertex(currCenter)) {
            System.out.println(">>> CurrentCenter " + currCenter + " is not found in the graph. Press u to change center");
            return;
        }

        Map<String, Integer> separations = new HashMap<>();

        for (String actor : currentTree.vertices()) {
            List<String> actorPath = GraphLibrary.getPath(centeredGraph, actor);

            int separation = actorPath.size() - 1;

            if (separation >= low && separation <= high) {
                separations.put(actor, separation);
            }
        }

        List<String> actorsList = new ArrayList<>(separations.keySet());
        actorsList.sort((actor1, actor2) -> {
            int separation1 = separations.get(actor1);
            int separation2 = separations.get(actor2);
            return Integer.compare(separation1, separation2);
        });

        System.out.println(" >>> List of actors with separation between " + low + " and " + high + " from the current center \"" + currCenter + "\" :");

        this.printActors(actorsList);
    }

    /**
     * List actors sorted by degree, with degree between low and high.
     *
     * @param actors The graph containing actors and their connections.
     * @param low    The lower bound of the degree range.
     * @param high   The upper bound of the degree range.
     */
    public void degreeSort(Graph<String, Set<String>> actors, int low, int high) {
        List<String> listOfVertices = new ArrayList<>((Collection<String>) actors.vertices());

        // Filter and keep only actors within the specified degree range
        listOfVertices.removeIf(actor -> {
            int inDegree = actors.inDegree(actor);
            return inDegree < low || inDegree > high;
        });

        // Sort the remaining actors based on inDegree (degree) in ascending order
        listOfVertices.sort((actor1, actor2) -> {
            int inDegree1 = actors.inDegree(actor1);
            int inDegree2 = actors.inDegree(actor2);
            return Integer.compare(inDegree1, inDegree2);
        });

        System.out.println("Vertices sorted with respect to the number of neighbors within the specified range (" + low + " to " + high + "):");
        printActors(listOfVertices);
    }

    /**
     * Make the given actor the center of the universe.
     *
     * @param actorsGraph The graph containing actors and their connections.
     * @param newCenter   The name of the actor to be set as the center.
     */
    public void setCenter(Graph<String, Set<String>> actorsGraph, String newCenter) {

        if (!actorsGraph.hasVertex(newCenter)) {
            System.out.println(">>>Center NOT CHANGED. Input: "+ newCenter + " is not found in the actors graph.");
            System.out.println(">>>Follow commands to update the center to a valid vertex/center(!!)");
            return;
        }

        // Compute the subgraph centered around the new actor
        this.currentCenter = newCenter;
        this.centeredGraph = GraphLibrary.bfs(actorsGraph, newCenter);

        // Calculate the number of actors connected to the new center
        int connectedActors = this.centeredGraph.numVertices();

        // Calculate the average separation
        double averageSeparation = GraphLibrary.averageSeparation(this.centeredGraph, newCenter);

        System.out.println(newCenter + " is now the center of the universe, connected to " + connectedActors + "/" +
                actorsGraph.numVertices() + " actors with an average separation of " + averageSeparation);



    }

    /**
     * Initialize the game and provide available commands.
     *
     * @param actorsGraph The graph containing actors and their connections.
     */
    public void startGame(Graph<String, Set<String>> actorsGraph) {
        System.out.println("\n***Kevin Bacon Game >>>\n");
        System.out.println("Default Center: Kevin Bacon :)");
        setCenter(actorsGraph, "Kevin Bacon");
        System.out.println("Press \"u\" if you'd like to change the center!");
        GameDriver.printUsage();
    }
}
