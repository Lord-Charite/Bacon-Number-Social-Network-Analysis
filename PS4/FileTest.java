import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class FileTest {

    public static void main(String[] args) throws IOException {
        try {
            // Read actor and movie data from files
            Map<String, String> actorMap = ReadingFile.readFile("PS4/actorsTest.txt");
            Map<String, String> movieMap = ReadingFile.readFile("PS4/moviesTest.txt");
            Map<String, Set<String>> movieActorsMap = ReadingFile.movieActorsBuildMap("PS4/movie-actorsTest.txt");

            // Build the actors graph
            Graph<String, Set<String>> actorsGraph = ReadingFile.buildActorsGraph(actorMap, movieMap, movieActorsMap);

            System.out.println("\033[32mOutputs of FileCoded Graph:\033[0m\n");
            System.out.println("Mother Graph:\n" +actorsGraph + "\n");
            System.out.println("Kevin Bacon Graph:\n" + GraphLibrary.bfs(actorsGraph,"Kevin Bacon") + "\n");
            System.out.println("\n\n\033[31mGAME STARTING PLAYING...\033[0m");
            GameDriver.mainGameCenter(actorsGraph);
    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
