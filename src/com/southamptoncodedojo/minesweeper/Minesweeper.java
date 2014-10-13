package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.players.*;
import com.southamptoncodedojo.minesweeper.ui.CommandLineUI;

/**
 * Entry point for Minesweeper
 *
 * Run this with ./run.sh
 */
public class Minesweeper {
    public static void main(String[] args) {

        // Configure the game
        int MAP_SIZE = 16;
        int NUMBER_OF_MINES = 40;

        // This is used when displaying the game so that exceptions don't crash out
        // Don't use this when developing for better debugging
        boolean SUPPRESS_EXCEPTIONS = false;

        // For Text output
        int TEXT_WIDTH = 170; // We will wrap text longer than this to ensure it fits
        long ROUND_DELAY = 500; // Use this to slow down the game to watch it as it progresses



        Player[] players = new Player[]{new CornerPlayer(), new UltraSuperPlayer()};

        Game game = new Game(MAP_SIZE, NUMBER_OF_MINES, players);
        // if, while testing, you want to configure the map manually, use:
        // Map m = new TestMap(MAP_SIZE, new Coordinate[]{new Coordinate(9, 1), new Coordinate(2,2), new Coordinate(6,2), new Coordinate(0,3), new Coordinate(1, 5), new Coordinate(6,6), new Coordinate(3,7), new Coordinate(7,8), new Coordinate(1,9), new Coordinate(8,9)});
        // game = new Game(m, players);
        // (this specific example will have a single mine at 5,5

        // Then use the CommandLineUI to play through an entire game quickly
        new CommandLineUI(game, TEXT_WIDTH, ROUND_DELAY, SUPPRESS_EXCEPTIONS).start();
    }
}
