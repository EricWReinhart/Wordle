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

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gather all 5-letter words from a master dictionary, then
 * filter all valid 5-letter words from each novel that are in the dictionary
 * and write them to a file.
 */
public class TextProcessor {
    /** TreeMap to store valid words and their frequencies */
    private Map<String,Integer> wordMap;

    /** HashSet to store all 5-letter words in the dictionary */
    private final Set<String> dictionaryWords;

    /** Total number of words processed*/
    private int totalWords;

    /** Total number of valid words that were not 5-letters long */
    private int totalGoodWordsDiscarded;

    /** Total number of good words kept in the final TreeMap */
    private int totalGoodWordsKept;

    /** Total number of unique words in the final TreeMap */
    private int totalUniqueWords;

    /** Scanner object */
    private Scanner scnr;

    /**
     * Initialize the set of novels, gather all 5-letter words from a dictionary, then
     * filter all valid 5-letter words from each novel and write them to a file
     */
    public TextProcessor(URL masterDictURL) {
        this.wordMap = new TreeMap<>();
        this.totalWords = 0;
        this.totalGoodWordsKept = 0;
        this.totalUniqueWords = 0;
        this.totalGoodWordsDiscarded = 0;
        dictionaryWords = new TreeSet<>();

        processMasterDict(masterDictURL);
    }

    /**
     * Processes 5-letter words from a dictionary
     * @param masterDictURL the url of the dictionary.
     */
    private void processMasterDict(URL masterDictURL){
        this.dictionaryWords.clear();

        try (BufferedInputStream in = new BufferedInputStream(masterDictURL.openStream())) {
            scnr = new Scanner(in);
            // Match 5-letter capital words
            while(scnr.hasNext()){
                String target = scnr.next();
                Pattern validWordFilter = Pattern.compile("^[^A-Za-z0-9]*([A-Z]{5})[^A-Za-z0-9]*$");
                Matcher m = validWordFilter.matcher(target);
                if(m.matches()) {
                    dictionaryWords.add(m.group(1).toLowerCase());
                }
            }
        }catch(IOException e){
            System.out.println("ERROR: Cannot access the master dictionary URL.");
        }
    }

    /**
     * Gather all 5-letter words from a dictionary then gather all words in each novel and
     * add only the valid 5-letter words to the wordMap
     * @param url String of the URL for the dictionary or novel
     */
    public void processTextAtURL(URL url) {
        try (BufferedInputStream in = new BufferedInputStream(url.openStream())) {
            scnr = new Scanner(in, StandardCharsets.UTF_8);
            while (scnr.hasNext()) {
                this.totalWords++;

                // Removes the leading and trailing white spaces.
                // Referred the Java API documentations.
                String word = scnr.next();
                Pattern validWordFilter = Pattern.compile("^[^A-Za-z0-9]*([a-z]{5})[^A-Za-z0-9]*$");
                Matcher m = validWordFilter.matcher(word);

                if (m.matches()) {
                    String trimedWord = m.group(1);
                    // Check if the word is in the dictionary.
                    if (isWordValid(trimedWord)) {
                        // If the wordMap does not contain the word, add it. Else increase the frequency of the word by 1
                        if (wordMap.containsKey(trimedWord)) {
                            wordMap.put(trimedWord, wordMap.get(trimedWord) + 1);
                        } else {
                            wordMap.put(trimedWord, 1);
                            this.totalUniqueWords++;
                        }
                        totalGoodWordsKept++;
                    } else {
                        // The word was a valid word, but it was not a 5-letter word in the dictionary.
                            totalGoodWordsDiscarded++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: No resources with the given URL");
        }
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
        final int PRINT_MAX = 20;

        System.out.println("Total number of words processed: " + totalWords);
        System.out.println("Total good words but wrong length or was not in the dictionary: " + totalGoodWordsDiscarded);
        System.out.println("Total number of words kept: " + totalGoodWordsKept);
        System.out.println("Number of unique words: " + totalUniqueWords);
        System.out.println("Top " + PRINT_MAX + " most frequently occurring words");

        // Sort wordMap then print the 20 most frequently occurring words
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordMap.entrySet());
        sortEntries(entries, (entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));
        for (int i = 0; i < PRINT_MAX; i++) {
            System.out.println(entries.get(i).getKey() + " : " + entries.get(i).getValue());
        }
    }

    /**
     * Sorts the map of words based on frequency
     * @param entries List of Map entries for the words and their counts
     * @param comp the comparator
     */
    private void sortEntries(List<Map.Entry<String, Integer>> entries, Comparator<Map.Entry<String, Integer>> comp){
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
     * @return a Set with all the valid 5-letter words in the master dictionary.
     */
    public Set<String> getDictionaryWords(){
        return dictionaryWords;
    }

    /**
     * Write all words that occur more than once to the designated file
     * @param filename name of the file to write the words to
     */
    public void writeListOfWords(String filename) {
        try (PrintStream out = new PrintStream(filename)) {
            for (String key : wordMap.keySet()) {
                if (wordMap.get(key) > 0) {
                    out.println(key);
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("ERROR: Cannot write the words into the file.");
        }
    }
}