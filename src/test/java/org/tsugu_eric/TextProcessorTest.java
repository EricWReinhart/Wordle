package org.tsugu_eric;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link TextProcessor} class
 */
class TextProcessorTest {
    /** Target word length in Wordle. */
    private static final int WORD_LENGTH = 5;
    private static final String DICT_URL = "https://www.gutenberg.org/cache/epub/29765/pg29765.txt";
    private static final String NOVEL_URL1 = "https://www.gutenberg.org/files/1342/1342-0.txt";
    private static final String NOVEL_URL2 = "https://www.gutenberg.org/files/84/84-0.txt";

    /** The TextProcessor class */
    private TextProcessor tp;

    /**
     * Initializes the {@link TextProcessor}
     * @throws MalformedURLException if the DICT_URL is an invalid URL
     */
    @BeforeEach
    void setup() throws MalformedURLException {
        URL masterURL = new URL(DICT_URL);
        tp = new TextProcessor(masterURL);
    }

    /**
     * Checks if the TextProcessor read valid words after parsing the Dictionary
     */
    @Test
    @DisplayName("Checks if the TextProcessor read valid words after parsing the Dictionary")
    void getDictionaryWords(){
        final int MAX_TEST_LENGTH = 1000;
        Set<String> wordsSet = tp.getDictionaryWords();

        // There must be at least one 5-letter words
        assertNotEquals(0, wordsSet.size());

        int wordCount = 0;
        for(String word : wordsSet){
            if(wordCount > MAX_TEST_LENGTH) // Stops the testing after reading first MAX_TEST_LENGTH words
                break;

            // Tests if the dictionary word is a valid 5-letter word in lowercase.
            assertTrue(word.matches("^[a-z]{5}$"));
            wordCount++;
        }
        System.out.println("Found " + wordsSet.size() + " 5-letter words in the dictionary.");
    }

    /**
     * Tests if the {@link TextProcessor} reads novels and parses 5-letter words correctly.
     * @throws MalformedURLException if the URL is invalid.
     */
    @Test
    void processTextAtURL() throws MalformedURLException{
        tp.processTextAtURL(new URL(NOVEL_URL1));
        Set<String> filteredWords1 = new TreeSet<>(tp.getSetOfWords());

        // There must be at least one word
        assertNotEquals(0, filteredWords1.size());

        tp.processTextAtURL(new URL(NOVEL_URL2));
        Set<String> filteredWords2 = new TreeSet<>(tp.getSetOfWords());

        // New set must contain more words.
        assertTrue(filteredWords1.size() < filteredWords2.size());
        tp.printReport();
    }

    /**
     * Tests if the {@link TextProcessor} reads novels and parses 5-letter words correctly.
     * @throws MalformedURLException if the URL is invalid.
     */
    @Test
    void writeListOfWords() throws MalformedURLException, FileNotFoundException {
        final String FILENAME = "build/TestListOfWords.txt";
        tp.processTextAtURL(new URL(NOVEL_URL1));
        tp.processTextAtURL(new URL(NOVEL_URL2));
        tp.writeListOfWords(FILENAME);
        InputStream in = new FileInputStream(FILENAME);
        Scanner sc = new Scanner(in);

        // Checks that all words in the file contains all
        while(sc.hasNextLine()){
            String word = sc.nextLine();
            assertEquals(WORD_LENGTH, word.length());
        }
    }
}