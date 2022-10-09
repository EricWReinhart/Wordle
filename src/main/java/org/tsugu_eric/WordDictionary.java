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
    public static void main(String[] args) {
        // TODO: REMOVE MAIN - temporary testing only
        new WordDictionary("words.txt");
        System.out.println(wordSet);

        // Test WordDictionary - wordList is initially 0 but is greater than 0 after reading the file
            // assertTrue(wordList.size() == 0);
            // WordDictionary("words.txt");
            // assertTrue(wordList.size() > 0);

            // WordDictionary("wrongfile.txt");
            // How can I check that this causes a FileNotFoundException?
                // Maybe add return false under catch then: assertFalse(WordDictionary("wrongfile.txt"));

        // Test isWordInSet - test if a valid word is included and an invalid word is excluded
            //assertTrue(isWordInSet("vague"));
            //assertFalse(isWordInSet("")); // empty or wrong-letters or invalid word

        // Test addWords - add the words in the list to the wordSet then check if it contains the word or not
            // List<String> wordList = Arrays.asList("apple", "mango", "chair");
            // addWords(wordList);
            // assertTrue(wordSet.contains("mango");
            // assertFalse(wordSet.contains("fruit");

        // Test getRandomWord - generate a random word and check that the word is added to the wordSet correctly
            // String randomWord = getRandomWord();
            // assertTrue(wordSet.contains(randomWord);

    }
    /** List containing the URLs of the novels*/
    // TODO: Should the novel URLs go here instead of in Textprocessor?
    public static String[] LIST_OF_TEXT_URLS;

    private static final Set<String> wordSet = new HashSet<>();

    /**
     * Read in the filename and store the words in a set
     * @param filename name of the file to be read from
     */
    public WordDictionary(String filename) {
        try(Scanner scnr = new Scanner(new File(filename))) {
            while(scnr.hasNextLine()) {
                wordSet.add(scnr.nextLine());
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    // TODO: not sure when to use this method
    public void generateNewWordSet(URL url) {

    }

    // TODO: not sure when to use this method
    /**
     * Add news words from wordList to the wordSet
     * @param wordList list of words
     */
    public void addWords(List<String> wordList) {
        wordSet.addAll(wordList);
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
}


