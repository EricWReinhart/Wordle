/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Eric Reinhart, Tsugunobu Miyake
 * Section: 02 11am
 * Date: 10/7/22
 * Time: 11:16 AM
 *
 * Project: csci205_hw
 * Package: org.tsugu_eric
 * Class: WordDictionary
 *
 * Description: Read in words.txt, store the words, and provide random words
 *              for the game. Also check for valid words being used as guesses
 * ****************************************
 */
package org.tsugu_eric;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Set of unique valid words. Reads in words.txt, stores them, and
 * provide random words for the game. Check for valid words being used
 * as guesses
 */
public class WordDictionary {
    /** URLS to the novels that will be used to generate the word set */
    private TreeMap<String, String> novelsURL;

    /** URL address to master dictionary*/
    private final String DICT_URL = "https://www.gutenberg.org/cache/epub/29765/pg29765.txt";


    /** Set of words that are found in the master dictionary and novels. */
    private Set<String> wordSet;

    /** The filename of the words */
    public final String FILE_NAME = "words.txt";

    /** The Scanner to check user's inputs */
    private Scanner userInputScanner;


    /**
     * Read in the filename and store the words in a set
     */
    public WordDictionary() {
        wordSet = new TreeSet<>();
        userInputScanner = new Scanner(System.in);
        novelsURL = new TreeMap<>();

        storeNovelsURL();
    }

    /**
     * Stores the novel's URL with its title
     */
    private void storeNovelsURL(){
        novelsURL.put("Pride and Prejudice", "https://www.gutenberg.org/files/1342/1342-0.txt");
        novelsURL.put("Frankenstein", "https://www.gutenberg.org/files/84/84-0.txt");
        novelsURL.put("The Scarlet Letter", "https://www.gutenberg.org/files/25344/25344-0.txt");
        novelsURL.put("Alice in Wonderland", "https://www.gutenberg.org/files/11/11-0.txt");
        novelsURL.put("Metamorphosis", "https://www.gutenberg.org/files/5200/5200-0.txt");
    }

    /**
     * Read the words that are used for the Wordle game
     */
    public void readWords() {
        try(Scanner scnr = new Scanner(new File(FILE_NAME))) {
            String answer;
            do {
                System.out.print(FILE_NAME + ": FOUND. Generate a new file? [y/n]:");
                answer = userInputScanner.next();
                if(answer.equalsIgnoreCase("y")
                        || answer.equalsIgnoreCase("n")){
                    System.out.println("Please type y or n");
                }
            }while(!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));

            if(answer.equalsIgnoreCase("y"))
                readWordFile(scnr);
            else
                generateNewWordSet();
        }
        catch(FileNotFoundException e) {
            System.out.println(FILE_NAME + " NOT FOUND. Generating a new set of words.");
            generateNewWordSet();
        }
    }

    /**
     * Reads the words.txt file and add them into the wordSet
     * @param fileScanner
     */
    private void readWordFile(Scanner fileScanner) {
        while (fileScanner.hasNextLine()) {
            wordSet.add(fileScanner.nextLine());
        }
    }


    /**
     * Generate a new word set using the dictionary and novels
     * Write the result into the text file
     * Prints the status onto the console.
     */
    private void generateNewWordSet() {
        try {
            URL masterDictionary = new URL(DICT_URL);
            System.out.println("Reading master word set from Webster's Unabridged Dictionary.");
            TextProcessor tp = new TextProcessor(masterDictionary);

            System.out.println("Finding common words from novels:");
            for(String title : novelsURL.keySet()){
                URL novel = new URL(novelsURL.get(title));
                System.out.print(" - Reading in " + title + "......");
                tp.processTextAtURL(novel);
                System.out.println("done");
            }

            this.wordSet = tp.getSetOfWords();
            System.out.println("Keeping " + this.wordSet.size() + " valid words for the game.");

            tp.writeListOfWords(FILE_NAME);
            System.out.println("Storing word dataset as word.txt");

        }catch(MalformedURLException e){
            System.out.println("ERROR: The URL was invalid.");
        }

    }

    /**
     * If the word is in the set, return true, otherwise false
     * @param word word that will be checked in the set
     * @return true if the word is in the set, otherwise false
     */
    public boolean isWordInSet(String word) {
        return wordSet.contains(word.toLowerCase());
    }


    /**
     * Obtain a random integer then iterate through the wordSet and
     * return the corresponding word
     * @return a random word from the set of valid words
     */
    public String getRandomWord() {
        Random rand = new Random();
        int randWordIndex = rand.nextInt(wordSet.size());
        int i = 0;
        String word = "";
        Iterator<String> iter = wordSet.iterator();
        while (i != randWordIndex) {
            word = iter.next();
            i++;
        }
        return word;
    }

    public Set<String> getWordSet() {
        return wordSet;
    }
}


