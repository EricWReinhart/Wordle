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
 * Description: Testing for the Wordle game.
 * ****************************************
 */

package org.tsugu_eric;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    private Wordle wordleGame;

    @BeforeEach
    void setUp() {
        wordleGame = new Wordle();
        wordleGame.initNewGame();

        //TODO: use getter methods to test methods / variables at each stage
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void initNewGame() {
        String secretWord = wordleGame.getSecretWord();



    }

    @Test
    void playNextTurn() {
        // getters for state / guess number / getLastGuess
        // Simulate making a few guesses and compare it to secretword
        // Does guessNumber change properly?
        // state of the game? new game / in progress / win / lose
    }

    @Test
    void isGameOver() {
        // Initially the game is not over
        //assertTrue();
    }
}