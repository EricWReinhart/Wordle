/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Tsugunobu Miyake
 * Section: 02 - 11am
 * Date: 10/7/2022
 * Time: 11:10
 *
 * Project: csci205_hw
 * Package: org.tsugu_eric
 * Class: GuessEvaluator
 *
 * Description: Compares guessed words to the secret words and
 *              generates the string that represents how close the guess is to the secret word.
 *
 * ****************************************
 */
package org.tsugu_eric;

import java.util.*;

/**
 * Compares guessed words to the secret words and
 * generates the string that represents how close the guess is to the secret word.
 */
public class GuessEvaluator {
    /** The length of the word to be used in the game */
    public static final int WORD_LENGTH = 5;

    /** Character that represents that the given letter is not in the secret word. */
    public static final char WRONG_POSITION = '+';

    /** Character that represents that the given letter is in the secret word but wrong position. */
    public static final char CORRECT_POSITION = '*';

    /** Character that represents that the given letter is in the secret word and correct position. */
    public static final char WRONG_LETTER = '-';

    /** Secret word to be guessed by the player */
    private String secretWord;

    /** The guess made by the player */
    private String currentGuess;

    /**
     * Constructor of the Guess Evaluator
     */
    public GuessEvaluator(){
        this.secretWord = "";
        this.currentGuess = "";
    }

    /**
     * Setter method for the secretWord
     * @param secretWord the secret word to be guessed
     * @throws IllegalArgumentException if the secretWord is not a five-letter word.
     */
    public void setSecretWord(String secretWord) {
        if(secretWord.length() != WORD_LENGTH)
            throw new IllegalArgumentException("ERROR: Secret word must be " + WORD_LENGTH + " letter words.");

        this.secretWord = secretWord.toLowerCase();
    }

    /**
     * Analyze the guess input. Return the string that describes how close the guess is to the secret word.
     * '*' describes that the character is exactly at that position in the secret word.
     * '+' describes that the character is in the secret word but at different position.
     * '-' describes that the character is not in the secret word.
     * Example:
     *      Secret word: "super"
     *      Guess:       "breed"
     *      Output:      "-+-*-"
     * @param guess The words to be guessed
     * @return The string that shows how close the guess is to the secret word
     * @throws IllegalArgumentException if the guess is not a five-letter word.
     */
    public String analyzeGuess(String guess){
        if(guess.length() != WORD_LENGTH)
            throw new IllegalArgumentException("ERROR: Guess must be " + WORD_LENGTH + " letter words.");

        this.currentGuess = guess.toLowerCase();

        List<Character> secretLetters = stringToList(this.secretWord);
        char[] guessEval = new char[5];

        // Check if the character is in the secret word and it matches.
        for(int i = 0; i < 5; i++){
            char guessLetter = this.currentGuess.charAt(i);
            if(guessLetter == this.secretWord.charAt(i)){
                guessEval[i] = CORRECT_POSITION;
                secretLetters.remove((Character) guessLetter);
            }
        }

        // Checks the rest of the words.
        for(int i = 0; i < 5; i++){
            char guessLetter = this.currentGuess.charAt(i);
            if(guessEval[i] != '*') {
                // The character is in the secret word but in a different position
                if (secretLetters.contains(guessLetter)) {
                    guessEval[i] = WRONG_POSITION;
                    secretLetters.remove((Character) guessLetter);
                }
                // The character is not in the secret word.
                else {
                    guessEval[i] = WRONG_LETTER;
                }
            }
        }

        return new String(guessEval);
    }

    /**
     * Helper method that returns a set of characters that is used in the string
     * @param str The string to be analyzed
     * @return the set of characters that is used in the string.
     */
    private List<Character> stringToList(String str){
        List<Character> charList = new LinkedList<>();
        for(int i = 0; i < str.length(); i++) {
            charList.add(str.charAt(i));
        }
        return charList;
    }
}