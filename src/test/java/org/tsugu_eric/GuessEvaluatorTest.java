/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Tsugunobu Miyake, Eric Reinhart
 * Section: 02 11am
 * Date: 10/7/22
 * Time: 11:16 AM
 *
 * Project: csci205_hw
 * Package: org.tsugu_eric
 * Class: GuessEvaluatorTest
 *
 * Description: Unit testing for GuessEvaluator
 * ****************************************
 */
package org.tsugu_eric;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link GuessEvaluator} class
 */
class GuessEvaluatorTest {
    /** The {@link GuessEvaluator} instance */
    private static GuessEvaluator evaluator;

    /**
     * 2-D array that describes the combinations of guess, secret word, and expected return.
     * The format is as follows.
     * {guessedWord, secretWord, expectedReturn}
     */
    private final String[][] expectedEvals = {
            {"breed", "super", "-+-*-"},
            {"brown", "query", "-+---"},
            {"FLYER", "query", "--+++"},
            {"REAMS", "QUERY", "++---"},
            {"fIEry", "QUERY", "--***"}, // Cases should not matter
            {"QUERY", "query", "*****"},
            {"eater", "wheel", "+--*-"},
            {"eater", "teeth", "+-++-"} // Repeated characters
    };

    /**
     * Initializes the {@link GuessEvaluator} instance
     */
    @BeforeEach
    void setUp() {
        evaluator = new GuessEvaluator();
    }

    /**
     * Tests the output of the analyses with several combinations of
     * secretWord and guessedWord.
     */
    @Test
    void analyzeGuess() {
        for(String[] analysisCombination : expectedEvals){
            String guessedWord = analysisCombination[0];
            String secretWord = analysisCombination[1];
            String expectedResult = analysisCombination[2];

            evaluator.setSecretWord(secretWord);
            assertEquals(expectedResult, evaluator.analyzeGuess(guessedWord));
        }
    }

    /**
     * Checks if an exception is thrown from setSecretWord method
     * when non-5 letter word is passed.
     */
    @Test
    @DisplayName("Checks if an exception is thrown from setSecretWord method with invalid inputs.")
    void setInvalidSecretWord(){
        assertThrows(IllegalArgumentException.class, () -> evaluator.setSecretWord(""));
        assertThrows(IllegalArgumentException.class, () -> evaluator.setSecretWord("abc"));
        assertThrows(IllegalArgumentException.class, () -> evaluator.setSecretWord("hello world"));
    }

    /**
     * Checks if an exception is thrown from analyzeGuess method
     * when non-5 letter word is passed.
     */
    @Test
    @DisplayName("Checks if an exception is thrown from analyzeGuess method with invalid inputs.")
    void setInvalidGuess(){
        evaluator.setSecretWord("japan");
        assertThrows(IllegalArgumentException.class, () -> evaluator.analyzeGuess(""));
        assertThrows(IllegalArgumentException.class, () -> evaluator.analyzeGuess("abc"));
        assertThrows(IllegalArgumentException.class, () -> evaluator.analyzeGuess("hello world"));
    }
}