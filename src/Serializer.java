import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by alhanger on 10/14/15.
 */
public class Serializer {
    public static void main(String[] args) {
        Answers answers = loadAnswers();

        if (answers != null) {
            System.out.println("Would you like to revise your survey? [y/n]");
            Scanner scanner = new Scanner(System.in);
            String prompt = scanner.nextLine();
            if (prompt.equals("y")) {
                survey(answers);
            }
            else {
                System.out.println("Thanks for stopping by.");
            }
        }
        else {
            answers = new Answers();
            survey(answers);
        }

        saveAnswers(answers);

    }

    static void survey(Answers answers) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("What album did you listen to?");
        String albumAnswer = scanner.nextLine();
        answers.album = albumAnswer;

        System.out.println(String.format("What artist was %s by?", answers.album));
        String artistAnswer = scanner.nextLine();
        answers.artist = artistAnswer;

        System.out.println(String.format("What genre would you consider %s by %s to be?", answers.album, answers.artist));
        String genreAnswer = scanner.nextLine();
        answers.genre = genreAnswer;

        System.out.println(String.format("What was your favorite track on %s?", answers.album));
        String favTrackAnswer = scanner.nextLine();
        answers.favTrack = favTrackAnswer;

        System.out.println(String.format("What was your least favorite track on %s?", answers.album));
        String leastFavTrack = scanner.nextLine();
        answers.leastFav = leastFavTrack;

    }

    static void saveAnswers(Answers answers) {
        File f = new File("answers.json");
        JsonSerializer serializer = new JsonSerializer();
        String contentToSave = serializer.serialize(answers);

        try {
            FileWriter fw = new FileWriter(f);
            fw.write(contentToSave);
            fw.close();
        } catch (Exception e) {
            System.out.println("Unable to save answers.");
        }
    }

    static Answers loadAnswers() {
        try {
            File f = new File("answers.json");
            FileReader fr = new FileReader(f);
            int fileSize = (int) f.length();
            char[] contents = new char[fileSize];
            fr.read(contents);
            String fileContents = new String(contents);
            System.out.println(fileContents);

            JsonParser parser = new JsonParser();
            return parser.parse(fileContents, Answers.class);
        } catch (Exception e) {
            System.out.println("Parsing failed.");
            return null;
        }
    }
}
