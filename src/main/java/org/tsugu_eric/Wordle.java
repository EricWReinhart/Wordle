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

/**
 * A class that simulates the Wordle game
 */
public class Wordle {
    /** Word length */
    private static final int WORD_LENGTH = 5;

    /** The number of turns allowed */
    private static final int NUM_TURNS = 6;

    /** Number of guesses */
    private int guessNumber;

    public String getSecretWord() {return secretWord;}

    /** Secret word to be guessed */
    private String secretWord;

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
        scnr = new Scanner(System.in);
        System.out.println("Welcome to Wordle!");

        // Create a WordDictionary object with the words from the specified output file
        this.wordDict = new WordDictionary(OUTPUT_FILE);
        this.wordDict.readWords();

        System.out.println("READY!");
    }

    /**
     * Initialize a new game of Wordle
     */
    public void initNewGame() {
        this.guessEval = new GuessEvaluator();
        this.guessNumber = 0;
        this.secretWord = wordDict.getRandomWord();
        this.guessEval.setSecretWord(secretWord);
        this.state = GameState.NEW_GAME;

        System.out.println(getSecretWord());

        System.out.println("Ready to play Wordle! You have 6 guesses.");
        System.out.println();
    }

    /**
     * Play a new game of Wordle. At the end, ask the user if they would like to play again.
     */
    public void play() {
        boolean repeat;
        // Initialize and play a new game. At the end, ask the user to play again.
        do {
            initNewGame();
            playOneGame();
            repeat = askForAnotherGame();
        } while(repeat);

        System.out.print("Goodbye!");
    }

    /**
     * Asks user if they want to play another Wordle game or not.
     * @return if the user want to play another Wordle game or not.
     */
    private boolean askForAnotherGame(){
        String playAgain = "";
        boolean validInput = false;

        // Validate the input
        while(!validInput){
            System.out.print("Would you like to play again? [Y/N]: ");
            playAgain = scnr.nextLine();
            validInput = playAgain.equalsIgnoreCase("y") || playAgain.equalsIgnoreCase("n");
            if(!validInput){
                System.out.println("Please type Y or N");
            }
        }

        // If the input is not y or Y, it must be n or N.
        return playAgain.equalsIgnoreCase("y");
    }

    /**
     * Play one game of the Wordle
     */
    private void playOneGame() {
        this.state = GameState.GAME_IN_PROGRESS;
        while (!isGameOver()) {
            this.guessNumber++;
            String guess = obtainValidGuess();

            // Analyze and print the analysis
            String guessAnalysis = this.guessEval.analyzeGuess(guess);
            System.out.print("   -->    " + guessAnalysis + "   ");

            giveCommentsForGuess(guess);
        }
    }

    /**
     * Prints the comment onto the console about the user's guess.
     * Changes the GameState if the user wins or loses the game
     * @param guess guess made by the user.
     */
    private void giveCommentsForGuess(String guess) {
        // If the guess matches the secret word, print a winning message, otherwise print the remaining guesses.
        if (guess.equals(this.secretWord)) {
            System.out.println("YOU WON! You guessed the word in " + this.guessNumber + " turn(s)!");
            this.state = GameState.GAME_WINNER;
        }
        
        // If the word is not guessed after the maximum number of turns, the user loses
        else if(this.guessNumber >= NUM_TURNS){
            System.out.println("You lost! The word was " + this.secretWord);
            this.state = GameState.GAME_LOSER;
        } 
        
        else {
            System.out.println("Try again. " + (NUM_TURNS - this.guessNumber) + " guesses left.");
        }
    }

    /**
     * Asks the user a guess for the Wordle game until the user inputs the valid input.
     * @return the valid user input.
     */
    private String obtainValidGuess(){
        boolean invalidInput = true;
        String guess = "";

        while(invalidInput){
            System.out.print("Guess  " + this.guessNumber + ": ");
            guess = this.scnr.nextLine().toLowerCase();

            // Check that the guess is only letters, correct length, and in the valid word list
            if (!guess.matches("[a-zA-Z]+")) {
                System.out.println("Guess must only have letters.");
            } else if (guess.length() != WORD_LENGTH) {
                System.out.println("Guess must be 5-letters long.");
            } else if (!this.wordDict.isWordInSet(guess)) {
                System.out.println(guess + " is not a valid word.");
            }else{
                invalidInput = false;
            }
        }

        return guess;
    }

    /**
     * @return true if the {@link GameState} is GAME_WINNER or GAME_LOSER, false otherwise.
     */
    public boolean isGameOver() {
        return state == GameState.GAME_WINNER || state == GameState.GAME_LOSER;
    }

}
