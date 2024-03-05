import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {

            File inputFile = new File("src\\input.txt");
            Scanner scanner = new Scanner(inputFile);
            ArrayList<PokerHand> hands = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                PokerHand hand = new PokerHand(line);
                hands.add(hand);
            }
            scanner.close();


            Collections.sort(hands);


            File outputFile = new File("src\\output.txt");
            PrintWriter writer = new PrintWriter(outputFile);
            for (PokerHand hand : hands) {
                writer.println(hand.toString());
            }
            writer.close();

            System.out.println("Tri des mains de poker effectué avec succès.");
        } catch (FileNotFoundException e) {
            System.out.println("Erreur : Fichier introuvable.");
            e.printStackTrace();
        }
    }
}
