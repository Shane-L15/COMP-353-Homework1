import java.io.FileNotFoundException;
import static util.ImportDBFromFile.importDB;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        importDB("src/main/resources/homework1.txt");
    }
}
