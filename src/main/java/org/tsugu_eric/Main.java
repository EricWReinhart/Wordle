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
 * Class: Main
 *
 * Description: Main program for running a game of Wordle.
 * ****************************************
 */
package org.tsugu_eric;

/**
 * A class that launches and plays Wordle game
 */
public class Main {

    /**
     * Launches and runs the Wordle game
     * @param args command line argument
     */
    public static void main(String[] args) {
        Wordle wordleGame = new Wordle();
        wordleGame.play();
    }
}
