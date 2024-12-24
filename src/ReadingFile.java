import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * Reads Files that will be used in the Kevin Bacon Game
 * Builds a graph for the Kevin Bacon Game
 *
 * @author Lord Charité Igirimbabazi
 * CS10, Fall 2023, PS4
 */
public class ReadingFile {
    /**
     * Reads either "movies" or "actors" files and maps them with their respective IDs
     *
     * @param filePath The path to the file to be read.
     * @return A mapping of IDs to their corresponding names.
     * @throws IOException If an error occurs while reading the file.
     */
    public static Map<String, String> readFile(String filePath) throws IOException {
        // Try-with-resources block to automatically close the file reader
        Map<String, String> actorIdNameMap;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            // Initialize a map to store the ID-to-name mappings
            actorIdNameMap = new HashMap<>();

            String line;
            // Read each line from the file
            while ((line = fileReader.readLine()) != null) {
                // Split the line by the "|" character
                String[] data = line.split("\\|");
                // Map the first part (ID) to the second part (name) and store it in the map
                actorIdNameMap.put(data[0], data[1]);
            }

        } catch (IOException e) {
            System.err.println("An error occurred while reading the files: " + e.getMessage());
            throw e;
        }

        // Return the map containing ID-to-name mappings
        return actorIdNameMap;
    }

    /**
     * Reads movies-actors file and builds a graph with actors and their respective list of movies they played in.
     *
     * @param filePath The path to the movies-actors file.
     * @return A mapping of movies to a set of associated actors.
     * @throws IOException If an error occurs while reading the file.
     */

    public static Map<String, Set<String>> movieActorsBuildMap(String filePath) throws IOException {

        Map<String, Set<String>> movieActorsMap = new HashMap<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] str = line.split("\\|");
                String movie = str[0];
                String actor = str[1];

                Set<String> actors;
                // Check if the movie already exists in the map, and get its associated actors
                if (movieActorsMap.containsKey(movie)) {
                    actors = movieActorsMap.get(movie);
                } else {
                    // If the movie doesn't exist in the map, create a new set to store associated actors
                    actors = new HashSet<>();
                    // Add the movie and its associated actors to the map
                    movieActorsMap.put(movie, actors);
                }
                // Add the current actor to the set of associated actors for the movie
                actors.add(actor);
            }
        } catch (IOException e) {
        System.err.println("An error occurred while reading the files: " + e.getMessage());
    }

        // Return the map containing movies and their associated actors
        return movieActorsMap;
    }

    public static Graph<String, Set<String>> buildActorsGraph(Map<String, String> actorMap, Map<String, String> movieMap, Map<String, Set<String>> movieActorsMap) {
        Graph<String, Set<String>> actorsGraph = new AdjacencyMapGraph<>();

        // Iterate through each actor
        for (String actor : actorMap.keySet()) {
            // Add each actor as a vertex in the graph
            actorsGraph.insertVertex(actorMap.get(actor));
        }

        // Iterate through each movie
        for (String movie : movieActorsMap.keySet()) {
            Set<String> actorsInMovie = movieActorsMap.get(movie);

            // Iterate through each pair of actors in the same movie
            for (String actor : actorsInMovie) {
                for (String otherActor : actorsInMovie) {
                    // Make sure we don't connect an actor to themselves
                    if (!actor.equals(otherActor) && actorMap.containsKey(actor) && actorMap.containsKey(otherActor)) {
                        // Get the names of the actors
                        String actorName = actorMap.get(actor);
                        String otherActorName = actorMap.get(otherActor);

                        if (actorsGraph.hasEdge(actorName, otherActorName)){
                            Set<String> commonMovies = actorsGraph.getLabel(actorName, otherActorName);
                            commonMovies.add(movieMap.get(movie));
                        }
                        else {
                            Set<String> commonMovies = new HashSet<>();

                            // Add the current movie to the set of common movies
                            commonMovies.add(movieMap.get(movie));

                            // Create an undirected edge between the two actors with the set of common movies as the edge label
                            actorsGraph.insertUndirected(actorName, otherActorName, commonMovies);
                        }

                    }
                }
            }
        }

        // Return the graph representing actors and their connections
        return actorsGraph;
    }
}
