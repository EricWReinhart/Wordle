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
 * Description: Read in words.txt, store them, and provide random words
 *              for the game. Also check for valid words being used as guesses
 *
 * ****************************************
 */
package org.tsugu_eric;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Set of unique valid words. Reads in words.txt, stores them, and
 * provide random words for the game. Check for valid words being used
 * as guesses
 */
public class WordDictionary {
    public String[] LIST_OF_TEXT_URLS;
    private Set<String> wordSet;
    private Scanner scnr;

    // call textprocessor to get returned list
        // TODO: make test file too
    /**
     * //TODO: finish javadoc, description / exception
     * @param filename name of the file to be read from
     */
    public WordDictionary(String filename) throws IOException {
        // filename is always words.txt

        // read in each uppercase 5-letter word
        //scnr = new Scanner();

        // Access Webster's dictionary and sift through all uppercase 5-letter
        String webstersDictionary = "https://www.gutenberg.org/cache/epub/29765/pg29765.txt";
        URL webstersURL = new URL(webstersDictionary);
        BufferedInputStream in = new BufferedInputStream(webstersURL.openStream());



        // add it to filename at the end
    }

    public void generateNewWordSet(URL url) {

    }

    /**
     * Add news words from wordList to the wordSet
     * @param wordList list of words
     */
    public void addWords(List<String> wordList) {

    }


    /**
     * If the word is in the set, return true, otherwise false
     * @param word word that will be checked in the set
     * @return true if the word is in the set, otherwise false
     */
    public boolean isWordInSet(String word) {
        return true;
    }

    // TODO: get random word from set of words
    public String getRandomWord() {

        return "";
    }

}


