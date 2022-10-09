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
 * Description: Controls the flow of the game of Wordle.
 * ****************************************
 */
package org.tsugu_eric;

import java.util.Scanner;

/** Different states that the game can be in */
enum GameState {
    NEW_GAME,
    GAME_IN_PROGRESS,
    GAME_WINNER,
    GAME_LOSER
}

public class Wordle {
    /** Minimum length of words */
    public static int MIN_WORD_LENGTH;
    /** Maximum length of words */
    public static int MAX_WORD_LENGTH;
    /** Number of guesses */
    private int guessNumber;
    /** Secret word to be guessed */
    private String secretWord;
    /** Last guess by the user */
    private String lastGuess;
    /** Name of the output file */
    private final String OUTPUT_FILE = "words.txt";
    /** Current state of the game */
    private GameState state;
    /** WordDictionary object */
    WordDictionary wordDict;
    /** GuessEvaluator object */
    GuessEvaluator guessEval;
    /** Scanner object */
    private Scanner scnr;

    public Wordle() {
        // Create a WordDictionary object with the words from the specified output file
        this.wordDict = new WordDictionary(OUTPUT_FILE);

        // Print setup messages about the word dataset
            //introductionMessages();

        // Don't need this b/c playNextTurn() initializes a new game at the start
            // Initialize a new game
            //initNewGame();

        // Start the game
        playNextTurn();
    }

    /**
     * Initialize a new game of Wordle
     */
    public void initNewGame() {
        this.guessEval = new GuessEvaluator();
        this.guessNumber = 0;
        this.lastGuess = null;
        this.secretWord = wordDict.getRandomWord();
        this.guessEval.setSecretWord(secretWord);
        this.state = GameState.NEW_GAME;
    }

    /**
     * Welcome the user and print information about the creation of the word dataset
     */
    public void introductionMessages() {
        System.out.println("Welcome to Wordle!");

        // TODO: Choose: Move print statements to where they actually happen
        // Check if words.txt exists
        // if (not exists) {System.out.println(OUTPUT_FILE + ": NOT FOUND! Generating a new set of words.")}
                // wordDict.generateNewWordSet
                    // gotta implement this ^^
                    // add EVERY word into this set

        System.out.println("Reading master word set from Webster's Unabridged Dictionary.");

        System.out.println("Finding common words from novels:" +
                "\n- Reading in Pride and Prejudice by Jane Austen......done" +
                "\n- Reading in Frankenstein by Mary Shelley......done" +
                "\n- Reading in The Scarlet Letter by Nathaniel Hawthorne......done" +
                "\n- Reading in Alice in Wonderland by Lewis Carroll......done" +
                "\n- Reading in The Scarlet Letter by Franz Kafka......done");
            // Hardcoded for now, can make it efficient later if we want to

        //System.out.println("Keeping " + wordDict.getWordSet.size() + " valid words for the game...");
            // Add getter method for wordSet ^^

        System.out.println("Storing word dataset as words.txt...");


        System.out.println("READY!");
    }

    /**
     * Play a new game of Wordle. At the end, ask the user if they would like to play again.
     */
    public void playNextTurn() {
        String guess;
        String guessAnalysis;
        int validWordLength = 5;
        int numOfTurns = 6;
        String playAgain = "";
        System.out.print("Ready to play Wordle! You have 6 guesses.");

        // Initialize and play a new game. At the end, ask the user to play again.
        do {
            initNewGame();
            while (!isGameOver()) { //  maybe use GameState.GAME_IN_PROGRESS?
                // TODO: add string formatting if needed
                System.out.print("Guess " + this.guessNumber + ": ");
                guess = scnr.nextLine().toLowerCase();

                // Check that the guess is only letters, correct length, and in the valid word list
                if (!guess.matches("[a-zA-Z]+")) {
                    System.out.println("Guess must only have letters.");
                } else if (guess.length() != validWordLength) {
                    System.out.println("Guess must be 5-letters long.");
                } else if (!this.wordDict.isWordInSet(guess)) {
                    System.out.println("Guess is not a valid word.");
                }

                // If the guess is valid and there are turns left, then analyze it and print the results
                else if (this.guessNumber < numOfTurns) {
                    this.guessNumber++;
                    this.lastGuess = guess;
                    System.out.println(guess.toUpperCase());
                    guessAnalysis = this.guessEval.analyzeGuess(guess);
                    System.out.print("   -->    " + guessAnalysis + "   ");

                    // If the guess matches the secret word, print a winning message, otherwise print the remaining guesses.
                    if (guess.equals(this.secretWord)) {
                        System.out.println("YOU WON! You guessed the word in " + this.guessNumber + " turns!");
                        this.state = GameState.GAME_WINNER;
                        System.out.println("Would you like to play again? [Y/N]: ");
                        playAgain = scnr.nextLine();
                    } else {
                        System.out.println("Try again. " + this.guessNumber + " guesses left.");
                    }

                }

                // If the word is not guessed after the maximum number of turns, the user loses
                else {
                    System.out.println("You lost! The word was " + this.secretWord);
                    this.state = GameState.GAME_LOSER;
                    System.out.println("Would you like to play again? [Y/N]: ");
                    playAgain = scnr.nextLine();
                }
            }

        } while(playAgain.equalsIgnoreCase("Y"));
        // TODO: only accept N to exit? or just anything that's not Y? (easier to do this)
        System.out.print("Goodbye!");
    }

    public boolean isGameOver() {
        return state == GameState.GAME_WINNER || state == GameState.GAME_LOSER;
    }

}
