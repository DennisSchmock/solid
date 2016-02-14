package model;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class WordPairsLogic implements WordPairsFacade {
    private final HashMap<String, String> wordCollection = new HashMap<>();

    public HashMap<String, String> getWordCollection() {
        return wordCollection;
    }

    


    @Override
    public void clear() {
        wordCollection.clear();
    }

    @Override
    public int size() {
        return wordCollection.size();
    }

    @Override
    public boolean checkGuess(String question, String answer) {
        String word = wordCollection.get(question);
        if (word!=null){
            return word.equalsIgnoreCase(answer);
        }
        return false;
    }

    @Override
    public String lookup(String question) {
        return wordCollection.get(question);
    }

    @Override
    public String getRandomQuestion() {
        if ( wordCollection.isEmpty() )
            return null;
        Set s = wordCollection.keySet();
        Object[] sa = s.toArray();        
        Random r = new Random();
        return (String) sa[ r.nextInt( sa.length ) ];
    }

    @Override
    public void add(String question, String answer) {
        wordCollection.put(question, answer);
    }

    @Override
    public boolean save(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName);) {
            for (String key : wordCollection.keySet())//s = getNextQuestion()) != null) {
                writer.println(key + "," + wordCollection.get(key));
            return true;
        } catch (FileNotFoundException ex) {
            return false;
        }
    }

    @Override
    public boolean load(String filename) {
        String fileName = filename;
        String line;
        try (FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                String question = parts[0];
                String answer = parts[1];
                wordCollection.put(question, answer);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
