package org.tsugu_eric;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link WordDictionary} class
 */
class WordDictionaryTest {

    /** The WordDictionary object */
    private WordDictionary wd;

    /** The output file */
    private File outputFile;

    /** The filename of the test output file */
    private final String FILE_NAME = "testWords.txt";

    /** The default System.in */
    private InputStream defaultConsoleStream;

    /** Pseudo input used to enter "n". */
    private String pseudoInput = "n\n";


    /**
     * Initializes the {@link WordDictionary}
     * Replaces the System.in into the psuedo-InputStream
     */
    @BeforeEach
    void setup(){
        defaultConsoleStream = System.in;
        System.setIn(new ByteArrayInputStream(pseudoInput.getBytes()));

        wd = new WordDictionary(FILE_NAME);
        outputFile = new File(wd.wordFile);
    }

    /**
     * Deletes the file generated and resets the System.in
     */
    @AfterEach
    void tearDown() {
        outputFile.delete();
        System.setIn(defaultConsoleStream);
    }

    /**
     * Checks if readWords() and its helper method reads the words.txt correctly
     * @throws FileNotFoundException if words.txt was unable to be written
     */
    @Test
    @DisplayName("Test readWords() with words.txt")
    void parseWords() throws FileNotFoundException{
        String testWords[] = {"abhor", "abide", "abode", "about", "above",
                "abuse", "abyss", "actor", "acute", "adapt"
        };

        // Generates the words.text with above ten words
        PrintStream out = new PrintStream(outputFile);
        for (String word : testWords) {
            out.println(word);
        }
        out.close();

        // Read those ten words
        wd.readWords();

        // Referred Java API documentation
        String[] actualWords = wd.getWordSet().toArray(new String[0]);

        assertArrayEquals(testWords, actualWords);
    }

    /**
     * Checks if readWords() and its helper method generates the words correctly
     */
    @Test
    @DisplayName("Test readWords() without words.txt")
    void generateWords() {
        assertEquals(0, wd.getWordSet().size());
        wd.readWords();
        assertNotEquals(0,wd.getWordSet().size());
    }

    /**
     * Checks if the generated wordSet contains the commonly found words
     * and does not contain invalid words.
     */
    @Test
    void isWordInSet() {
        generateWords();

        // These words should be in the final word set
        String[] commonWords = {"could", "which", "among", "shine", "think"};

        // Non 5-letter words and invalid English words.
        String[] invalidWords = {"ants", "aiaia", "thinking", "(@_@)", "", "bucknell"};

        for(String word : commonWords){
            assertTrue(wd.isWordInSet(word));
        }

        for(String word : invalidWords){
            assertFalse(wd.isWordInSet(word));
        }
    }

    /**
     * Checks if the random word returns the word that is contained in the wordSet.
     */
    @Test
    void getRandomWord() {
        generateWords();

        final int NUM_TESTS = 100;
        Set<String> wordSet = wd.getWordSet();

        for(int i = 0; i < NUM_TESTS; i++){
            String word = wd.getRandomWord();
            assertTrue(wordSet.contains(word));
        }

    }
}