/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Eric Reinhart, Tsugunobu Miyake
 * Section: 02 11am
 * Date: 10/7/22
 * Time: 11:56 AM
 *
 * Project: csci205_hw
 * Package: org.tsugu_eric
 * Class: TextProcessor
 *
 * Description: Create a Set of all 5-letter words from the dictionary then read through several
 *              novels and filter the text only for valid words. Information about what was read
 *              in can be printed and the list of words is written to a file after processing.
 * ****************************************
 */
package org.tsugu_eric;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Initialize a set of novels, gather all 5-letter words from a dictionary, then
 * filter all valid 5-letter words from each novel and write them to a file
 */
public class TextProcessor {
    public static void main(String[] args) { // TODO: remove main method, temporary testing
        new TextProcessor();

        // TODO: Testing file ideas
        // Test that the number of words in the set after converting TreeMap --> TreeSet is same
        //        int i = 0;
        //        for(String word : getSetOfWords()) {
        //            i++;
        //        }
        //        System.out.println(i);

        //TODO: testing: write a few sentences with each example he uses for 5 letter words & make sure these words are added properly
        // TODO: make getters for the total word counts then compare them

        // TODO: check that correct words are written to file
        // TODO: check exceptions
    }
    //private URL url;
    private TreeMap<String,Integer> wordMap = new TreeMap<>();
    private final Set<String> dictionaryWords = new HashSet<>();
    private int totalWords;
    private int totalGoodWordsDiscarded;
    private int totalGoodWords;
    private int totalUniqueWords;
    private Scanner scnr;
    private boolean processingDictionary = true;
    private final String OUTPUT_FILE = "words.txt";
    private final String webstersDictionary = "https://www.gutenberg.org/cache/epub/29765/pg29765.txt";

    private final String novel1 = "https://www.gutenberg.org/files/1342/1342-h/1342-h.htm"; // Pride and Prejudice
    private final String novel2 = "https://www.gutenberg.org/files/84/84-h/84-h.htm"; // Frankenstein
    private final String novel3 = "https://www.gutenberg.org/files/25344/25344-h/25344-h.htm"; // The Scarlet Letter
    private final String novel4 = "https://www.gutenberg.org/files/11/11-h/11-h.htm"; // Alice in Wonderland
    private final String novel5 = "https://www.gutenberg.org/files/5200/5200-h/5200-h.htm"; // Metamorphosis

    /**
     * Initialize the set of novels, gather all 5-letter words from a dictionary, then
     * filter all valid 5-letter words from each novel and write them to a file
     */
    public TextProcessor() { // removed parameter: URL url
        // Resource: https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction
        //Set<String> novelSet = Set.of(novel1, novel2, novel3, novel4, novel5);
        //TODO: comparing to only his output:
        Set<String> novelSet = Set.of(novel1);

        // Access Webster's dictionary and add all uppercase 5-letter words to the set dictionaryWords
        processTextAtURL(webstersDictionary);

        // Filter each novel for only valid 5-letter words and store the words with their frequencies in wordMap
        for (String novel : novelSet) {
            processTextAtURL(novel);
        }

        // Write the list of words to the intended output file
        writeListOfWords(OUTPUT_FILE);

        // TODO: wordMap will still have words with frequency = 1, be careful when accessing it

        // TODO: Remove printReport call?
        printReport();
    }

