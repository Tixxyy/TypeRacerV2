import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadSentences {
    public static ArrayList<String> getSentenceArray(File file) {


        var sentences = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(file,"unicode"); // utf-8 causes weird padding issues at the beginning of the the txt.
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine().trim().replaceAll("\\s+", " ");
                if (!nextLine.isEmpty()) sentences.add(nextLine);
            }
            scanner.close();
            return sentences;
        }
        catch (FileNotFoundException e){ // sloppy
            return sentences;
        }
    }
}