    /**
     * Gather all 5-letter words from a dictionary then gather all words in each novel and
     * add only the valid 5-letter words to the wordMap
     * @param url String of the URL for the dictionary or novel
     */
    private void processTextAtURL(String url) {
        try (BufferedInputStream in = new BufferedInputStream((new URL(url)).openStream())) {
            scnr = new Scanner(in);
            //scnr.useDelimiter(" "); // Might need?
            while (scnr.hasNext()) {
                String word = scnr.next();
                // Add a word to the set DictionaryWords if processing the dictionary is true, it is uppercase, and 5 letters long
                if (processingDictionary) {
                    if (word.equals(word.toUpperCase()) && word.length() == 5 ){
                        dictionaryWords.add(word.toLowerCase());
                    }
                }

                else {
                    totalWords++;
                    // Trim word of quotation marks, punctuation, and underscores
                    word = trimWord(word);
                        // TODO: only check for len==5 *AFTER* trim & eliminate hyphen/apostrophe

                    // TODO: Use regex to eliminate words with hyphen / apostrophe? Just 1 regex in other method?
                        // do this before trimming?

                    // Word is in the dictionary, and it has no capital letters
                    if (isWordValid(word) && word.equals(word.toLowerCase()) ) {
                        word = word.toLowerCase();
                        totalGoodWords++;
                        // If the wordMap does not contain the word, add it. Else increase the frequency of the word by 1
                        if (!wordMap.containsKey(word)) {
                            wordMap.put(word, 1);
                            totalUniqueWords++;
                        }
                        else {
                            wordMap.put(word, wordMap.get(word) + 1);
                        }
                    }
                }
            }

            // Only process the dictionary the first time this method is called
            processingDictionary = false;
        } catch (IOException e) {
            System.out.println("URL not found");
        }
    }

    /**
     * Trims word of quotation marks, punctuation, and underscores
     * @param word word to be trimmed
     * @return trimmed word
     */
    private String trimWord(String word) {
        // Strip out starting and ending double quotation marks


        //TODO: replace word with any special character with nothing
        //word = word.replaceAll("[^a-zA-Z0-9]", "");
            // slightly diff results, ^ has 10 more words kept & 1 more unique
        //TODO: open/closing quotes not in correct direction? Does it matter?
        word = word.replaceAll("[“”]","");

        // Trim trailing punctuation marks
        Pattern p = Pattern.compile(".+(\\p{Punct}+)");
        Matcher m = p.matcher(word);
        if (m.matches()) {
           // System.out.println("Trailing punctuation: " + word);
            //System.out.println("Matches: " + m.group(1) + " at index " + m.start(1));
            word = word.substring(0,m.start(1));
            //System.out.println("Trimmed to " + word);
        }

        // TODO: trim out underscores

        return word;
    }


    /**
     * Return true if the dictionary contains the word, false otherwise
     * @param word word to be checked
     * @return true if the dictionary contains the word, false otherwise
     */
    private boolean isWordValid(String word) {
        return dictionaryWords.contains(word.toLowerCase());
    }

    /**
     * Print some interesting stats after processing all the words in the novels
     */
    public void printReport() {
        // TODO: print statements like in his example
        // Maybe make them different? Need to calculate total good words but wrong length
            // the solution is to add every word to dictionary Set
            // If len≠5 of GoodWord then totalGoodWordsDiscarded++
        //totalGoodWordsDiscarded -= totalGoodWords;
        System.out.println("Total number of words processed: " + totalWords);
        //System.out.println("Total good words but wrong length: " + totalGoodWordsDiscarded);
        System.out.println("Total number of words kept: " + totalGoodWords);
        System.out.println("Number of unique words: " + totalUniqueWords);
        System.out.println("Top 20 most frequently occurring words");

        // Sort wordMap then print the 20 most frequently occurring words
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordMap.entrySet());
        sortEntries(entries, (entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));
        for (int i = 0; i < 20; i++) {
            System.out.println(entries.get(i).getKey() + " : " + entries.get(i).getValue());
        }
    }

    /**
     * Sorts the map of words based on frequency
     * @param entries List of Map entries for the words and their counts
     * @param comp the comparator
     */
    public void sortEntries(List<Map.Entry<String, Integer>> entries, Comparator<Map.Entry<String, Integer>> comp){
        entries.sort(comp);
    }

    /**
     * Return a TreeSet with all the words in the wordMap
     * @return TreeSet of the words in the wordMap
     */
    public Set<String> getSetOfWords(){
        // Source: https://www.javadevjournal.com/java/convert-map-map-java/
        return new TreeSet<>(wordMap.keySet());
    }

    /**
     * Write all words that occur more than once to the designated file
     * @param filename name of the file to write the words to
     */
    public void writeListOfWords(String filename) {
        try (PrintStream out = new PrintStream(OUTPUT_FILE)) {
            for (String key : wordMap.keySet()) {
                if (wordMap.get(key) > 1) {
                    out.println(key);
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File does not exist.");
        }
    }
}
